package com.tech.gigabyte.todo.dialogbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tech.gigabyte.todo.MainActivity;
import com.tech.gigabyte.todo.database.DatabaseHelper;


/**
 * Created by GIGABYTE on 8/6/2017.
 * Delete All List--> Completed List
 * Delete Dialog
 */

public class DeleteAllDialog extends DialogFragment {
    DatabaseHelper helper;

    //Creating a Dialog for deleting list from activity_finished
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        helper = new DatabaseHelper(getActivity());
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure you want to delete all Task?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                long result = helper.deleteall();
                if (result > 0) {
                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "You can't Delete Empty", Toast.LENGTH_LONG).show();
                }
            }
        });
        return builder.create();
    }
}
