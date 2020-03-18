package com.example.bnb.blood_bank;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12;
    RadioGroup rd;
    RadioButton rdGender;
    Button b1;
    CheckBox cb;
    FirebaseDatabase database;
    FirebaseUser CurrentUserId;
    DatabaseReference ref;
    ImageButton btnDatePicker;

    private FirebaseAuth firebaseAuth;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);


        e1 = (EditText) findViewById(R.id.username);
        e2 = (EditText) findViewById(R.id.email);
        e3 = (EditText) findViewById(R.id.mobile);
        e4 = (EditText) findViewById(R.id.Address);
        e5 = (EditText) findViewById(R.id.city);
        e6 = (EditText) findViewById(R.id.state);
        e7 = (EditText) findViewById(R.id.country);
        e8 = (EditText) findViewById(R.id.pincode);
        e9 = (EditText) findViewById(R.id.bloodgroup);
        e10 = (EditText) findViewById(R.id.pass);
        e11 = (EditText) findViewById(R.id.repass);
        e12 = (EditText) findViewById(R.id.DOB);

        btnDatePicker=(ImageButton)findViewById(R.id.ImageView7);


        rd = (RadioGroup) findViewById(R.id.rdgroup);
        int id=rd.getCheckedRadioButtonId();
        rdGender = (RadioButton)findViewById(id);

        b1 = (Button) findViewById(R.id.singup);

        cb = (CheckBox) findViewById(R.id.checkbox1);

        ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        CurrentUserId = firebaseAuth.getCurrentUser();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        CheckEmail();
            }

        });


    }

    public void onClick( View v)
    {


        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            e12.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

    }





       public void CheckEmail()
    {

        try {
            String Email = e2.getText().toString();
            if(TextUtils.isEmpty(Email))
            {
              Toast.makeText(getApplicationContext(),"Fill Details before Sign up",Toast.LENGTH_LONG).show();
            }
            else
            {

                firebaseAuth.fetchProvidersForEmail(Email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {


                        boolean check = !task.getResult().getProviders().isEmpty();
                        if (!check)
                        {

                            performTask();

                        }

                        else
                            {
                            Toast.makeText(getApplicationContext(), "Email is already Exists  ", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error"+e, Toast.LENGTH_LONG).show();

        }
    }

    private void performTask()
    {



        try {

            if (cb.isChecked())
            {

              String user = e1.getText().toString();
              int id = rd.getCheckedRadioButtonId();
              rdGender = (RadioButton) findViewById(id);
              String gender = rdGender.getText().toString();
              String Email = e2.getText().toString();
              String Mobile = e3.getText().toString().trim();
              String Address = e4.getText().toString();
              String City = e5.getText().toString();
              String State = e6.getText().toString();
              String Country = e7.getText().toString();
              String pin = e8.getText().toString().trim();
              String BloodGroup = e9.getText().toString();
              String Pass = e10.getText().toString();
              String Repass = e11.getText().toString();
              String DateOfBirth = e12.getText().toString();

                if (TextUtils.isEmpty(user))
                {
                    Toast.makeText(getApplicationContext(), "fill You name first", Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(gender))
                {
                    Toast.makeText(getApplicationContext(), "Select your Gender first", Toast.LENGTH_LONG).show();

                }
               else if(TextUtils.isEmpty(Email))
                {
                    Toast.makeText(getApplicationContext(),"Fill Details before Sign up",Toast.LENGTH_LONG).show();
                }


                else if (TextUtils.isEmpty(Mobile))
                {
                    Toast.makeText(getApplicationContext(), "Mobile field not be empty", Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(Pass))
                {
                    Toast.makeText(getApplicationContext(), "Enter your Password first", Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(Repass))
                {
                    Toast.makeText(getApplicationContext(), "Enter Repass", Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(Address))
                {
                    Toast.makeText(getApplicationContext(), "Address Field is empty", Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(DateOfBirth))
                {
                    Toast.makeText(getApplicationContext(), "Enter date of birth", Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(City))
                {
                    Toast.makeText(getApplicationContext(), "city Field is empty", Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(State))
                {
                    Toast.makeText(getApplicationContext(), "Enter State", Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(Country))
                {
                    Toast.makeText(getApplicationContext(), "country Field is empty", Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(pin))
                {
                    Toast.makeText(getApplicationContext(), "Enter pin", Toast.LENGTH_LONG).show();

                }
                else if (TextUtils.isEmpty(BloodGroup))
                {
                    Toast.makeText(getApplicationContext(), "Blood Group Field is empty", Toast.LENGTH_LONG).show();

                }
                else
                {

                    if (!user.matches("[a-zA-Z,]+([a-zA-Z, ])*$"))
                    {

                        e1.requestFocus();
                        e1.setError("Enter a valid name contains only  alphabet");

                    }
                    else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                    {
                        e2.requestFocus();
                        e2.setError("Enter a valid Email Address");

                    }
                    else if (!Mobile.matches("[0-9]") && Mobile.length() != 10)
                    {
                        e3.requestFocus();
                        e3.setError("Enter a valid Mobile Number");

                    }
                    else if (!Pass.matches("^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).(?=.*[!@#$%&*=+]).*$"))
                    {
                        e11.requestFocus();
                        e11.setError("Password at least 8 Character \n Password contains Chararter[!@#$%&*=+]\n Password Contain at least one Uppercase \n Password must Contain at least one Lowercase");
                    }
                    else if (!DateOfBirth.matches("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$"))
                    {


                        e12.requestFocus();
                        e12.setError("Enter a valid Formate(dd/mm/yyyy)");
                    }
                    else if (!City.matches("[a-zA-Z,]+([a-zA-Z, ])*$"))
                    {
                        e5.requestFocus();
                        e5.setError("Enter a valid City Name");

                    }
                    else if (!State.matches("[a-zA-Z,]+([a-zA-Z, ])*$"))
                    {
                        e6.requestFocus();
                        e6.setError("Enter a valid State Name");

                    }
                    else if (!Country.matches("[a-zA-Z,]+([a-zA-Z, ])*$"))
                    {
                        e7.requestFocus();
                        e7.setError("Enter a valid Country Name");

                    }
                    else if (!pin.matches("[0-9]") && pin.length() != 6)
                    {
                        e8.requestFocus();
                        e8.setError("Enter a Pin Code");

                    }
                    else if (!BloodGroup.matches("^(A|B|AB|O)[-+]$"))
                    {
                        e9.requestFocus();
                        e9.setError("Enter a valid Blood Group");

                    }
                    else
                    {

                        if (Pass.equals(Repass))
                        {

                            AuthenticationForUser();

                        }
                        else
                        {
                            e11.requestFocus();
                            e11.setError("Password does not match");


                        }

                    }

                }

            }

        }
        catch (Exception e)
        {

            Toast.makeText(getApplicationContext(),"Fill Details Before Submit" +e,Toast.LENGTH_LONG).show();
        }
    }

    private void AuthenticationForUser()
    {


        final String user = e1.getText().toString();
        int id = rd.getCheckedRadioButtonId();
        rdGender = (RadioButton) findViewById(id);
        final String gender = rdGender.getText().toString();
        final String Email = e2.getText().toString();
        final String Mobile = e3.getText().toString().trim();
        final String Address = e4.getText().toString();
        final String City = e5.getText().toString();
        final String State = e6.getText().toString();
        final String Country = e7.getText().toString();
        final String pin = e8.getText().toString().trim();
        final String BloodGroup = e9.getText().toString();
        final String Pass = e10.getText().toString();
        final String Repass = e11.getText().toString();
        final String DateOfBirth = e12.getText().toString();


        firebaseAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {

                    String CurrentUserID = firebaseAuth.getCurrentUser().getUid();
                    UserInformation obj = new UserInformation(CurrentUserID, user, gender, Email, Mobile, DateOfBirth, Address, City, State, Country, pin, BloodGroup);
                    ref.child("Users").child(CurrentUserID).setValue(obj);

                 SendVerficationMail();


                }
                else
                {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void SendVerficationMail()
    {


        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {

                Toast.makeText(getApplicationContext(), "Please Check Your Email for verification ", Toast.LENGTH_SHORT).show();


               CallLoginPage();

            }
        });
    }

    private void CallLoginPage()
    {

        Intent i = new Intent(SignUp.this, Login.class);
        startActivity(i);
        finish();
    }


}
