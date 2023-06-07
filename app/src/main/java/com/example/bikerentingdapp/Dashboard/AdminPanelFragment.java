package com.example.bikerentingdapp.Dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikerentingdapp.BikeManagement.AddBikeActivity;
import com.example.bikerentingdapp.BikeManagement.ViewBikeAdapter;
import com.example.bikerentingdapp.BikeManagement.bikeClass;
import com.example.bikerentingdapp.ContractManagement.ContractExecutiveFunctions;
import com.example.bikerentingdapp.ContractManagement.Rental_sol_BikeRenting;
import com.example.bikerentingdapp.R;
import com.example.bikerentingdapp.databinding.FragmentAdminPanelBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple10;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import jnr.ffi.annotations.In;

public class AdminPanelFragment extends Fragment {

    FragmentAdminPanelBinding bind;
    FirebaseDatabase database;
    DatabaseReference ref;
    DatabaseReference myref;
    ArrayList<bikeClass> list;
    ViewBikeAdapter adapter;
    public AdminPanelFragment() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        list = new ArrayList<>();
        adapter = new ViewBikeAdapter(list);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        bind.bikerecyclerview.setLayoutManager(layoutManager);
        bind.bikerecyclerview.setItemAnimator(new DefaultItemAnimator());
        bind.bikerecyclerview.setAdapter(adapter);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String address = snapshot.child("Contract")
                        .child("ContractAddress").getValue().toString();
                bind.ContractAddressText.setText(address);
                bind.ContractAddressText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setClipboard(getContext(),address);
                    }
                });
                bind.AddbikeDashboardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AddBikeActivity.class);
                        intent.putExtra("A1",address);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myref = database.getReference().child("Contract").child("Bikes").getRef();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    String reg = dataSnapshot.child("reg").getValue().toString();
                    String rent = dataSnapshot.child("rent").getValue().toString();
                    list.add(new bikeClass(reg,rent));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentAdminPanelBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }
    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard =
                    (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard =
                    (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(getContext(),
                "Text copied to clipboard",Toast.LENGTH_SHORT).show();
    }


}