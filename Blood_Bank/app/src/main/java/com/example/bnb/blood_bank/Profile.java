package com.example.bnb.blood_bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView name,gender,email,mobilenumber,DOB,address,city,state,country,pincode,bloodgroup;

    Button EditProfile;

    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase firebaseDatabase;

    private  String CurrentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        try {

            firebaseAuth = FirebaseAuth.getInstance();

            firebaseDatabase = FirebaseDatabase.getInstance();

            CurrentUserId = firebaseAuth.getCurrentUser().getUid();

            name = (TextView) findViewById(R.id.username);
            gender = (TextView) findViewById(R.id.userGender);
            email = (TextView) findViewById(R.id.userEmail);
            mobilenumber = (TextView) findViewById(R.id.userMobileNumber);
            DOB = (TextView) findViewById(R.id.userDateOfBirth);
            address = (TextView) findViewById(R.id.userAddress);
            city = (TextView) findViewById(R.id.userCity);
            state = (TextView) findViewById(R.id.userState);
            country = (TextView) findViewById(R.id.userCountry);
            pincode = (TextView) findViewById(R.id.userPincode);
            bloodgroup = (TextView) findViewById(R.id.userBloodGroup);
            EditProfile = (Button)findViewById(R.id.btnEdit);


            DatabaseReference databaseReference = firebaseDatabase.getReference();


            EditProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Profile.this,EditProfile.class);
                    startActivity(i);

                }
            });

            databaseReference.child("Users").child(CurrentUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {



                        if((dataSnapshot.exists()))
                        {

                            String retrivename = dataSnapshot.child("username").getValue().toString();
                            String retrivegender = dataSnapshot.child("gender").getValue().toString();
                            String retriveEmail = dataSnapshot.child("email").getValue().toString();
                            String retriveMobileNumber = dataSnapshot.child("mobileNumber").getValue().toString();
                            String retriveDOB = dataSnapshot.child("date_Birth").getValue().toString();
                            String retriveAddress = dataSnapshot.child("userAddress").getValue().toString();
                            String retriveCity = dataSnapshot.child("usercity").getValue().toString();
                            String retriveState = dataSnapshot.child("userState").getValue().toString();
                            String retriveCountry = dataSnapshot.child("userCountry").getValue().toString();
                            String retrivePin = dataSnapshot.child("userpin").getValue().toString();
                            String retriveBloodGroup = dataSnapshot.child("bloodGroup").getValue().toString();



                            name.setText(retrivename);
                            gender.setText(retrivegender);
                            email.setText(retriveEmail);
                            mobilenumber.setText(retriveMobileNumber);
                            DOB.setText(retriveDOB);
                            address.setText(retriveAddress);
                            city.setText(retriveCity);
                            state.setText(retriveState);
                            country.setText(retriveCountry);
                            pincode.setText(retrivePin);
                            bloodgroup.setText(retriveBloodGroup);

                        }
                        else
                        {



                        }

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(Profile.this,"Error"+e,Toast.LENGTH_LONG).show();

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(Profile.this, databaseError.getCode(), Toast.LENGTH_LONG).show();

                }
            });


        }
        catch (Exception e)
        {

            Toast.makeText(Profile.this,"Error"+e,Toast.LENGTH_LONG).show();
        }
    }


}
