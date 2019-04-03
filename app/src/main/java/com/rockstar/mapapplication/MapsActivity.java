package com.rockstar.mapapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;

    public static String tvLongi;
    public static String tvLati;
    LocationManager locationManager;
    EditText etSource,etDestination;
    String con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        this.setFinishOnTouchOutside(true);
        CheckPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_getdirections, null);

        builder.setView(dialogView);



        final Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        etSource= dialogView.findViewById(R.id.et_source);
        etDestination= dialogView.findViewById(R.id.et_destination);
        Button btnstartnavigation= dialogView.findViewById(R.id.btn_startNavigation);

        btnstartnavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String st_destination=etDestination.getText().toString();
                Toast.makeText(MapsActivity.this, ""+etDestination.getText().toString(), Toast.LENGTH_LONG).show();
                if(st_destination.equals("ndmvp college of engineering") || st_destination.equals("Ndmvp college") || st_destination.equals("NDMVP")|| st_destination.equals("ndmvp college")|| st_destination.equals("ndmvp")) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+con+"&daddr=19.886365,75.332019"));
                startActivity(intent);
                }
                else if(st_destination.equals("nimani")  || st_destination.equals("Nimani bus stand") || st_destination.equals("Nimani")|| st_destination.equals("Nimani bus stand")|| st_destination.equals("panchvati") ||  st_destination.equals("nemani")) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+con+"&daddr=20.011564,73.796847"));
                    startActivity(intent);
                }
                else if(st_destination.equals("Mac'd")  || st_destination.equals("Mac Donald") || st_destination.equals("macd")|| st_destination.equals("mac donald")|| st_destination.equals("mac'd") ) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+con+"&daddr=20.005810,73.765038"));
                    startActivity(intent);
                }
                else if(st_destination.equals("CBS")  || st_destination.equals("cbs bus stand") || st_destination.equals("old cbs")|| st_destination.equals("cbs")|| st_destination.equals("CBS") ||  st_destination.equals("Panchvati bus stop")) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+con+"&daddr=19.997665,73.780205"));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MapsActivity.this, "Soemthing went wrong", Toast.LENGTH_SHORT).show();
                }



            }
        });
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP;
        //wmlp.x = x;
        //wmlp.y = y;
        dialog.show();



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /* Request updates at startup */
    @Override
    public void onResume() {
        super.onResume();
        getLocation();
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void CheckPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        tvLongi = String.valueOf(location.getLongitude());
        tvLati = String.valueOf(location.getLatitude());

        //con=""+tvLongi+","+tvLati;
        con=""+tvLati+","+tvLongi;


        etSource.setText(""+con);
       /* // Setting Current Longitude
        tvLongitude.setText("Longitude:" + tvLongi);
        // Setting Current Latitude
        tvLatitude.setText("Latitude:" + tvLati);*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(this, "Enabled new provider!" + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

        Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

    }

    public void getSpeechInput(View view) {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    etDestination.setText(result.get(0));
                }
                break;
        }
    }
}
