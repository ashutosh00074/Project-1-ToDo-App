package com.tech.gigabyte.todo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tech.gigabyte.todo.R;
import com.tech.gigabyte.todo.model.EachRow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by GIGABYTE on 8/6/2017.
 * <p>
 * My Adapter
 * Binding from an app-specific data set (Image)to views that are displayed within a RecyclerView.
 * A ViewHolder for an item view and metadata about its place within the RecyclerView.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<EachRow> ArrayEach;
    private int[] image = {R.drawable.ic_task_done, R.drawable.ic_action_bad, R.drawable.ic_action_event};
    private ClickInterface clickInterface;

    public interface ClickInterface {
        void onclick(int position);

        void OnLongClick(int position);
    }

    private void MoveToFinished(int position) {
        ArrayEach.remove(position);
        notifyItemRemoved(position);
    }

    public void setClickInterface(final ClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    public MyAdapter(Context context, ArrayList<EachRow> ArrayEach) {
        this.context = context;
        this.ArrayEach = ArrayEach;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EachRow eachRow = ArrayEach.get(position);
        holder.date_main_heading.setText(eachRow.getDate());
        holder.title.setText(eachRow.getTitle());
        holder.description.setText(eachRow.getDescription());
        holder.date.setText(eachRow.getDate());
        holder.id_db.setText(String.valueOf(eachRow.getId()));
        holder.imageView.setImageResource(image[1]);
        String current = GetCurrentDate();
        if (current.equals( eachRow.getDate())) {
            holder.date_main_heading.setBackgroundColor(Color.parseColor("0xfff44336"));

        }
    }

    //DatePicker return year, month, date values
    public String GetCurrentDate() {
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/mm/yyyy", Locale.US);
        Date todayDate = new Date();
        return (currentDate.format(todayDate));
    }

    @Override
    public int getItemCount() {
        return ArrayEach.size();
    }

    //Existing items in the data set are still considered up to date and will not be rebound
    public void additem(EachRow eachRow, int position) {
        ArrayEach.add(eachRow);
        notifyItemInserted(position);
    }


    //OnLongClick Operation/Action Will Performed.
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        Context context;
        TextView date_main_heading;
        TextView title;
        TextView description;
        TextView date;
        TextView id_db;
        TextView importance;
        ImageView imageView;
        CardView cardView;

        //Defining View Using findViewById
        MyViewHolder(View itemView, Context ctx) {
            super(itemView);
            this.context = ctx;
            date_main_heading = (TextView) itemView.findViewById(R.id.date_main_heading);
            title = (TextView) itemView.findViewById(R.id.title_card);
            description = (TextView) itemView.findViewById(R.id.description_card);
            date = (TextView) itemView.findViewById(R.id.date_card);
            id_db = (TextView) itemView.findViewById(R.id.card_id_db);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            cardView = (CardView) itemView.findViewById(R.id.cardview);

            //Setting LongClickListener
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickInterface.onclick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            clickInterface.OnLongClick(getAdapterPosition());
            MoveToFinished(getAdapterPosition());
            return true;
        }
    }

}
