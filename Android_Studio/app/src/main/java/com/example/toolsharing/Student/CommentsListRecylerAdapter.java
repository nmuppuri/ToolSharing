package com.example.toolsharing.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toolsharing.PojoClasses.CommentsList_Pojo;
import com.example.toolsharing.R;

import java.util.ArrayList;

public class CommentsListRecylerAdapter extends RecyclerView.Adapter<CommentsListRecylerAdapter.Viewholder> {
    private ArrayList<CommentsList_Pojo> commentsList_pojos;
    private Context c;
    private View.OnClickListener onClickListener;

    public CommentsListRecylerAdapter(ArrayList<CommentsList_Pojo> commentsList_pojos, Context c) {
        this.commentsList_pojos = commentsList_pojos;
        this.c = c;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_student_tools_rat_coment,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.rsid.setText(String.valueOf(commentsList_pojos.get(position).getCommentedStudentId()));
        holder.rsid_rating.setRating(commentsList_pojos.get(position).getGivenRating());
        holder.rsid_comments.setText(commentsList_pojos.get(position).getComment());
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        onClickListener=itemClickListener;
    }

    @Override
    public int getItemCount() {
        return commentsList_pojos.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView rsid, rsid_comments;
        RatingBar rsid_rating;

        Viewholder(@NonNull View itemView) {
            super(itemView);

            rsid = itemView.findViewById(R.id.student_rat_id);
            rsid_comments = itemView.findViewById(R.id.rsid_comments);
            rsid_rating = itemView.findViewById(R.id.rsid_rating);

            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);


        }
    }
}
