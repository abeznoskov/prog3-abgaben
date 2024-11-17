package verwaltung;

import bankprojekt.verarbeitung.*;
import bankprojekt.verwaltung.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankTestMock {

    Bank bank;
    Kunde A;
    Kunde B;

    Girokonto giroKontoA;
    Sparbuch sparbuchB;

    long kontoNummerA;
    long kontoNummerB;

    @BeforeEach
    void setUp() {
        bank = new Bank(13571135L);
        A = new Kunde("testKundeA", "Girokonto", "Girostraße 1", LocalDate.parse("1998-01-05"));
        B = new Kunde("testKundeB", "Sparbuch", "Sparstraße 1", LocalDate.parse("2001-23-11"));

        giroKontoA = mock();
        sparbuchB = mock();

        when(giroKontoA.getInhaber()).thenReturn(A);
        when(sparbuchB.getInhaber()).thenReturn(B);
        kontoNummerA = bank.mockEinfuegen(giroKontoA);
        kontoNummerB = bank.mockEinfuegen(sparbuchB);
    }

    @Test
    public void kontoLoeschenKontoExistiertTest() {
        assertTrue(bank.kontoLoeschen(kontoNummerA));

        verifyNoInteractions(giroKontoA);
    }

    @Test
    public void kontoLoeschenKontoExistiertNichtTest() {
        assertFalse(bank.kontoLoeschen(99999L));
    }

    @Test
    public void kontoLoeschenKontoIstGesperrtTest() {
        when(giroKontoA.isGesperrt()).thenReturn(true);

        assertTrue(bank.kontoLoeschen(kontoNummerA));
    }
}
