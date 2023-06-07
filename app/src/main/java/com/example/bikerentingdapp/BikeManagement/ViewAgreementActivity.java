package com.example.bikerentingdapp.BikeManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bikerentingdapp.R;
import com.example.bikerentingdapp.databinding.ActivityViewAgreementBinding;

public class ViewAgreementActivity extends AppCompatActivity {

    ActivityViewAgreementBinding bind;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewAgreementBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        intent = getIntent();
        int position = intent.getIntExtra("A1",0);
        Toast.makeText(ViewAgreementActivity.this,
                "pOS: "+String.valueOf(position),Toast.LENGTH_SHORT).show();
    }
}