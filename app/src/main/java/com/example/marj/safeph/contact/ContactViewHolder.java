package com.example.marj.safeph.contact;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marj.safeph.R;

/**
 * Created by Marj on 3/30/2018.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder {
    private ImageView contactBackground;
    private ImageView contactRelationBackground;
    private TextView contactRelation;
    private TextView contactName;
    private TextView contactPhone;
    private ImageView contactCallBtn;

    public ContactViewHolder(View itemView) {
        super(itemView);
        contactBackground = itemView.findViewById(R.id.eci_item);
        contactRelationBackground = itemView.findViewById(R.id.eca_relation_box);
        contactRelation = itemView.findViewById(R.id.eci_relation);
        contactName = itemView.findViewById(R.id.eci_name);
        contactPhone = itemView.findViewById(R.id.eci_phone);
        contactCallBtn = itemView.findViewById(R.id.eci_call);
    }

    public ImageView getContactBackground() {
        return contactBackground;
    }

    public ImageView getContactRelationBackground() {
        return contactRelationBackground;
    }

    public TextView getContactRelation() {
        return contactRelation;
    }

    public TextView getContactName() {
        return contactName;
    }

    public TextView getContactPhone() {
        return contactPhone;
    }

    public ImageView getContactCallBtn() {
        return contactCallBtn;
    }
}
