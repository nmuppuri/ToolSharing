package com.example.toolsharing;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StudentRegister extends Fragment {

    EditText sfn, sln, sid, semail;
    Button btn_s_req;

    String sfn1, sln1, sid1, semail1;
    Toolbar toolbar;
    View view;
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
                Navigation.findNavController(view).navigate(R.id.action_studentRegister_to_loginScreen);
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_student_register,container,false);

        toolbar = view.findViewById(R.id.regis_tool);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_studentRegister_to_loginScreen);
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        sid = view.findViewById(R.id.sid);
        sfn = view.findViewById(R.id.sfn);
        sln = view.findViewById(R.id.sln);
        semail = view.findViewById(R.id.semail);
        btn_s_req = view.findViewById(R.id.btn_s_req);

        btn_s_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                sfn1 = sfn.getText().toString();
                sln1 = sln.getText().toString();
                semail1 = semail.getText().toString();
                sid1 = sid.getText().toString();

                if(sfn1.isEmpty() || sln1.isEmpty() || semail1.isEmpty() || sid1.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Please fill all details", Toast.LENGTH_LONG).show();
                }else {
                    //sendTestEmail();
                    studentRegister();
                }
            }
        });

    }

    public void studentRegister()
    {

        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        Call<StatusMessage_Pojo> call = service.getStudentRegister(Integer.parseInt(sid1), sfn1, sln1, semail1);

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
                Navigation.findNavController(view).navigate(R.id.action_studentRegister_to_loginScreen);

            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }

    /*private void sendTestEmail() {
        BackgroundMail.newBuilder(getActivity())
                .withUsername("cegep.toolsharing@gmail.com")
                .withPassword("group1toolsharing")
                .withMailTo("narendramuppuri@gmail.com")
                .withMailCc("cc-email@gmail.com")
                .withMailBcc("bcc-email@gmail.com")
                .withSubject("this is the subject")
                .withBody("this is the body")
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                    }
                })
                .send();
    }*/
}
