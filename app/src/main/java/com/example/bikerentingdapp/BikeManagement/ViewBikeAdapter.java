package com.example.bikerentingdapp.BikeManagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikerentingdapp.R;

import java.util.ArrayList;

public class ViewBikeAdapter extends RecyclerView.Adapter<ViewBikeAdapter.ViewHolder> {
    ArrayList<bikeClass> list;
    public ViewBikeAdapter(ArrayList<bikeClass> list)
    {
        this.list = list;
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
        holder.Rent.setText(bike.getRent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView Reg;
        TextView Rent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Reg = itemView.findViewById(R.id.RegCardText);
            Rent = itemView.findViewById(R.id.RentCardText);
        }
    }
}
