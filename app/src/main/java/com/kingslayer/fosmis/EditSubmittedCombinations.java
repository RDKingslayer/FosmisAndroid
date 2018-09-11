package com.kingslayer.fosmis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import REST_Classes.GetDataService;
import REST_Classes.RetrofitClientInstance;
import REST_Classes.Subjects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSubmittedCombinations extends Navigation {

    SharedPreferences mySharedPrefs;
    private Button btnUpdateSubmit;
    private ProgressDialog mProgress;
    private GetDataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_submitted_combinations);

        btnUpdateSubmit = findViewById(R.id.btn_updateSubmit);
        TextView ch1 = findViewById(R.id.table_txt_ch1);
        TextView ch2 = findViewById(R.id.table_txt_ch2);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        mySharedPrefs = getSharedPreferences("login",MODE_PRIVATE);

        ch1.setText(mySharedPrefs.getString("combination_ch1",""));
        ch2.setText(mySharedPrefs.getString("combination_ch2",""));

        String stream_id= mySharedPrefs.getString("stream_id","");

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Please Wait");
        mProgress.setMessage("Please Wait while processing completes ...");
        mProgress.show();

        Intent intent = getIntent();
        if(!intent.getBooleanExtra("submitable",false))
        {
            btnUpdateSubmit.setVisibility(View.INVISIBLE);
            btnUpdateSubmit.setEnabled(false);
        }

        Call<Subjects> call = service.getSubjects(stream_id);
        call.enqueue(new Callback<Subjects>() {
            @Override
            public void onResponse(Call<Subjects> call, Response<Subjects> response) {
                ListView listView =  findViewById(R.id.list);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(EditSubmittedCombinations.this,R.layout.spinner_item, response.body().getSubjects());
                listView.setAdapter(adapter1);
                mProgress.dismiss();
            }

            @Override
            public void onFailure(Call<Subjects> call, Throwable t) {

            }
        });

        btnUpdateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), CombinationRegistration.class));
                finish();
            }
        });


    }
}
