package blackjackapplication.blackjack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BlackjackApp extends Application {
    static BlackjackGame game;
    private TextField moneyField;
    private TextField betField;
    private TextField resultField;
    private ListView listCardDealer;
    private ListView listCardPlayer;
    private Button hitButton;
    private Button standButton;
    private Button playButton;
    private Button exitButton;
    private boolean isBetOk;
    private TextField pointFieldDealer;
    private TextField pointFieldPlayer;

    public static void main (String[] args) {

        System.out.println ( "BLACKJACK!" );
        System.out.println ( "Blackjack payout is 3:2 \n" );
        System.out.println ( "**********************************" );
        System.out.println ( "***** Pop-up window is open. *****" );
        System.out.println ( "**********************************" );

        game = new BlackjackGame ();
        launch ( args );
    }

    private boolean getFxBetAmount () {
        String betText = betField.getText ();
        if ( betText == null || betText.isEmpty () ) {
            return false;
        }
        try {
            double bet = Double.parseDouble ( betText );
            if ( bet < game.getMinBet () || bet > ( Math.min ( game.getMaxBet (), game.getTotalMoney () ) ) ) {
                return false;
            } else {
                game.setBet ( bet );
                return true;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private void showHands () {
        showPlayerHand ();
        showDealerShowCard ();
    }

    private void showDealerShowCard () {
        listCardDealer.getItems ().clear ();
        System.out.println ( "Showing the dealer's card in UI" );
        listCardDealer.getItems ().add ( game.getDealerShowCard ().display () );
    }

    private void showDealerHand () {
        listCardDealer.getItems ().clear ();
        System.out.println ( "Showing the dealer's hand in UI" );
        for (Card card : game.getDealerHand ().getCards ()) {
            if ( card != null ) {
                listCardDealer.getItems ().add ( card.display () );
            }
        }
        pointFieldDealer.setText ( String.valueOf ( game.getDealerHand ().getPoints () ) );
    }

    private void showPlayerHand () {
        listCardPlayer.getItems ().clear ();
        System.out.println ( "Showing your hand in UI" );
        for (Card card : game.getPlayerHand ().getCards ()) {
            if ( card != null ) {
                listCardPlayer.getItems ().add ( card.display () );
            }
        }
        pointFieldPlayer.setText ( String.valueOf ( game.getPlayerHand ().getPoints () ) );
    }

    @Override
    public void start (Stage stage) {

        stage.setTitle ( "Blackjack" );
        GridPane grid = new GridPane ();
        grid.setAlignment ( Pos.TOP_LEFT );
        grid.setPadding ( new Insets ( 25, 25, 25, 25 ) );
        grid.setHgap ( 10 );
        grid.setVgap ( 10 );

        Scene scene = new Scene ( grid, 400, 700 );

        grid.add ( new Label ( "Money :" ), 0, 0 );
        moneyField = new TextField ();
        moneyField.setText ( String.valueOf ( game.getTotalMoney () ) );
        moneyField.setEditable ( false );
        grid.add ( moneyField, 1, 0 );

        grid.add ( new Label ( "Bet :" ), 0, 1 );
        betField = new TextField ();
        grid.add ( betField, 1, 1 );

        grid.add ( new Label ( "DEALER" ), 0, 2 );

        grid.add ( new Label ( "Cards :" ), 0, 3 );
        listCardDealer = new ListView ();
        HBox dealerBox = new HBox ( listCardDealer );
        grid.add ( listCardDealer, 1, 3 );

        grid.add ( new Label ( "Points :" ), 0, 4 );
        pointFieldDealer = new TextField ();
        pointFieldDealer.setEditable ( false );
        grid.add ( pointFieldDealer, 1, 4 );

        grid.add ( new Label ( "YOU" ), 0, 5 );

        grid.add ( new Label ( "Cards :" ), 0, 6 );
        listCardPlayer = new ListView ();
        HBox playerBox = new HBox ( listCardPlayer );
        grid.add ( listCardPlayer, 1, 6 );

        grid.add ( new Label ( "Points :" ), 0, 7 );
        pointFieldPlayer = new TextField ();
        pointFieldPlayer.setEditable ( false );
        grid.add ( pointFieldPlayer, 1, 7 );

        hitButton = new Button ( "Hit" );
        hitButton.setOnAction ( event -> hitButtonClicked () );
        hitButton.setDisable ( true );

        standButton = new Button ( "Stand" );
        standButton.setOnAction ( event -> standButtonClicked () );
        standButton.setDisable ( true );

        HBox buttonBox = new HBox ( 10 );
        buttonBox.getChildren ().add ( hitButton );
        buttonBox.getChildren ().add ( standButton );
        grid.add ( buttonBox, 0, 8, 2, 1 );

        grid.add ( new Label ( "RESULT :" ), 0, 9 );
        resultField = new TextField ();
        resultField.setEditable ( false );
        grid.add ( resultField, 1, 9 );

        playButton = new Button ( "Play" );
        playButton.setOnAction ( event -> playButtonClicked () );
        playButton.setDisable ( false );

        exitButton = new Button ( "Exit" );
        exitButton.setOnAction ( event -> exitButtonClicked () );
        exitButton.setDisable ( false );

        HBox buttonBox2 = new HBox ( 10 );
        buttonBox2.getChildren ().add ( playButton );
        buttonBox2.getChildren ().add ( exitButton );
        grid.add ( buttonBox2, 0, 10, 2, 1 );

        Scene scene2 = new Scene ( dealerBox, 200, 100 );
        Scene scene3 = new Scene ( playerBox, 200, 100 );
        stage.setScene ( scene2 );
        stage.setScene ( scene3 );
        stage.setScene ( scene );
        stage.show ();
    }

    private void hitButtonClicked () {
        resultField.setText ( "Hit!" );
        System.out.println ( "you've pressed Hit!" );

        game.hit ();
        showPlayerHand ();
        showDealerShowCard ();

        if ( game.isBlackjackOrBust () ) {
            if ( game.getPlayerHand ().isBlackjack () ) {
                game.addBlackjackToTotal ();
                pointFieldDealer.setText ( String.valueOf ( game.getDealerHand ().getPoints () ) );
                resultField.setText ( "Blackjack! You won!" );
            } else if ( game.getPlayerHand ().isBust () ) {
                game.subtractBetFromTotal ();
                pointFieldDealer.setText ( String.valueOf ( game.getDealerHand ().getPoints () ) );
                resultField.setText ( "Busted! You lost!" );
            }

            //Buttons
            hitButton.setDisable ( true );
            standButton.setDisable ( true );
            playButton.setDisable ( false );
            exitButton.setDisable ( false );

            //Adjust Total Money only in the case of a blackjack OR a bust.
            moneyField.setText ( String.valueOf ( game.getTotalMoney () ) );

            //Vu que la partie est terminée, le betField se clear, afin de permettre une nouvelle partie.
            betField.clear ();

            //Cela permet de faire un reset sur le bet et de forcer le joueur a rentrer un chiffre.
            betField.setText ( "Please place a bet." );

            //On montre alors la main complète du dealer.
            showDealerHand ();

        } else {
            resultField.setText ( "Hit again!" );
        }
    }

    private void standButtonClicked () {
        resultField.setText ( "Stand!" );
        System.out.println ( "you've pressed Stand!" );

        game.stand ();
        showPlayerHand ();
        showDealerHand ();

        if ( game.isBlackjackOrBust () ) {
            if ( game.getPlayerHand ().isBlackjack () ) {
                game.addBlackjackToTotal ();
                resultField.setText ( "Blackjack! You won!" );
            } else if ( game.getPlayerHand ().isBust () ) {
                game.subtractBetFromTotal ();
                resultField.setText ( "Busted! You lost!" );
            } else if ( game.getDealerHand ().isBlackjack () ) {
                game.subtractBetFromTotal ();
                resultField.setText ( "You lost! Blackjack for the Dealer!" );
            } else if ( game.getDealerHand ().isBust () ) {
                game.addBetToTotal ();
                resultField.setText ( "You won! Dealer has busted!" );
            }

            //Buttons
            hitButton.setDisable ( true );
            standButton.setDisable ( true );
            playButton.setDisable ( false );
            exitButton.setDisable ( false );

        } else if ( game.isPush () ) {
            resultField.setText ( "Push!" );
        } else if ( game.playerWins () ) {
            game.addBetToTotal ();
            resultField.setText ( "You won!" );
        } else {
            game.subtractBetFromTotal ();
            resultField.setText ( "You lost!" );
        }

        //Buttons
        hitButton.setDisable ( true );
        standButton.setDisable ( true );
        playButton.setDisable ( false );
        exitButton.setDisable ( false );

        //Adjust Total Money in the bank no matter the result
        moneyField.setText ( String.valueOf ( game.getTotalMoney () ) );

        //Vu que la partie est terminée, le betField se clear, afin de permettre une nouvelle partie.
        betField.clear ();

        //Cela permet de faire un reset sur le bet et de forcer le joueur a rentrer un chiffre.
        betField.setText ( "Please place a bet." );

    }

    private void playButtonClicked () {
        resultField.setText ( "Awesome! Let's Play! First round!" );
        System.out.println ( "You've pressed Play!" );

        //Adjust Total Money to $100 if it's lower than $5
        if ( game.isOutOfMoney () ) {
            game.resetMoney ();
            moneyField.setText ( String.valueOf ( 100.0 ) );
            System.out.println ( "Reloading to $100.00." );
            resultField.setText ( "You're out of money! Reloading to $100.00." );
        }

        isBetOk = getFxBetAmount ();

        if ( isBetOk ) {

            game.resetHands ();
            game.deal ();
            showHands ();

            //Buttons
            hitButton.setDisable ( false );
            standButton.setDisable ( false );
            playButton.setDisable ( true );
            exitButton.setDisable ( true );

            //Points
            pointFieldDealer.clear ();
            pointFieldPlayer.clear ();
            pointFieldPlayer.setText ( String.valueOf ( game.getPlayerHand ().getPoints () ) );

        } else {
            resultField.setText ( "Bet must be greater than $5 and less than or equal $1000, and can't be greater than the money in the bank." );

            //Message dans le betField pour forcer le bet avec un chiffre en cas d'erreur.
            betField.setText ( "Please place a bet." );

            //Points cleared.
            pointFieldDealer.clear ();
            pointFieldPlayer.clear ();

            //les listcards sont cleared.
            listCardPlayer.getItems ().clear ();
            listCardDealer.getItems ().clear ();

        }
    }

    private void exitButtonClicked () {
        System.exit ( 0 );
    }
}