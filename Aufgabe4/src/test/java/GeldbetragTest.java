import bankprojekt.geld.Waehrung;
import bankprojekt.verarbeitung.Geldbetrag;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class GeldbetragTest {

    // Tests fuer umrechnen():
    @Test
    public void testUmrechnen_EuroZuEuro() {
        // Konvertiere 100 Euro zu 100 Euro
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.EUR);
        geldbetrag.umrechnen(Waehrung.EUR);
        assertEquals(100, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, geldbetrag.getWaehrung());
    }
    @Test
    public void testUmrechnen_EscudosZuEscudos() {
        // Konvertiere 100 Escudos zu 100 Escudos
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.ESCUDO);
        geldbetrag.umrechnen(Waehrung.ESCUDO);
        assertEquals(100, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.ESCUDO, geldbetrag.getWaehrung());
    }

    @Test
    public void testUmrechnenEuroZuNichtEuro() {
        // Konvertiere 100 Euro zu 2.430.474,29 Dobra
        Geldbetrag geldbetrag = new Geldbetrag(100.0, Waehrung.EUR);
        geldbetrag.umrechnen(Waehrung.DOBRA);
        assertEquals(100.0 * Waehrung.DOBRA.getRate(), geldbetrag.getBetrag(), 0.001);
    }

    @Test
    public void testUmrechnenNichtEuroZuEuro() {
        // Konvertiere 1000 Dobra zu 0.04 Euro
        Geldbetrag geldbetrag = new Geldbetrag(1000, Waehrung.DOBRA);
        double expectedBetrag = 1000 / Waehrung.DOBRA.getRate();

        geldbetrag.umrechnen(Waehrung.EUR);

        assertEquals(expectedBetrag, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, geldbetrag.getWaehrung());
    }

    @Test
    public void testUmrechnenNichtEuroZuAndererNichtEuro() {
        // Konvertiere 100 Franc zu 4.950,86 Dobra
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.FRANC);
        double expectedBetrag = 100 / Waehrung.FRANC.getRate() * Waehrung.DOBRA.getRate();
        geldbetrag.umrechnen(Waehrung.DOBRA);
        assertEquals(expectedBetrag, geldbetrag.getBetrag(), 0.001);
        assertEquals(Waehrung.DOBRA, geldbetrag.getWaehrung());
    }

    @Test
    public void testUmrechnen_ThrowsIllegalArgumentExceptionWennParameterNullIst() {
        // Ziehlwaehrung ist null
        Geldbetrag geldbetrag = new Geldbetrag();
        assertThrows(IllegalArgumentException.class, () -> geldbetrag.umrechnen(null));
    }

    // Tests für plus(): ------------------------------------------------------------------------------------

    @Test
    public void testPlus_VerschiedeneWaehrungen() {
        // Addiere 100 Euro mit 1000 Dobra
        Geldbetrag geldbetrag1 = new Geldbetrag(100, Waehrung.EUR);
        Geldbetrag geldbetrag2 = new Geldbetrag(1000, Waehrung.DOBRA);

        geldbetrag1.umrechnen(Waehrung.DOBRA); // 100€ -> 2.430.474,29 Db
        Geldbetrag result = geldbetrag1.plus(geldbetrag2);
        double expectedBetrag = 100 * Waehrung.DOBRA.getRate() + 1000;

        assertEquals(expectedBetrag, result.getBetrag(), 0.001);
        assertEquals(Waehrung.DOBRA, result.getWaehrung());
    }

    @Test
    public void testPlus_ThrowsIllegalArgumentExceptionWennSummandIsNull() {
        // Summand ist null
        Geldbetrag geldbetrag = new Geldbetrag(100, Waehrung.EUR);
        Geldbetrag summand = null;

        assertThrows(IllegalArgumentException.class, () -> geldbetrag.plus(summand));
    }

    @Test
    public void testPlus_NegativerSummand() {
        // Addiere Euro mit negativer Dobra
        Geldbetrag geldbetrag1 = new Geldbetrag(100, Waehrung.EUR);
        Geldbetrag geldbetrag2 = new Geldbetrag(-1000, Waehrung.EUR);

        Geldbetrag result = geldbetrag1.plus(geldbetrag2);
        double expectedBetrag = 100 + (-1000);

        assertEquals(expectedBetrag, result.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, result.getWaehrung());
    }

    @Test
    public void testPlus_SummandIstZero() {
        // Summand ist 0 EUR
        Geldbetrag geldbetrag1 = new Geldbetrag(100, Waehrung.EUR);
        Geldbetrag geldbetrag2 = new Geldbetrag(0, Waehrung.EUR);

        Geldbetrag result = geldbetrag1.plus(geldbetrag2);

        assertEquals(100, result.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, result.getWaehrung());
    }

    @Test
    public void testPlus_SummandIstSehrGroß() {
        // Addiere Euro mit einem sehr großen Euro
        Geldbetrag geldbetrag1 = new Geldbetrag(100, Waehrung.EUR);
        Geldbetrag geldbetrag2 = new Geldbetrag(999999899, Waehrung.EUR);

        Geldbetrag result = geldbetrag1.plus(geldbetrag2);

        assertEquals(999999999, result.getBetrag(), 0.001);
        assertEquals(Waehrung.EUR, result.getWaehrung());
    }

    @Test
    public void testPlus_AdditionMitNullen() {
        // Addiere 0 Euro mit 0 Euro
        Geldbetrag geldbetrag1 = new Geldbetrag(0, Waehrung.EUR);
        Geldbetrag geldbetrag2 = new Geldbetrag(0, Waehrung.EUR);

        Geldbetrag result = geldbetrag1.plus(geldbetrag2);

        assertEquals(0, result.getBetrag());
        assertEquals(Waehrung.EUR, result.getWaehrung());
    }
}