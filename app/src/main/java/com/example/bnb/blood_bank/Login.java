package com.example.bnb.blood_bank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextView tv,tvForget;
    EditText UserPassword,UserEmail;
    Button btnSignUp,btnLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private int Counter = 5;
    SharedPreferences sp;
    AlertDialog.Builder builder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        tv =(TextView)findViewById(R.id.textView5);
        tvForget =(TextView)findViewById(R.id.textView4);
        UserPassword = (EditText)findViewById(R.id.edPassword);
        UserEmail = (EditText)findViewById(R.id.edEmail);
        btnSignUp=(Button)findViewById(R.id.button3);
        btnLogin = (Button)findViewById(R.id.button2);
        builder = new AlertDialog.Builder(this);
        firebaseAuth = FirebaseAuth.getInstance();

        sp = getSharedPreferences("Login", Context.MODE_PRIVATE);


        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null)
        {

            finish();

            startActivity(new Intent(Login.this,Main2Activity.class));
        }


        progressDialog  = new ProgressDialog(this);



        if(sp.contains("Email"))
        {

            UserEmail.setText(sp.getString("Email",""));
        }
        if(sp.contains("Password"))
        {

            UserPassword.setText(sp.getString("Password",""));
        }

        if(UserEmail.getText().length()!=0 && UserPassword.getText().length()!=0)
        {

        }


        btnSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                 Intent i=new Intent(Login.this,SignUp.class);
                 startActivity(i);

            }
        });


        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent  i = new Intent(Login.this,ForgetPassword.class);
                startActivity(i);

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try {
                    if(TextUtils.isEmpty(UserEmail.getText().toString()) && TextUtils.isEmpty(UserPassword.getText().toString()))
                    {

                        Toast.makeText(getApplicationContext(),"Enter Email and Password Before Login",Toast.LENGTH_LONG).show();


                    }


                    if((!TextUtils.isEmpty(UserEmail.getText().toString()) && !TextUtils.isEmpty(UserPassword.getText().toString()))) {

                        final Dialog dialog = new Dialog(Login.this);

                        dialog.setContentView(R.layout.custom_dialog);
                        dialog.setCanceledOnTouchOutside(false);

                        Button yes = (Button) dialog.findViewById(R.id.btn_yes);
                        Button no = (Button) dialog.findViewById(R.id.btn_no);


                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String email = UserEmail.getText().toString().trim();

                                String Password = UserPassword.getText().toString();

                                SharedPreferences.Editor editor = sp.edit();

                                editor.putString("Email", email);

                                editor.putString("Password", Password);

                                editor.commit();
                                dialog.dismiss();
                                login();


                            }
                        });

                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                SharedPreferences.Editor editor = sp.edit();
                                editor.clear();
                                editor.commit();
                                dialog.dismiss();

                                login();
                            }
                        });

                        dialog.show();

                    }




                }
                catch (Exception e)
                {

                    Toast.makeText(Login.this, "Error  : "+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    private void login()
    {





            validate(UserEmail.getText().toString(), UserPassword.getText().toString());



    }

    private void validate(String Username, final String Password)
    {

        final String Email = UserEmail.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(Username,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {

                try
                {
                    if (task.isSuccessful())

                    {
                        if(firebaseAuth.getCurrentUser().isEmailVerified())
                        {
                            progressDialog.setMessage("Login");
                            progressDialog.show();
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();


                            Intent i = new Intent(Login.this, Main2Activity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Email", UserEmail.getText().toString());
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                        else
                        {

                            Toast.makeText(Login.this, "Firsty Verify your Email", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(Login.this, ReVerifyEmail.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Email", UserEmail.getText().toString());
                            bundle.putString("Password", UserPassword.getText().toString());
                            i.putExtras(bundle);
                            startActivity(i);

                        }

                    }
                    else
                        {
                            progressDialog.setMessage("Login");
                            progressDialog.show();

                        Toast.makeText(Login.this, "Invalid Email ID and Password", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();


                        Counter--;
                        tv.setText("Number of attempts remaining : " + Counter);
                        if (Counter == 0)
                        {
                            btnLogin.setEnabled(false);

                        }
                    }
                }
                catch (Exception e)
                {



                }

            }
        });

    }
}
