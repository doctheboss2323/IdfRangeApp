package com.example.idfrange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<User> list;

    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user=list.get(position);
        holder.name.setText(user.getName()+"\n");
        holder.score1.setText(user.getScore1());
        holder.score2.setText(user.getScore2());
        holder.score3.setText(user.getScore3());
        holder.score4.setText(user.getScore4());
        holder.score5.setText(user.getScore5());
        holder.score6.setText(user.getScore6());
        holder.score7.setText(user.getScore7());
        holder.score8.setText(user.getScore8());
        holder.score9.setText(user.getScore9());
        holder.score10.setText(user.getScore10());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,score1,score2,score3,score4,score5,score6,score7,score8,score9,score10;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.nametextview);
            score1=itemView.findViewById(R.id.score1textview);
            score2=itemView.findViewById(R.id.score2textview);
            score3=itemView.findViewById(R.id.score3textview);
            score4=itemView.findViewById(R.id.score4textview);
            score5=itemView.findViewById(R.id.score5textview);
            score6=itemView.findViewById(R.id.score6textview);
            score7=itemView.findViewById(R.id.score7textview);
            score8=itemView.findViewById(R.id.score8textview);
            score9=itemView.findViewById(R.id.score9textview);
            score10=itemView.findViewById(R.id.score10textview);

        }
    }
}
