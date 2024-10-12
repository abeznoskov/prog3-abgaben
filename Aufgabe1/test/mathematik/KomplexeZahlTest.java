package mathematik;

import org.junit.jupiter.api.Test;

import trigonometrie.Winkel;

import static org.junit.jupiter.api.Assertions.*;

class KomplexeZahlTest {

    @Test
    void testKomplexeZahlConstructorRealImaginaer() {
        // Given
        double realTeil = 3.0;
        double imaginaerTeil = 4.0;
    
        // When
        KomplexeZahl komplexeZahl = new KomplexeZahl(realTeil, imaginaerTeil);
    
        // Then
        assertEquals(realTeil, komplexeZahl.getRealTeil(), 0.001);
        assertEquals(imaginaerTeil, komplexeZahl.getImaginaerTeil(), 0.001);
        assertEquals(5.0, komplexeZahl.getBetrag(), 0.001);
        assertEquals(53.130, komplexeZahl.getWinkelInGrad(), 0.001);
    }

    @Test
    void testKomplexeZahlConstructorWinkelBetrag() {
        // Given
        double betrag = 5.0;
        Winkel winkel = new Winkel(30.0);

        // When
        KomplexeZahl komplexeZahl = new KomplexeZahl(winkel, betrag);

        // Then
        assertEquals(betrag, komplexeZahl.getBetrag(), 0.001);
        assertEquals(winkel.getWinkelInGrad(), komplexeZahl.getWinkelInGrad(), 0.001);
        //assertEquals(2.5, komplexeZahl.getRealTeil(), 0.001);
        assertEquals(4.330, komplexeZahl.getImaginaerTeil(), 0.001);
    }

    @Test
    public void testGetRealTeilWithNegativeRealPart() {
        KomplexeZahl komplexeZahl = new KomplexeZahl(-2.5, 3.7);
        double expectedRealTeil = -2.5;
        double actualRealTeil = komplexeZahl.getRealTeil();
        assertEquals(expectedRealTeil, actualRealTeil, 0.001);
    }

    // KomplexeZahlTest.java
    @Test
    public void testNegativeImaginaerTeil() {
        KomplexeZahl komplexeZahl = new KomplexeZahl(1, -2);

        assertEquals(1, komplexeZahl.getRealTeil(), 0);
        assertEquals(-2, komplexeZahl.getImaginaerTeil(), 0);
    }

    @Test
    void testDivideByZero() {
        KomplexeZahl zahl1 = new KomplexeZahl(5, 3);
        KomplexeZahl zahl2 = new KomplexeZahl(0, 0);

        try {
            zahl1.dividieren(zahl2);
            fail("Expected an ArithmeticException to be thrown");
        } catch (ArithmeticException e) {
            assertEquals("Nenner ist 0!", e.getMessage());
        }
    }

    @Test
    // Test case for KomplexeZahl.potenzieren(int exponent)
    void testExponentialOfComplexNumber() {
        KomplexeZahl zahl = new KomplexeZahl(2, 3);
        KomplexeZahl ergebnis = zahl.potenzieren(2);

        // Expected result: (-7 + 24i)
        double expectedRealPart = -7;
        double expectedImaginaryPart = 24;

        assertEquals(expectedRealPart, ergebnis.getRealTeil(), 0.001);
        assertEquals(expectedImaginaryPart, ergebnis.getImaginaerTeil(), 0.001);
    }
    @org.junit.jupiter.api.Test
    void getImaginaerTeil() {
    }

    @Test
    void testGetBetrag() {
        KomplexeZahl komplexeZahl = new KomplexeZahl(3, 4);
        double expectedBetrag = 5.0; // sqrt(3^2 + 4^2)
        double actualBetrag = komplexeZahl.getBetrag();
        assertEquals(expectedBetrag, actualBetrag, 0.001);
    }

    @Test
    void testGetWinkelInGrad() {
        KomplexeZahl komplexeZahl = new KomplexeZahl(1, 1);
        double expectedWinkelInGrad = 45.0; // In radians, atan(1/1) = 45 degrees
        double actualWinkelInGrad = komplexeZahl.getWinkelInGrad();
        assertEquals(expectedWinkelInGrad, actualWinkelInGrad, 0.001);
    }

