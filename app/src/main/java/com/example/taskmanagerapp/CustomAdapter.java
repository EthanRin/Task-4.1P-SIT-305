package com.example.taskmanagerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList task_id, task_title, task_desc, task_date;
    Activity activity;

    CustomAdapter(Context context,
                   ArrayList task_id,
                   ArrayList task_title,
                   ArrayList task_desc,
                   ArrayList task_date,
                  Activity activity) {
        this.activity = activity;
        this.context = context;
        this.task_id = task_id;
        this.task_title = task_title;
        this.task_desc = task_desc;
        this.task_date = task_date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.taskTitles.setText(String.valueOf(task_title.get(position)));
        holder.taskDates.setText(String.valueOf(task_date.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(task_id.get(holder.getAdapterPosition())));
                intent.putExtra("title", String.valueOf(task_title.get(holder.getAdapterPosition())));
                intent.putExtra("date", String.valueOf(task_date.get(holder.getAdapterPosition())));
                intent.putExtra("desc", String.valueOf(task_desc.get(holder.getAdapterPosition())));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView taskTitles, taskDates;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskTitles = itemView.findViewById(R.id.task_Title);
            taskDates = itemView.findViewById(R.id.task_Date);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
