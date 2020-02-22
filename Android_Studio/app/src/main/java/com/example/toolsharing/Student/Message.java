package com.example.toolsharing.Student;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toolsharing.PojoClasses.MyMessages_Pojo;
import com.example.toolsharing.PojoClasses.StatusMessage_Pojo;
import com.example.toolsharing.R;
import com.example.toolsharing.Utils.GetDataServiceInterface;
import com.example.toolsharing.Utils.RetrofitClientInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Message extends Fragment {
    private View view;
    private MessageListRecylerAdapter messageListRecylerAdapter;
    private ArrayList<MyMessages_Pojo> myMessagesPojos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String tsid = getArguments().getString("sId");
        myMessages(Integer.parseInt(tsid));
    }

    public void myMessages(final int tsid)
    {
        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);
        Call<StatusMessage_Pojo> call = service.getMyMessages(tsid);

        System.out.println("URL Tools: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {
                final StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Student recycler Called!: " + status);

                if(!status.equalsIgnoreCase("error")) {
                    myMessagesPojos = new ArrayList<>(statusMessage_pojo.getMyMessages());
                    messageListRecylerAdapter = new MessageListRecylerAdapter(myMessagesPojos, getActivity().getApplicationContext());
                    @SuppressLint("WrongConstant") LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                    RecyclerView recyclerView = view.findViewById(R.id.recycler_student_msg);
                    //empty_view.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(linearLayout);
                    recyclerView.setAdapter(messageListRecylerAdapter);

                    messageListRecylerAdapter.setOnItemClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //view.startAnimation(buttonClick);
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                            int position = viewHolder.getAdapterPosition();

                            int fsid = statusMessage_pojo.getMyMessages().get(position).getFromStudentId();
                            String msg = statusMessage_pojo.getMyMessages().get(position).getMessage();

                            System.out.println("URL FromStudent: " + fsid);
                            System.out.println("URL Message: " + msg);
                            System.out.println("URL Sent Time: " + statusMessage_pojo.getMyMessages().get(position).getSentDate());

                            Bundle bundle = new Bundle();
                            bundle.putString("tsid", String.valueOf(tsid));
                            bundle.putString("fsid", String.valueOf(fsid));


                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            final MessageDetails messageDetails = new MessageDetails();
                            fragmentTransaction.replace(R.id.frag_stu, messageDetails);
                            fragmentTransaction.addToBackStack(null);
                            messageDetails.setArguments(bundle);
                            fragmentTransaction.commit();
                        }
                    });

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
