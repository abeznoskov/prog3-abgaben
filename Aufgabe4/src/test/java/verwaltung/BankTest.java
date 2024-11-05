package verwaltung;

import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verwaltung.Bank;
import bankprojekt.verwaltung.KontoNichtVorhandenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// TODO: Tests noch WIP
class BankTest {
    Bank bank; // Deklariere die Bank hier
    static LocalDate geb = LocalDate.of(2020, 1, 1);
    static Kunde testKundeA = new Kunde("David", "Schneider", "Arsch", geb);
    static Kunde testKundeB = new Kunde("David2", "Schneider2", "Arsch2", geb);

    @BeforeEach
    void setUp() {
        bank = new Bank(10000000); // Initialisiere die Bank in setUp
    }

    @Test
    void getBankleitzahl() {
        assertEquals(10000000L, bank.getBankleitzahl());
    }

    @Test
    void girokontoUndSparbuchErstellen() {
        assertEquals(100000000001L, bank.girokontoErstellen(testKundeA)); // Erstelle das Girokonto
        assertEquals(100000000002L, bank.sparbuchErstellen(testKundeB)); // Erstelle das Sparbuch
    }

    @Test
    void getAlleKonten() {
        bank.girokontoErstellen(testKundeA);
        bank.sparbuchErstellen(testKundeB);

        //System.out.println(bank.getAlleKonten());
    }

    @Test
    void getAlleKontonummern() {

    }

    @Test
    void geldAbheben() {

    }

    @Test
    void kontoLoeschen() {

    }

    @Test
    void getKontostand() {

    }

    @Test
    void geldUeberweisen() throws GesperrtException, KontoNichtVorhandenException {
        bank.girokontoErstellen(testKundeA);
        bank.girokontoErstellen(testKundeB);
        Geldbetrag g = new Geldbetrag(100);
        bank.geldEinzahlen(100000000001L,g);

        assertTrue(bank.geldUeberweisen(100000000001L,100000000002L, g, "Gegenstände die gegen das Kriegswaffenkontrollgesetz verstoßen"));
        assertEquals(bank.getKontostand(100000000001L), new Geldbetrag(0));
        assertEquals(bank.getKontostand(100000000002L), new Geldbetrag(100));
    }
}