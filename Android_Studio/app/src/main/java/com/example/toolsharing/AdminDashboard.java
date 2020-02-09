package com.example.toolsharing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboard extends Fragment {
    RecyclerAdapter recyclerAdapter;
    ArrayList<StudentRegisList_Pojo> studentRegisList_pojoArrayList;
    RecyclerView recyclerView;
    TextView empty_view;
    View view;
    Toolbar toolbar;
    AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    GetDataServiceInterface service;

    Bundle bundle;

    int position;
    String aid;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        empty_view = getView().findViewById(R.id.empty_view);
        toolbar = view.findViewById(R.id.dash_tool);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.refresh:
                        studentRegisDetails();
                        return true;
                }
                return  false;
            }
        });

        studentRegisDetails();
    }





    public void studentRegisDetails()
    {

        Call<StatusMessage_Pojo> call = service.getstudentRegisDetails();

        System.out.println("URL: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {


                final StatusMessage_Pojo statusMessage_pojo = response.body();

                final String status = statusMessage_pojo.getStatus();
                System.out.println("URL Status Called!: " + status);

                if(!status.equalsIgnoreCase("error")) {
                    studentRegisList_pojoArrayList = new ArrayList<StudentRegisList_Pojo>(statusMessage_pojo.getStudentRegis());
                    recyclerAdapter = new RecyclerAdapter(studentRegisList_pojoArrayList, getActivity().getApplicationContext());
                    @SuppressLint("WrongConstant") LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                    recyclerView = getView().findViewById(R.id.recycler_admin);
                    recyclerView.setVisibility(View.VISIBLE);
                    empty_view.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(linearLayout);
                    recyclerView.setAdapter(recyclerAdapter);

                    recyclerAdapter.setOnItemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            view.startAnimation(buttonClick);
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            position = viewHolder.getAdapterPosition();

                            int sid = statusMessage_pojo.getStudentRegis().get(position).getStudentId();

                            String email = statusMessage_pojo.getStudentRegis().get(position).getStudentEmail();
                            String body = "Your request is approved. Use following password to login: " + statusMessage_pojo.getStudentRegis().get(position).getStudentPwd();

                            System.out.println("URL SID: " + sid);
                            System.out.println("URL AID: " + aid);
                            System.out.println("URL EMAIL: " + email);
                            System.out.println("URL PWD: " + statusMessage_pojo.getStudentRegis().get(position).getStudentPwd());

                            studentRegisAccept(sid,"Accepted", email, body);

                        }
                    });

                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    empty_view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

    public void studentRegisAccept(int sid, String dec, final String email, final String body)
    {
        Call<StatusMessage_Pojo> call = service.getStudentRegisAccept(sid, dec);

        System.out.println("URL: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {


                StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Status Called!: " + status);
                System.out.println("URL Status Called!: " + statusMessage_pojo.getMessage());

                if(status.equalsIgnoreCase("error")){
                    Toast.makeText(getActivity().getApplicationContext(),"Registration Failed!!", Toast.LENGTH_LONG).show();
                }else{
                    EmailUtil.sendEmail(getActivity(),email,"Welcome to CEGEP ToolSharing Application!", body);
                }

            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

}