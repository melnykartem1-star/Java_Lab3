package com.example;

import java.util.HashMap;

public class CardRegister {

    private final HashMap<Long, TravelCard> cardRegistry;

    public CardRegister() {
        this.cardRegistry = new HashMap<>();
    }

    public ReloadableTravelCard createReloadableCard(){
        ReloadableTravelCard newCard = new ReloadableTravelCard(0);
        cardRegistry.put(newCard.getUID(), newCard);
        return newCard;
    }

    public ExpiringTravelCard createExpiringTravelCard(TravelCardType cardType){
        ExpiringTravelCard newCard = new ExpiringTravelCard(cardType);
        cardRegistry.put(newCard.getUID(), newCard);
        return newCard;
    }

    public boolean checkCardExistence(TravelCard card){
        return cardRegistry.containsValue(card);
    }

    public void depositFunds(int sum, ReloadableTravelCard card){
        if(sum < 0){
            IO.println("Deposit denied. Sum can not be negative");
            return;
        } else if (card == null || !cardRegistry.containsValue(card)) {
            IO.println("Deposit denied. Card not registered");
            return;
        }
        card.deposit(sum);
    }

}
