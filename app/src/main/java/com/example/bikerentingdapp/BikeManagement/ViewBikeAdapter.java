package com.example.bikerentingdapp.BikeManagement;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerentingdapp.R;

import org.web3j.utils.Convert;

import java.util.ArrayList;

public class ViewBikeAdapter extends RecyclerView.Adapter<ViewBikeAdapter.ViewHolder> {
    ArrayList<bikeClass> list;
    private RecyclerItemSelectListener itemSelectListener;
    public ViewBikeAdapter(RecyclerItemSelectListener l,ArrayList<bikeClass> list)
    {
        this.list = list;
        this.itemSelectListener = l;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bikeaddcard,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bikeClass bike = list.get(position);
        holder.Reg.setText(bike.getReg());
        String rent = Convert.fromWei(bike.getRent(),Convert.Unit.ETHER).toString();
        holder.Rent.setText(rent + " ETH");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView Reg;
        TextView Rent;
        CardView bikecard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Reg = itemView.findViewById(R.id.RegCardText);
            Rent = itemView.findViewById(R.id.RentCardText);
            bikecard = itemView.findViewById(R.id.bikecardview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemSelectListener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION);
                        {
                            itemSelectListener.onItemClicked(position);
                        }
                    }
                }
            });
        }
    }
}
