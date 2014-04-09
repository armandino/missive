package com.missive.activity;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.missive.app.context.Missive;

public class UserLocationListener implements LocationListener {
    private static final Logger log = LoggerFactory.getLogger(UserLocationListener.class);

    private static final int MIN_TIME_MILLIS = 500;
    private static final int MIN_DISTANCE_METERS = 10;

    private final LocationManager mLocationManager;
    private final LocationChangeHandler mLocationChangeHandler;

    private Location mLastLocation;

    public UserLocationListener(final LocationChangeHandler locationChangeHandler) {
        Context ctx = Missive.getInstance().getApplicationContext();
        mLocationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        mLocationChangeHandler = locationChangeHandler;
    }

    @Override
    public void onLocationChanged(final Location newLocation) {
        if (newLocation == null) {
            return;
        }

        if (mLastLocation != null) {
            notifyHandler(newLocation);
        }

        mLastLocation = newLocation;
    }

    private void notifyHandler(final Location newLocation) {
        mLocationChangeHandler.userLocationChanged(newLocation);
    }

    @Override
    public void onProviderEnabled(final String provider) {
        if (GPS_PROVIDER.equals(provider)) {
            registerForUpdates(GPS_PROVIDER);
        }
    }

    @Override
    public void onProviderDisabled(final String provider) {
        // no-op
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {
        // no-op
    }

    boolean isGpsEnabled() {
        return mLocationManager.isProviderEnabled(GPS_PROVIDER);
    }

    void stopUpdates() {
        mLocationManager.removeUpdates(this);
    }

    void startUpdates() {
        registerForUpdates(GPS_PROVIDER);
        registerForUpdates(NETWORK_PROVIDER);
    }

    void registerForUpdates(final String provider) {
        try {
            mLocationManager.requestLocationUpdates(provider, MIN_TIME_MILLIS, MIN_DISTANCE_METERS, this);
        } catch (Exception ex) {
            log.error("Unable to register with provider: " + provider, ex);
        }
    }

}