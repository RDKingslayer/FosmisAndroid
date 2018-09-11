package com.kingslayer.fosmis;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.pusher.pushnotifications.PushNotifications;

import java.io.IOException;
import java.util.List;

import REST_Classes.GetDataService;
import REST_Classes.RetrofitClientInstance;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeActivity extends Navigation {

    SharedPreferences mySharedPreferences ;
    SharedPreferences.Editor mEditor;


    private RecyclerView mRecycler;
    private ProgressDialog mProgressDialog;
    private GetDataService service;

    private String stream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        PushNotifications.start(getApplicationContext(), "616e0271-de80-4c6b-9b23-aef93cbc01f3");
        PushNotifications.subscribe("all");

        mySharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        mEditor = mySharedPreferences.edit();

        if(mySharedPreferences.getString("stream_id","").equals(""))
        {
            service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            final Call<ResponseBody> call1 = service.getStream(mySharedPreferences.getString("user_id",""));
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        stream = response.body().string().trim().substring(0,2);
                        PushNotifications.subscribe(stream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
        else
        {
            stream = mySharedPreferences.getString("stream_id","");
            PushNotifications.subscribe(stream);
        }




        /*





        mProgressDialog = new ProgressDialog(NoticeActivity.this);
        mProgressDialog.setTitle("Loading");
        mProgressDialog.setMessage("Please wait until notices are retrieved.");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(this);

        View header = mNavView.getHeaderView(0);
        TextView header_name = (TextView) header.findViewById(R.id.header_txt_name);
        header_name.setText(mySharedPreferences.getString("user",""));

        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        NOTICES

        mRecycler = (RecyclerView) findViewById(R.id.noticeRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycler.setLayoutManager(linearLayoutManager);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Notice>> call = service.getNotices(mySharedPreferences.getString("user_id", ""));
        call.enqueue(new Callback<List<Notice>>() {
            @Override
            public void onResponse(Call<List<Notice>> call, Response<List<Notice>> response) {
                NoticeAdapter adapter = new NoticeAdapter(response.body());
                mRecycler.setAdapter(adapter);
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Notice>> call, Throwable t) {
                Toasty.error(getApplicationContext(),"Something Went Wrong !!!", Toast.LENGTH_LONG).show();
            }
        });
         */
    }
}
