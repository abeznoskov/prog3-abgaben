package verwaltung;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import bankprojekt.verarbeitung.*;
import bankprojekt.verwaltung.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import bankprojekt.verwaltung.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


class BankMockTest {

    Bank bank;
    Kunde A;
    Kunde B;
    Kunde C;

    Girokonto giroKontoA;
    Sparbuch sparbuchB;
    Girokonto giroKontoC;

    long kontoNummerA;
    long kontoNummerB;
    long kontoNummerC;

    @BeforeEach
    void setUp() {
        bank = new Bank(13571135L);
        A = new Kunde("testKundeA", "Girokonto", "Girostraße 1", LocalDate.parse("1998-01-05"));
        B = new Kunde("testKundeB", "Sparbuch", "Sparstraße 1", LocalDate.parse("2011-11-11"));
        C = new Kunde("testKundeC", "Girokonto", "Girostraße 2", LocalDate.parse("2011-11-11"));

        giroKontoA = mock(Girokonto.class);
        sparbuchB = mock(Sparbuch.class);
        giroKontoC = mock(Girokonto.class);

        when(giroKontoA.getInhaber()).thenReturn(A);
        when(sparbuchB.getInhaber()).thenReturn(B);
        when(giroKontoC.getInhaber()).thenReturn(C);
        kontoNummerA = bank.mockEinfuegen(giroKontoA);
        kontoNummerB = bank.mockEinfuegen(sparbuchB);
        kontoNummerC = bank.mockEinfuegen(giroKontoC);
    }

    @Test
    void geldUeberweisenErfolgreich() throws GesperrtException, KontoNichtVorhandenException {
        when(giroKontoA.ueberweisungAbsenden(any(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(true);
        assertTrue(bank.geldUeberweisen(kontoNummerA, kontoNummerC, new Geldbetrag(100), "abc"));

        verify(giroKontoA, times(1)).ueberweisungAbsenden(any(), anyString(), anyLong(), anyLong(), anyString());
        verify(giroKontoC, times(1)).ueberweisungEmpfangen(any(), anyString(), anyLong(), anyLong(), anyString());
    }

    @Test
    void testGeldUeberweisenKontoNichtUeberweisungsfaehig() throws GesperrtException, KontoNichtVorhandenException {
        Geldbetrag betrag = new Geldbetrag(10000);

        boolean result = bank.geldUeberweisen(kontoNummerA, kontoNummerB, betrag, "Miete");

        assertFalse(result);

        verifyNoInteractions(giroKontoA);
        verifyNoInteractions(sparbuchB);
    }

    @Test
    void testGeldUeberweisenGesperrtesKonto() throws Exception {
        Geldbetrag betrag = new Geldbetrag(10000);

        when(giroKontoA.ueberweisungAbsenden(any(), anyString(), anyLong(), anyLong(), anyString())).thenThrow(GesperrtException.class);

        assertThrows(GesperrtException.class, () -> bank.geldUeberweisen(kontoNummerA, kontoNummerC, betrag, "Miete"));
        
        verify(giroKontoA, times(1)).ueberweisungAbsenden(any(), any(), anyLong(), anyLong(), any());
    }

    @Test
    void testGeldUeberweisenKontoNichtVorhanden() {
        Geldbetrag betrag = new Geldbetrag(10000);

        assertThrows(KontoNichtVorhandenException.class, () ->
                bank.geldUeberweisen(999999L, kontoNummerB, betrag, "Miete"));
    }

    @Test
    void testGeldUeberweisenBetragNull() {
        assertThrows(IllegalArgumentException.class, () ->
                bank.geldUeberweisen(kontoNummerA, kontoNummerB, null, "Miete"));
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
