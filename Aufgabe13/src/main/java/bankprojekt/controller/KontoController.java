package bankprojekt.controller;

import bankprojekt.oberflaeche.KontoOberflaeche;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KontoController extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Erstelle eine Instanz von KontoOberflaeche
        KontoOberflaeche kontoOberflaeche = new KontoOberflaeche();

        // Erstelle eine Szene mit der KontoOberflaeche
        Scene scene = new Scene(kontoOberflaeche, 800, 600); // Größe der Szene anpassen nach Bedarf

        // Setze den Titel des Fensters
        primaryStage.setTitle("Konto Verwaltung");

        // Setze die Szene auf die Bühne
        primaryStage.setScene(scene);

        // Zeige die Bühne
        primaryStage.show();
    }
}