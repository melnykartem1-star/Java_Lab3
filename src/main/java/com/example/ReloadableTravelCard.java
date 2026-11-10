package com.example;

public class ReloadableTravelCard extends TravelCard {
    private static final TravelCardType type = TravelCardType.STANDARD;
    private int balance;

    ReloadableTravelCard(int balance){
        super();
        this.balance = Math.max(balance, 0);
    }

    void charge(int fare){
        this.balance = balance - fare;
    }

    void deposit(int sum) {this.balance = balance + sum;}

    public int getBalance() {
        return balance;
    }

    public TravelCardType getType() {return type;}
}
