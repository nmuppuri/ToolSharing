package com.example.toolsharing.Student;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toolsharing.PojoClasses.StatusMessage_Pojo;
import com.example.toolsharing.PojoClasses.ToolsList_Pojo;
import com.example.toolsharing.R;
import com.example.toolsharing.Utils.GetDataServiceInterface;
import com.example.toolsharing.Utils.RetrofitClientInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentOwned extends Fragment {
    private View view;
    ToolsListOwnedRecylerAdapter toolsListOwnedRecylerAdapter;
    private ArrayList<ToolsList_Pojo> toolsList_pojos;
    int position;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    TextView empty_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_owned, container, false);

        //String sid = getArguments().getString("SID");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        empty_view = view.findViewById(R.id.owned_empty_view);

        System.out.println("URL STUDENT OWNED: " + getArguments().getString("SID"));
        myToolList(Integer.parseInt(getArguments().getString("SID")));

    }

    public void myToolList(final int psid)
    {
        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);
        Call<StatusMessage_Pojo> call = service.getMyTools(psid);

        System.out.println("URL Tools: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {
                final StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Student recycler Called!: " + status);
                RecyclerView recyclerView = view.findViewById(R.id.recycler_student_owned_mytools);

                if(!status.equalsIgnoreCase("error")) {
                    toolsList_pojos = new ArrayList<>(statusMessage_pojo.getToolsList());
                    toolsListOwnedRecylerAdapter = new ToolsListOwnedRecylerAdapter(toolsList_pojos, getActivity().getApplicationContext());
                    @SuppressLint("WrongConstant") LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);

                    empty_view.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(linearLayout);
                    recyclerView.setAdapter(toolsListOwnedRecylerAdapter);

                    toolsListOwnedRecylerAdapter.setOnItemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            view.startAnimation(buttonClick);
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            int position = viewHolder.getAdapterPosition();

                            int toolId = statusMessage_pojo.getToolsList().get(position).getToolId();
                            int avail = statusMessage_pojo.getToolsList().get(position).getToolAvailability();

                            System.out.println("URL toolName: " + toolId);
                            System.out.println("URL toolImg: " + avail);
                            System.out.println("URL toolImg: " + psid);
                            if(avail == 0) {
                                updateToolAvail(psid, toolId, 1);
                            } else{
                                updateToolAvail(psid, toolId, 0);
                            }
                        }
                    });

                }
                else{
                    empty_view.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

    private void updateToolAvail(final int psid, int ptid, int avail) {

        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        Call<StatusMessage_Pojo> call = service.updateAvailMyTool(psid, ptid, avail);


        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {

                StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Status Called!: " + status);

                if(status.equalsIgnoreCase("error")){
                    Toast.makeText(getActivity().getApplicationContext(),"Error!!", Toast.LENGTH_LONG).show();
                }else{
                    myToolList(psid);
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