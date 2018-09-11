package com.kingslayer.fosmis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import REST_Classes.RetrofitClientInstance;

import java.io.IOException;

import REST_Classes.GetDataService;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUser;
    private EditText txtPassword;
    private Button btnLogin;
    private ProgressDialog mProgress;
    private GetDataService service;
    String outResponse = "";

    //Shared Preferences
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mySharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = mySharedPreferences.edit();

        txtUser = findViewById(R.id.login_userTxt);
        txtPassword = findViewById(R.id.login_passwordTxt);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Logging In");
        mProgress.setMessage("Please wait while we check your credentials");



        btnLogin = findViewById(R.id.login_loginBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.show();

                service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<ResponseBody> call = service.login(txtUser.getText().toString(), txtPassword.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            outResponse = response.body().string().trim();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mProgress.dismiss();
                        if(outResponse.equals("Invalid Username !!! Please Check again") || outResponse.equals("Invalid Username or Password !!!"))
                        {
                            Toasty.error(LoginActivity.this,outResponse, Toast.LENGTH_SHORT).show();

                        }
                        else if(outResponse.equals("You Must be a Valid Student"))
                        {
                            Toasty.info(LoginActivity.this,outResponse, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toasty.success(LoginActivity.this,outResponse, Toast.LENGTH_SHORT).show();
                        }

                        if(outResponse.substring(0,22).equals("Logged In Successfully"))
                        {
                            editor.putString("user", outResponse.substring(26));
                            editor.putString("user_id", txtUser.getText().toString());
                            editor.commit();

                            //String device_token = FirebaseInstanceId.getInstance().getToken();

                            startActivity(new Intent(LoginActivity.this, NoticeActivity.class));
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                        mProgress.dismiss();
                        Toasty.error(LoginActivity.this, "Please enable mobile data and try again", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}
