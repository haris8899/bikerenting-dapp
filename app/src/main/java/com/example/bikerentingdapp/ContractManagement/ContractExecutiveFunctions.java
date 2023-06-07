package com.example.bikerentingdapp.ContractManagement;

import android.app.Activity;
import android.util.Log;

import com.example.bikerentingdapp.BuildConfig;
import com.example.bikerentingdapp.WalletManagement.WalletClass;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Wallet;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ContractExecutiveFunctions {
    String web3api;
    String Address;

    public ContractExecutiveFunctions() {
        this.web3api = BuildConfig.WEB3_API;
    }

    public String getWeb3api() {
        return web3api;
    }

    public String deployContract(Activity Context, String Password) throws CipherException, IOException, ExecutionException, InterruptedException {
        Web3j web3j = Web3j.build(new HttpService(web3api));
        Rental_sol_BikeRenting contract = null;
        Log.d("Tag","Pass: "+Password);
        contract = Rental_sol_BikeRenting.
                deploy(web3j,new WalletClass().LoadWalletCrediantials(Context,Password),new DefaultGasProvider()).sendAsync().get();
        String contractaddress = contract.getContractAddress();
        Log.d("Tag","Address"+contractaddress);
        return contractaddress;
    }

    public Rental_sol_BikeRenting LoadContract(Activity Context,String ContractAddress, String Password) throws CipherException, IOException {
        Web3j web3j = Web3j.build(new HttpService(web3api));
        Rental_sol_BikeRenting contract = Rental_sol_BikeRenting.load(ContractAddress,web3j,
                new WalletClass().LoadWalletCrediantials(Context, Password),new DefaultGasProvider());
        return contract;
    }
}
