package com.example.keshavgupta.shoutouts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DrawerLayout extends AppCompatActivity {
    ArrayList<String> aloptions = new ArrayList<>();
    ListView lvmitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);
        lvmitems = findViewById(R.id.lvmitems);
        aloptions.add("Start To Notify");
        aloptions.add("Stop To Notify");
        aloptions.add("Alert!!");
        aloptions.add("Make A Call");
        aloptions.add("Logout");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, aloptions);
        lvmitems.setAdapter(adapter);

        lvmitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    startActivity(new Intent(DrawerLayout.this, MapsActivity.class));
                } else if (i == 1) {

                } else if (i == 2) {
//sendEmail();
                    try {
                        SharedPreferences mydata = getSharedPreferences("mydata", MODE_PRIVATE);
                        String name = mydata.getString("email1", null);
                        String name1 = mydata.getString("email2", null);
                        String password = mydata.getString("password", null);


//                    SendMail sm = new SendMail(DrawerLayout.this,name1, "Alert Called..", "Alert Has Been Called Up by one of your favourites.This Service is issued incase help is required. " +
//                            "Kindly Contact them or take certain appropriate measures. ");

                        SendMail sm = new SendMail(DrawerLayout.this, name1, "Alert Called..", "https://www.google.com/maps/@-31.6,74.5");
                        //Executing sendmail to send email
                        sm.execute();


                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }


                } else if (i == 3) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri uri = Uri.parse("tel:9999xxxx");
                    intent.setData(uri);
                    startActivity(intent);
                } else if (i == 4) {
                    SharedPreferences mydata = getSharedPreferences("mydata", MODE_PRIVATE);
                    SharedPreferences.Editor editor = mydata.edit();

                    editor.remove("email1");
                    editor.remove("email2");
                    editor.remove("password");
                    editor.commit();
                    startActivity(new Intent(DrawerLayout.this, MainActivity.class));
                }
            }
        });


    }


    protected void sendEmail() {
        SharedPreferences mydata = getSharedPreferences("mydata", MODE_PRIVATE);
        String name = mydata.getString("email1", null);
        String name1 = mydata.getString("email2", null);
        String password = mydata.getString("password", null);

        String[] TO = {name1};
        String[] CC = {name};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Alert Message");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "it is danger!!!");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }


}
