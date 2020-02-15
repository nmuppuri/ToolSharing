package com.example.toolsharing.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toolsharing.PojoClasses.ToolsList_Pojo;
import com.example.toolsharing.R;

import java.util.ArrayList;
import java.util.List;

public class ToolsListSearchRecylerAdapter extends RecyclerView.Adapter<ToolsListSearchRecylerAdapter.Viewholder> implements Filterable {
    private List<ToolsList_Pojo> toolsPojoArrayList;
    private List<ToolsList_Pojo> toolsPojoArrayListFull;
    private Context c;
    private View.OnClickListener onClickListener;

    ToolsListSearchRecylerAdapter(List<ToolsList_Pojo> toolsPojoArrayList, Context c) {
        this.toolsPojoArrayList = toolsPojoArrayList;
        toolsPojoArrayListFull = new ArrayList<>(toolsPojoArrayList);
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

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ToolsList_Pojo> filterList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filterList.addAll(toolsPojoArrayListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(ToolsList_Pojo list_pojo : toolsPojoArrayListFull){
                    if(list_pojo.getToolName().toLowerCase().contains(filterPattern)){
                        filterList.add(list_pojo);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            toolsPojoArrayList.clear();
            toolsPojoArrayList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
