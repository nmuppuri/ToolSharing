package com.example.toolsharing.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toolsharing.R;
import com.example.toolsharing.PojoClasses.StudentRegisList_Pojo;

import java.util.ArrayList;

//import com.bumptech.glide.Glide;

public class AdminRecyclerAdapter extends RecyclerView.Adapter<AdminRecyclerAdapter.Viewholder> {
    private ArrayList<StudentRegisList_Pojo> studentRegisList_pojos;
    private Context c;
    private View.OnClickListener onClickListener;

    public AdminRecyclerAdapter(ArrayList<StudentRegisList_Pojo> pokemonArrayList, Context c) {
        this.studentRegisList_pojos = pokemonArrayList;
        this.c = c;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_admin_dash, parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        //Glide.with(c).asBitmap().load(pokemonArrayList.get(position).getImage()).into(holder.pokemonImage);
        holder.rsname.setText(studentRegisList_pojos.get(position).getStudentName());
        holder.rsid.setText(studentRegisList_pojos.get(position).getStudentId().toString());
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener){
        onClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return studentRegisList_pojos.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView rsname, rsid;
        Button btn_approve;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            rsid = itemView.findViewById(R.id.rsid);
            rsname = itemView.findViewById(R.id.rsname);

            btn_approve = itemView.findViewById(R.id.btn_approve);
            btn_approve.setTag(this);
            btn_approve.setOnClickListener(onClickListener);

        }
    }
}
