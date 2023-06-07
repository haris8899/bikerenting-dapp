package com.example.bikerentingdapp.Dashboard;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikerentingdapp.ContractManagement.ContractExecutiveFunctions;
import com.example.bikerentingdapp.databinding.FragmentDeployContractBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class DeployContractFragment extends Fragment {

    FragmentDeployContractBinding bind;
    FirebaseDatabase database;
    DatabaseReference myref;
    public DeployContractFragment() {
        database = FirebaseDatabase.getInstance();
        myref = database.getReference();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentDeployContractBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        bind.DeployContractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeployAlertDialog();
            }
        });
    }

    public void DeployAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        final EditText input = new EditText(getContext());
        input.setHint("Enter Wallet Password");
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String address = new ContractExecutiveFunctions().deployContract(getActivity(),input.getText().toString());
                    myref.child("Contract").child("ContractAddress").setValue(address);
                    Log.d("Tag","Dialog: Contract Address: "+address);
                    dialog.cancel();
                } catch (Exception e) {
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.d("Tag",e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }
}