package com.example.toolsharing.Student;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.toolsharing.PojoClasses.StatusMessage_Pojo;
import com.example.toolsharing.R;
import com.example.toolsharing.Utils.GetDataServiceInterface;
import com.example.toolsharing.Utils.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentProfile extends Fragment {

    View view;

    private TextView stu_pro_name;
    private EditText stu_pro_fn, stu_pro_ln, stu_pro_addr, stu_pro_phone, stu_pro_pwd;
    private Button btn_stu_pro_update, btn_stu_pro_delete;

    private String pwd1, fn, ln, addr, phn, pwd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stu_pro_name = view.findViewById(R.id.stu_pro_name);
        stu_pro_fn = view.findViewById(R.id.stu_pro_fn);
        stu_pro_ln = view.findViewById(R.id.stu_pro_ln);
        stu_pro_addr = view.findViewById(R.id.stu_pro_addr);
        stu_pro_phone = view.findViewById(R.id.stu_pro_phone);
        stu_pro_pwd = view.findViewById(R.id.stu_pro_pwd);

        profileInfo();

        stu_pro_fn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    fn = stu_pro_fn.getText().toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    fn = stu_pro_fn.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        stu_pro_ln.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    ln = stu_pro_ln.getText().toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    ln = stu_pro_ln.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        stu_pro_addr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    addr = stu_pro_addr.getText().toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    addr = stu_pro_addr.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        stu_pro_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    phn = stu_pro_phone.getText().toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    phn = stu_pro_phone.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        stu_pro_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    pwd = stu_pro_pwd.getText().toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() != 0){
                    pwd = stu_pro_pwd.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fn = stu_pro_fn.getText().toString();
        ln = stu_pro_ln.getText().toString();
        addr = stu_pro_addr.getText().toString();
        phn = stu_pro_phone.getText().toString();
        pwd = stu_pro_pwd.getText().toString();

        btn_stu_pro_update = view.findViewById(R.id.btn_stu_pro_update);
        btn_stu_pro_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("URL fn: " + fn);
                System.out.println("URL ln: " + ln);
                System.out.println("URL addr: " + addr);
                System.out.println("URL phn: " + phn);
                System.out.println("URL pwd: " + pwd);

                if(fn.isEmpty() || phn.isEmpty() || ln.isEmpty() || addr.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Enter all details!", Toast.LENGTH_LONG).show();
                } else{
                    updateProfile(pwd, fn, ln, addr, phn);
                }
            }
        });
    }

    private void profileInfo()
    {

        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        Call<StatusMessage_Pojo> call = service.getProfileInfo(Integer.parseInt(getArguments().getString("sId")));

        System.out.println("URL: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {


                StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Status Called!: " + status);

                if(status.equalsIgnoreCase("error")){
                    Toast.makeText(getActivity().getApplicationContext(),"Error!!", Toast.LENGTH_LONG).show();
                }else{

                    stu_pro_name.setText(statusMessage_pojo.getFirstName() + " " + statusMessage_pojo.getLastName());
                    stu_pro_fn.setText(statusMessage_pojo.getFirstName());
                    stu_pro_ln.setText(statusMessage_pojo.getLastName());
                    if(statusMessage_pojo.getPhone() != 0){
                        stu_pro_phone.setText(String.valueOf(statusMessage_pojo.getPhone()));
                    }
                    stu_pro_addr.setText(statusMessage_pojo.getAddress());
                    pwd1 = statusMessage_pojo.getPasswd();
                    //System.out.println("URL pwd1: " + pwd1);
                    //stu_pro_pwd.setText(statusMessage_pojo.getPasswd());
                    profileInfo();
                }

            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

    private void updateProfile(String pwd, String fn, String ln, String addr, String phn) {

        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        Call<StatusMessage_Pojo> call;

        if(pwd.isEmpty()) {
            call = service.getUpdateProfile(Integer.parseInt(getArguments().getString("sId")), pwd1, fn, ln, Long.parseLong(phn), addr);
        } else{
            call = service.getUpdateProfile(Integer.parseInt(getArguments().getString("sId")), pwd, fn, ln, Long.parseLong(phn), addr);
        }
        System.out.println("URL: " + call);

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
