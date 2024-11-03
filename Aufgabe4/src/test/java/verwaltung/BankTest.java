package verwaltung;

import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Kunde;
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
    void geldAbheben_betragExceedingAccountBalance_shouldReturnFalse() throws GesperrtException {
        // Arrange
        long von = 1;
        Geldbetrag betragExceedingBalance = new Geldbetrag(1001); // Assuming account balance is 1000
        bank.girokontoErstellen(testKundeA);

        // Act
        boolean result = bank.geldAbheben(von, betragExceedingBalance);

        // Assert
        assertFalse(result);
    }

    @Test
    void geldAbheben_accountDoesNotExist_shouldReturnFalse() throws GesperrtException {
        // Arrange
        long von = 100000000; // Non-existent account number
        Geldbetrag anyAmount = new Geldbetrag(100);

        // Act
        boolean result = bank.geldAbheben(von, anyAmount);

        // Assert
        assertFalse(result);
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