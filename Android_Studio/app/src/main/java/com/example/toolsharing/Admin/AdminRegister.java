package com.example.toolsharing.Admin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.toolsharing.Utils.GetDataServiceInterface;
import com.example.toolsharing.R;
import com.example.toolsharing.Utils.RetrofitClientInstance;
import com.example.toolsharing.PojoClasses.StatusMessage_Pojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminRegister extends Fragment {

    Toolbar toolbar;
    View view;

    EditText aid, pwd, rpwd, fn, ln, email;
    Button btn_create;

    String pwd1, rpwd1, fn1, ln1, email1, aid1;

    Bundle bundle;
    GetDataServiceInterface service;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_adminRegister_to_loginScreen);
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_register,container,false);

        toolbar = view.findViewById(R.id.regis_tool);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_adminRegister_to_loginScreen);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        aid = view.findViewById(R.id.aid);
        pwd = view.findViewById(R.id.pwd);
        rpwd = view.findViewById(R.id.rpwd);
        fn = view.findViewById(R.id.fn);
        ln = view.findViewById(R.id.ln);
        email = view.findViewById(R.id.email);
        btn_create = view.findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                pwd1 = pwd.getText().toString();
                rpwd1 = rpwd.getText().toString();
                fn1 = fn.getText().toString();
                ln1 = ln.getText().toString();
                email1 = email.getText().toString();
                aid1 = aid.getText().toString();

                if(pwd1.isEmpty() || rpwd1.isEmpty() || fn1.isEmpty() || ln1.isEmpty() || email1.isEmpty() || aid1.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Please fill all details", Toast.LENGTH_LONG).show();
                }else {
                    if(pwd1.equals(rpwd1)){
                        adminRegister();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Password doesn't match", Toast.LENGTH_LONG).show();
                        pwd.setText(null);
                        rpwd.setText(null);
                    }
                }
            }
        });

    }

    public void adminRegister()
    {

        Call<StatusMessage_Pojo> call = service.getAdminRegister(Integer.parseInt(aid1), pwd1, fn1, ln1, email1);

        System.out.println("URL: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {


                StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Status Called!: " + status);

                if(status.equalsIgnoreCase("error")){
                    Toast.makeText(getActivity().getApplicationContext(),"ID already exists. Please Login with your password!!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }
                Navigation.findNavController(view).navigate(R.id.action_adminRegister_to_loginScreen);

            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }
}