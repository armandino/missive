package com.missive.activity;

import android.location.Location;

public interface LocationChangeHandler {
    void userLocationChanged(Location newLocation);
}