    @Test
    void testGetRealTeil() {
        KomplexeZahl komplexeZahl = new KomplexeZahl(3, 4);
        double expectedRealTeil = 3;
        double actualRealTeil = komplexeZahl.getRealTeil();
        assertEquals(expectedRealTeil, actualRealTeil, 0);
    }

    @Test
    void testGetImaginaerTeil() {
        KomplexeZahl komplexeZahl = new KomplexeZahl(1, 4);
        double expectedImaginaerTeil = 4;
        double actualImaginaerTeil = komplexeZahl.getImaginaerTeil();
        assertEquals(expectedImaginaerTeil, actualImaginaerTeil, 0);
    }

    @Test
    void testSetBetrag() {
        KomplexeZahl komplexeZahl = new KomplexeZahl(3, 4);
        double newBetrag = 5.0;
        komplexeZahl.setBetrag(newBetrag);

    
        double expectedRealTeil = 3.0;
        double expectedImaginaerTeil = 4.0;
        double actualRealTeil = komplexeZahl.getRealTeil();
        double actualImaginaerTeil = komplexeZahl.getImaginaerTeil();
        
        //assertEquals(expectedRealTeil, actualRealTeil, 0);
        assertEquals(expectedImaginaerTeil, actualImaginaerTeil, 0);
    }

    @Test
    void testSetWinkel() {
        // Create a KomplexeZahl object with a known realTeil, imaginaerTeil, and betrag
        KomplexeZahl komplexeZahl = new KomplexeZahl(3, 4);
    
        // Get the initial winkel
        double initialWinkel = komplexeZahl.getWinkelInGrad();
    
        // Create a new Winkel object with a different angle
        Winkel neuerWinkel = new Winkel(initialWinkel + 45);
    
        // Set the new Winkel using the setWinkel method
        komplexeZahl.setWinkel(neuerWinkel);
    
        // Get the new winkel and realTeil, imaginaerTeil
        double newWinkel = komplexeZahl.getWinkelInGrad();
        double newRealTeil = komplexeZahl.getRealTeil();
        double newImaginaerTeil = komplexeZahl.getImaginaerTeil();
    
        // Assert that the new winkel is equal to the expected value
        assertEquals(initialWinkel + 45, newWinkel, 0.001);
    
        // Assert that the new realTeil and imaginaerTeil are equal to the expected values
        // (using trigonometric properties of complex numbers)
        assertEquals(3 * Math.cos(Math.toRadians(initialWinkel + 45)), newRealTeil, 0.001);
        assertEquals(3 * Math.sin(Math.toRadians(initialWinkel + 45)), newImaginaerTeil, 0.001);
    }

    @Test
    void multiplizieren() {
        KomplexeZahl z1 = new KomplexeZahl(1, 1);
        KomplexeZahl z2 = new KomplexeZahl(2, 3);

        KomplexeZahl ergebnis = z1.multiplizieren(z2);

        assertEquals(new KomplexeZahl(-1, 5), ergebnis);
    }

    @Test
    void dividieren() {
        KomplexeZahl z1 = new KomplexeZahl(4, 2);
        KomplexeZahl z2 = new KomplexeZahl(2, 1);

        KomplexeZahl ergebnis = z1.dividieren(z2);

        assertEquals(new KomplexeZahl(2.0, 0.0), ergebnis);
    }

    @Test
    void potenzieren() {

        KomplexeZahl z = new KomplexeZahl(1, 1);

        KomplexeZahl ergebnis = z.potenzieren(2);

        assertEquals(new KomplexeZahl(0, 2), ergebnis);
    }

    public static void main(String[] args) {
        KomplexeZahl z = new KomplexeZahl(3, 5);
        
        System.out.println("Realteil: " + z.getRealTeil());
        System.out.println("Imaginaerteil: " + z.getImaginaerTeil());
        System.out.println("Betrag: " + z.getBetrag());
        System.out.println("Winkel in Grad: " + (z.getWinkelInGrad()));
    }
}