package com.example.bikerentingdapp.UserManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.bikerentingdapp.R;
import com.example.bikerentingdapp.WalletManagement.CreateWalletActivity;
import com.example.bikerentingdapp.WalletManagement.ViewWalletActivity;
import com.example.bikerentingdapp.WalletManagement.Wallet;
import com.example.bikerentingdapp.databinding.ActivityUserProfilePageBinding;

public class UserProfilePage extends AppCompatActivity {

    ActivityUserProfilePageBinding binding;
    Wallet userwallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfilePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userwallet = new Wallet();
        if(userwallet.WalletExists(this))
        {
            Log.d("Tag","Wallet Exists");
            binding.Walletbutton.setText("View Wallet");
        }
        else {
            Log.d("Tag","Wallet not Exists");
            binding.Walletbutton.setText("Create Wallet");
        }
        binding.Walletbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userwallet.WalletExists(UserProfilePage.this))
                {
                    Log.d("Tag","Wallet Exists");
                    Intent intent = new Intent(UserProfilePage.this, ViewWalletActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.d("Tag","Wallet not Exists");
                    Intent intent = new Intent(UserProfilePage.this, CreateWalletActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}