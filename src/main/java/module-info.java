module blackjackapplication.blackjack {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires eu.hansolo.tilesfx;

    opens blackjackapplication.blackjack to javafx.fxml;
    exports blackjackapplication.blackjack;
}