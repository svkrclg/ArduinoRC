package com.svkrdj.arduinobluetooth;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by skdj on 9/9/17.
 */

public class List extends ArrayAdapter<design> {
    List(Context context, ArrayList<design> words) {
        super(context, R.layout.list_item, words);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater sauravinflater = LayoutInflater.from(getContext());
        View customView = sauravinflater.inflate(R.layout.list_item, parent,false);
        design locals=getItem(position);
        TextView miwokword=(TextView) customView.findViewById(R.id.add);
        TextView defaultword = (TextView) customView.findViewById(R.id.name);
        miwokword.setText(locals.getMiwokTranslation());
        defaultword.setText(locals.getDefaultTranslation());
        return customView;
    }
}
