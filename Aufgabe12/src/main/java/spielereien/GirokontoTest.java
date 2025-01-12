package spielereien;
import bankprojekt.verarbeitung.*;


/**
 * Test fuer Beobachter
 * @author Andreas B.;
 *         Tuan Anh N.
 */
public class GirokontoTest {
    /**
     * Adhoc-Test fuer Aufgabe 12
     */
    public static void main(String[] args) throws GesperrtException {
        // Testdaten vorbereiten
        Kunde kunde1 = new Kunde();
        Girokonto girokonto = new Girokonto(kunde1, 123456789L, new Geldbetrag(1000)); // Dispo: 1000

        Kunde kunde2 = new Kunde();
        Girokonto anderesKonto = new Girokonto(kunde2, 987654321L, new Geldbetrag(1500)); // Neuer Dispo: 1500

        // Test: aktualisieren-Methode aufrufen
        System.out.println("=== Test: Dispo erhoehen ===");
        anderesKonto.einzahlen(new Geldbetrag(500)); // Erhöht den Dispo des anderen Kontos auf 2000
        girokonto.aktualisieren(anderesKonto);

        System.out.println("=== Test: Dispo erhoehen ===");
        anderesKonto.einzahlen(new Geldbetrag(1000)); // Reduziert den Dispo auf 1000
        girokonto.aktualisieren(anderesKonto);

        System.out.println("=== Test: Dispo verringern ===");
        //girokonto.ueberweisungAbsenden( new Geldbetrag(1500), "FirmaTest", 123456789L, 10202, "Dept");
        boolean erfolgreich = anderesKonto.ueberweisungAbsenden(
                new Geldbetrag(1200),       // Betrag
                "Firma ABC",              // Empfänger
                987654321L,               // Zielkontonummer
                10020030L,                // Ziel-BLZ
                "Rechnung 1234"           // Verwendungszweck
        );
        girokonto.aktualisieren(anderesKonto);

        System.out.println("=== Test: Dispo bleibt gleich ===");
        girokonto.aktualisieren(anderesKonto);
    }
}

