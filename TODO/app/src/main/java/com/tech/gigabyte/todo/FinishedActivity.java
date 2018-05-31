package com.tech.gigabyte.todo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.tech.gigabyte.todo.adapter.FinishedAdapter;
import com.tech.gigabyte.todo.database.DatabaseHelper;
import com.tech.gigabyte.todo.dialogbox.DeleteAllDialog;
import com.tech.gigabyte.todo.model.FinishedEachRow;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 8/6/2017.
 * Activity/Tasks Finished
 */

public class FinishedActivity extends AppCompatActivity implements FinishedAdapter.ClickFinishedListener {
    RecyclerView FinishedRecycler;
    Toolbar finished_toolbar;
    ArrayList<FinishedEachRow> FinishedList;
    DatabaseHelper helper;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //Only be called on the given API level or higher.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);

        //ActionBarMenu -- finished_menu
        finished_toolbar = (Toolbar) findViewById(R.id.toolbar_finished);
        setSupportActionBar(finished_toolbar);
        FinishedRecycler = (RecyclerView) findViewById(R.id.recycler_finished);
        helper = new DatabaseHelper(this);

        //Setting the RecyclerView.LayoutManager that this RecyclerView will use.
        FinishedRecycler.setLayoutManager(new LinearLayoutManager(this));
        FinishedRecycler.setItemAnimator(new DefaultItemAnimator());
        FinishedList = helper.GetFinished();
        int size = FinishedList.size();
        if (size == 0) {
            Snackbar no_task = Snackbar.make(FinishedRecycler, "You Don't Have Any Completed Task", Snackbar.LENGTH_LONG);
            no_task.show();
        } else {
            FinishedAdapter finished_adapter = new FinishedAdapter(FinishedActivity.this, FinishedList);
            FinishedRecycler.setAdapter(finished_adapter);
            finished_adapter.setClickFinishedListener(this);
        }
    }

    @Override
    //Implementing Action Bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.finished_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //On Options Menu Click Event
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.Back:
                Intent intent = new Intent(FinishedActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.Delete_all:
                DeleteAllDialog deleteAll = new DeleteAllDialog();
                deleteAll.show(getFragmentManager(), "Delete All");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    //As Per Problem Requirement Single Click not Required
    public void OnSingleFinishedClick(int position) {
       /* int id=FinishedList.get(position).getId();
        DeleteBox deletealert=new DeleteBox();
        Bundle bundle=new Bundle();
        bundle.putInt("Id",id);
        bundle.putInt("Position",position);
        deletealert.setArguments(bundle);
        deletealert.show(getFragmentManager(),"Delete alert");*/
    }

    @Override
    //Performing LongClick for Deleting Completed List
    public void OnLongFinishedClick(int position) {
        int id = FinishedList.get(position).getId();
        long result = helper.DeleteFromFinished(id);
        if (result > 0) {
            Snackbar task_deleted = Snackbar.make(FinishedRecycler, "Event Deleted Successfully ", Snackbar.LENGTH_LONG);
            task_deleted.show();
        } else {
            Toast.makeText(FinishedActivity.this, " Not Deleted....Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(FinishedActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
