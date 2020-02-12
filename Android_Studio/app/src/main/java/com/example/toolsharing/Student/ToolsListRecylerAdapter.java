package com.example.toolsharing.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toolsharing.PojoClasses.ToolsList_Pojo;
import com.example.toolsharing.R;

import java.util.ArrayList;

public class ToolsListRecylerAdapter extends RecyclerView.Adapter<ToolsListRecylerAdapter.Viewholder> {
    private ArrayList<ToolsList_Pojo> toolsPojoArrayList;
    private Context c;
    private View.OnClickListener onClickListener;

    public ToolsListRecylerAdapter(ArrayList<ToolsList_Pojo> toolsPojoArrayList, Context c) {
        this.toolsPojoArrayList = toolsPojoArrayList;
        this.c = c;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_student_tool_search,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Glide.with(c).asBitmap().load(toolsPojoArrayList.get(position).getToolImg()).into(holder.tool_img);
        holder.tool_name.setText(toolsPojoArrayList.get(position).getToolName());
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
        TextView tool_name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tool_img = itemView.findViewById(R.id.tool_image);
            tool_name = itemView.findViewById(R.id.tool_name);

            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);


        }
    }
}
