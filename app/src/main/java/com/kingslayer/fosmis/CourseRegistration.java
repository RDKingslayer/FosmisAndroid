package com.kingslayer.fosmis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import REST_Classes.CoreCourse;
import REST_Classes.GetDataService;
import REST_Classes.RetrofitClientInstance;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseRegistration extends Navigation {

    private ProgressDialog mProgress;
    private Button mBtnOptional;
    private Button mRegisterCore;
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;

    private GetDataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_registration);

        mySharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = mySharedPreferences.edit();

        mBtnOptional = findViewById(R.id.btn_goto_optional);
        mRegisterCore = findViewById(R.id.btn_core_reg);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Please Wait");
        mProgress.setMessage("Please wait while process completes");
        mProgress.show();

        final RecyclerView core_recycler = findViewById(R.id.recycler_core_course);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        core_recycler.setLayoutManager(linearLayoutManager);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<CoreCourse>> call = service.getCoreCourses(mySharedPreferences.getString("user_id",""));
        call.enqueue(new Callback<List<CoreCourse>>() {
            @Override
            public void onResponse(Call<List<CoreCourse>> call, retrofit2.Response<List<CoreCourse>> response) {
                CoreCourseAdapter adapter2 = new CoreCourseAdapter(response.body());
                core_recycler.setAdapter(adapter2);
                mProgress.dismiss();
            }

            @Override
            public void onFailure(Call<List<CoreCourse>> call, Throwable t) {
                mProgress.dismiss();
                Toasty.error(getApplicationContext(),"Something Went Wrong !!!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        mRegisterCore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<ResponseBody> call2 = service.registerCoreCourses(mySharedPreferences.getString("user_id",""));
                call2.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call2, Response<ResponseBody> response) {
                        try {
                            Toasty.success(getApplicationContext(), response.body().string().trim(),Toast.LENGTH_LONG).show();
                            editor.putString("coreRegistered", "1");
                            editor.apply();
                            mRegisterCore.setEnabled(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call2, Throwable t) {
                        Toasty.error(getApplicationContext(),"Please Try Again",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        if(mySharedPreferences.getString("coreRegistered","").equals("1"))
        {
            mRegisterCore.setEnabled(false);
        }

        mBtnOptional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OptionalCourseRegistration.class));

            }
        });

    }
}
