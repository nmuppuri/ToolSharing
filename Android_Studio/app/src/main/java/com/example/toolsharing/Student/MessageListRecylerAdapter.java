package com.example.toolsharing.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toolsharing.PojoClasses.MyMessages_Pojo;
import com.example.toolsharing.R;

import java.util.ArrayList;

public class MessageListRecylerAdapter extends RecyclerView.Adapter<MessageListRecylerAdapter.Viewholder> {
    private ArrayList<MyMessages_Pojo> myMessagesPojo;
    private Context c;
    private View.OnClickListener onClickListener;

    public MessageListRecylerAdapter(ArrayList<MyMessages_Pojo> myMessagesPojo, Context c) {
        this.myMessagesPojo = myMessagesPojo;
        this.c = c;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_student_my_messages,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.sent_sid.setText(String.valueOf(myMessagesPojo.get(position).getFromStudentId()));
        holder.sent_time.setText(String.valueOf(myMessagesPojo.get(position).getSentDate()));
        if(myMessagesPojo.get(position).getMessage().length() > 20) {
            holder.msg_text.setText(myMessagesPojo.get(position).getMessage().substring(0, 19) + " ....");
        } else {
            holder.msg_text.setText(myMessagesPojo.get(position).getMessage());
        }

    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        onClickListener=itemClickListener;
    }

    @Override
    public int getItemCount() {
        return myMessagesPojo.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{


        TextView sent_sid, msg_text, sent_time;

        Viewholder(@NonNull View itemView) {
            super(itemView);

            sent_sid = itemView.findViewById(R.id.sent_sid);
            msg_text = itemView.findViewById(R.id.msg_text);
            sent_time = itemView.findViewById(R.id.sent_time);

            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
        }
    }
}
