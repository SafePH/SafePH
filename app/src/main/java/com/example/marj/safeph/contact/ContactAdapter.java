package com.example.marj.safeph.contact;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marj.safeph.R;

import java.util.ArrayList;

/**
 * Created by Marj on 3/30/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private ArrayList<ContactModel> contacts;

    public ContactAdapter(ArrayList<ContactModel> storeContacts) {
        contacts = storeContacts;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_contacts_item,parent,false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, int position) {
        final ContactModel contact = contacts.get(position);
        holder.getContactRelation().setText(contact.getRelation());
        holder.getContactName().setText(contact.getName());
        holder.getContactPhone().setText(contact.getPhone());
        holder.getContactBackground().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context,ContactDetails.class);
                intent.putExtra("name", contact.getName());
                intent.putExtra("phone", contact.getPhone());
                intent.putExtra("relation", contact.getRelation());
                context.startActivity(intent);
            }
        });
        holder.getContactCallBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contact.getPhone()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(callIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
