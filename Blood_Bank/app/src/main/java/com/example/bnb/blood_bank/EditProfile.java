package com.example.bnb.blood_bank;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {

    private EditText name, email, mobilenumber, DOB, address, city, state, country, pincode, bloodgroup;
    private RadioGroup rd;
    private RadioButton rdGender;
    private Button edit;
    private FirebaseDatabase firebaseDatabase;
    private String CurrentUserId;
    private FirebaseAuth firebaseAuth;
    private  DatabaseReference rootRef;
    private CircularImageView ProfilePick;

    private static final int GalleryPick =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

        CurrentUserId = firebaseAuth.getCurrentUser().getUid();

        rootRef = FirebaseDatabase.getInstance().getReference();

        rd = (RadioGroup) findViewById(R.id.rdgroup);
        int id = rd.getCheckedRadioButtonId();
        rdGender = (RadioButton) findViewById(id);
        name = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.userEmail);
        mobilenumber = (EditText) findViewById(R.id.userMobileNumber);
        DOB = (EditText) findViewById(R.id.userDateOfBirth);
        address = (EditText) findViewById(R.id.userAddress);
        city = (EditText) findViewById(R.id.userCity);
        state = (EditText) findViewById(R.id.userState);
        country = (EditText) findViewById(R.id.userCountry);
        pincode = (EditText) findViewById(R.id.userPincode);
        bloodgroup = (EditText) findViewById(R.id.userBloodGroup);
        edit = (Button) findViewById(R.id.btnEdit);

        ProfilePick = (CircularImageView)findViewById(R.id.profilepic);

        ProfilePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GalleryPick);


            }
        });


        DatabaseReference databaseReference = firebaseDatabase.getReference();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateData();

            }
        });


        databaseReference.child("Users").child(CurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {


                    if ((dataSnapshot.exists())) {

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
                        if (retrivegender == "male") {


                        } else {


                        }

                        email.setText(retriveEmail);
                        mobilenumber.setText(retriveMobileNumber);
                        DOB.setText(retriveDOB);
                        address.setText(retriveAddress);
                        city.setText(retriveCity);
                        state.setText(retriveState);
                        country.setText(retriveCountry);
                        pincode.setText(retrivePin);
                        bloodgroup.setText(retriveBloodGroup);

                    } else {


                    }

                } catch (Exception e) {
                    Toast.makeText(EditProfile.this, "Error" + e, Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(EditProfile.this, databaseError.getCode(), Toast.LENGTH_LONG).show();

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void updateData()
    {

   try{
        String UpdateUname = name.getText().toString();
       rd = (RadioGroup) findViewById(R.id.rdgroup);
       int id = rd.getCheckedRadioButtonId();
       rdGender = (RadioButton) findViewById(id);
        String Updategender = rdGender.getText().toString();
        String UpdateEmail = email.getText().toString();
        String UpdateMobileNumber = mobilenumber.getText().toString();
        String UpdateDOB = DOB.getText().toString();
        String UpdateAddress = address.getText().toString();
        String UpdateCity = city.getText().toString();
        String UpdateState = state.getText().toString();
        String UpdateCountry = country.getText().toString();
        String UpdatePin = pincode.getText().toString();
        String UpdateBloodGroup = bloodgroup.getText().toString();


        if (TextUtils.isEmpty(UpdateUname))
        {
            Toast.makeText(getApplicationContext(), "fill You name first", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(Updategender))
        {
            Toast.makeText(getApplicationContext(), "Select your Gender first", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(UpdateEmail))
        {
            Toast.makeText(getApplicationContext(), "Fill Details before Sign up", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(UpdateMobileNumber))
        {
            Toast.makeText(getApplicationContext(), "Mobile field not be empty", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(UpdateAddress))
        {
            Toast.makeText(getApplicationContext(), "Address Field is empty", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(UpdateDOB))
        {
            Toast.makeText(getApplicationContext(), "Enter date of birth", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(UpdateCity))
        {
            Toast.makeText(getApplicationContext(), "city Field is empty", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(UpdateState))
        {
            Toast.makeText(getApplicationContext(), "Enter State", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(UpdateCountry))
        {
            Toast.makeText(getApplicationContext(), "country Field is empty", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(UpdatePin))
        {
            Toast.makeText(getApplicationContext(), "Enter pin", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(UpdateBloodGroup))
        {
            Toast.makeText(getApplicationContext(), "Blood Group Field is empty", Toast.LENGTH_LONG).show();

        }
        else
        {

            if (!UpdateUname.matches("[a-zA-Z,]+([a-zA-Z, ])*$"))
            {

                name.requestFocus();
                name.setError("Enter a valid name contains only  alphabet");

            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(UpdateEmail).matches())
            {
                email.requestFocus();
                email.setError("Enter a valid Email Address");

            }
            else if (!UpdateMobileNumber.matches("[0-9]") && UpdateMobileNumber.length() != 10)
            {
                mobilenumber.requestFocus();
                mobilenumber.setError("Enter a valid Mobile Number");

            }
            else if (!UpdateDOB.matches("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$"))
            {


                DOB.requestFocus();
                DOB.setError("Enter a valid Formate(dd/mm/yyyy)");
            }
            else if (!UpdateCity.matches("[a-zA-Z,]+([a-zA-Z, ])*$"))
            {
                city.requestFocus();
                city.setError("Enter a valid City Name");

            }
            else if (!UpdateState.matches("[a-zA-Z,]+([a-zA-Z, ])*$"))
            {
                state.requestFocus();
                state.setError("Enter a valid State Name");

            }
            else if (!UpdateCountry.matches("[a-zA-Z,]+([a-zA-Z, ])*$"))
            {
                country.requestFocus();
                country.setError("Enter a valid Country Name");

            }
            else if (!UpdatePin.matches("[0-9]") && pincode.length() != 6)
            {
                pincode.requestFocus();
                pincode.setError("Enter a Pin Code");

            }
            else if (!UpdateBloodGroup.matches("^(A|B|AB|O)[-+]$"))
            {
                bloodgroup.requestFocus();
                bloodgroup.setError("Enter a valid Blood Group");

            }
            else
                {

                    HashMap<String,String>  ProfileData = new HashMap<>();

                    ProfileData.put("UID",CurrentUserId);
                    ProfileData.put("username",UpdateUname);
                    ProfileData.put("gender",Updategender);
                    ProfileData.put("email",UpdateEmail);
                    ProfileData.put("date_Birth",UpdateDOB);
                    ProfileData.put("mobileNumber",UpdateMobileNumber);
                    ProfileData.put("userAddress",UpdateAddress);
                    ProfileData.put("usercity",UpdateCity);
                    ProfileData.put("userState",UpdateState);
                    ProfileData.put("userCountry",UpdateCountry);
                    ProfileData.put("userpin",UpdatePin);
                    ProfileData.put("bloodGroup",UpdateBloodGroup);

                    rootRef.child("Users").child(CurrentUserId).setValue(ProfileData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {

                            if(task.isSuccessful())
                            {
                              sendToMainPage();
                              Toast.makeText(EditProfile.this,"Profile Update Sucessfully",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                              Toast.makeText(EditProfile.this,"Error  : "+task.getException(),Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }


        }

    }catch (Exception e)
   {
   Toast.makeText(EditProfile.this,"Error : "+e,Toast.LENGTH_LONG).show();
   }
    }

    private void sendToMainPage()
    {

        Intent i =new Intent(EditProfile.this,Main2Activity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }


}








