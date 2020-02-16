package com.example.toolsharing.Student;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.toolsharing.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ToolDetailsNOrder extends Fragment {

    TextView td_sid, td_name, td_desc, td_avail, td_sd, td_ed;
    CircleImageView td_img;
    RatingBar td_rat;
    ImageButton btn_td_sd, btn_td_ed;
    View view;
    String psid, bsid, tid;
    Toolbar toolbar;

    DatePickerDialog.OnDateSetListener onDateSetListener;
    DatePickerDialog.OnDateSetListener onDateSetListener1;

    public ToolDetailsNOrder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tool_details, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.tool_det_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        td_sid = view.findViewById(R.id.td_sid);
        //td_tid = view.findViewById(R.id.td_tid);
        td_img = view.findViewById(R.id.td_img);
        td_desc = view.findViewById(R.id.td_desc);
        td_name = view.findViewById(R.id.td_name);
        td_rat = view.findViewById(R.id.td_rat);
        td_avail = view.findViewById(R.id.td_avail);
        td_sd = view.findViewById(R.id.td_sd);
        td_ed = view.findViewById(R.id.td_ed);
        btn_td_sd = view.findViewById(R.id.btn_td_sd);
        btn_td_ed = view.findViewById(R.id.btn_td_ed);

        if (getArguments() != null) {
            tid = getArguments().getString("tId", "NULL");
            psid = getArguments().getString("psId", "NULL");
            td_sid.setText(psid);
            //td_tid.setText(tid);
            td_name.setText(getArguments().getString("tN", "NULL"));
            //td_img.setText(getArguments().getString("tImg", "NULL"));
            td_desc.setText(getArguments().getString("tD", "NULL"));
            //td_rat.setText(getArguments().getString("tr", "NULL"));
            td_avail.setText(getArguments().getString("avail", "NULL"));
            Glide.with(getContext()).asBitmap().load(getArguments().getString("tImg", "NULL")).into(td_img);
        }
        td_rat.setMax(5);
        td_rat.setRating(Integer.parseInt(getArguments().getString("tr")));
    }


}
