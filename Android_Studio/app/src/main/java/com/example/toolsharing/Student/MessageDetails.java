package com.example.toolsharing.Student;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toolsharing.PojoClasses.MessageDetails_Pojo;
import com.example.toolsharing.PojoClasses.StatusMessage_Pojo;
import com.example.toolsharing.R;
import com.example.toolsharing.Utils.GetDataServiceInterface;
import com.example.toolsharing.Utils.RetrofitClientInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageDetails extends Fragment {
    private View view;
    private MessageDetailsRecyclerAdapter messageDetailsRecylerAdapter;
    private ArrayList<MessageDetails_Pojo> messageDetails_pojos;
    Toolbar msg_det_toolbar;
    TextView fsid_msg_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message_details, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fsid_msg_title = view.findViewById(R.id.fsid_msg_title);
        fsid_msg_title.setText(getArguments().getString("fsid"));
        msg_det_toolbar = view.findViewById(R.id.msg_det_toolbar);
        msg_det_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        getMyMessageDetails(getArguments().getString("tsid"), getArguments().getString("fsid"));
    }

    public void getMyMessageDetails(String tsid, String fsid)
    {
        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);
        Call<StatusMessage_Pojo> call = service.getMyMessageDetails(Integer.parseInt(tsid), Integer.parseInt(fsid));

        System.out.println("URL Tools: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {
                final StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Student recycler Called!: " + status);

                if(!status.equalsIgnoreCase("error")) {
                    messageDetails_pojos = new ArrayList<>(statusMessage_pojo.getMessageDetails());
                    messageDetailsRecylerAdapter = new MessageDetailsRecyclerAdapter(messageDetails_pojos, getActivity().getApplicationContext());
                    @SuppressLint("WrongConstant") LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                    RecyclerView recyclerView = view.findViewById(R.id.recycler_message_details);
                    //empty_view.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(linearLayout);
                    recyclerView.setAdapter(messageDetailsRecylerAdapter);

                    /*messageListRecylerAdapter.setOnItemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //view.startAnimation(buttonClick);
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            int position = viewHolder.getAdapterPosition();

                            int fsid = statusMessage_pojo.getMyMessages().get(position).getFromStudentId();
                            String msg = statusMessage_pojo.getMyMessages().get(position).getMessage();

                            System.out.println("URL FromStudent: " + fsid);
                            System.out.println("URL Message: " + msg);



                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            final MessageDetails messageDetails = new MessageDetails();
                            fragmentTransaction.replace(R.id.frag_stu, messageDetails);
                            fragmentTransaction.addToBackStack(null);
                            //toolDetailsNOrder.setArguments(bundle);
                            fragmentTransaction.commit();
                        }
                    });*/

                }
                else {
                    //recyclerView.setVisibility(View.INVISIBLE);
                    //empty_view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

}
