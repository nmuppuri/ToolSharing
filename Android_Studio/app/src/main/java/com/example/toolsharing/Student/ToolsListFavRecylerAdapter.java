package com.example.toolsharing.Student;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toolsharing.PojoClasses.SearchToolsList_Pojo;
import com.example.toolsharing.R;

import java.util.List;

public class ToolsListFavRecylerAdapter extends RecyclerView.Adapter<ToolsListFavRecylerAdapter.Viewholder> {
    private List<SearchToolsList_Pojo> searchToolsList;
    private Context c;
    private View.OnClickListener onClickListener;
    private View view;

    ToolsListFavRecylerAdapter(List<SearchToolsList_Pojo> searchToolsList, Context c) {
        this.searchToolsList = searchToolsList;
        this.c = c;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_student_tool_fav,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        view.setBackgroundColor(Color.parseColor("#cc9c9b9a"));
        if(String.valueOf(searchToolsList.get(position).getToolFavorite()).trim().equals("1")){
            holder.btn_add_fav.setBackgroundResource(R.drawable.ic_turned_in_black_24dp);
            Glide.with(c).asBitmap().load(searchToolsList.get(position).getToolImg()).into(holder.tool_img);
            holder.tool_name.setText(searchToolsList.get(position).getToolName());

            if(String.valueOf(searchToolsList.get(position).getPostedStudentId()).trim().equals("0") || String.valueOf(searchToolsList.get(position).getToolAvailability()).trim().equals("0")){
                holder.ts_sid.setText("_ _");
                holder.ts_avail.setBackgroundResource(R.drawable.ic_highlight_off_black_24dp);
                System.out.println("URL name sid: " + searchToolsList.get(position).getToolName() + " : " + searchToolsList.get(position).getPostedStudentId());

                view.setFocusable(false);
            } else if(searchToolsList.get(position).getToolAvailableFromInDays() > 0){
                holder.ts_avail.setBackgroundResource(R.drawable.ic_remove_circle_black_24dp);
                holder.ts_sid.setText(String.valueOf(searchToolsList.get(position).getPostedStudentId()));
            } else{
                holder.ts_avail.setBackgroundResource(R.drawable.ic_check_circle_black_24dp);
                holder.ts_sid.setText(String.valueOf(searchToolsList.get(position).getPostedStudentId()));
            }
        }
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        onClickListener=itemClickListener;
    }

    @Override
    public int getItemCount() {
        return searchToolsList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{


        ImageView tool_img;
        TextView tool_name, ts_sid;
        Button ts_avail;
        ImageButton btn_add_fav;

        Viewholder(@NonNull View itemView) {
            super(itemView);

            tool_img = itemView.findViewById(R.id.tool_image_fav);
            tool_name = itemView.findViewById(R.id.tool_name_fav);
            ts_sid = itemView.findViewById(R.id.ts_sid_fav);
            ts_avail = itemView.findViewById(R.id.ts_avail_fav);
            btn_add_fav = itemView.findViewById(R.id.btn_add_favorite_fav);

            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);


        }
    }
}
