package com.example.toolsharing.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toolsharing.PojoClasses.ToolsList_Pojo;
import com.example.toolsharing.R;

import java.util.ArrayList;

public class ToolsListBorrowedRecylerAdapter extends RecyclerView.Adapter<ToolsListBorrowedRecylerAdapter.Viewholder> {
    private ArrayList<ToolsList_Pojo> toolsPojoArrayList;
    private Context c;
    private View.OnClickListener onClickListener;

    public ToolsListBorrowedRecylerAdapter(ArrayList<ToolsList_Pojo> toolsPojoArrayList, Context c) {
        this.toolsPojoArrayList = toolsPojoArrayList;
        this.c = c;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_student_borrowed_tool,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Glide.with(c).asBitmap().load(toolsPojoArrayList.get(position).getToolImg()).into(holder.tool_img);
        holder.tool_name.setText(toolsPojoArrayList.get(position).getToolName());
        holder.tool_return.setText("Return By: " + toolsPojoArrayList.get(position).getReturnDate());
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        onClickListener=itemClickListener;
    }

    @Override
    public int getItemCount() {
        return toolsPojoArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{


        ImageView tool_img;
        TextView tool_name, tool_return;
        Button btn_borr_return;

        Viewholder(@NonNull View itemView) {
            super(itemView);

            tool_img = itemView.findViewById(R.id.borr_tool_image);
            tool_name = itemView.findViewById(R.id.borr_tool_name);
            tool_return = itemView.findViewById(R.id.borr_return);


            btn_borr_return = itemView.findViewById(R.id.btn_borr_return);
            btn_borr_return.setTag(this);
            btn_borr_return.setOnClickListener(onClickListener);


        }
    }
}
