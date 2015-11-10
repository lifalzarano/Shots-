package com.example.laurenfalzarano.shots.Models;

import android.view.View;
import android.widget.TextView;

import com.example.laurenfalzarano.shots.R;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 11/9/15.
 */
public class FontAwesomeViewHolder
{
    @Bind(R.id.item_icon) public IconTextView itemIcon;
    @Bind(R.id.item_name) public TextView itemName;

    public FontAwesomeViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}