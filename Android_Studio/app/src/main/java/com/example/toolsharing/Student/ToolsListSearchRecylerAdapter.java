package com.example.toolsharing.Student;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toolsharing.PojoClasses.SearchToolsList_Pojo;
import com.example.toolsharing.R;

import java.util.ArrayList;
import java.util.List;

public class ToolsListSearchRecylerAdapter extends RecyclerView.Adapter<ToolsListSearchRecylerAdapter.Viewholder> implements Filterable {
    private List<SearchToolsList_Pojo> searchToolsList;
    private List<SearchToolsList_Pojo> searchToolsListFull;
    private Context c;
    private View.OnClickListener onClickListener;
    private View view;

    ToolsListSearchRecylerAdapter(List<SearchToolsList_Pojo> searchToolsList, Context c) {
        this.searchToolsList = searchToolsList;
        searchToolsListFull = new ArrayList<>(searchToolsList);
        this.c = c;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_student_tool_search,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        view.setBackgroundColor(Color.parseColor("#cc9c9b9a"));
        Glide.with(c).asBitmap().load(searchToolsList.get(position).getToolImg()).into(holder.tool_img);
        holder.tool_name.setText(searchToolsList.get(position).getToolName());

        holder.ts_rat.getProgressDrawable().setColorFilter(Color.parseColor("#FCB637"), PorterDuff.Mode.SRC_ATOP);
        holder.ts_rat.setRating(searchToolsList.get(position).getToolRating());
        if(String.valueOf(searchToolsList.get(position).getToolFavorite()).trim().equals("1")){
            holder.btn_add_fav.setBackgroundResource(R.drawable.ic_turned_in_black_24dp);
        }

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
        RatingBar ts_rat;

        Viewholder(@NonNull View itemView) {
            super(itemView);

            tool_img = itemView.findViewById(R.id.tool_image);
            tool_name = itemView.findViewById(R.id.tool_name);
            ts_sid = itemView.findViewById(R.id.ts_sid);
            ts_avail = itemView.findViewById(R.id.ts_avail);
            btn_add_fav = itemView.findViewById(R.id.btn_add_favorite);
            ts_rat = itemView.findViewById(R.id.ts_rat);

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
            List<SearchToolsList_Pojo> filterList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filterList.addAll(searchToolsListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(SearchToolsList_Pojo list_pojo : searchToolsListFull){
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
            searchToolsList.clear();
            searchToolsList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
