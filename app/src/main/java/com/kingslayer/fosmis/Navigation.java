package com.kingslayer.fosmis;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.Toast;

import java.io.IOException;

import REST_Classes.GetDataService;
import REST_Classes.RetrofitClientInstance;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    SharedPreferences mySharedPreferences ;
    SharedPreferences.Editor mEditor;

    private GetDataService service;
    private String outResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        mEditor = mySharedPreferences.edit();
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        outResponse = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*getMenuInflater().inflate(R.menu.drawermenu, menu);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_navigation);

        ViewStub content = findViewById(R.id.content_frame);
        content.setLayoutResource(layoutResID);
        content.inflate();
        initDrawer();

    }

    private void initDrawer() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public boolean onNavigationItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {

            case R.id.notice: {
                startActivity(new Intent(getApplicationContext(), NoticeActivity.class));
            }
            break;

            case R.id.combinations: {


                Call<ResponseBody> call2 = service.checkCallCombination(mySharedPreferences.getString("user_id", ""));
                call2.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            if (response.body().string().trim().equals("true")) {
                                if (!mySharedPreferences.getString("combination_ch1", "").equals("")) {
                                    Intent intent1 = new Intent(getApplicationContext(), EditSubmittedCombinations.class);
                                    intent1.putExtra("submitable", true);
                                    startActivity(intent1);
                                } else {
                                    Intent intent2 = new Intent(getApplicationContext(), CombinationRegistration.class);
                                    intent2.putExtra("submitable", true);
                                    startActivity(intent2);
                                }
                            } else {
                                Intent intent = new Intent(getApplicationContext(), EditSubmittedCombinations.class);
                                intent.putExtra("submitable", false);
                                startActivity(intent);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toasty.error(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
                    }

                });
            }
            break;

            case R.id.course: {


                Call<ResponseBody> call3 = service.checkCourseRegistration(mySharedPreferences.getString("user_id", ""));
                call3.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            outResponse = response.body().string().trim();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (outResponse.equals("true"))
                        {
                            startActivity(new Intent(getApplicationContext(), CourseRegistration.class));
                        }
                        else
                        {
                            Toasty.info(getApplicationContext(),"Course Registration is Closed",Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
            break;

            case R.id.exams: {

                Call<ResponseBody> call4 = service.checkExamRegistration(mySharedPreferences.getString("user_id", ""));
                call4.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        try {
                            outResponse = response.body().string().trim();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (outResponse.equals("true"))
                        {
                            startActivity(new Intent(getApplicationContext(), ExamRegistration.class));
                        }
                        else
                        {
                            Toasty.info(getApplicationContext(),"Exam Registration is Closed",Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
            break;

            case R.id.logout: {

                new AlertDialog.Builder(this)
                        .setTitle("Confirm")
                        .setMessage("Do you really want to logout?")
                        .setIcon(R.drawable.ic_round_exit_to_app_24px)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mEditor.remove("user");
                                mEditor.apply();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null).show();

            }
            break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
