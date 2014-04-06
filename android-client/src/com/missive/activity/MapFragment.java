package com.missive.activity;

import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.util.CloudmadeUtil;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends Fragment {
    private static final Logger log = LoggerFactory.getLogger(MapFragment.class);

    private static final String PREFS_NAME = "org.andnav.osm.prefs";
    private static final String PREFS_TILE_SOURCE = "tilesource";
    private static final String PREFS_SCROLL_X = "scrollX";
    private static final String PREFS_SCROLL_Y = "scrollY";
    private static final String PREFS_ZOOM_LEVEL = "zoomLevel";

    private SharedPreferences mPrefs;
    private MapView mMapView;
    private MyLocationNewOverlay mLocationOverlay;
    private CompassOverlay mCompassOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ResourceProxy mResourceProxy = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
        mMapView = new MapView(inflater.getContext(), 256, mResourceProxy);
        mMapView.setUseSafeCanvas(true);
        // Call this method to turn off hardware acceleration at the View level.
        // setHardwareAccelerationOff();
        return mMapView;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setHardwareAccelerationOff() {
        // Turn off hardware acceleration here, or in manifest
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mMapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Context context = this.getActivity();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();

        mPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (CloudmadeUtil.getCloudmadeKey().length() == 0) {
            CloudmadeUtil.retrieveCloudmadeKey(context.getApplicationContext());
        }

        mCompassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), mMapView);
        mLocationOverlay = new MyLocationNewOverlay(context, new GpsMyLocationProvider(context), mMapView);

        mMinimapOverlay = new MinimapOverlay(getActivity(), mMapView.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);

        mScaleBarOverlay = new ScaleBarOverlay(context);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);

        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapView.getOverlays().add(mLocationOverlay);
        mMapView.getOverlays().add(mCompassOverlay);
        mMapView.getOverlays().add(mMinimapOverlay);
        mMapView.getOverlays().add(mScaleBarOverlay);

        mMapView.getController().setZoom(mPrefs.getInt(PREFS_ZOOM_LEVEL, 1));
        mMapView.scrollTo(mPrefs.getInt(PREFS_SCROLL_X, 0), mPrefs.getInt(PREFS_SCROLL_Y, 0));

        mLocationOverlay.enableMyLocation();
        mCompassOverlay.enableCompass();

        setHasOptionsMenu(false); // Hide OSM menu
    }

    @Override
    public void onPause() {
        final SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString(PREFS_TILE_SOURCE, mMapView.getTileProvider().getTileSource().name());
        edit.putInt(PREFS_SCROLL_X, mMapView.getScrollX());
        edit.putInt(PREFS_SCROLL_Y, mMapView.getScrollY());
        edit.putInt(PREFS_ZOOM_LEVEL, mMapView.getZoomLevel());
        edit.commit();

        mLocationOverlay.disableMyLocation();
        mCompassOverlay.disableCompass();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        final String tileSourceName = mPrefs.getString(PREFS_TILE_SOURCE, TileSourceFactory.DEFAULT_TILE_SOURCE.name());
        try {
            mMapView.setTileSource(TileSourceFactory.getTileSource(tileSourceName));
        } catch (IllegalArgumentException ex) {
            log.error("onResume() error", ex);
        }
    }
}
