package com.tech.gigabyte.todo.dialogbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.gigabyte.todo.MainActivity;
import com.tech.gigabyte.todo.R;
import com.tech.gigabyte.todo.database.DatabaseHelper;

/**
 * Created by GIGABYTE on 8/6/2017.
 * My Custom Alert Dialog for Modifying data or Updating Task .
 * Validation Has Applied .
 */


//A fragment that displays a dialog window
public class alertdialogWithInfo extends DialogFragment {
    EditText et_title, et_desc;
    DatabaseHelper dbHelper;
    TextView tv_date;
    Button changedate_btn, update_btn, cancel_btn;
    DatePicker datePicker;

    @Override
    //Return a new Dialog instance to be displayed by the Fragment.
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_withinfo, datePicker);
        builder.setView(view);
        Dialog dialog = builder.create();
        init(view);

        //Dialog will not close automatically until user click on cancle button.
        setCancelable(false);
        return dialog;
    }

    public void init(View view) {
        et_title = (EditText) view.findViewById(R.id.editText_title);
        et_desc = (EditText) view.findViewById(R.id.editText_description);
        tv_date = (TextView) view.findViewById(R.id.textView_date);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker_alert);
        dbHelper = new DatabaseHelper(getActivity());

        //Getting Bundle Arguments from MainActivity
        final String t = getArguments().getString("Title");
        final String desc = getArguments().getString("Description");
        final String date = getArguments().getString("Date");

        //Setting Bundle values in the Dialog

        et_title.setText(t);
        et_desc.setText(desc);
        tv_date.setText(date);

        //Setting Date ---> Modifying Date
        changedate_btn = (Button) view.findViewById(R.id.button_setdate);
        changedate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                if (day < 10 && month < 10) {
                    String date = "0" + day + "/0" + month + "/" + year;
                    tv_date.setText(date);
                } else if (day >= 10 && month < 10) {
                    String date = day + "/0" + month + "/" + year;
                    tv_date.setText(date);
                } else if (day < 10 && month >= 10) {
                    String date = "0" + day + "/" + month + "/" + year;
                    tv_date.setText(date);
                } else {
                    String date = day + "/" + month + "/" + year;
                    tv_date.setText(date);
                }

            }
        });
        update_btn = (Button) view.findViewById(R.id.button_update);

        //On Updating
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int id=position+1;
                final int id = getArguments().getInt("Id");
                Toast.makeText(getActivity(), "id" + id, Toast.LENGTH_LONG).show();

                //Values to be Updated
                final String U_title = et_title.getText().toString();
                final String U_desc = et_desc.getText().toString();
                final String U_date = tv_date.getText().toString();

                long update = dbHelper.UpdateRow(id, U_title, U_desc, U_date);
                if (update > 0) {
                    Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
                dismiss();
            }
        });

        //On Cancel Button Event
        cancel_btn = (Button) view.findViewById(R.id.button_cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Modification Aborted",Toast.LENGTH_SHORT).show();
                dismiss();
                dismiss();
            }
        });
    }
}
