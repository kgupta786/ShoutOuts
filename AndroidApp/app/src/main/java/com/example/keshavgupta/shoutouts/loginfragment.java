package com.example.keshavgupta.shoutouts;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class loginfragment extends Fragment {
Button btlogin;
    private SQLiteDatabase mydb;
EditText etloginemail;
EditText etloginpass;
    public loginfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loginfragment, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences mydata = getActivity().getSharedPreferences("mydata", MODE_PRIVATE);
        if(mydata.getString("email1",null)==""){
            Toast.makeText(getContext(), "Please sign-up first...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(),MainActivity.class));
        }


        mydb = getActivity().openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        TextView tvlogin=getActivity().findViewById(R.id.tvloginmain);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/Quicksand-Medium.ttf");

        tvlogin.setTypeface(custom_font);
        tvlogin.setText("login..");

        btlogin=getActivity().findViewById(R.id.btlogin);
        etloginemail=getActivity().findViewById(R.id.etloginemail);
        etloginpass=getActivity().findViewById(R.id.etloginpass);

        btlogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String email=etloginemail.getText().toString();
        String pass=etloginpass.getText().toString();
        Cursor cursor = mydb.rawQuery("select * from signup1 where email1="+"'"+email+"'"+"and password="+"'"+pass+"'", null);
        String data = "";
        while (cursor.moveToNext()) {
            String ename = cursor.getString(cursor.getColumnIndex("email1"));
            String ename2 = cursor.getString(cursor.getColumnIndex("email2"));

            String pass2 = cursor.getString(cursor.getColumnIndex("password"));
            SharedPreferences mydata = getActivity().getSharedPreferences("mydata", MODE_PRIVATE);
            SharedPreferences.Editor editor = mydata.edit();
            editor.putString("email1", ename);
            editor.putString("email2", ename2);
            editor.putString("password", pass2);
            editor.commit();
            data=ename+ename2+pass2;

        }
        if(data!=""){
          startActivity(new Intent(getContext(),MapActivity.class));  //Toast.makeText(getContext(), "Shared Preferences created...", Toast.LENGTH_SHORT).show();




        }
        else if(data==""){
            Toast.makeText(getContext(), "Login Failed..", Toast.LENGTH_SHORT).show();
        }

    }
});
    }
}
