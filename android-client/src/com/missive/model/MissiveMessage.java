package com.missive.model;

import java.util.Date;

public final class MissiveMessage {
    private final String userId;
    private final String body;
    private final MissiveLocation location;
    private final Date timePosted;

    public MissiveMessage(String userId, String body, MissiveLocation location, Date timePosted) {
        this.userId = userId;
        this.body = body;
        this.location = location;
        this.timePosted = timePosted;
    }

    public String getUserId() {
        return userId;
    }

    public String getBody() {
        return body;
    }

    public MissiveLocation getLocation() {
        return location;
    }

    public Date getTimePosted() {
        return timePosted;
    }

    @Override
    public String toString() {
        return userId + " says: " + body + "\n on " + timePosted;
    }
}
