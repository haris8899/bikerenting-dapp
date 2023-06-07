package com.example.bikerentingdapp.BikeManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikerentingdapp.ContractManagement.ContractExecutiveFunctions;
import com.example.bikerentingdapp.ContractManagement.Rental_sol_BikeRenting;
import com.example.bikerentingdapp.R;
import com.example.bikerentingdapp.databinding.ActivityAddBikeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class AddBikeActivity extends AppCompatActivity {

    ActivityAddBikeBinding bind;
    String Password;
    Rental_sol_BikeRenting contract;
    FirebaseDatabase database;
    DatabaseReference ref;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityAddBikeBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        intent = getIntent();
        String address = intent.getStringExtra("A1");
        bind.Addbikebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reg = bind.RegnoText.getText().toString();
                String rent = bind.RentText.getText().toString();
                BigDecimal brent = Convert.toWei(rent, Convert.Unit.ETHER);
                AddBikeAlertDialog(address,reg,brent.toBigInteger(),brent.toBigInteger());
                //finish();
            }
        });
    }
    public void AddBikeAlertDialog(String finalContractAddress,
                                   String Title, BigInteger rent, BigInteger advance)
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
                        contract = new ContractExecutiveFunctions().LoadContract(AddBikeActivity.this, finalContractAddress, Password);
                        TransactionReceipt receipt = contract.addBike(Title,rent,advance).sendAsync().get();
                        Log.d("Tag", "Hash: "+receipt.getTransactionHash());
                        //BigInteger timestamp = block.component5();
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.child("Contract").child("TotalBikes").exists())
                                {
                                    String Totalbikes = snapshot.child("Contract").child("TotalBikes").getValue().toString();
                                    int totalbikes = Integer.parseInt(Totalbikes);
                                    totalbikes = totalbikes+1;
                                    ref.child("Contract").child("TotalBikes").setValue(totalbikes);
                                    ref.child("Contract").child("Bikes").child(String.valueOf(totalbikes))
                                            .setValue(new bikeClass(Title,rent.toString()));
                                }
                                else
                                {
                                    ref.child("Contract").child("TotalBikes").setValue("1");
                                    ref.child("Contract").child("Bikes").child("1")
                                            .setValue(new bikeClass(Title,rent.toString()));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } catch (CipherException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.cancel();
                } catch (Exception e) {
                    Toast.makeText(AddBikeActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("Tag","Test: "+e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }
}