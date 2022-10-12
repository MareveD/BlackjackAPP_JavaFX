package blackjackapplication.blackjack;

public class BlackjackGame {
    private final Hand playerHand;
    private final Hand dealerHand;
    private final Deck deck;
    private final double minBet;
    private final double maxBet;
    private double betAmount;
    private double totalMoney;
    public BlackjackGame () {
        this.deck = new Deck ();
        this.playerHand = new Hand ( "player" );
        this.dealerHand = new Hand ( "dealer" );
        this.minBet = 5.0;
        this.maxBet = 1000.0;
        this.totalMoney = 100;
    }
    public void loadMoney () {
        totalMoney = 100;
    }
    public boolean isOutOfMoney () {
        return this.totalMoney < this.minBet;
    }
    public void resetMoney () {
        loadMoney ();
    }
    public double getMinBet () {
        return minBet;
    }
    public double getMaxBet () {
        return maxBet;
    }
    public double getTotalMoney () {
        return totalMoney;
    }
    public void setBet (double amt) {
        this.betAmount = amt;
    }
    public void deal () {
        playerHand.addCard ( deck.drawCard () );
        playerHand.addCard ( deck.drawCard () );
        dealerHand.addCard ( deck.drawCard () );
        dealerHand.addCard ( deck.drawCard () );
    }
    public void hit () {
        playerHand.addCard ( deck.drawCard () );
    }
    public void stand () {
        while ( this.dealerHand.getPoints () < 17 ) {
            dealerHand.addCard ( deck.drawCard () );
        }
    }
    public Card getDealerShowCard () {
        return dealerHand.getCards ()[1];
    }
    public Hand getDealerHand () {
        return dealerHand;
    }
    public Hand getPlayerHand () {
        return playerHand;
    }
    public boolean isBlackjackOrBust () {
        return this.playerHand.isBlackjack () || this.playerHand.isBust ()
                || this.dealerHand.isBlackjack () || this.dealerHand.isBust ();
    }
    public boolean isPush () {
        return ( this.playerHand.getPoints () <= 21 ) && ( this.playerHand.getPoints () == this.dealerHand.getPoints () );
    }
    public boolean playerWins () {
        return ( this.dealerHand.getPoints () > 21 || this.playerHand.getPoints () == 21 )
                && ( this.playerHand.getPoints () > this.dealerHand.getPoints () || this.dealerHand.getPoints () < this.playerHand.getPoints () );
    }
    public void addBetToTotal () {
        totalMoney = totalMoney + betAmount;
    }
    public void addBlackjackToTotal () {
        totalMoney = totalMoney + ( betAmount * 1.5 );
    }
    public void subtractBetFromTotal () {
        totalMoney = totalMoney - betAmount;
    }
    public void resetHands () {
        this.playerHand.resetHand ();
        this.dealerHand.resetHand ();
    }
}