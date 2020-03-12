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
    private List<SearchToolsList_Pojo> getSearchToolsListFiltered;
    private Context c;
    private View.OnClickListener onClickListener;
    private View view;
    private SelectedTool selectedTool;

    public ToolsListSearchRecylerAdapter(List<SearchToolsList_Pojo> searchToolsList, SelectedTool selectedTool, Context c) {
        this.searchToolsList = searchToolsList;
        this.getSearchToolsListFiltered = searchToolsList;
        this.selectedTool = selectedTool;
        this.c = c;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_student_tool_search,parent,false);
        return new Viewholder(view);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    filterResults.count = getSearchToolsListFiltered.size();
                    filterResults.values = getSearchToolsListFiltered;
                } else{
                    String searchChar = charSequence.toString().toLowerCase();
                    List<SearchToolsList_Pojo> resultData = new ArrayList<>();

                    for(SearchToolsList_Pojo searchToolsList_pojo: getSearchToolsListFiltered){
                        if(searchToolsList_pojo.getPostedStudentId().toString().contains(searchChar) || searchToolsList_pojo.getToolName().toLowerCase().contains(searchChar)){
                            resultData.add(searchToolsList_pojo);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;

                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                searchToolsList = (List<SearchToolsList_Pojo>) filterResults.values;
                notifyDataSetChanged();

            }
        };

        return filter;
    }

    public interface SelectedTool{
        void selectedTool(SearchToolsList_Pojo searchToolsList_pojo);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        view.setBackgroundColor(Color.parseColor("#cc9c9b9a"));
        Glide.with(c).asBitmap().load(searchToolsList.get(position).getToolImg()).into(holder.tool_img);
        holder.tool_name.setText(searchToolsList.get(position).getToolName());

        holder.ts_rat.getProgressDrawable().setColorFilter(Color.parseColor("#FCB637"), PorterDuff.Mode.SRC_ATOP);
        holder.ts_rat.setRating(searchToolsList.get(position).getToolRating());
        /*if(String.valueOf(searchToolsList_pojos.get(position).getToolFavorite()).trim().equals("1")){
            holder.btn_add_fav.setBackgroundResource(R.drawable.ic_turned_in_black_24dp);
        }*/

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
        RatingBar ts_rat;

        Viewholder(@NonNull View itemView) {
            super(itemView);

            tool_img = itemView.findViewById(R.id.tool_image);
            tool_name = itemView.findViewById(R.id.tool_name);
            ts_sid = itemView.findViewById(R.id.ts_sid);
            ts_avail = itemView.findViewById(R.id.ts_avail);
            ts_rat = itemView.findViewById(R.id.ts_rat);

            itemView.setTag(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedTool.selectedTool(searchToolsList.get(getAdapterPosition()));
                }
            });


        }
    }
}
