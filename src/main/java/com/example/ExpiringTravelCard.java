package com.example;

import java.time.LocalDate;

public class ExpiringTravelCard extends TravelCard {
    private TravelCardType type;
    private LocalDate expiringDate;
    private int trips;


    ExpiringTravelCard(TravelCardType cardType) {
        super();
        this.type = cardType;
        this.trips = type.getInitialTrips();
        this.expiringDate = LocalDate.now().plusDays(type.getInitialPeriod());
    }

    void charge(){
        trips--;
    }

    public LocalDate getExpiringDate() {
        return expiringDate;
    }

    public int getTrips() {
        return trips;
    }

    public TravelCardType getType() {return type;}
}
