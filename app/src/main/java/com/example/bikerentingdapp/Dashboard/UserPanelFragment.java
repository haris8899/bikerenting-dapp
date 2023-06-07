package com.example.bikerentingdapp.Dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bikerentingdapp.BikeManagement.RecyclerItemSelectListener;
import com.example.bikerentingdapp.BikeManagement.SingleViewActivity;
import com.example.bikerentingdapp.BikeManagement.ViewBikeAdapter;
import com.example.bikerentingdapp.BikeManagement.bikeClass;
import com.example.bikerentingdapp.R;
import com.example.bikerentingdapp.databinding.FragmentUserPanelBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserPanelFragment extends Fragment implements RecyclerItemSelectListener {

    FragmentUserPanelBinding bind;
    ArrayList<bikeClass> list;
    FirebaseDatabase database;
    DatabaseReference myref;
    ViewBikeAdapter adapter;
    public UserPanelFragment() {
        database = FirebaseDatabase.getInstance();
        myref = database.getReference().child("Contract").child("Bikes").getRef();
        list = new ArrayList<>();
        adapter = new ViewBikeAdapter(this,list);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentUserPanelBinding.inflate(getLayoutInflater());
        return bind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        bind.userrecyclerview.setLayoutManager(layoutManager);
        bind.userrecyclerview.setItemAnimator(new DefaultItemAnimator());
        bind.userrecyclerview.setAdapter(adapter);
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
    public void onItemClicked(int position) {
        Intent intent = new Intent(getActivity(), SingleViewActivity.class);
        intent.putExtra("A1",position);
        startActivity(intent);
    }
}