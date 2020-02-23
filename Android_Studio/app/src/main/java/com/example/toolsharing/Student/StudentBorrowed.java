package com.example.toolsharing.Student;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
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

public class StudentBorrowed extends Fragment implements RatingDialog.RatingDialogListener {
    private View view;
    ToolsListBorrowedRecylerAdapter toolsListBorrowedRecylerAdapter;
    private ArrayList<ToolsList_Pojo> toolsList_pojos;
    int position;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    float rat, tool_rating;
    int psid, toolId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_borrowed, container, false);

        return view;
    }

    public void openDialog(){

        RatingDialog ratingDialog = new RatingDialog();
        ratingDialog.setTargetFragment(StudentBorrowed.this, 1);
        ratingDialog.show(getFragmentManager(), null);

    }

    @Override
    public void ratingVal(String rating) {
        rat = Float.parseFloat(rating);
        System.out.println("URL RATING2: " + rat);
        if(tool_rating == 0.0){
            updateToolRating(psid, toolId, ((5 + rat)/2));
            System.out.println("URL PSID: " + psid);
            System.out.println("URL toolID: " + toolId);
            System.out.println("URL Average toolRating: " + ((5 + rat)/2));
        }else {
            updateToolRating(psid, toolId, ((tool_rating + rat)/2));
            System.out.println("URL PSID: " + psid);
            System.out.println("URL toolID: " + toolId);
            System.out.println("URL Average toolRating: " + ((tool_rating + rat) / 2));
        }
        borrowToolReturn(Integer.parseInt(getArguments().getString("SID")), toolId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        borrowToolList(Integer.parseInt(getArguments().getString("SID")));
    }

    public void borrowToolList(final int bsid)
    {
        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);
        Call<StatusMessage_Pojo> call = service.getborrowedToolList(bsid);

        System.out.println("URL Tools: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {
                final StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Student recycler Called!: " + status);

                if(!status.equalsIgnoreCase("error")) {
                    toolsList_pojos = new ArrayList<>(statusMessage_pojo.getToolsList());
                    toolsListBorrowedRecylerAdapter = new ToolsListBorrowedRecylerAdapter(toolsList_pojos, getActivity().getApplicationContext());
                    @SuppressLint("WrongConstant") LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                    RecyclerView recyclerView = view.findViewById(R.id.recycler_student_borrow_mytools);
                    //empty_view.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(linearLayout);
                    recyclerView.setAdapter(toolsListBorrowedRecylerAdapter);

                    toolsListBorrowedRecylerAdapter.setOnItemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            view.startAnimation(buttonClick);
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            int position = viewHolder.getAdapterPosition();

                            toolId = statusMessage_pojo.getToolsList().get(position).getToolId();
                            psid = statusMessage_pojo.getToolsList().get(position).getPostedStudentId();
                            String return_date = statusMessage_pojo.getToolsList().get(position).getReturnDate();
                            tool_rating = statusMessage_pojo.getToolsList().get(position).getToolRating();

                            System.out.println("URL toolId: " + toolId);
                            System.out.println("URL psid: " + psid);
                            System.out.println("URL return: " + return_date);
                            System.out.println("URL toolRating: " + tool_rating);

                            openDialog();

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

    private void borrowToolReturn(final int bsid, int ptid) {

        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        Call<StatusMessage_Pojo> call = service.returnBorrowTool(bsid, ptid);


        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {

                StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Status Called!: " + status);

                if(status.equalsIgnoreCase("error")){
                    Toast.makeText(getActivity().getApplicationContext(),"Error!!", Toast.LENGTH_LONG).show();
                }else{
                    borrowToolList(bsid);
                    Toast.makeText(getActivity().getApplicationContext(), statusMessage_pojo.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

    private void updateToolRating(final int psid, int ptid, float tool_rat) {

        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        Call<StatusMessage_Pojo> call = service.updateToolRat(psid, ptid, String.valueOf(tool_rat));


        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {

                StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Status Called!: " + status);

                if(status.equalsIgnoreCase("error")){
                    Toast.makeText(getActivity().getApplicationContext(),"Error!!", Toast.LENGTH_LONG).show();
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