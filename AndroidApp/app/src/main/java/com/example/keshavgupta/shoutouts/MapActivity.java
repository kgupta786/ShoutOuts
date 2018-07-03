package com.example.keshavgupta.shoutouts;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.itstartnotify) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                int i = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);


                if (i == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this, MapsActivity.class));

                } else {
                    String permission[] = {
                            Manifest.permission.ACCESS_COARSE_LOCATION,


                    };
                    ActivityCompat.requestPermissions(this, permission, 1);
                }
            } else {
                startActivity(new Intent(this, MapsActivity.class));

            }


        } else if (id == R.id.itsendalert) {

            try {
                SharedPreferences mydata = getSharedPreferences("mydata", MODE_PRIVATE);
                String name = mydata.getString("email1", null);
                String name1 = mydata.getString("email2", null);
                String password = mydata.getString("password", null);
                SendMail sm = new SendMail(MapActivity.this, name1, "Alert Called..", "https://www.google.com/maps/@-31.6,74.5");
                //Executing sendmail to send email
                sm.execute();

            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }


        } else if (id == R.id.itemercalls) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri uri = Uri.parse("tel:000");
            intent.setData(uri);
            startActivity(intent);

        } else if (id == R.id.itcops) {


            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri uri = Uri.parse("tel:100");
            intent.setData(uri);
            startActivity(intent);


        } else if (id == R.id.itlogout) {

            SharedPreferences mydata = getSharedPreferences("mydata", MODE_PRIVATE);
            SharedPreferences.Editor editor = mydata.edit();

            editor.remove("email1");
            editor.remove("email2");
            editor.remove("password");
            editor.commit();
            Intent intent=new Intent(MapActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_send) {
sendEmail();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void sendEmail() {
        SharedPreferences mydata = getSharedPreferences("mydata", MODE_PRIVATE);
        String name = mydata.getString("email1", null);
        String name1 = mydata.getString("email2", null);
        String password = mydata.getString("password", null);

        String[] TO = {"iitengineer786@gmail.com"};
        String[] CC = {name};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack Forum");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            boolean b = grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (b) {
startActivity(new Intent(this,MapsActivity.class));
            } else {
                Toast.makeText(this, "Storage Permission not allowed", Toast.LENGTH_SHORT).show();
            }


        }
    }
}