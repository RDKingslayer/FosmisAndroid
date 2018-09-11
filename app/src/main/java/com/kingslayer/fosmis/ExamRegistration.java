package com.kingslayer.fosmis;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import REST_Classes.CoreCourse;
import REST_Classes.ExamCourses;
import REST_Classes.GetDataService;
import REST_Classes.RetrofitClientInstance;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;

public class ExamRegistration extends Navigation {

    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor editor;

    private Button mFinish;
    private RecyclerView mRecycler;
    private ProgressDialog mProgress;
    private GetDataService service;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_registration);

        mFinish = findViewById(R.id.btnFinishExam);
        mRecycler = findViewById(R.id.recycler_exam_registration);

        mySharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = mySharedPreferences.edit();
        user_id = mySharedPreferences.getString("user_id","");

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Please Wait");
        mProgress.setMessage("Please wait while process completes");
        mProgress.show();

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<ExamCourses>> call = service.getExamCourses(user_id);
        call.enqueue(new Callback<List<ExamCourses>>() {
            @Override
            public void onResponse(Call<List<ExamCourses>> call, retrofit2.Response<List<ExamCourses>> response) {
                ExamRegistrationAdapter adapter = new ExamRegistrationAdapter(response.body(),getApplicationContext());
                mRecycler.setAdapter(adapter);
                mProgress.dismiss();
            }

            @Override
            public void onFailure(Call<List<ExamCourses>> call, Throwable t) {
                mProgress.dismiss();
                Toasty.error(getApplicationContext(),"Something Went Wrong !!!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
