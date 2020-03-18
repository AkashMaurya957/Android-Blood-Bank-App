package com.example.bnb.blood_bank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ReVerifyEmail extends AppCompatActivity {

    Button VerifyButton;

    TextView tv;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_re_verify_email);

        firebaseAuth = FirebaseAuth.getInstance();

        VerifyButton = (Button)findViewById(R.id.btnVerification);

        tv = (TextView)findViewById(R.id.tvVerify);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        final FirebaseUser user =  firebaseAuth.getCurrentUser();

        final String email = bundle.getString("Email");

        tv.setText(""+email);

        VerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {



                                        Toast.makeText(getApplicationContext(), "Please Check Your Email for verification ", Toast.LENGTH_SHORT).show();


                                        Intent i = new Intent(ReVerifyEmail.this, Login.class);
                                        startActivity(i);

                                    }
                                });





                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Error :"+e, Toast.LENGTH_LONG).show();

                }

            }
        });






    }
}
