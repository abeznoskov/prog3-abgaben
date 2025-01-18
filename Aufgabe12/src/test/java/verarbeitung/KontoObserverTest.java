package verarbeitung;

import static org.mockito.Mockito.*;

import bankprojekt.verarbeitung.Beobachter;
import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import org.junit.jupiter.api.Test;

public class KontoObserverTest {

    // Test fuer Benachrichtigung:

    @Test
    public void testNachEinzahlung() {
        Beobachter mockBeobachter = mock(Beobachter.class);
        Konto konto = new Girokonto();
        konto.anmelden(mockBeobachter);
        konto.einzahlen(new Geldbetrag(100));

        // Ueberpruefen, ob aktualisieren() des Beobachters aufgerufen wurde
        verify(mockBeobachter).aktualisieren(konto);
    }

    @Test
    public void testNachUeberweisungAbsenden() throws Exception {
        Beobachter mockBeobachter = mock(Beobachter.class);
        Girokonto girokonto = new Girokonto();
        girokonto.anmelden(mockBeobachter);

        // Überweisung absenden
        boolean erfolgreich = girokonto.ueberweisungAbsenden(
                new Geldbetrag(100),
                "Empfänger",
                123456789L,
                987654321L,
                "Miete"
        );

        // Überprüfen, ob die Überweisung erfolgreich war
        assert erfolgreich;
        verify(mockBeobachter, times(1)).aktualisieren(girokonto);
    }

    @Test
    public void testKeineMeldungNachAbmeldung() {
        Beobachter mockBeobachter = mock(Beobachter.class);
        Konto konto = new Girokonto();
        konto.anmelden(mockBeobachter);
        konto.abmelden(mockBeobachter);

        konto.einzahlen(new Geldbetrag(100));

        // Überprüfen, dass aktualisieren() nicht aufgerufen wurde
        verify(mockBeobachter, never()).aktualisieren(konto);
    }

    @Test
    public void testBeiKontostandsAenderung() {
        Beobachter mockBeobachter = mock(Beobachter.class);
        Konto konto = new Girokonto();
        konto.anmelden(mockBeobachter);

        konto.einzahlen(new Geldbetrag(500));

        // Ueberpruefen, ob die Beobachter benachrichtigt wurden
        verify(mockBeobachter, times(1)).aktualisieren(konto);
    }
    @Test
    public void testBeiDispoAenderung() {
        Beobachter mockBeobachter = mock(Beobachter.class);
        Girokonto girokonto = new Girokonto();
        girokonto.anmelden(mockBeobachter);

        girokonto.setDispo(new Geldbetrag(2000));

        // Überprüfen, ob die Beobachter benachrichtigt wurden
        verify(mockBeobachter, times(1)).aktualisieren(girokonto);
    }


}
