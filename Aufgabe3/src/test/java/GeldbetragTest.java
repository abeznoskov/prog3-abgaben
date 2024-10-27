import bankprojekt.geld.Waehrung;
import bankprojekt.verarbeitung.Geldbetrag;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeldbetragTest {

    // Tests fuer umrechnen():
    @Test
    public void testUmrechnen_EuroZuEuro() {
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.EUR);
        geldbetrag.umrechnen(Waehrung.EUR);
        assertEquals(100, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, geldbetrag.getWaehrung());
    }
    @Test
    public void testUmrechnen_EscudosZuEscudos() {
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.ESCUDO);
        geldbetrag.umrechnen(Waehrung.ESCUDO);
        assertEquals(100, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.ESCUDO, geldbetrag.getWaehrung());
    }

    @Test
    public void testUmrechnenEuroZuNichtEuro() {
        Geldbetrag geldbetrag = new Geldbetrag(100.0, Waehrung.EUR);
        geldbetrag.umrechnen(Waehrung.DOBRA);
        assertEquals(100.0 * Waehrung.DOBRA.getRate(), geldbetrag.getBetrag(), 0.001);
    }

    @Test
    public void testUmrechnenNichtEuroZuEuro() {
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.DOBRA);
        double expectedBetrag = 100 / Waehrung.DOBRA.getRate();

        geldbetrag.umrechnen(Waehrung.EUR);

        assertEquals(expectedBetrag, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, geldbetrag.getWaehrung());
    }

    @Test
    public void testUmrechnenNichtEuroZuAndererNichtEuro() {
        Geldbetrag geldbetrag = new Geldbetrag(1000, Waehrung.FRANC);
        double expectedBetrag = 1000 / Waehrung.FRANC.getRate() * 24304.7429;
        geldbetrag.umrechnen(Waehrung.DOBRA);
        assertEquals(expectedBetrag, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.DOBRA, geldbetrag.getWaehrung());
    }

    @Test
    public void testUmrechnen_ThrowsIllegalArgumentExceptionWennParameterNullIst() {

        Geldbetrag geldbetrag = new Geldbetrag();
        assertThrows(IllegalArgumentException.class, () -> geldbetrag.umrechnen(null));
    }

    // Tests für plus(): ------------------------------------------------------------------------------------

    @Test
    public void testPlus_VerschiedeneWaehrungen() {
        Geldbetrag geldbetrag1 = new Geldbetrag(100, Waehrung.EUR);
        Geldbetrag geldbetrag2 = new Geldbetrag(50, Waehrung.DOBRA);

        geldbetrag1.umrechnen(Waehrung.DOBRA); // 100€ -> 2.454,8532 DOBRA
        Geldbetrag result = geldbetrag1.plus(geldbetrag2);
        double expectedBetrag = 100 * Waehrung.DOBRA.getRate() + 50;

        assertEquals(expectedBetrag, result.getBetrag(), 0.001);
        assertEquals(Waehrung.DOBRA, result.getWaehrung());
    }

    @Test
    public void testPlus_ThrowsIllegalArgumentExceptionWennSummandIsNull() {
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.EUR);
        Geldbetrag summand = null;

        assertThrows(IllegalArgumentException.class, () -> geldbetrag.plus(summand));
    }

    @Test
    public void testPlus_NegativerSummand() {
        Geldbetrag geldbetrag1 = new Geldbetrag(100, Waehrung.EUR);
        Geldbetrag geldbetrag2 = new Geldbetrag(-50, Waehrung.EUR);

        Geldbetrag result = geldbetrag1.plus(geldbetrag2);
        double expectedBetrag = 100 + (-50);

        assertEquals(expectedBetrag, result.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, result.getWaehrung());
    }

    @Test
    public void testPlus_SummandIstZero() {
        Geldbetrag geldbetrag1 = new Geldbetrag(100, Waehrung.EUR);
        Geldbetrag geldbetrag2 = new Geldbetrag(0, Waehrung.EUR);

        Geldbetrag result = geldbetrag1.plus(geldbetrag2);

        assertEquals(100, result.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, result.getWaehrung());
    }

    @Test
    public void testPlus_SummandIstSehrGroß() {
        Geldbetrag geldbetrag1 = new Geldbetrag(100, Waehrung.EUR);
        Geldbetrag geldbetrag2 = new Geldbetrag(999999899, Waehrung.EUR);

        Geldbetrag result = geldbetrag1.plus(geldbetrag2);

        assertEquals(999999999, result.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, result.getWaehrung());
    }

    @Test
    public void testPlus_AdditionMitNullen() {
        Geldbetrag geldbetrag1 = new Geldbetrag(100, Waehrung.EUR);
        Geldbetrag geldbetrag2 = new Geldbetrag(0.00000001, Waehrung.EUR);

        Geldbetrag result = geldbetrag1.plus(geldbetrag2);

        assertEquals(100.00000001, result.getBetrag(), 0.0001);
        assertEquals(Waehrung.EUR, result.getWaehrung());
    }
}