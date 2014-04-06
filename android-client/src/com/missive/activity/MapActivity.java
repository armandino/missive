package com.missive.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.missive.R;

public class MapActivity extends FragmentActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map_container, new MapFragment())
                .commit();
    }

}
