package verarbeitung;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Beobachter;
import bankprojekt.verarbeitung.Geldbetrag;
import org.junit.Before;
import org.junit.Test;

public class BeobachterTest {

    private KontoProbe konto;
    private Beobachter beobachter1;
    private Beobachter beobachter2;

    // Eine Unterklasse von Konto, um die protected Methode zu testen
    private class KontoProbe extends Konto {
        @Override
        protected boolean pruefeAbhebung(Geldbetrag betrag) {
            return true;
        }

        // Ermöglicht benachrichtigen() aus Konto.java aufzurufen
        // da diese protected ist
        public void testBenachrichtigen() {
            super.benachrichtigen();
        }
    }

    @Before
    public void setUp() {
        konto = new KontoProbe();  // Verwenden der Unterklasse

        // Mock-Beobachter
        beobachter1 = mock(Beobachter.class);
        beobachter2 = mock(Beobachter.class);

        // Beobachter anmelden
        konto.anmelden(beobachter1);
        konto.anmelden(beobachter2);
    }

    @Test
    public void testBenachrichtigen() {
        // Methode benachrichtigen auf dem Konto aufrufen
        konto.testBenachrichtigen();

        // Wurde aktualisieren aufgerufen?
        verify(beobachter1, times(1)).aktualisieren(konto);
        verify(beobachter2, times(1)).aktualisieren(konto);
    }
}