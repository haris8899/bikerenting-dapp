package com.example.bikerentingdapp.WalletManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bikerentingdapp.UserManagement.UserProfilePage;
import com.example.bikerentingdapp.databinding.ActivityCreateWalletBinding;
import com.google.firebase.auth.FirebaseAuth;

public class CreateWalletActivity extends AppCompatActivity {

    FirebaseAuth auth;
    ActivityCreateWalletBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityCreateWalletBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        bind.CreateWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean bool = new WalletClass().WalletExists(CreateWalletActivity.this);
                if (bool) {
                    Log.d("Tag", "True");
                } else {
                    Log.d("Tag", "False");
                }
                if (bind.EnterWalletPasswordText.getText().toString().length() >= 6) {
                    new WalletClass().CreateNewWallet(CreateWalletActivity.this,
                            bind.EnterWalletPasswordText.getText().toString());
                    Toast.makeText(CreateWalletActivity.this,
                            "Wallet Created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateWalletActivity.this, UserProfilePage.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(CreateWalletActivity.this,
                            "Password cannot have less than 6 characters", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}