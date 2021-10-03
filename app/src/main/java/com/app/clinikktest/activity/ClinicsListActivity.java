package com.app.clinikktest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.clinikktest.R;
import com.app.clinikktest.adapter.ClinicsAdapter;
import com.app.clinikktest.constant.Config;
import com.app.clinikktest.model.Clinics;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ClinicsListActivity extends AppCompatActivity {
    LinearLayout ll;
    ArrayList<Clinics>clinicList=new ArrayList();
    ClinicsAdapter clinicsAdapter;
    RecyclerView recyclerView;
    ImageView imageView;
    int apiCallCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinics_list);
        initLoading();
        initRecyclerView();
        getClinics();
    }

    private void initLoading(){
        ll=findViewById(R.id.ll_loading);
        imageView=findViewById(R.id.img_loading);
        Glide.with(this)
                .load(R.drawable.ic_search_radar)
                .into(imageView);
    }
    private void initRecyclerView()
    {
        recyclerView=findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void getClinics() {
        String url ="";
        if(Config.CURRENT_LATITUDE==""||Config.CURRENT_LATITUDE==null)
        {
            url=Config.BASE_URL+"/v1/clinic/search?";
        }
        else
        {
            url=Config.BASE_URL+"/v1/clinic/search?"+"radius="+Config.CURRENT_RADIUS+"&lat="+Config.CURRENT_LATITUDE+"&lon="+Config.CURRENT_LONGITUDE;
        }
        RequestQueue queue = Volley.newRequestQueue(ClinicsListActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.trim(),
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ll.setVisibility(View.GONE);
                        if(response!=null){
                           parseData(response);
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ll.setVisibility(View.GONE);
                Toast.makeText(ClinicsListActivity.this,"Error : Unable to load data",Toast.LENGTH_SHORT).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Config.TIME_OUT_DURATION, Config.MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);


    }

   private void parseData(String response){
       try {
           JSONObject jsonObject = new JSONObject(response);
           JSONArray jsonArray=jsonObject.getJSONArray("clinics");
           if (jsonArray!=null&&jsonArray.length() > 0) {
               String name="";
               String imageUrl="";
               String description="";
               String address="";
               String timings="";
               for(int i=0;i<jsonArray.length();i++){

                   JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    name=jsonObject1.getString("name");
                   JSONArray jaImages=jsonObject1.getJSONArray("images");
                   if(jaImages!=null&&jaImages.length()>0)
                   {
                     imageUrl=jaImages.get(0).toString();
                   }
                   JSONObject joAddress=jsonObject1.getJSONObject("address");
                   address=joAddress.getString("line")+" , "+joAddress.getString("city")+" , "+joAddress.getString("state")+" , "+joAddress.getString("country")+" , "+joAddress.getString("zipcode");
                   //only fetching 3 things for now ...name , img, address
                   clinicList.add(new Clinics(imageUrl,name,"",address,""));
               }
           }
           else
           {
               apiCallCount=1;
               if(apiCallCount<2)
               {
                   Config.CURRENT_LATITUDE="";
                   Config.CURRENT_LONGITUDE="";
                   Toast.makeText(ClinicsListActivity.this,"Error : No centres found as per current location - fetching other avaialble centres",Toast.LENGTH_SHORT).show();
                   getClinics();
               }
               else
               {
                   Toast.makeText(ClinicsListActivity.this,"Error : No centres found",Toast.LENGTH_SHORT).show();
               }}

       } catch (JSONException e) {
           e.printStackTrace();
       }
       if(clinicList.size()>0)
       {
           clinicsAdapter = new ClinicsAdapter(ClinicsListActivity.this,clinicList);
           recyclerView.setAdapter(clinicsAdapter);
       }
       else
       {
           Toast.makeText(ClinicsListActivity.this,"Error : No data found",Toast.LENGTH_SHORT).show();
       }
    }
}