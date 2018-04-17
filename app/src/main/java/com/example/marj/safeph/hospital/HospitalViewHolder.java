package com.example.marj.safeph.hospital;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marj.safeph.R;

/**
 * Created by Marj on 4/2/2018.
 */

public class HospitalViewHolder extends RecyclerView.ViewHolder {
    private Button hospitalNavigateBtn;
    private TextView hospitalName;
    private TextView hospitalAddress;

    public HospitalViewHolder(View itemView) {
        super(itemView);
        hospitalNavigateBtn = itemView.findViewById(R.id.navigate_btn);
        hospitalName = itemView.findViewById(R.id.nhi_name);
        hospitalAddress = itemView.findViewById(R.id.nhi_address);
    }

    public Button getHospitalNavigateBtn() {
        return hospitalNavigateBtn;
    }

    public TextView getHospitalName() {
        return hospitalName;
    }

    public TextView getHospitalAddress() {
        return hospitalAddress;
    }
}
