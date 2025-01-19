package bankprojekt.controller;

import bankprojekt.oberflaeche.KontoOberflaeche;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.geld.Waehrung;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;

public class KontoController extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Erstelle ein Girokonto-Objekt (den Kontoinhaber usw. können wir festlegen)
        Kunde kunde = new Kunde("Max", "Mustermann", "Beispielstraße 123", LocalDate.parse("1998-01-05"));
        Geldbetrag dispo = new Geldbetrag(1000); // Beispiel: Dispo von 1000
        Girokonto girokonto = new Girokonto(kunde, 12345678, dispo);

        // Erstelle eine Instanz von KontoOberflaeche
        KontoOberflaeche kontoOberflaeche = new KontoOberflaeche();

        // Aktualisiere die Oberfläche mit den Daten des Girokontos
        kontoOberflaeche.getNummer().setText(String.valueOf(girokonto.getKontonummer()));
        kontoOberflaeche.updateKontostand(girokonto.getKontostand().toString());
        kontoOberflaeche.getGesperrt().setSelected(girokonto.isGesperrt());
        kontoOberflaeche.getAdresse().setText(girokonto.getInhaber().getAdresse());

        // Setze Ereignis-Handler für die Oberfläche
        kontoOberflaeche.getEinzahlenButton().setOnAction(event -> {
            try {
                double betrag = Double.parseDouble(kontoOberflaeche.getBetrag().getText());
                Waehrung waehrung = kontoOberflaeche.getWaehrung().getValue();
                girokonto.einzahlen(new Geldbetrag(betrag, waehrung));
                kontoOberflaeche.updateKontostand(girokonto.getKontostand().toString());
                kontoOberflaeche.getMeldung().setText("Einzahlung erfolgreich.");
            } catch (NumberFormatException e) {
                kontoOberflaeche.getMeldung().setText("Ungültiger Betrag.");
            }
        });

        kontoOberflaeche.getAbhebenButton().setOnAction(event -> {
            try {
                double betrag = Double.parseDouble(kontoOberflaeche.getBetrag().getText());
                Waehrung waehrung = kontoOberflaeche.getWaehrung().getValue();
                if (girokonto.abheben(new Geldbetrag(betrag, waehrung))) {
                    kontoOberflaeche.updateKontostand(girokonto.getKontostand().toString());
                    kontoOberflaeche.getMeldung().setText("Abhebung erfolgreich.");
                } else {
                    kontoOberflaeche.getMeldung().setText("Abhebung nicht möglich.");
                }
            } catch (NumberFormatException | GesperrtException e) {
                kontoOberflaeche.getMeldung().setText("Ungültiger Betrag oder Konto gesperrt.");
            }
        });

        // Im KontoController.java
        kontoOberflaeche.getGesperrt().setOnAction(event -> {
            //girokonto.setGesperrt(kontoOberflaeche.getGesperrt().isSelected());
        });

        kontoOberflaeche.getAdresse().textProperty().addListener((observable, oldValue, newValue) -> {
            girokonto.getInhaber().setAdresse(newValue);
        });

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