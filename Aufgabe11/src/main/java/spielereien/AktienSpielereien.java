package spielereien;

import bankprojekt.verarbeitung.Aktie;
import bankprojekt.verarbeitung.Aktienkonto;
import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.Kunde;

import java.time.LocalDate;
import java.util.concurrent.Future;

public class AktienSpielereien {
    public static void main(String[] args) throws Exception {
        // Aktien erstellen
        Aktie aktie1 = new Aktie("123456", new Geldbetrag(10));
        Aktie aktie2 = new Aktie("654321", new Geldbetrag(20));
        Aktie aktie3 = new Aktie("111111", new Geldbetrag(50));

        // Konto erstellen
        LocalDate geb = LocalDate.of(1999, 1, 1);
        Aktienkonto konto = new Aktienkonto(new Kunde("A", "B", "c", geb), 1);
        konto.einzahlen(new Geldbetrag(1750));

        // Kaufaufträge
        Future<Geldbetrag> kauf1 = konto.kaufauftrag("123456", 1, new Geldbetrag(9.99));
        Future<Geldbetrag> kauf2 = konto.kaufauftrag("654321", 5, new Geldbetrag(19.99));

        // Ergebnisse anzeigen
        System.out.println("Kauf 1 Preis: " + kauf1.get());
        System.out.println("Kauf 2 Preis: " + kauf2.get());

        // Verkaufaufträge
        Future<Geldbetrag> verkauf1 = konto.verkaufauftrag("123456", new Geldbetrag(10.01));
        Future<Geldbetrag> verkauf2 = konto.verkaufauftrag("654321", new Geldbetrag(20.01));

        // Ergebnisse anzeigen
        System.out.println("Verkauf 1 Erlös: " + verkauf1.get());
        System.out.println("Verkauf 2 Erlös: " + verkauf2.get());
    }
}
