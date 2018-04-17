package com.example.marj.safeph.hotline;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marj.safeph.R;

/**
 * Created by Marj on 3/13/2018.
 */

public class HotlineViewHolder extends RecyclerView.ViewHolder {
    private ImageView hotlineBackground;
    private TextView hotlineName;
    private TextView hotlinePhone;
    private ImageView hotlineCallBtn;

    public HotlineViewHolder(View view) {
        super(view);
        hotlineBackground = view.findViewById(R.id.hotline_item_background);
        hotlineName = view.findViewById(R.id.hotline_name);
        hotlinePhone = view.findViewById(R.id.hotline_phone);
        hotlineCallBtn = view.findViewById(R.id.hotline_call_btn);
    }

    public ImageView getHotlineBackground() {
        return hotlineBackground;
    }

    public TextView getHotlineName() {
        return hotlineName;
    }

    public TextView getHotlinePhone() {
        return hotlinePhone;
    }

    public ImageView getHotlineCallBtn() {
        return hotlineCallBtn;
    }
}
