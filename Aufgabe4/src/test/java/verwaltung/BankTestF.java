package verwaltung;

import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.GesperrtException;
//import bankprojekt.verwaltung.Bank;
import bankprojekt.verarbeitung.Kunde;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankTestF {

    private Bank bank;
    private Bank bank2;
    private Kunde kunde;
    private Kunde kunde2;

    @BeforeEach
    public void setUp() {
        bank = new Bank(10000000);
        bank2 = new Bank(20000000);
        kunde = new Kunde("Max", "Mustermann", "Campus Wilhelminenhof", LocalDate.parse("2000-10-01"));
        kunde2 = new Kunde("Moritz",  "Mustermann", "Campus Wilhelminenhof", LocalDate.parse("1998-02-28"));
    }

    @Test
    @Description("Testet den Konstruktor von Bank.class")
    public void testKonstruktoren() {
        assertThrows(IllegalArgumentException.class, () -> new Bank(-1));
        assertThrows(IllegalArgumentException.class, () -> new Bank(0));
    }

    @Test
    @Description("Testet den Getter für die Bankleitzahl")
    public void testGetter() {
        assertEquals(10000000, bank.getBankleitzahl());
    }

    @Test
    @Description("Testet die Methode girokontoErstellen()")
    public void testGirokontoErstellen() {
        assertThrows(IllegalArgumentException.class, () -> bank.girokontoErstellen(null));
        long kontonummer = bank.girokontoErstellen(kunde);
        List<Long> kontenListe = bank.getAlleKontonummern();
        assertEquals(1, kontenListe.size());
        assertTrue(kontonummer < 999999999999L);
        assertTrue(kontonummer >= 100000000000L);
        assertEquals(12, String.valueOf(kontonummer).length());
        //assertTrue(bank.isKonto(kontonummer));
    }

    @Test
    @Description("Testet die Methode sparbuchErstellen()")
    public void testSparbuchErstellen() {
        assertThrows(IllegalArgumentException.class, () -> bank2.sparbuchErstellen(null));
        long kontonummer = bank2.sparbuchErstellen(kunde);
        List<Long> kontenListe = bank2.getAlleKontonummern();
        assertEquals(1, kontenListe.size());
        assertTrue(kontonummer < 999999999999L);
        assertTrue(kontonummer >= 100000000000L);
        assertEquals(12, String.valueOf(kontonummer).length());
        //assertTrue(bank2.isKonto(kontonummer));
    }

    @Test
    @Description("Testet die Methode geldAbheben()")
    public void testGeldAbheben() throws GesperrtException {
        bank = new Bank(10000000);
        long kontonummer1 = bank.girokontoErstellen(kunde);
        Geldbetrag geldbetrag = new Geldbetrag(100);
        Geldbetrag geldbetrag2 = new Geldbetrag(-100);
        bank.geldEinzahlen(kontonummer1, geldbetrag);
        //assertFalse(bank.geldAbheben(92303023L, geldbetrag));
        assertThrows(IllegalArgumentException.class, () -> bank.geldAbheben(92303023L, geldbetrag));
        assertThrows(IllegalArgumentException.class, () -> bank.geldAbheben(kontonummer1, null));
        assertThrows(IllegalArgumentException.class, () -> bank.geldAbheben(kontonummer1, geldbetrag2));
        assertTrue(bank.geldAbheben(kontonummer1, geldbetrag));
    }

    @Test
    @Description("Testst die Methode geldEinzahlen()")
    public void testGeldEinzahlen() throws GesperrtException {
        bank = new Bank(10000000);
        long kontonummer1 = bank.girokontoErstellen(kunde);
        Geldbetrag geldbetrag = new Geldbetrag(100);
        Geldbetrag geldbetrag2 = new Geldbetrag(-100);
        assertThrows(IllegalArgumentException.class, () -> bank.geldEinzahlen(92303023L, geldbetrag));
        assertThrows(IllegalArgumentException.class, () -> bank.geldEinzahlen(kontonummer1, null));
        assertThrows(IllegalArgumentException.class, () -> bank.geldEinzahlen(kontonummer1, geldbetrag2));
        bank.geldEinzahlen(kontonummer1, geldbetrag);
        assertEquals(geldbetrag, bank.getKontostand(kontonummer1));
    }

    @Test
    @Description("Testet die Methode kontoLoeschen()")
    public void testKontoLoeschen() throws GesperrtException {
        bank = new Bank(10000000);
        long kontonummer1 = bank.girokontoErstellen(kunde);
        assertFalse(bank.kontoLoeschen(92303023L));
        assertTrue(bank.kontoLoeschen(kontonummer1));
    }

    @Test
    @Description("Testet die Methode geldUeberweisen()")
    public void testGeldUeberweisen() throws GesperrtException {
        bank = new Bank(10000000);
        long kontonummer1 = bank.girokontoErstellen(kunde);
        long kontonummer2 = bank.girokontoErstellen(kunde2);
        long kontonummer3 = bank.sparbuchErstellen(kunde);
        Geldbetrag geldbetrag = new Geldbetrag(1000);
        Geldbetrag geldbetrag2 = new Geldbetrag(100);
        assertFalse(bank.geldUeberweisen(92303023L, kontonummer1, geldbetrag, "Test")); //Konto von existiert nicht
        assertFalse(bank.geldUeberweisen(kontonummer1, 92303023L, geldbetrag, "Test")); //Konto nach existiert nicht
        assertFalse(bank.geldUeberweisen(kontonummer3, kontonummer2, geldbetrag, "Test")); //Von ist Sparbuch
        assertFalse(bank.geldUeberweisen(kontonummer1, kontonummer3, geldbetrag, "Test")); //Nach ist Sparbuch
        assertFalse(bank.geldUeberweisen(kontonummer1, kontonummer2, geldbetrag, "Test")); //Von hat kein Guthaben
        bank.geldEinzahlen(kontonummer1, geldbetrag);
        assertTrue(bank.geldUeberweisen(kontonummer1, kontonummer2, geldbetrag2, "Test")); //Erfolgreich
    }

    @Test
    @Description("Testet die Methode getKontostand()")
    public void testGetKontostand() throws GesperrtException {
        bank = new Bank(10000000);
        long kontonummer1 = bank.girokontoErstellen(kunde);
        Geldbetrag geldbetrag = new Geldbetrag(100);
        bank.geldEinzahlen(kontonummer1, geldbetrag);
        assertEquals(100, bank.getKontostand(kontonummer1).getBetrag());
    }

    @Test
    @Description("Testet die Methode getAlleKonten()")
    public void testGetAlleKonten() throws GesperrtException {
        bank = new Bank(10000000);
        bank.girokontoErstellen(kunde);
        bank.girokontoErstellen(kunde2);
        bank.sparbuchErstellen(kunde);
        String alleKonten = bank.getAlleKonten();
        assertEquals(alleKonten, bank.getAlleKonten());
    }

    @Test
    @Description("Testet die Methode getAlleKontonummern()")
    public void testGetAlleKontonummern() throws GesperrtException {
        bank = new Bank(10000000);
        bank.girokontoErstellen(kunde);
        bank.girokontoErstellen(kunde2);
        bank.sparbuchErstellen(kunde);
        List<Long> alleKontonummern = bank.getAlleKontonummern();
        assertEquals(alleKontonummern.size(), 3);
        assertEquals(alleKontonummern, bank.getAlleKontonummern());
    }
}