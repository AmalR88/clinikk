package com.app.clinikktest.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import com.app.clinikktest.R;
import com.app.clinikktest.adapter.LanguagesAdapter;
import com.app.clinikktest.adapter.RecyclerViewDataPass;
import com.app.clinikktest.constant.Config;
import com.app.clinikktest.locale.LocaleHelper;
import com.app.clinikktest.model.Languages;
import java.util.ArrayList;
public class SelectLanguageActivity extends AppCompatActivity {
    ArrayList<Languages>languagesArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    androidx.appcompat.widget.AppCompatImageView imgProceed;
    androidx.appcompat.widget.AppCompatTextView tvPoweredby,tvappname;
    RecyclerViewDataPass recyclerViewDataPass;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        imgProceed=findViewById(R.id.img_proceed);
        tvPoweredby=findViewById(R.id.tv_powered_by);
        tvappname=findViewById(R.id.tv_appname);
        imgProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectLanguageActivity.this,MainActivity.class));
            }
        });
         recyclerViewDataPass = new RecyclerViewDataPass() {
             @Override
             public void pass(String selectedLanguageCode) {
                 setLocale(SelectLanguageActivity.this,selectedLanguageCode);
             }
         };

        languagesAvailable();
    }
    // When user change the language it will reflect only on the same screen for now ...it has not been done for the entire app
    public void setLocale(Activity activity, String languageCode) {
        Config.LANGUAGE_SELECTED_CODE=languageCode;
        context = LocaleHelper.setLocale(activity,languageCode);
        resources =context.getResources();
        tvPoweredby.setText(resources.getString(R.string.msg));
        tvappname.setText(resources.getString(R.string.app_name1));

    }

    private void languagesAvailable(){
        //only adding 3 for now
        languagesArrayList.add(new Languages("English","en"));
        languagesArrayList.add(new Languages("हिंदी . Hindi","hi"));
        languagesArrayList.add(new Languages("ಕನ್ನಡ . Kannada","kn"));
        initRecyclerView();

    }
    private void initRecyclerView()
    {
        recyclerView=findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new LanguagesAdapter(SelectLanguageActivity.this,languagesArrayList,recyclerViewDataPass));

    }
}