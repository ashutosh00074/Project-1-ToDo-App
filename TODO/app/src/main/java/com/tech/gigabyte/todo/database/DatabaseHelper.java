package com.tech.gigabyte.todo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.tech.gigabyte.todo.model.EachRow;
import com.tech.gigabyte.todo.model.FinishedEachRow;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 8/6/2017.
 * <p>
 * My Database Helper Class
 * KEY_ID ---> INTEGER PRIMARY KEY
 * KEY_TITLE ---> TEXT
 * KEY_DESCRIPTION ---> TEXT
 * KEY_DATE ----> TEXT
 * KEY_STATUS ----> INTEGER
 */

public class DatabaseHelper {
    private SQLiteDatabase sqLiteDatabase;
    private ContentValues contentValues;
    private ArrayList<EachRow> eachRowArrayList;


    public DatabaseHelper(Context context) {
        Helper myhelper = new Helper(context);
        sqLiteDatabase = myhelper.getWritableDatabase();
        eachRowArrayList = new ArrayList<EachRow>();
        contentValues = new ContentValues();
    }

    //Insert Data into Database /(Add value to the set.)
    public long InsertInDB(String title, String description, String date) throws SQLException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Helper.KEY_TITLE, title);
        contentValues.put(Helper.KEY_DESCRIPTION, description);
        contentValues.put(Helper.KEY_DATE, date);
        contentValues.put(Helper.KEY_STATUS, 0);
        long result = sqLiteDatabase.insert(Helper.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    //Get all details of the table and Load into RecyclerView
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //element should only be called on the given API level or higher.
    public ArrayList<EachRow> GetDetails() {
        String[] columns = {Helper.KEY_ID, Helper.KEY_TITLE, Helper.KEY_DESCRIPTION, Helper.KEY_DATE};

        //Database Query.
        try (Cursor cursor = sqLiteDatabase.query(Helper.TABLE_NAME, columns, Helper.KEY_STATUS + "=?", new String[]{String.valueOf(0)}, null, null, null)) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Helper.KEY_ID));
                String title = cursor.getString(cursor.getColumnIndex(Helper.KEY_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(Helper.KEY_DESCRIPTION));
                String date = cursor.getString(cursor.getColumnIndex(Helper.KEY_DATE));

                EachRow eachRow = new EachRow(id, title, description, date);
                eachRowArrayList.add(eachRow);
            }
        }
        return eachRowArrayList;

    }

    //Updating Value in the Database
    public long UpdateRow(int id, String title, String desc, String date) {
        contentValues.put(Helper.KEY_TITLE, title);
        contentValues.put(Helper.KEY_DESCRIPTION, desc);
        contentValues.put(Helper.KEY_DATE, date);
        return (long) sqLiteDatabase.update(Helper.TABLE_NAME, contentValues, Helper.KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    //Updation Completed

    public long UpdateStatus(int id) {
        contentValues.put(Helper.KEY_STATUS, 1);
        return (long) sqLiteDatabase.update(Helper.TABLE_NAME, contentValues, Helper.KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //element should only be called on the given API level or higher.
    public ArrayList<FinishedEachRow> GetFinished() {
        ArrayList<FinishedEachRow> finishedEachRows = new ArrayList<FinishedEachRow>();
        String[] columns = {Helper.KEY_TITLE, Helper.KEY_DATE, Helper.KEY_ID};

        //On Update Database Query
        try (Cursor cursor = sqLiteDatabase.query(Helper.TABLE_NAME, columns, Helper.KEY_STATUS + "=?", new String[]{String.valueOf(1)}, null, null, null)) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(Helper.KEY_TITLE));
                String date = cursor.getString(cursor.getColumnIndex(Helper.KEY_DATE));
                int id = cursor.getInt(cursor.getColumnIndex(Helper.KEY_ID));
                FinishedEachRow finishedEachRow = new FinishedEachRow(id, title, date);
                finishedEachRows.add(finishedEachRow);
            }
        }
        return finishedEachRows;
    }

    //Delete Single item from list
    public long DeleteFromFinished(int id) {
        return (long) sqLiteDatabase.delete(Helper.TABLE_NAME, Helper.KEY_ID + "=?", new String[]{String.valueOf(id)});

    }

    //Delete All from Finished (activity_finished)
    public long deleteall() {
        return (long) sqLiteDatabase.delete(Helper.TABLE_NAME, Helper.KEY_STATUS + "=?", new String[]{String.valueOf(1)});
    }

    //Database creation and upgrade
    private static class Helper extends SQLiteOpenHelper {
        private static final String DB_NAME = "TODO_LIST.db";
        private static final String TABLE_NAME = "USER_LIST";
        private static final int VERSION = 1;
        private static final String KEY_ID = "ID";
        private static final String KEY_TITLE = "TITLE";
        private static final String KEY_DESCRIPTION = "DESCRIPTION";
        private static final String KEY_DATE = "DATE";
        private static final String KEY_STATUS = "STATUS";

        Helper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }


        @Override
        //Creation of Tables and the initial population of the Tables should happen.
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String create_table = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_DATE + " TEXT," + KEY_STATUS + " INTEGER);";
            sqLiteDatabase.execSQL(create_table);

        }

        @Override
        //Called when the database needs to be upgraded.
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
