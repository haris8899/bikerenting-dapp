package com.example.bikerentingdapp.BikeManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.example.bikerentingdapp.WalletManagement.WalletClass;
import com.example.bikerentingdapp.databinding.ActivitySingleViewBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple10;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class SingleViewActivity extends AppCompatActivity {

    ActivitySingleViewBinding bind;
    FirebaseDatabase database;
    FirebaseAuth auth;
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
        auth = FirebaseAuth.getInstance();
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
                        String adv = Convert.fromWei(receipt.component3().toString(),Convert.Unit.ETHER).toString();
                        String rent = Convert.fromWei(receipt.component6().toString(),Convert.Unit.ETHER).toString();
                        bind.BikeIDText.setText(receipt.component1().toString());
                        bind.RegViewText.setText(receipt.component2());
                        bind.AdvanceText.setText(adv + " ETH");
                        bind.AvailabilityText.setText(receipt.component5().toString());
                        bind.RentViewText.setText(rent + " ETH");
                        String MyAddress = new WalletClass()
                                .LoadWalletCrediantials(SingleViewActivity.this,Password).getAddress();
                        //Log.d("Tag","Addr: "+receipt.component8());
                        if(MyAddress.equals(receipt.component8()))
                        {
                            bind.MainBikeButton.setVisibility(View.GONE);
                            if(!receipt.component5())
                            {
                                bind.MainBikeButton.setVisibility(View.VISIBLE);
                                bind.MainBikeButton.setText("View Contract");
                                bind.MainBikeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent1 = new Intent(SingleViewActivity.this, ViewAgreementActivity.class);
                                        intent1.putExtra("A1",position);
                                        intent1.putExtra("A2",Password);
                                        intent1.putExtra("A3",finalContractAddress);
                                        startActivity(intent1);
                                    }
                                });
                            }
                        }
                        else
                        {
                            if(MyAddress.equals(receipt.component9()))
                            {
                                bind.MainBikeButton.setText("View Contract");
                                bind.MainBikeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent1 = new Intent(SingleViewActivity.this, ViewAgreementActivity.class);
                                        intent1.putExtra("A1",position);
                                        intent1.putExtra("A2",Password);
                                        intent1.putExtra("A3",finalContractAddress);
                                        startActivity(intent1);
                                    }
                                });
                            }
                            else if(receipt.component5().toString().equals("False"))
                            {
                                bind.MainBikeButton.setVisibility(View.GONE);
                            }
                            else
                            {
                                database.getReference().addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.child("Users").child(auth.getUid()).child("bike").exists())
                                        {
                                            bind.MainBikeButton.setText("Contract Already Active");
                                            bind.MainBikeButton.setEnabled(false);
                                        }
                                        else
                                        {
                                            bind.MainBikeButton.setText("Rent");
                                            bind.MainBikeButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Float amount = Float.valueOf(adv) + Float.valueOf(rent);
                                                    BigDecimal brent = Convert.toWei(String.valueOf(amount), Convert.Unit.ETHER);
                                                    SignAgreementAlertDialog(finalContractAddress,brent.toBigInteger());
                                                    database.getReference()
                                                            .child("Users")
                                                            .child(auth.getUid()).child("bike").setValue(position);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
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

    public void SignAgreementAlertDialog(String finalContractAddress, BigInteger Amount)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setHint("Enter Wallet Password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setCancelable(false);
        setFinishOnTouchOutside(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Password = input.getText().toString();
                try {
                    //Log.d("Tag","Final: "+finalContractAddress);
                    contract = new ContractExecutiveFunctions().LoadContract(SingleViewActivity.this, finalContractAddress, Password);
                    //Log.d("Tag","Address: "+contract.getContractAddress());
                    TransactionReceipt receipt= contract.signAgreement(new BigInteger(String.valueOf(position)),Amount).sendAsync().get();
                    Log.d("Tag","Hash: "+receipt.getTransactionHash());
                    setClipboard(SingleViewActivity.this, receipt.getTransactionHash());
                    //Log.d("Tag",contract.getTransactionReceipt().toString());
                    dialog.cancel();
                } catch (Exception e) {
                    Toast.makeText(SingleViewActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("Tag",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }
    private void setClipboard(Context context, String text) {
        android.content.ClipboardManager clipboard =
                (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this,
                "Transaction Hash copied to clipboard",Toast.LENGTH_SHORT).show();
    }
}