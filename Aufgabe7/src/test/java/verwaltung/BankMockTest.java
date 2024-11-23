package verwaltung;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import bankprojekt.verarbeitung.*;
import bankprojekt.verwaltung.Bank;
import org.junit.jupiter.api.BeforeEach;
import bankprojekt.verwaltung.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


class BankMockTest {

    Bank bank;
    Kunde kundeA;
    Kunde kundeB;
    Kunde kundeC;

    Konto kontoA;
    Konto kontoB;
    Konto kontoC;

    long kontoNummerA;
    long kontoNummerB;
    long kontoNummerC;

    @BeforeEach
    void setUp() {
        bank = new Bank(13571135L);
        kundeA = new Kunde("testKundeA", "Girokonto", "Girostraße 1", LocalDate.parse("1998-01-05"));
        kundeB = new Kunde("testKundeB", "Sparbuch", "Sparstraße 1", LocalDate.parse("2011-11-11"));
        kundeC = new Kunde("testKundeC", "Girokonto", "Girostraße 2", LocalDate.parse("2011-11-11"));

        kontoA = mock(UeberweisungsfaehigesKonto.class);
        kontoB = mock(Konto.class);
        kontoC = mock(UeberweisungsfaehigesKonto.class);

        when(kontoA.getInhaber()).thenReturn(kundeA);
        when(kontoB.getInhaber()).thenReturn(kundeB);
        when(kontoC.getInhaber()).thenReturn(kundeC);
        kontoNummerA = bank.mockEinfuegen(kontoA);
        kontoNummerB = bank.mockEinfuegen(kontoB);
        kontoNummerC = bank.mockEinfuegen(kontoC);
    }

    @Test
    void geldUeberweisenErfolgreich() throws GesperrtException, KontoNichtVorhandenException {
        when(((UeberweisungsfaehigesKonto) kontoA).ueberweisungAbsenden(any(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(true);
        assertTrue(bank.geldUeberweisen(kontoNummerA, kontoNummerC, new Geldbetrag(100), "abc"));

        verify((UeberweisungsfaehigesKonto) kontoA, times(1)).ueberweisungAbsenden(any(), anyString(), anyLong(), anyLong(), anyString());
        verify((UeberweisungsfaehigesKonto) kontoC, times(1)).ueberweisungEmpfangen(any(), anyString(), anyLong(), anyLong(), anyString());
    }

    @Test
    void testGeldUeberweisenKontoNichtUeberweisungsfaehig() throws GesperrtException, KontoNichtVorhandenException {
        Geldbetrag betrag = new Geldbetrag(10000);

        boolean result = bank.geldUeberweisen(kontoNummerA, kontoNummerB, betrag, "Miete");

        assertFalse(result);

        verifyNoInteractions(kontoA);
        verifyNoInteractions(kontoB);
    }

    @Test
    void testGeldUeberweisenGesperrtesKonto() throws Exception {
        Geldbetrag betrag = new Geldbetrag(10000);

        when(((UeberweisungsfaehigesKonto) kontoA).ueberweisungAbsenden(any(), anyString(), anyLong(), anyLong(), anyString()))
                .thenThrow(GesperrtException.class);

        assertThrows(GesperrtException.class, () -> bank.geldUeberweisen(kontoNummerA, kontoNummerC, betrag, "Miete"));

        verify((UeberweisungsfaehigesKonto) kontoA, times(1)).ueberweisungAbsenden(any(), anyString(), anyLong(), anyLong(), anyString());
        // TODO: Fix code anhand dieses Testes
        // Test fails hier, irgendwas bei kontoC
        verifyNoInteractions(kontoC); // Konto C darf nicht empfangen
    }

    @Test
    void testGeldUeberweisenKontoNichtVorhanden() {
        Geldbetrag betrag = new Geldbetrag(10000);

        assertThrows(KontoNichtVorhandenException.class, () ->
                bank.geldUeberweisen(999999L, kontoNummerB, betrag, "Miete"));
        verifyNoInteractions(kontoA, kontoB, kontoC); // Keine Aktionen auf Konten

    }

    @Test
    void testGeldUeberweisenBetragNull() {
        assertThrows(IllegalArgumentException.class, () ->
                bank.geldUeberweisen(kontoNummerA, kontoNummerB, null, "Miete"));
    }

    @Test
    void testIstGesperrtKontoLoeschen() {
        when(kontoA.isGesperrt()).thenReturn(true);

        assertTrue(bank.kontoLoeschen(kontoNummerA));

        assertFalse(bank.kontoVorhanden(kontoNummerA)); // Konto sollte entfernt sein

    }

    @Test
    void testExistierendesKontoLoeschen() {
        assertTrue(bank.kontoLoeschen(kontoNummerA));

        assertFalse(bank.kontoVorhanden(kontoNummerA)); // Konto sollte entfernt sein
    }

    @Test
    void testNichtExistierendesKontoLoeschen() {
        assertFalse(bank.kontoLoeschen(99999L));
    }



}
