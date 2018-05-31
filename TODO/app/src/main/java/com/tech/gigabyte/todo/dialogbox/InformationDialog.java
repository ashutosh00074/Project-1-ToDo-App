package com.tech.gigabyte.todo.dialogbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * My Custom Alert Dialog for Taking input from user .
 * Validation Has Applied Using TextInputLayout for EditText.
 */

//A fragment that displays a dialog window.
public class InformationDialog extends DialogFragment {

    TextInputLayout til_title, til_des, til_date;
    EditText title, description;
    DatePicker datePicker;
    TextView textView;
    Button btn_save, btn_cancel, setdate;
    DatabaseHelper helper;

    //Return a new Dialog instance to be displayed by the Fragment.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.informationdialog, datePicker);
        builder.setView(view);
        init(view);
        Dialog dialog = builder.create();

        //Dialog will not close automatically until user click on cancle button.
        setCancelable(false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void init(View view) {
        helper = new DatabaseHelper(getActivity());

        //Defining items using findViewById
        til_title = (TextInputLayout) view.findViewById(R.id.til_title);
        til_des = (TextInputLayout) view.findViewById(R.id.til_des);
        title = (EditText) view.findViewById(R.id.editText_title);
        description = (EditText) view.findViewById(R.id.editText_description);
        textView = (TextView) view.findViewById(R.id.textview_dateshow);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        setdate = (Button) view.findViewById(R.id.set_date);

        //Setting Schedule Date
        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                int monthnew = month + 1;
                if (day < 10 && monthnew < 10) {
                    String date = "0" + day + "/0" + monthnew + "/" + year;
                    textView.setText(date);
                } else if (day >= 10 && monthnew < 10) {
                    String date = day + "/0" + monthnew + "/" + year;
                    textView.setText(date);
                } else if (day < 10 && monthnew >= 10) {
                    String date = "0" + day + "/" + monthnew + "/" + year;
                    textView.setText(date);
                } else {
                    String date = day + "/" + monthnew + "/" + year;
                    textView.setText(date);
                }
            }
        });
        btn_save = (Button) view.findViewById(R.id.save_button);
        btn_cancel = (Button) view.findViewById(R.id.cancel_button);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Aborted",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        //Saving Data -- Given by User -- To Database (Database helper)
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Normal Field Validation
                if (title.getText().toString().trim().isEmpty()) {
                    til_title.setError(getString(R.string.til_title));
                } else if (description.getText().toString().trim().isEmpty()) {
                    til_des.setError(getString(R.string.til_des));
                } else if (textView.length() > 0) {
                    addValues();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("Title", title.getText().toString());
                    startActivity(intent);
                    dismiss();
                } else {
                    Snackbar date_snack = Snackbar.make(view, "Schedule Date Required", Snackbar.LENGTH_LONG);
                    date_snack.show();
                }

            }
        });
    }


    //Adding Values
    public long addValues() {
        String t = title.getText().toString();
        String desc = description.getText().toString();
        String txtDate = textView.getText().toString();
        long result = helper.InsertInDB(t, desc, txtDate);
        if (result > 0) {

            Toast.makeText(getActivity(),"Successfully saved",Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Something went wrong..Not saved", Toast.LENGTH_LONG).show();
        }
        return result;
    }


}
