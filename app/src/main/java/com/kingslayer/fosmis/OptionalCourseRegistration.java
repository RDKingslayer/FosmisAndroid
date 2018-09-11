package com.kingslayer.fosmis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class OptionalCourseRegistration extends AppCompatActivity {

    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;

    private static GetDataService service;

    private Button mFinish;
    private ProgressDialog mProgress;

    private static String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optional_course_registration);

        mySharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = mySharedPreferences.edit();
        user_id = mySharedPreferences.getString("user_id","");

        mFinish = findViewById(R.id.btnFinish);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Please Wait");
        mProgress.setMessage("Please wait while process completes");
        mProgress.show();

        final RecyclerView optional_recycler = findViewById(R.id.recycler_optional_course);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        optional_recycler.setLayoutManager(linearLayoutManager);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<CoreCourse>> call = service.getOptionalCourses(user_id);
        call.enqueue(new Callback<List<CoreCourse>>() {
            @Override
            public void onResponse(Call<List<CoreCourse>> call, retrofit2.Response<List<CoreCourse>> response) {
                OptionalCourseAdapter adapter = new OptionalCourseAdapter(response.body(),getApplicationContext());
                optional_recycler.setAdapter(adapter);
                mProgress.dismiss();
            }

            @Override
            public void onFailure(Call<List<CoreCourse>> call, Throwable t) {
                mProgress.dismiss();
                Toasty.error(getApplicationContext(),"Something Went Wrong !!!", Toast.LENGTH_LONG).show();
            }
        });


        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NoticeActivity.class) );
                finish();
            }
        });
    }

    public static void setCourse(String course, final Context ctx, int status)
    {

        Call<ResponseBody> call2 = service.registerOptionalCourses(user_id, course, status);
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call2, Response<ResponseBody> response) {
                try {
                    Toasty.success(ctx, response.body().string().trim(),Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call2, Throwable t) {
                Toasty.error(ctx,"Please Try Again",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
