package com.example.toolsharing.Student;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.toolsharing.PojoClasses.StatusMessage_Pojo;
import com.example.toolsharing.R;
import com.example.toolsharing.Utils.GetDataServiceInterface;
import com.example.toolsharing.Utils.RetrofitClientInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToolDetailsNOrder extends Fragment {

    TextView td_sid, td_tid, td_name, td_desc, td_avail, td_sd, td_ed;
    CircleImageView td_img;
    RatingBar td_rat;
    ImageButton btn_td_sd, btn_td_ed;
    View view;
    String psid, bsid, tid, fromDate, toDate;
    Toolbar toolbar;
    Button btn_td_borrow;

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
            bsid = getArguments().getString("lsid", "NULL");
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

        btn_td_sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(onDateSetListener);
            }
        });

        btn_td_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(onDateSetListener1);
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m = m + 1;
                String date = y + "-" + m + "-" + d;
                Date date1 = null;
                try {
                    date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                fromDate = simpleDateFormat.format(date1);
                System.out.println("URL Date: " + fromDate);
                td_sd.setText(fromDate);
            }
        };


        onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m = m + 1;
                String date = y + "-" + m + "-" + d;
                Date date1 = null;
                try {
                    date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                toDate = simpleDateFormat.format(date1);
                System.out.println("URL Date: " + toDate);
                td_ed.setText(toDate);
            }
        };

        btn_td_borrow = view.findViewById(R.id.btn_td_borrow);
        btn_td_borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(td_sd.getText().toString().isEmpty() || td_ed.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Please Enter All Details!", Toast.LENGTH_LONG).show();
                } else{
                    toolOrder();
                    //getActivity().onBackPressed();
                }
            }
        });
    }

    private void selectDate(DatePickerDialog.OnDateSetListener onDateSetListener) {
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), onDateSetListener, y, m ,d);

        datePickerDialog.getWindow();
        datePickerDialog.show();
    }

    public void toolOrder()
    {

        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        Call<StatusMessage_Pojo> call = service.getToolOrder(Integer.parseInt(tid), Integer.parseInt(psid), Integer.parseInt(bsid),
                fromDate, toDate);

        System.out.println("URL: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {


                StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Status Called!: " + status);

                if(status.equalsIgnoreCase("error")){
                    Toast.makeText(getActivity().getApplicationContext(),"Borrow Request Failed!!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), statusMessage_pojo.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

}
