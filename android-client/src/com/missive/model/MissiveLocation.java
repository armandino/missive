package com.missive.model;

public final class MissiveLocation {
    private final double lat;
    private final double lon;

    public MissiveLocation(final double lat, final double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
