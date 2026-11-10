package com.example;

public enum TravelCardType {
    SCHOOL(30, 5),
    UNIVERSITY(30, 10),
    STANDARD(10, 10);

    private int initialTrips;
    private int initialPeriod;

    TravelCardType(int initialTrips, int initialPeriod) {
        this.initialTrips = initialTrips;
        this.initialPeriod = initialPeriod;
    }

    public int getInitialTrips() {
        return initialTrips;
    }

    public int getInitialPeriod() {
        return initialPeriod;
    }
}
