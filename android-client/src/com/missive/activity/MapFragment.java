package com.missive.activity;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.util.CloudmadeUtil;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
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
import android.widget.Toast;

import com.missive.model.MissiveMessage;
import com.missive.service.MissiveServerFacade;

public class MapFragment extends Fragment {
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(MapFragment.class);

    private static final String PREFS_NAME = "org.andnav.osm.prefs";
    private static final String PREFS_TILE_SOURCE = "tilesource";
    private static final String PREFS_SCROLL_X = "scrollX";
    private static final String PREFS_SCROLL_Y = "scrollY";
    private static final int DEFAULT_ZOOM = 12;

    private SharedPreferences mPrefs;
    private MapView mMapView;
    private MyLocationNewOverlay mMyLocationOverlay;
    private CompassOverlay mCompassOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private ResourceProxy mResourceProxy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mResourceProxy = new ResourceProxyImpl(inflater.getContext().getApplicationContext());
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
        mMyLocationOverlay = new MyLocationNewOverlay(context, new GpsMyLocationProvider(context), mMapView);

        mMinimapOverlay = new MinimapOverlay(getActivity(), mMapView.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);

        mScaleBarOverlay = new ScaleBarOverlay(context);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);

        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapView.getOverlays().add(mMyLocationOverlay);
        mMapView.getOverlays().add(mCompassOverlay);
        mMapView.getOverlays().add(mMinimapOverlay);
        mMapView.getOverlays().add(mScaleBarOverlay);
        mMapView.getOverlays().add(getMissiveOverlay());

        final IMapController mapController = mMapView.getController();
        mapController.setZoom(DEFAULT_ZOOM);

        mCompassOverlay.enableCompass();
        mMyLocationOverlay.enableMyLocation();

        mMapView.getController().setCenter(new GeoPoint(49.276318, -123.134154));

        setHasOptionsMenu(false); // Hide OSM menu
    }

    private List<OverlayItem> createMissiveMessagesOverlay() {
        final MissiveServerFacade serverFacade = new MissiveServerFacade();
        final List<MissiveMessage> messages = serverFacade.getMessages();
        final List<OverlayItem> items = new ArrayList<OverlayItem>();

        for (MissiveMessage message : messages) {
            GeoPoint geoPoint = new GeoPoint(message.getLocation().getLat(), message.getLocation().getLon());
            items.add(new OverlayItem(message.getUserId(), message.toString(), geoPoint));
        }
        return items;
    }

    private Overlay getMissiveOverlay() {
        final Overlay overlay = new ItemizedIconOverlay<OverlayItem>(createMissiveMessagesOverlay(),
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        showMessage(item);
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        showMessage(item);
                        return true;
                    }

                    private void showMessage(OverlayItem item) {
                        Toast.makeText(MapFragment.this.getActivity(), item.getSnippet(), Toast.LENGTH_LONG).show();
                    }

                }, mResourceProxy);

        return overlay;
    }

    @Override
    public void onPause() {
        final SharedPreferences.Editor edit = mPrefs.edit();
        edit.putString(PREFS_TILE_SOURCE, mMapView.getTileProvider().getTileSource().name());
        edit.putInt(PREFS_SCROLL_X, mMapView.getScrollX());
        edit.putInt(PREFS_SCROLL_Y, mMapView.getScrollY());
        edit.commit();

        mMyLocationOverlay.disableMyLocation();
        mCompassOverlay.disableCompass();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
    }

}
