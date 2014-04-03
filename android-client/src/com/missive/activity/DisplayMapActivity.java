package com.missive.activity;

import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.missive.R;

public class DisplayMapActivity extends MapActivity {
	private static final int DEFAULT_ZOOM = 17;

	private MapController mapController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_map);

		initMapView();
	}

	private void initMapView() {
		final MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(false);

		mapView.postInvalidate();

		mapController = mapView.getController();
		mapController.setZoom(DEFAULT_ZOOM);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
