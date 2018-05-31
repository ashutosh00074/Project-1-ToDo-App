package com.tech.gigabyte.todo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tech.gigabyte.todo.R;
import com.tech.gigabyte.todo.model.FinishedEachRow;

import java.util.ArrayList;

/**
 * Created by GIGABYTE on 8/6/2017.
 * <p>
 * Finished Adapter
 * Binding from an app-specific data set (Image)to views that are displayed within a RecyclerView.
 * A ViewHolder for an item view and metadata about its place within the RecyclerView.
 */

public class FinishedAdapter extends RecyclerView.Adapter<FinishedAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<FinishedEachRow> finished_arraylist;
    int image[] = {R.drawable.ic_task_done};


    private ClickFinishedListener clickFinishedListener;

    public interface ClickFinishedListener {
        void OnSingleFinishedClick(int position);

        void OnLongFinishedClick(int position);
    }

    public void setClickFinishedListener(ClickFinishedListener clickFinishedListener1) {
        this.clickFinishedListener = clickFinishedListener1;
    }

    public FinishedAdapter(Context context, ArrayList<FinishedEachRow> finished_arraylist) {
        this.context = context;
        this.finished_arraylist = finished_arraylist;
    }

    private void delete(int position) {
        finished_arraylist.remove(position);
        notifyItemRemoved(position);
    }

    //ViewGroup into which the new View will be added after it is bound to an adapter position.
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finished_card_layout, parent, false);
        return new MyViewHolder(view, context, finished_arraylist);
    }

    //ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // The position of the item within the adapter's data set.
        FinishedEachRow fin_each = finished_arraylist.get(position);
        holder.title.setText(fin_each.getTitle());
        holder.date.setText(fin_each.getDate());
        holder.id_fin.setText(String.valueOf(fin_each.getId()));
        holder.iv.setImageResource(R.drawable.ic_task_done);
    }

    //Getting total number of items in this adapter
    @Override
    public int getItemCount() {
        return finished_arraylist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView title, date, id_fin;
        ImageView iv;
        Context ctx;
        ArrayList<FinishedEachRow> FinisedEachRows;

        //Resizable-array implementation of the List interface. Implements all optional list operations, and permits all elements.
        MyViewHolder(View itemView, Context context, ArrayList<FinishedEachRow> finisedEachRows) {
            super(itemView);
            this.ctx = context;
            this.FinisedEachRows = finisedEachRows;
            title = (TextView) itemView.findViewById(R.id.textView_title);
            date = (TextView) itemView.findViewById(R.id.textView_date);
            id_fin = (TextView) itemView.findViewById(R.id.textview_id_fin);
            iv = (ImageView) itemView.findViewById(R.id.imageView_good_done);

            itemView.setOnLongClickListener(this);
        }

        //On Single Click NoAction will performed --> Deleting List (Completed Task)
        @Override
        public void onClick(View view) {
            clickFinishedListener.OnSingleFinishedClick(getAdapterPosition());
        }

        //On Long Click Action will performed --> Deleting List (Completed Task)
        @Override
        public boolean onLongClick(View view) {

            clickFinishedListener.OnLongFinishedClick(getAdapterPosition());
            delete(getAdapterPosition());
            return true;
        }
    }
}
