package com.example.cooklab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapScreen extends AppCompatActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private SupportMapFragment supportMapFragment;
    private Button hqBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);

        supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.main_map);
        supportMapFragment.getMapAsync(this);
        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        hqBtn=findViewById(R.id.hq_button);
        homeBtn=findViewById(R.id.home_button);

        hqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng banovici=new LatLng(44.40863372604413, 18.528236600017316);
                mMap.clear();
                mMap.animateCamera(CameraUpdateFactory.newLatLng(banovici));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng banovici = new LatLng(44.40863372604413, 18.528236600017316);
        mMap.addMarker(new MarkerOptions().position(banovici).title("Marker in Banovici"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(banovici,15.0f));
    }

    public boolean checkPermission(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    private void getLocation(){
        if(checkPermission()){
            if(isLocationEnabled()){
                mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location= task.getResult();
                        if(location!=null){
                            LatLng currentLocation=new LatLng(location.getLatitude(),location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker on current location"));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15.0f));
                        }
                        else{
                            Toast.makeText(MapScreen.this,"Unable to get location",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(MapScreen.this,"Location is diabled",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            getPermissions();
        }
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void getPermissions(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},69);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==69){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

            }
            else{
                Toast.makeText(this,"Permission is not granted. Some features might not work!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}