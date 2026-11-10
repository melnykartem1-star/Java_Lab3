package com.example;

import java.rmi.server.UID;

public abstract class TravelCard {

    private static long counter = 0L;
    private final long UID;

    TravelCard() {
        this.UID = ++counter;
    }

    public long getUID() {
        return UID;
    }
    public abstract TravelCardType getType();
}
