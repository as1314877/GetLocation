package com.example.getposition;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.webkit.WebSettings.PluginState.ON;

public class MainActivity extends AppCompatActivity {

    private Button getBtn;
    private TextView locationText;
    private FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();
        getBtn = findViewById(R.id.getLocation);
        locationText = findViewById(R.id.location);
        client = LocationServices.getFusedLocationProviderClient(this);

        Handler mTimeHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            locationText.setText(location.getLatitude()+" "+ location.getLongitude());
                            Log.e("TEST", "in");
                        }
                    }
                });
                sendEmptyMessageDelayed(0, 1000);
            }
        };
        mTimeHandler.sendEmptyMessageDelayed(0, 1000);

    }
    public void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
