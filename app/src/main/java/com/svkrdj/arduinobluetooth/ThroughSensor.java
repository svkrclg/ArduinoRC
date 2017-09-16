package com.svkrdj.arduinobluetooth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThroughSensor extends Fragment {


    public ThroughSensor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.through_sensor, container, false);
        return rootView;
    }
    @Override
    public void onStop() {
        super.onStop();
    }

}
