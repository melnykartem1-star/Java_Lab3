import com.example.*;

void main() {
    CardRegister cardRegister = new CardRegister();
    ReloadableTravelCard reloadableTravelCard = cardRegister.createReloadableCard();
    ExpiringTravelCard expiringTravelCard = cardRegister.createExpiringTravelCard(TravelCardType.UNIVERSITY);
    Turnstile turnstile = new Turnstile(cardRegister);

    turnstile.checkCardAccess(reloadableTravelCard);
    turnstile.checkCardAccess(expiringTravelCard);
    turnstile.checkCardAccess(expiringTravelCard);

    cardRegister.depositFunds(6, reloadableTravelCard);

    turnstile.checkCardAccess(reloadableTravelCard);
    turnstile.checkCardAccess(reloadableTravelCard);

    IO.println();
    turnstile.printGeneralStatistics();
    IO.println();
    turnstile.printStatisticsByCardType();

}
