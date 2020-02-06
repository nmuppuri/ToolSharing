package com.example.toolsharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRegis extends AppCompatActivity {

    EditText aid, pwd, rpwd, fn, ln, email;
    Button btn_create;

    String pwd1, rpwd1, fn1, ln1, email1, aid1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        aid = findViewById(R.id.aid);
        pwd = findViewById(R.id.pwd);
        rpwd = findViewById(R.id.rpwd);
        fn = findViewById(R.id.fn);
        ln = findViewById(R.id.ln);
        email = findViewById(R.id.email);
        btn_create = findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwd1 = pwd.getText().toString();
                rpwd1 = rpwd.getText().toString();
                fn1 = fn.getText().toString();
                ln1 = ln.getText().toString();
                email1 = email.getText().toString();
                aid1 = aid.getText().toString();

                if(pwd1.isEmpty() || rpwd1.isEmpty() || fn1.isEmpty() || ln1.isEmpty() || email1.isEmpty() || aid1.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_LONG).show();
                }else {
                    if(pwd1.equals(rpwd1)){
                        adminRegister();
                    }else{
                        Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_LONG).show();
                        pwd.setText(null);
                        rpwd.setText(null);
                    }
                }
            }
        });

    }

    public void adminRegister()
    {

        GetDataServiceInterface service = RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        Call<StatusMessage_Pojo> call = service.getAdminRegister(Integer.parseInt(aid1), pwd1, fn1, ln1, email1);

        System.out.println("URL: " + call);

        call.enqueue(new Callback<StatusMessage_Pojo>() {
            @Override
            public void onResponse(Call<StatusMessage_Pojo> call, Response<StatusMessage_Pojo> response) {


                StatusMessage_Pojo statusMessage_pojo = response.body();
                String status = statusMessage_pojo.getStatus();
                System.out.println("URL Status Called!: " + status);

                if(status.equalsIgnoreCase("error")){
                    Toast.makeText(getApplicationContext(),"ID already exists. Please Login with your password!!", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(NewRegis.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<StatusMessage_Pojo> call, Throwable t) {

                System.out.println("URL Failure Called! :" + t.getMessage());

            }
        });
    }
}








