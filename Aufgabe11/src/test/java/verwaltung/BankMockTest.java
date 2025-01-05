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
    Kunde kundeD;

    Girokonto kontoA;
    Girokonto kontoB;
    Sparbuch kontoC;
    Sparbuch kontoD;

    long kontoNummerA;
    long kontoNummerB;
    long kontoNummerC;
    long kontoNummerD;

    Kontofabrik giroKontofabrikKundeA;
    Kontofabrik giroKontofabrikKundeB;
    Kontofabrik sparbuchfabrikKundeC;
    Kontofabrik sparbuchfabrikKundeD;

    @BeforeEach
    void setUp() {
        bank = new Bank(13571135L);
        kundeA = new Kunde("testKundeA", "Girokonto", "Girostraße 1", LocalDate.parse("1998-01-05"));
        kundeB = new Kunde("testKundeB", "Sparbuch", "Sparstraße 1", LocalDate.parse("2011-02-11"));
        kundeC = new Kunde("testKundeC", "Girokonto", "Girostraße 2", LocalDate.parse("2011-11-11"));
        kundeD = new Kunde("testKundeD", "Girokonto", "Sparstraße 3", LocalDate.parse("2010-07-07"));

        kontoA = mock();
        kontoB = mock();
        kontoC = mock();
        kontoD = mock();

        giroKontofabrikKundeA = mock();
        giroKontofabrikKundeB = mock();
        sparbuchfabrikKundeC = mock();
        sparbuchfabrikKundeD = mock();

        when(giroKontofabrikKundeA.erstellen(anyLong(), any())).thenReturn(kontoA);
        when(giroKontofabrikKundeB.erstellen(anyLong(), any())).thenReturn(kontoB);
        when(sparbuchfabrikKundeC.erstellen(anyLong(), any())).thenReturn(kontoC);
        when(sparbuchfabrikKundeD.erstellen(anyLong(), any())).thenReturn(kontoD);

        when(kontoA.getInhaber()).thenReturn(kundeA);
        when(kontoB.getInhaber()).thenReturn(kundeB);
        when(kontoC.getInhaber()).thenReturn(kundeC);
        when(kontoD.getInhaber()).thenReturn(kundeD);

        kontoNummerA = bank.kontoErstellen(giroKontofabrikKundeA, kundeA);
        kontoNummerB = bank.kontoErstellen(giroKontofabrikKundeB, kundeB);
        kontoNummerC = bank.kontoErstellen(sparbuchfabrikKundeC, kundeC);
        kontoNummerD = bank.kontoErstellen(sparbuchfabrikKundeD, kundeD);
    }

    @Test
    void geldUeberweisenErfolgreich() throws GesperrtException, KontoNichtVorhandenException {
        when(kontoA.ueberweisungAbsenden(any(), anyString(), anyLong(), anyLong(), anyString())).thenReturn(true);
        assertTrue(bank.geldUeberweisen(kontoNummerA, kontoNummerB, new Geldbetrag(100), "abc"));

        verify(kontoA, times(1)).ueberweisungAbsenden(any(), anyString(), anyLong(), anyLong(), anyString());
        verify(kontoB, times(1)).ueberweisungEmpfangen(any(), anyString(), anyLong(), anyLong(), anyString());
    }

    @Test
    void testGeldUeberweisenZweiKontoNichtUeberweisungsfaehig() throws GesperrtException, KontoNichtVorhandenException {
        Geldbetrag betrag = new Geldbetrag(10000);

        boolean result = bank.geldUeberweisen(kontoNummerB, kontoNummerD, betrag, "Miete");

        assertFalse(result);

        verifyNoInteractions(kontoD);
        verifyNoInteractions(kontoB);
    }

    @Test
    void testGeldUeberweisenMitGesperrtemKonto() throws GesperrtException {
        Geldbetrag betrag = new Geldbetrag(10000);

        // Quellkonto ist gesperrt
        when(kontoA.ueberweisungAbsenden(any(), anyString(), anyLong(), anyLong(), anyString())).thenThrow(GesperrtException.class);

        // TODO: GesperrteException wird nicht korrekt ausgeworfen
        // GesperrtException erwartet
        assertThrows(GesperrtException.class, () -> bank.geldUeberweisen(kontoNummerA, kontoNummerB, new Geldbetrag(100), "Miete"));

        // Sicherstellen, dass ueberweisungAbsenden nicht aufgerufen wurde
        verify(kontoA, never()).ueberweisungEmpfangen(any(), anyString(), anyLong(), anyLong(), anyString());
        verifyNoInteractions(kontoB);
    }

    @Test
    void testGeldUeberweisenKontoNichtVorhanden() {
        Geldbetrag betrag = new Geldbetrag(10000);

        assertThrows(KontoNichtVorhandenException.class, () ->
                bank.geldUeberweisen(999999L, kontoNummerB, betrag, "Miete"));
        verifyNoInteractions(kontoA, kontoB, kontoC);

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
