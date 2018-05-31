package com.tech.gigabyte.todo.model;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by GIGABYTE on 8/6/2017.
 * <p>
 * This interface imposes a total ordering on the objects of each class that implements it.
 * This ordering is referred to as the class's natural ordering, and the class's compareTo method is referred to as its natural comparison method.
 * Lists (and arrays) of objects that implement this interface can be sorted automatically by Collections.sort (and Arrays.sort).
 * Objects that implement this interface can be used as keys in a sorted map or as elements in a sorted set, without the need to specify a comparator.
 */

public class EachRow implements Comparable<EachRow> {
    private String title, description, date;
    private int image;
    private int id;


    public EachRow(int id, String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.id = id;
    }

    public EachRow(String title) {
        this.title = title;
    }

    private Date convertDate(String date) {
        Date mydate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy", Locale.US);
            mydate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mydate;
    }


    private Date sendDate() {
        return convertDate(date);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    @Override
    public int compareTo(@NonNull EachRow eachRow) {
        return sendDate().compareTo(eachRow.sendDate());
    }
}
