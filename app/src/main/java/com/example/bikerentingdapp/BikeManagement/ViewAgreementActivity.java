package com.example.bikerentingdapp.BikeManagement;

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
import com.example.bikerentingdapp.databinding.ActivityViewAgreementBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple10;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ViewAgreementActivity extends AppCompatActivity {

    ActivityViewAgreementBinding bind;
    Intent intent;
    String Password;
    String Address;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference ref;
    int position;
    SimpleDateFormat simpleformat;
    Rental_sol_BikeRenting contract;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityViewAgreementBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        getSupportActionBar().hide();
        intent = getIntent();
        position = intent.getIntExtra("A1",0);
        Password = intent.getStringExtra("A2");
        Address =  intent.getStringExtra("A3");
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        try {
            ViewAgreementAlertDialog(Address);
        } catch (CipherException e) {
            throw new RuntimeException(e);
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void ViewAgreementAlertDialog(String finalContractAddress) throws CipherException, IOException, ExecutionException, InterruptedException {

        contract = new ContractExecutiveFunctions().LoadContract(ViewAgreementActivity.this, finalContractAddress, Password);
        BigInteger big = BigInteger.valueOf(position);
        Tuple10<BigInteger, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String> receipt = contract.AgreementMapping(big).sendAsync().get();
        //Log.d("Tag", "REG: "+receipt.component2());
        String adv = Convert.fromWei(receipt.component4().toString(), Convert.Unit.ETHER).toString();
        String rent = Convert.fromWei(receipt.component3().toString(), Convert.Unit.ETHER).toString();
        Long l = receipt.component5().longValue();
        long unixTime = Instant.now().getEpochSecond();
        long x = unixTime - l;
        Float totalrent = (x/3600) * Float.parseFloat(rent);
        bind.CurrentrentText.setText(String.valueOf(totalrent));
        bind.BikeIDText.setText(receipt.component1().toString());
        bind.RegViewText.setText(receipt.component2());
        Log.d("TAG","Time: "+String.valueOf(l));
        simpleformat = new SimpleDateFormat("dd/MMMM/yyyy HH:mm a");
        Date date = new Date(TimeUnit.SECONDS.toMillis(l));
        String date1 = simpleformat.format(date);
        bind.StartTimeText.setText(date1);
        bind.RentConText.setText(rent + " ETH");
        bind.AdvanceConText.setText(rent + " ETH");
        String MyAddress = new WalletClass()
                .LoadWalletCrediantials(ViewAgreementActivity.this, Password).getAddress();
        bind.ViewRentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long unixTime = Instant.now().getEpochSecond();
                long x = unixTime - l;
                Float totalrent = (x/3600) * Float.parseFloat(rent);
                bind.CurrentrentText.setText(String.valueOf(totalrent));
            }
        });
        if(MyAddress.equals(receipt.component9()))
        {
            bind.ReturnBikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Float totalrent = currentRent(l,rent);
                    BigDecimal brent = Convert.toWei(String.valueOf(totalrent), Convert.Unit.ETHER);
                    ReturnBikeAlertDialog(finalContractAddress,brent.toBigInteger());
                }
            });
        }
        else
        {
            bind.ReturnBikeButton.setVisibility(View.GONE);
        }
    }

    public Float currentRent(Long l, String rent)
    {
        long unixTime = Instant.now().getEpochSecond();
        long x = unixTime - l;
        Float totalrent = (x/3600) * Float.parseFloat(rent);
        return totalrent;
    }
    public void ReturnBikeAlertDialog(String finalContractAddress, BigInteger Amount)
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
                    contract = new ContractExecutiveFunctions().LoadContract(ViewAgreementActivity.this, finalContractAddress, Password);
                    BigInteger big = BigInteger.valueOf(position);
                    //Log.d("Tag","Address: "+contract.getContractAddress());
                    TransactionReceipt receipt= contract.ReturnBike(big,Amount).sendAsync().get();
                    setClipboard(ViewAgreementActivity.this,receipt.getTransactionHash());
                    //Log.d("Tag",contract.getTransactionReceipt().toString());
                    database.getReference().child("Users").child(auth.getUid()).child("bike").removeValue();
                    dialog.cancel();
                    finish();
                } catch (Exception e) {
                    Toast.makeText(ViewAgreementActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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