package com.example;

import java.time.LocalDate;
import java.util.*;

public class Turnstile {

    private static final int FARE = 8;
    private final CardRegister cardRegister;
    private final List<PassRecord> accessLog = new ArrayList<>();
    public record PassRecord(TravelCardType cardType, TurnstileAccessState state) {}

    public Turnstile(CardRegister cardRegister) {
        this.cardRegister = cardRegister;
    }

    public void checkCardAccess(TravelCard card){
        if(!cardRegister.checkCardExistence(card)){
            logAndPrint(card, TurnstileAccessState.BLOCKED_EXISTENCE);
            return;
        }
        if(card instanceof ReloadableTravelCard){checkReloadableCardAccess((ReloadableTravelCard) card);}
        else {checkExpiringCardAccess((ExpiringTravelCard) card);}
    }

    private void checkReloadableCardAccess(ReloadableTravelCard card){
        if(card.getBalance() < FARE){
            logAndPrint(card, TurnstileAccessState.BLOCKED_FUNDS);
            return;
        }
        card.charge(FARE);
        logAndPrint(card, TurnstileAccessState.GRANTED);
    }

    private void checkExpiringCardAccess(ExpiringTravelCard card){
        LocalDate currentDate = LocalDate.now();
        if (currentDate.isAfter(card.getExpiringDate())){
            logAndPrint(card, TurnstileAccessState.BLOCKED_PERIOD);
            return;
        }
        else if (card.getTrips() == 0) {
            logAndPrint(card, TurnstileAccessState.BLOCKED_TRIPS);
            return;
        }
        card.charge();
        logAndPrint(card, TurnstileAccessState.GRANTED);
    }

    private void logAndPrint(TravelCard card, TurnstileAccessState state) {
        printAccessMessage(state);
        TravelCardType type = (card != null) ? card.getType() : null;
        accessLog.add(new PassRecord(type, state));
    }

    private void printAccessMessage(TurnstileAccessState state){
        switch (state){
            case GRANTED -> IO.println("Access allowed. Have a pleasant trip!");
            case BLOCKED_FUNDS -> IO.println("Access denied. Insufficient funds");
            case BLOCKED_TRIPS -> IO.println("Access denied. Insufficient trips");
            case BLOCKED_PERIOD -> IO.println("Access denied. Validity period expired");
            case BLOCKED_EXISTENCE -> IO.println("Access denied. Card not registered");
            default -> IO.println("Internal error. Sorry for the inconvenience");
        }
    }

    public void printGeneralStatistics() {
        long allowedCount = accessLog.stream()
                .filter(r -> r.state() == TurnstileAccessState.GRANTED)
                .count();
        long deniedCount = accessLog.size() - allowedCount;
        IO.println("=== General Statistics ===");
        IO.println("Total attempts: " + accessLog.size());
        IO.println("Allowed: " + allowedCount);
        IO.println("Denied: " + deniedCount);
    }

    public void printStatisticsByCardType() {
        Map<TravelCardType, int[]> typeStats = new EnumMap<>(TravelCardType.class);
        for (TravelCardType type : TravelCardType.values()) {
            typeStats.put(type, new int[2]);
        }
        for (PassRecord record : accessLog) {
            if (record.cardType() != null) {
                int[] counts = typeStats.get(record.cardType());
                if (record.state() == TurnstileAccessState.GRANTED) {
                    counts[0]++;
                } else {
                    counts[1]++;
                }
            }
        }
        IO.println("=== Statistics by Card Type ===");
        typeStats.forEach((type, counts) -> {
            IO.println(type + " -> Allowed: " + counts[0] + ", Denied: " + counts[1]);
        });
    }
}
