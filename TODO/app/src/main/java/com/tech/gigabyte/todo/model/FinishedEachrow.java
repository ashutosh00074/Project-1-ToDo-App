package com.tech.gigabyte.todo.model;

/**
 * Created by GIGABYTE on 8/6/2017.
 * <p>
 * This interface imposes a total ordering on the objects of each class that implements it.
 * This ordering is referred to as the class's natural ordering, and the class's compareTo method is referred to as its natural comparison method.
 * Lists (and arrays) of objects that implement this interface can be sorted automatically by Collections.sort (and Arrays.sort).
 * Objects that implement this interface can be used as keys in a sorted map or as elements in a sorted set, without the need to specify a comparator.
 */

public class FinishedEachRow {
    private String title, date;
    private int id;

    public FinishedEachRow(int id, String title, String date) {
        this.title = title;
        this.date = date;
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
