package com.example.keshavgupta.shoutouts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //AlertDialog.Builder builder;
    private GoogleMap mMap;
    LocationManager locationManager;
    Button btstopnotify;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        btstopnotify=findViewById(R.id.btstopnotify);
        btstopnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MapsActivity.this,MapActivity.class));
                finish();
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean bGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean bNW = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!bGPS){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "Please enable GPS/Network location", Toast.LENGTH_SHORT).show();
        }
        else{

            MyLocationListener myLocationListener = new MyLocationListener();

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
                    0, myLocationListener);

        }








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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        addMarker(31.6340, 74.8723);
    }

    Marker m;

    private void sendmail(double latitude,double longitude,String address){
        try {
            SharedPreferences mydata = getSharedPreferences("mydata", MODE_PRIVATE);
            String name = mydata.getString("email1", null);
            String name1 = mydata.getString("email2", null);
            String password = mydata.getString("password", null);
            if (name1 != "") {
                SendMail sm = new SendMail(MapsActivity.this, name1, "Location Information Called..", "\n\n"+"My Current Address is :"+address+"\n\n"+"https://www.google.com/maps/@"+latitude+","+longitude);
                sm.execute();

            }
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

    }

    private void addMarker(double lat, double lng) {

        if (m != null) {
            m.remove();
        }

        LatLng amritsar = new LatLng(lat, lng);
        m = mMap.addMarker(new MarkerOptions().position(amritsar).title("Current Location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(amritsar));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(amritsar, 15));
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 2);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My", strReturnedAddress.toString());
            } else {
                Log.w("My", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My", "Canont get Address!");
        }
        return strAdd;
    }


    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            addMarker(latitude, longitude);
            String address=getCompleteAddressString(latitude,longitude);
            sendmail(latitude,longitude,address);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }
}