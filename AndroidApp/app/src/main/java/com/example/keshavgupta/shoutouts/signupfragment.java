package com.example.keshavgupta.shoutouts;


import android.content.Intent;
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
public class signupfragment extends Fragment {
    private SQLiteDatabase mydb;
Button btsignup;
    public signupfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mydb = getActivity().openOrCreateDatabase("mydb", MODE_PRIVATE, null);

        String cmd = "create table if not exists signup1(email1 text,email2 text,password text)";
        mydb.execSQL(cmd);





        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signupfragment, container, false);
    }

    @Override
    public void onResume() {

        super.onResume();

        TextView tvsignin=getActivity().findViewById(R.id.tvsignin);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/Quicksand-Medium.ttf");

        tvsignin.setTypeface(custom_font);
        tvsignin.setText("Sign-Up..");



        btsignup=getActivity().findViewById(R.id.btsignup);
        final EditText etsignupemail=getActivity().findViewById(R.id.etsignemail);
        final EditText etsignupguardemail=getActivity().findViewById(R.id.etsignguardemail);
        final EditText etsignpass=getActivity().findViewById(R.id.etsignpass);
        final EditText etsignconpass=getActivity().findViewById(R.id.etsignconpass);


        btsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1=etsignupemail.getText().toString();
                String email2=etsignupguardemail.getText().toString();
                String pass1 =  etsignpass.getText().toString();
                String confirmpass=etsignconpass.getText().toString();
                int confirmpass1= Integer.parseInt(confirmpass);
                int pass2= Integer.parseInt(pass1);

                if(confirmpass1==pass2){

                    Cursor cursor = mydb.rawQuery("select * from  signup1 where email1="+"'"+email1+"'", null);
                    String data = "";
                    while (cursor.moveToNext()) {
                        String email11 = cursor.getString(cursor.getColumnIndex("email1"));
                        String email21 = cursor.getString(cursor.getColumnIndex("email2"));
                        data = data + email11 + email21;
                    }
                    if(data==""){
                        String insertrow = "insert into signup1 values("+"'"+email1+"'"+","+"'"+email2+"'"+","+"'"+pass1+"'"+")";

                        mydb.execSQL(insertrow);
                        Toast.makeText(getContext(), "Signed Up Successfully...", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getContext(),MapActivity.class));
                    }
                    else if(data!=""){
                        Toast.makeText(getContext(), "Already Signed up...", Toast.LENGTH_SHORT).show();

                    }

                }
                else{
                    Toast.makeText(getContext(), "Passwords not matched..."+confirmpass+pass1, Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}
