package verwaltung;

import mathematik.NullstellenSuche;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NullstellenSucheTest {

    static double toleranz = 0.01;

    @Test
    void testFindeNullstelleF() {
        // Test mit f(x) = x² - 25
        double nullstelleF = NullstellenSuche.findeNullstelle(x -> x * x - 25, 0, 10, toleranz);
        assertEquals(5.0, nullstelleF, toleranz);

    }

    @Test
    void testFindeNullstelleG() {
        // Test mit g(x) = e^(3x) - 7
        double nullstelleG = NullstellenSuche.findeNullstelle(x -> Math.exp(3 * x) - 7, 0, 2, toleranz);
        assertEquals(0.648, nullstelleG, toleranz);

    }

    @Test
    void testFindeNullstelleH() {
        // Test mit h(x) = sin(x²) - 0.5
        double nullstelleH = NullstellenSuche.findeNullstelle(x -> Math.sin(x * x) - 0.5, 0, 1.5, toleranz);
        assertEquals(0.724, nullstelleH, toleranz);
    }

    @Test
    void testFindeNullstelleK() {
        // Test mit k(x) = x² + 1 (keine Nullstelle)
        assertThrows(IllegalArgumentException.class, () ->
                NullstellenSuche.findeNullstelle(x -> x * x + 1, -2, 2, toleranz)
        );
    }
}
