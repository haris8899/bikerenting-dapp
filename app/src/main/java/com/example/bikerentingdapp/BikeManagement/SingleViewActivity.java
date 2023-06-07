package com.example.bikerentingdapp.BikeManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikerentingdapp.ContractManagement.ContractExecutiveFunctions;
import com.example.bikerentingdapp.ContractManagement.Rental_sol_BikeRenting;
import com.example.bikerentingdapp.R;
import com.example.bikerentingdapp.databinding.ActivitySingleViewBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple9;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;

public class SingleViewActivity extends AppCompatActivity {

    ActivitySingleViewBinding bind;
    FirebaseDatabase database;
    DatabaseReference ref;
    Intent intent;
    String Password;
    Rental_sol_BikeRenting contract;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivitySingleViewBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        intent = getIntent();
        position = intent.getIntExtra("A1",0);
        position = position +1;
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Contract");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Address = snapshot.child("ContractAddress").getValue().toString();
                ViewBikeAlertDialog(Address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void ViewBikeAlertDialog(String finalContractAddress)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setHint("Enter Wallet Password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Password = input.getText().toString();
                    try {
                        contract = new ContractExecutiveFunctions().LoadContract(SingleViewActivity.this, finalContractAddress, Password);
                        BigInteger big = BigInteger.valueOf(position);
                        Tuple9<BigInteger, String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, String, String> receipt = contract.bike_mapping(big).sendAsync().get();
                        //Log.d("Tag", "REG: "+receipt.component2());
                        bind.BikeIDText.setText(receipt.component1().toString());
                        bind.RegViewText.setText(receipt.component2());
                        bind.AdvanceText.setText(receipt.component3().toString());
                        bind.AvailabilityText.setText(receipt.component5().toString());
                        bind.RentViewText.setText(receipt.component6().toString());
                        //BigInteger timestamp = block.component5();
                    } catch (CipherException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.cancel();
                } catch (Exception e) {
                    Toast.makeText(SingleViewActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("Tag","Test: "+e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }
}