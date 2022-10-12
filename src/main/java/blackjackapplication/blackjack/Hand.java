package blackjackapplication.blackjack;

public class Hand {
    private int counter = 0;
    private Card[] hand;
    private String user;

    public Hand (String user) {
        this.user = user;
        this.hand = new Card[10];
    }
    public Card[] getCards () {
        return this.hand;
    }

    public int getPoints () {
        int total = 0;
        for (Card card : hand) {
            if ( card != null ) {
                total = total + card.getPoints ();
            }
        }
        if ( total > 21 ) {
            total = 0;
            for (Card card : hand) {
                if ( card != null ) {
                    if ( card.isAce () ) {
                        total = total + 1;
                    } else {
                        total = total + card.getPoints ();
                    }
                }
            }
        }
        return total;
    }
    public void addCard (Card card) {
        if ( card != null )
            this.hand[counter] = card;
        counter++;
    }
    public boolean isBlackjack () {
        return getPoints () == 21;
    }
    public boolean isBust () {
        return getPoints () > 21;
    }
    public void resetHand () {
        this.hand = new Card[10];
        this.counter = 0;
    }
}