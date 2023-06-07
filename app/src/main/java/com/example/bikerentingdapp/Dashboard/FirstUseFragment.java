package com.example.bikerentingdapp.Dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bikerentingdapp.R;
import com.example.bikerentingdapp.WalletManagement.CreateWalletActivity;
import com.example.bikerentingdapp.WalletManagement.Wallet;
import com.example.bikerentingdapp.databinding.FragmentFirstUseBinding;

import org.web3j.abi.datatypes.Int;

public class FirstUseFragment extends Fragment {

    Wallet userwallet;
    FragmentFirstUseBinding binding;

    public FirstUseFragment() {
        userwallet = new Wallet();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateWalletActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstUseBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}