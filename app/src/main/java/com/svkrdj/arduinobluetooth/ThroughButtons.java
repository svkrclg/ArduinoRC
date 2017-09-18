package com.svkrdj.arduinobluetooth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThroughButtons extends Fragment {


    public ThroughButtons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.through_buttons, container, false);
        return rootView;
    }
    @Override
    public void onStop() {
        super.onStop();
    }

}
