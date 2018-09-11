package com.kingslayer.fosmis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import REST_Classes.Combinations;
import REST_Classes.GetDataService;
import REST_Classes.RetrofitClientInstance;
import REST_Classes.Subjects;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CombinationRegistration extends Navigation {

        SharedPreferences mySharedPrefs;
        SharedPreferences.Editor editor;

        private TextView txt_stream;
        private ProgressDialog mProgress;

        private Spinner spinner1;
        private Spinner spinner2;

        private Button btnSubmit;
        private GetDataService service;

        String stream_id;
        String[] combination_ids;
        String outResponse = null;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_combination_registration);

            spinner1 = findViewById(R.id.spinner1);
            spinner2 = findViewById(R.id.spinner2);

            btnSubmit = findViewById(R.id.comb_btn_submit);

            txt_stream = findViewById(R.id.comb_txt_stream);

            mySharedPrefs = getSharedPreferences("login",MODE_PRIVATE);
            editor = mySharedPrefs.edit();
            final String user_id = mySharedPrefs.getString("user_id","");

            mProgress = new ProgressDialog(CombinationRegistration.this);
            mProgress.setTitle("Processing");
            mProgress.setMessage("Please wait while we check your information");
            mProgress.show();

            service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            final Call<ResponseBody> call1 = service.getStream(user_id);
            call1.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        outResponse = response.body().string().trim();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    editor.putString("stream_id", outResponse.substring(0,2) );
                    editor.putString("stream_name", outResponse.substring(2) );
                    editor.commit();
                    txt_stream.setText(mySharedPrefs.getString("stream_name",""));

                    stream_id= mySharedPrefs.getString("stream_id","");

                    Call<Combinations> call2 = service.getCombinations(stream_id);
                    call2.enqueue(new Callback<Combinations>() {
                        @Override
                        public void onResponse(Call<Combinations> call, Response<Combinations> response) {

                            combination_ids = response.body().getCombinationIds().toArray(new String[response.body().getCombinationIds().size()]);
                            ArrayAdapter<String> adapter = new ArrayAdapter(CombinationRegistration.this, R.layout.spinner_item, response.body().getCombinations());
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            spinner1.setAdapter(adapter);
                            spinner2.setAdapter(adapter);

                            Call<Subjects> call3 = service.getSubjects(stream_id);
                            call3.enqueue(new Callback<Subjects>() {
                                @Override
                                public void onResponse(Call<Subjects> call, Response<Subjects> response) {

                                    ListView listView = findViewById(R.id.list);
                                    ArrayAdapter<String> adapter1 = new ArrayAdapter(CombinationRegistration.this,R.layout.listview_item,R.id.list_content, response.body().getSubjects());
                                    listView.setAdapter(adapter1);

                                    mProgress.dismiss();
                                }

                                @Override
                                public void onFailure(Call<Subjects> call, Throwable t) {
                                    Toasty.error(getApplicationContext(),"Something went wrong Please Check mobile data !",Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Combinations> call, Throwable t) {

                            Toasty.error(getApplicationContext(),"Something went wrong Please Check mobile data !",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    Toasty.error(getApplicationContext(),"Something went wrong Please Check mobile data !",Toast.LENGTH_LONG).show();
                    finish();
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(spinner1.getSelectedItem() == spinner2.getSelectedItem())
                    {
                        Toasty.info(CombinationRegistration.this, "Please select different Combinations", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        mProgress.setTitle("Please Wait ...");
                        mProgress.setMessage("Please wait until process completes");
                        mProgress.show();

                        Call<ResponseBody> call4 = service.registerCombinations(user_id,combination_ids[(int) spinner1.getSelectedItemId()],combination_ids[(int) spinner2.getSelectedItemId()]);
                        call4.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                editor.putString("combination_ch1",spinner1.getSelectedItem().toString());
                                editor.putString("combination_ch2",spinner2.getSelectedItem().toString());
                                editor.apply();
                                try {
                                    Toasty.success(CombinationRegistration.this, response.body().string().trim(), Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(getApplicationContext(), EditSubmittedCombinations.class);
                                intent.putExtra("submitable", true);
                                mProgress.dismiss();
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                mProgress.dismiss();
                                Toasty.error(CombinationRegistration.this, "Please enable mobile data and try again", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                    }
                }
            });

    }
}
