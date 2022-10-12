package blackjackapplication.blackjack;

import java.util.Random;

public class Deck {
    private Card[] deck;
    private int currentCardIndex;

    public Deck () {
        deck = new Card[52];
        String[] allRanks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] allSuites = {"Clubs", "Diamonds", "Hearts", "Spades"};
        int counter = 0;
        for (String suite : allSuites) {
            for (String rank : allRanks) {
                int points = switch (rank) {
                    case "Jack", "Queen", "King" -> 10;
                    case "Ace" -> 11;
                    default -> Integer.parseInt ( rank );
                };
                deck[counter] = new Card ( suite, rank, points );
                counter++;
            }
        }
        shuffleDeck ();
    }
    private void shuffleDeck () {
        Random r = new Random ();
        for (int i = 51; i > 0; i--) {
            int randomCard = r.nextInt (i+1 );
            Card temporary = deck[i];
            deck[i] = deck[randomCard];
            deck[randomCard] = temporary;
        }
    }
    public Card drawCard () {
        if ( currentCardIndex == 51 ) {
            Card currCard = deck[currentCardIndex];
            shuffleDeck ();
            return currCard;
        } else {
            return deck[currentCardIndex++];
        }
    }
}