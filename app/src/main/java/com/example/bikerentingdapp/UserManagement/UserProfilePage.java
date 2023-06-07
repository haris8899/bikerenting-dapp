package com.example.bikerentingdapp.UserManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.bikerentingdapp.Dashboard.DeployContractFragment;
import com.example.bikerentingdapp.Dashboard.FirstUseFragment;
import com.example.bikerentingdapp.Dashboard.MissingContractFragment;
import com.example.bikerentingdapp.R;
import com.example.bikerentingdapp.WalletManagement.CreateWalletActivity;
import com.example.bikerentingdapp.WalletManagement.ViewWalletActivity;
import com.example.bikerentingdapp.WalletManagement.Wallet;
import com.example.bikerentingdapp.databinding.ActivityUserProfilePageBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfilePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActivityUserProfilePageBinding binding;
    Wallet userwallet;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference myref;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfilePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myref = database.getReference();
        if(new Wallet().WalletExists(this))
        {
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("Users").child(auth.getUid()).child("type").getValue().equals("Admin"))
                    {
                        if(snapshot.child("Contract").exists())
                        {

                        }
                        else
                        {
                            loadFragment(new DeployContractFragment());
                        }
                    }
                    else
                    {
                        if(snapshot.child("Contract").exists())
                        {

                        }
                        else
                        {
                            loadFragment(new MissingContractFragment());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            loadFragment(new FirstUseFragment());
        }
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navigationView;
        navigationDrawer();
        //userwallet = new Wallet();
//        if(userwallet.WalletExists(this))
//        {
//            Log.d("Tag","Wallet Exists");
//            binding.Walletbutton.setText("View Wallet");
//        }
//        else {
//            Log.d("Tag","Wallet not Exists");
//            binding.Walletbutton.setText("Create Wallet");
//        }
//        binding.Walletbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(userwallet.WalletExists(UserProfilePage.this))
//                {
//                    Log.d("Tag","Wallet Exists");
//                    Intent intent = new Intent(UserProfilePage.this, ViewWalletActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//                else {
//                    Log.d("Tag","Wallet not Exists");
//                    Intent intent = new Intent(UserProfilePage.this, CreateWalletActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });

    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // For navigation menu item clicked
        Intent intent;
        if(item.getItemId() ==R.id.nav_Logout)
        {
            auth.signOut();
            finish();
            intent= new Intent(UserProfilePage.this, SigninActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() ==R.id.user_Wallet_DrawerMenu)
        {
            if(new Wallet().WalletExists(this))
            {
                intent = new Intent(UserProfilePage.this, ViewWalletActivity.class);
                startActivity(intent);
            }
            else
            {
                intent = new Intent(UserProfilePage.this, CreateWalletActivity.class);
                startActivity(intent);
            }
        }
//        if (item.getItemId() == R.id.nav_home) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else if (item.getItemId() == R.id.nav_Logout) {
//            auth.signOut();
//            getActivity().finish();
//            Intent intent= new Intent(getActivity(), UserLoginActivity.class);
//            startActivity(intent);
//        }
//        else if(item.getItemId() == R.id.view_contracts_menu)
//        {
//            Intent intent = new Intent(getActivity(), ViewMyContracts.class);
//            startActivity(intent);
//        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        if(new Wallet().WalletExists(UserProfilePage.this))
        {
            navigationView.getMenu().findItem(R.id.user_Wallet_DrawerMenu).setTitle("View Wallet");
        }
        else
        {
            navigationView.getMenu().findItem(R.id.user_Wallet_DrawerMenu).setTitle("Create Wallet");
        }
        binding.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    public void loadFragment(Fragment fr)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.dashboardcontainer, fr);
        ft.commit();
    }
}