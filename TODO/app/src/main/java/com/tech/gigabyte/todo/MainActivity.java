package com.tech.gigabyte.todo;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.tech.gigabyte.todo.adapter.MyAdapter;
import com.tech.gigabyte.todo.database.DatabaseHelper;
import com.tech.gigabyte.todo.dialogbox.alertdialogWithInfo;
import com.tech.gigabyte.todo.dialogbox.InformationDialog;
import com.tech.gigabyte.todo.model.EachRow;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by GIGABYTE on 8/6/2017.
 * Activity/Tasks MainActivity
 */

//My Adapter Binding from an app-specific data set (Image)to views that are displayed within a RecyclerView.
public class MainActivity extends AppCompatActivity implements MyAdapter.ClickInterface {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<EachRow> arrayList;
    DatabaseHelper helper;
    String currentDate;
    private static final int NOTIFY_ID = 100;
    MyAdapter adapter;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting Toolbar -- menu_items
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        helper = new DatabaseHelper(MainActivity.this);

        //Providing View that represent items in a data set.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = helper.GetDetails();

        //Sorting the list
        Collections.sort(arrayList);
        adapter = new MyAdapter(MainActivity.this, arrayList);
        recyclerView.setAdapter(adapter);
        adapter.setClickInterface(this);
        currentDate = adapter.GetCurrentDate();

        // Save state
        Parcelable recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

        // Restore state
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
    }

    @Override
    //Creating OptionsMenu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //On OptionsMenu Click Event -- Action will performed .
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.Add_menu:
                InformationDialog dialog = new InformationDialog();
                dialog.show(getFragmentManager(), "Information");
                break;
            case R.id.completed_menu:
                Intent intent = new Intent(MainActivity.this, FinishedActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onclick(int position) {

        String title = arrayList.get(position).getTitle();
        String description = arrayList.get(position).getDescription();
        String date = arrayList.get(position).getDate();
        int id = arrayList.get(position).getId();
        alertdialogWithInfo alertdialogWithInfo = new alertdialogWithInfo();
        Bundle bundle = new Bundle();
        bundle.putString("Title", title);
        bundle.putString("Description", description);
        bundle.putString("Date", date);
        bundle.putInt("Id", id);
        alertdialogWithInfo.setArguments(bundle);
        alertdialogWithInfo.show(getFragmentManager(), "AlertDialogInfo");
    }

    @Override
    //On Long Click List/task will be moved to finished_activity
    public void OnLongClick(int position) {
        int id = arrayList.get(position).getId();
        long updateStatus = helper.UpdateStatus(id);
        if (updateStatus > 0) {
            Snackbar task_snack = Snackbar.make(recyclerView, "Task Finished Successfully", Snackbar.LENGTH_LONG);

            task_snack.show();
        } else {
            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * BackPressActivity will show alert dialog when user
     * presses back button.
     */

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exit = new AlertDialog.Builder(this);
        exit.setCancelable(false);
        exit.setIcon(R.mipmap.ic_launcher);
        exit.setTitle(" EXIT");
        exit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application

                finish();
            }
        });
        exit.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = exit.create();
        alert.show();
    }
}

