package spielereien;

import bankprojekt.verarbeitung.Aktie;
import bankprojekt.verarbeitung.Aktienkonto;
import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.Kunde;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.Future;

public class AktienSpielereien {
    public static void main(String[] args) throws Exception {
        // Aktien erstellen
        Aktie aktie1 = new Aktie("123456", new Geldbetrag(10));
        //Aktie aktie2 = new Aktie("654321", new Geldbetrag(20000));
        //Aktie aktie3 = new Aktie("111111", new Geldbetrag(5000));

//        // Konto erstellen
//        LocalDate geb = LocalDate.of(2020, 1, 1);
//        Aktienkonto konto = new Aktienkonto(new Kunde("Max", "Mustermann", "aa", geb), 1);
//        konto.einzahlen(new Geldbetrag(10000000));
//
//        // Kaufaufträge
//        Future<Geldbetrag> kauf1 = konto.kaufauftrag("123456", 10, new Geldbetrag(10500));
//        Future<Geldbetrag> kauf2 = konto.kaufauftrag("654321", 5, new Geldbetrag(21000));
//
//        // Verkaufaufträge
//        Future<Geldbetrag> verkauf1 = konto.verkaufauftrag("123456", new Geldbetrag(9500));
//
//        // Ergebnisse anzeigen
//        System.out.println("Kauf 1 Preis: " + kauf1.get());
//        System.out.println("Kauf 2 Preis: " + kauf2.get());
//        System.out.println("Verkauf 1 Erlös: " + verkauf1.get());
    }
}
