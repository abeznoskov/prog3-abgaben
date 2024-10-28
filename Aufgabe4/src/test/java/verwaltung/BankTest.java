package verwaltung;

import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Kunde;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    Bank bank; // Deklariere die Bank hier
    static LocalDate geb = LocalDate.of(2020, 1, 1);
    static Kunde testKundeA = new Kunde("David", "Schneider", "Arsch", geb);
    static Kunde testKundeB = new Kunde("David2", "Schneider2", "Arsch2", geb);

    @BeforeEach
    void setUp() {
        bank = new Bank(420); // Initialisiere die Bank in setUp
    }

    @Test
    void getBankleitzahl() {
        assertEquals(420L, bank.getBankleitzahl());
    }

    @Test
    void girokontoUndSparbuchErstellen() {
        assertEquals(1L, bank.girokontoErstellen(testKundeA)); // Erstelle das Girokonto
        assertEquals(2L, bank.sparbuchErstellen(testKundeB)); // Erstelle das Sparbuch
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
    void geldEinzahlen() {

    }

    @Test
    void kontoLoeschen() {

    }

    @Test
    void getKontostand() {

    }

    @Test
    void geldUeberweisen() throws GesperrtException {
        bank.girokontoErstellen(testKundeA);
        bank.girokontoErstellen(testKundeB);
        Geldbetrag g = new Geldbetrag(100);
        bank.geldEinzahlen(1,g);

        assertTrue(bank.geldUeberweisen(1,2, g, "Gegenstände die gegen das Kriegswaffenkontrollgesetz verstoßen"));
        assertEquals(bank.getKontostand(1), new Geldbetrag(0));
        assertEquals(bank.getKontostand(2), new Geldbetrag(100));
    }
}