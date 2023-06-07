package com.example.bikerentingdapp.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bikerentingdapp.R;
import com.example.bikerentingdapp.databinding.FragmentDeployContractBinding;

public class DeployContractFragment extends Fragment {

    FragmentDeployContractBinding bind;
    public DeployContractFragment() {
        // Required empty public constructor
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
}