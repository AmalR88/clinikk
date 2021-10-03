package com.app.clinikktest.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.app.clinikktest.R;
import com.app.clinikktest.constant.Config;
import com.google.android.gms.location.LocationListener;
public class MainActivity extends AppCompatActivity implements LocationListener {
    ImageView imgFindCentres, imgNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgFindCentres = findViewById(R.id.img_find_centres);
        imgNavigation = findViewById(R.id.img_back_arrow);
        imgFindCentres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              checkRuntimePermissions();

            }
        });
        imgNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SelectLanguageActivity.class));
            }
        });

    }

    private void checkRuntimePermissions(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            try {
                ActivityCompat.requestPermissions(this, new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION },
                        1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            lastLocation();
        }
    }

    private void lastLocation(){
        try {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(lastLocation!=null){
                Config.CURRENT_LATITUDE= String.valueOf(lastLocation.getLatitude());
                Config.CURRENT_LONGITUDE= String.valueOf(lastLocation.getLongitude());
            }
            startActivity(new Intent(MainActivity.this, ClinicsListActivity.class));
        } catch (NullPointerException | SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    lastLocation();
                } else {
                    //do other operation because permission not given
                    checkRuntimePermissions();
                }
                return;
            }
        }
    }

    @Override
    public void onLocationChanged( Location location) {
        Config.CURRENT_LATITUDE= String.valueOf(location.getLatitude());
        Config.CURRENT_LONGITUDE= String.valueOf(location.getLongitude());
    }
}