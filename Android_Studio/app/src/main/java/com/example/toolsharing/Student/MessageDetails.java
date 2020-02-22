package com.example.toolsharing.Student;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText msg_det_text;
    ImageButton btn_msg_det_text_send;


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

        msg_det_text = view.findViewById(R.id.msg_det_text);
        btn_msg_det_text_send = view.findViewById(R.id.btn_msg_det_text_send);

        btn_msg_det_text_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if(!msg_det_text.getText().equals(null)){
                    sendNewMessage(getArguments().getString("tsid"), getArguments().getString("fsid"), String.valueOf(msg_det_text.getText()));
                    msg_det_text.getText().clear();
                }
            }
        });
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
                    recyclerView.setLayoutManager(linearLayout);
                    recyclerView.setAdapter(messageDetailsRecylerAdapter);

                }
            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

    public void sendNewMessage(String tsid, String fsid, String message_text)
    {
        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);
        Call<StatusMessage_Pojo> call = service.getNewMessage(Integer.parseInt(tsid), Integer.parseInt(fsid), message_text);

        System.out.println("URL Tools: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {
                final StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Student recycler Called!: " + status);

                if(!status.equalsIgnoreCase("error")) {
                    Toast.makeText(getActivity().getApplicationContext(), statusMessage_pojo.getMessage(), Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), statusMessage_pojo.getMessage(), Toast.LENGTH_LONG).show();
                }
                getMyMessageDetails(getArguments().getString("tsid"), getArguments().getString("fsid"));
            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

}
