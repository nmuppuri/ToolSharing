package com.example.toolsharing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.toolsharing.Admin.AdminBottomNav;
import com.example.toolsharing.PojoClasses.StatusMessage_Pojo;
import com.example.toolsharing.Student.StudentBottomNav;
import com.example.toolsharing.Utils.GetDataServiceInterface;
import com.example.toolsharing.Utils.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends Fragment {

    private View view;

    private TextView student_regis;
    private TextView forgot;
    private EditText lid;
    private EditText lpwd;
    private Button btn_login;

    private String lid1;
    private String lpwd1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_screen, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        student_regis = view.findViewById(R.id.btn_student_regis);
        forgot = view.findViewById(R.id.btn_forgot);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginScreen_to_forgotPassword);
            }
        });

        student_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginScreen_to_studentRegister);
            }
        });

        lid = view.findViewById(R.id.lid);
        lpwd = view.findViewById(R.id.lpwd);
        btn_login = view.findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EmailUtil.sendEmail(getActivity(),"cegep.toolsharing@gmail.com");
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                lid1 = lid.getText().toString();
                lpwd1 = lpwd.getText().toString();

                if(lpwd1.isEmpty() || lid1.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter login details", Toast.LENGTH_LONG).show();
                }else {
                    login();
                }
            }
        });
    }

    private void login()
    {

        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        Call<StatusMessage_Pojo> call = service.getlogin(Integer.parseInt(lid1), lpwd1);

        System.out.println("URL: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {


                StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Status Called!: " + status);

                if(status.equalsIgnoreCase("error")){
                    Toast.makeText(getActivity().getApplicationContext(),"Invalid Login!!", Toast.LENGTH_LONG).show();
                }else{
                    if(statusMessage_pojo.getAdminaccess().equalsIgnoreCase("y")){
                        Intent intent = new Intent(getActivity(), AdminBottomNav.class);
                        startActivity(intent);
                        //Navigation.findNavController(view).navigate(R.id.action_loginScreen_to_adminDashboard);
                    } else{
                        Intent intent = new Intent(getActivity(), StudentBottomNav.class);
                        intent.putExtra("stdId", lid1);
                        startActivity(intent);
                        //Navigation.findNavController(view).navigate(R.id.action_loginScreen_to_studentDashboard);
                    }
                    Toast.makeText(getActivity().getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

}
