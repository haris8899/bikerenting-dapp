package com.example.bikerentingdapp.UserManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.bikerentingdapp.R;
import com.example.bikerentingdapp.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    //To  show loading
    ProgressDialog dilog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //line to hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        //Database instance
        database = FirebaseDatabase.getInstance();

        dilog = new ProgressDialog(SignupActivity.this);
        dilog.setTitle("Creating Account");
        dilog.setMessage("We're creating your account ");

        binding.RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //for data validation
                String  username= binding.usernameTextbox.getEditText().getText().toString().trim();
                String  email= binding.EmailSignupText.getEditText().getText().toString().trim();
                String  pass= binding.PasswordSignUpTextBox.getEditText().getText().toString().trim();
                String  pno= binding.phonetextBox.getEditText().getText().toString().trim();

                if (username.isEmpty()){
                    binding.usernameTextbox.setError("Username is Required");
                    return;
                }
                if (email.isEmpty()){
                    binding.EmailSignupText.setError("Email Address is Required");
                    return;
                }
                if (pass.isEmpty()){
                    binding.PasswordSignUpTextBox.setError("Password is Required");
                    return;
                }
                if (pno.isEmpty()){
                    binding.phonetextBox.setError("Phone no is Required");
                    return;
                }


                //To create user Account
                dilog.show();
                auth.createUserWithEmailAndPassword(binding.EmailSignupText.getEditText().getText().toString().trim(), binding.PasswordSignUpTextBox.getEditText().getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dilog.dismiss();
                        if (task.isSuccessful()) {
                            //To send email verification
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task1) {
                                    if(task1.isSuccessful()){
                                        //calling singnup constructor for UserRegistrationclass
                                        UserClass User = new UserClass(binding.usernameTextbox.getEditText().getText().toString().trim(),
                                                binding.EmailSignupText.getEditText().getText().toString().trim(),
                                                binding.PasswordSignUpTextBox.getEditText().getText().toString().trim(),
                                                binding.phonetextBox.getEditText().getText().toString().trim(),
                                                "Simple User");

                                        //get Userid from Authentication portal
                                        String id = task.getResult().getUser().getUid();
                                        //Add User to database
                                        database.getReference().child("Users").child(id).setValue(User);
                                        Toast.makeText(SignupActivity.this, "Account Created Successfully! Please check your email for verification", Toast.LENGTH_SHORT).show();
                                    } else{
                                        Toast.makeText(SignupActivity.this,task1.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}