package com.example.bnb.blood_bank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity
{

   private  Button reset;
   private   EditText  Email;
   private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        reset = (Button) findViewById(R.id.Reset);
        Email = (EditText) findViewById(R.id.textReset);
        firebaseAuth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String userEmail = Email.getText().toString().trim();

                if(userEmail.equals(""))
                {

                    Toast.makeText(ForgetPassword.this,"Please enter your Register Email ID",Toast.LENGTH_LONG).show();
                }
                else
                {

                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if(task.isSuccessful())
                            {


                                Toast.makeText(getApplicationContext(),"Password Reset Email Send !!",Toast.LENGTH_LONG).show();
                                finish();
                                Intent i = new Intent(ForgetPassword.this,Login.class);
                                startActivity(i);
                                


                            }
                            else
                            {

                                Toast.makeText(getApplicationContext(),"Error in sending Password  reset Email",Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                }
            }
        });


    }


}
