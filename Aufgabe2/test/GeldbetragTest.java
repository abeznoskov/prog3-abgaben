import bankprojekt.geld.Waehrung;
import bankprojekt.verarbeitung.Geldbetrag;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeldbetragTest {
    @Test
    public void testUmrechnen_SameCurrencyEuro() {
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.EUR);
        geldbetrag.umrechnen(Waehrung.EUR);
        assertEquals(100, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, geldbetrag.getWaehrung());
    }
    @Test
    public void testUmrechnen_SameCurrencyEscudos() {
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.ESCUDO);
        geldbetrag.umrechnen(Waehrung.ESCUDO);
        assertEquals(100, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.ESCUDO, geldbetrag.getWaehrung());
    }

    @Test
    public void testUmrechnenEuroToAnotherCurrency() {
        Geldbetrag geldbetrag = new Geldbetrag(100.0, Waehrung.EUR);
        geldbetrag.umrechnen(Waehrung.DOBRA);
        assertEquals(100.0 * Waehrung.DOBRA.getRate(), geldbetrag.getBetrag(), 0.001);
    }

    @Test
    public void testUmrechnenNonEuroToEuro() {
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.DOBRA);
        double expectedBetrag = 100 / Waehrung.DOBRA.getRate();

        geldbetrag.umrechnen(Waehrung.EUR);

        assertEquals(expectedBetrag, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, geldbetrag.getWaehrung());
    }

    @Test
    public void testUmrechnenNonEuroToAnotherCurrency() {
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.FRANC);
        double expectedBetrag = 100 / Waehrung.FRANC.getRate();
        geldbetrag.umrechnen(Waehrung.DOBRA);
        assertEquals(expectedBetrag, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.DOBRA, geldbetrag.getWaehrung());
    }
}