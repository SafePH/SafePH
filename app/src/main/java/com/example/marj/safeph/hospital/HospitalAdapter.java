package com.example.marj.safeph.hospital;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marj.safeph.R;

import java.util.ArrayList;

/**
 * Created by Marj on 4/2/2018.
 */

public class HospitalAdapter extends RecyclerView.Adapter<HospitalViewHolder> {
    private ArrayList<HospitalModel> hospitals;

    public HospitalAdapter(ArrayList<HospitalModel> storeHospitals) {
        hospitals = storeHospitals;
    }

    @Override
    public HospitalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_hospital_item,parent,false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HospitalViewHolder holder, int position) {
        final HospitalModel hospital = hospitals.get(position);
        holder.getHospitalName().setText(hospital.getName());
        holder.getHospitalAddress().setText(hospital.getAddress());
        holder.getHospitalNavigateBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+hospital.getLat()+","+hospital.getLng());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }
}
