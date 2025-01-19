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


        // Aufgabe 3 a) Kontonummer darstellen
        kontoOberflaeche.getNummer().setText(String.valueOf(girokonto.getKontonummer()));
        // Aufgabe 3 b) Kontostand mit Bind
        kontoOberflaeche.bindToModel(girokonto.kontostandProperty());
        // Aufgabe 3 d) BirectionalBind des gesperrt-Status
        kontoOberflaeche.bindGesperrtToModel(girokonto.gesperrtProperty());
        // Aufgabe 3 e) BirectionalBind der Adresse
        kontoOberflaeche.bindAdresseToModel(kunde.adresseProperty());

        // Aufgabe 3 c):

        // Einzahlen Button
        // TODO: Negative Einzahlung fixen
        kontoOberflaeche.getEinzahlenButton().setOnAction(event -> {
            try {
                double betrag = Double.parseDouble(kontoOberflaeche.getBetrag().getText());
                Waehrung waehrung = kontoOberflaeche.getWaehrung().getValue();
                girokonto.einzahlen(new Geldbetrag(betrag, waehrung));
                kontoOberflaeche.getMeldung().setText("Einzahlung erfolgreich.");
            } catch (NumberFormatException e) {
                kontoOberflaeche.getMeldung().setText("Ungueltiger Betrag.");
            }
        });
        // Abheben Button
        // TODO: Negative Abhebung fixen
        kontoOberflaeche.getAbhebenButton().setOnAction(event -> {
            try {
                double betrag = Double.parseDouble(kontoOberflaeche.getBetrag().getText());
                Waehrung waehrung = kontoOberflaeche.getWaehrung().getValue();
                if (girokonto.abheben(new Geldbetrag(betrag, waehrung))) {
                    kontoOberflaeche.getMeldung().setText("Abhebung erfolgreich.");
                } else {
                    kontoOberflaeche.getMeldung().setText("Abhebung nicht moeglich.");
                }
            } catch (NumberFormatException | GesperrtException e) {
                kontoOberflaeche.getMeldung().setText("Ungueltiger Betrag oder Konto gesperrt.");
            }
        });

        Scene scene = new Scene(kontoOberflaeche, 800, 600); // Größe der Szene anpassen nach Bedarf
        primaryStage.setTitle("Konto Verwaltung");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}