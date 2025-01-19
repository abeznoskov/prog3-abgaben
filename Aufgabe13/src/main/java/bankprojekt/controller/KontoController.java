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


/**
 * Controller fuer unser Projekt
 * Er bringt die KontoOberflaeche auf den Bildschirm.
 * Er erzeugt ein Girokonto-Objekt (den Kontoinhaber usw. dürfen Sie selbst festlegen) als Model und übergibt es der Oberfläche.
 * Er bekommt die Methoden, um auf Ereignisse in der Oberfläche zu reagieren.
 *
 * @author  Andreas B.
 *          Tuan Anh N.
 */
public class KontoController extends Application {

    @Override
    public void start(Stage primaryStage) {
        Kunde kunde = new Kunde("Max", "Mustermann", "Beispielstraße 123", LocalDate.parse("1998-01-05"));
        Geldbetrag dispo = new Geldbetrag(1000); // Beispiel: Dispo von 1000
        Girokonto girokonto = new Girokonto(kunde, 12345678, dispo);
        KontoOberflaeche kontoOberflaeche = new KontoOberflaeche();

        // Aufgabe 3 a) Kontonummer darstellen
        kontoOberflaeche.getNummer().setText(String.valueOf(girokonto.getKontonummer()));
        // Aufgabe 3 b) Kontostand mit Bind
        kontoOberflaeche.bindToModel(girokonto.kontostandProperty());
        // Aufgabe 3 d) BirectionalBind des gesperrt-Status
        kontoOberflaeche.bindGesperrtToModel(girokonto.gesperrtProperty());
        // Aufgabe 3 e) BirectionalBind der Adresse
        kontoOberflaeche.bindAdresseToModel(kunde.adresseProperty());

        /* Aufgabe 3 c):
         *  Die Buttons sollen Einzahlungen bzw. Abhebungen vornehmen. Der Betrag
         *  wird aus dem Textfeld entnommen, die Währung aus der Choicebox. Sollte es
         *  Probleme dabei geben, soll eine entsprechende Meldung angezeigt werden.
         */
        // Einzahlen Button
        kontoOberflaeche.getEinzahlenButton().setOnAction(event -> {
            try {
                double betrag = Double.parseDouble(kontoOberflaeche.getBetrag().getText());
                if (betrag < 0) { // keine negative Einzahlung
                    kontoOberflaeche.getMeldung().setText("Einzahlbetrag darf nicht negativ sein.");
                    return;
                }
                Waehrung waehrung = kontoOberflaeche.getWaehrung().getValue(); // Waehrung aus der ChoiceBox
                girokonto.einzahlen(new Geldbetrag(betrag, waehrung));
                kontoOberflaeche.getMeldung().setText("Einzahlung erfolgreich.");
            } catch (NumberFormatException e) {
                kontoOberflaeche.getMeldung().setText("Ungueltiger Betrag.");
            }
        });
        // Abheben Button
        kontoOberflaeche.getAbhebenButton().setOnAction(event -> {
            try {
                double betrag = Double.parseDouble(kontoOberflaeche.getBetrag().getText());
                if (betrag < 0) { // keine negative Abhebung
                    kontoOberflaeche.getMeldung().setText("Abhebebetrag darf nicht negativ sein.");
                    return;
                }
                Waehrung waehrung = kontoOberflaeche.getWaehrung().getValue(); // Waehrung aus der ChoiceBox
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