package com.example.marj.safeph.hotline;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.marj.safeph.R;

import java.util.ArrayList;


/**
 * Created by Marj on 3/13/2018.
 */

public class HotlineAdapter extends RecyclerView.Adapter<HotlineViewHolder> {
    private ArrayList<HotlineModel> hotlines;

    public HotlineAdapter(ArrayList<HotlineModel> storeHotlines) {
        hotlines = storeHotlines;
    }

    @Override
    public HotlineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotline_item_layout,parent,false);
        return new HotlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HotlineViewHolder holder, int position) {
        final HotlineModel hotline = hotlines.get(position);
        holder.getHotlineName().setText(hotline.getName());
        holder.getHotlinePhone().setText(hotline.getPhone());
        holder.getHotlineCallBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+hotline.getPhone()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(callIntent);
            }
        });
        if (!hotline.getName().contains("Default")){
            holder.getHotlineBackground().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context,HotlineDetails.class);
                    intent.putExtra("name", hotline.getName());
                    intent.putExtra("phone", hotline.getPhone());
                    context.startActivity(intent);
                }
            });
        }else{
            holder.getHotlineBackground().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Cannot be edited/deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return hotlines.size();
    }
}
