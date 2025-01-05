package verwaltung;

import bankprojekt.verarbeitung.*;
import bankprojekt.verwaltung.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAufgabeSiebenTest {

    private Bank bank;
    private Konto kontoMock1;
    private Konto kontoMock2;
    private Kunde kundeMock1;
    private Kunde kundeMock2;

    @BeforeEach
    void setUp() {
        bank = new Bank(12345678L);

        // Mocking Kunden
        kundeMock1 = mock(Kunde.class);
        kundeMock2 = mock(Kunde.class);

        // Mocking Konten
        kontoMock1 = mock(Konto.class);
        kontoMock2 = mock(Konto.class);

        // Initiale Setups für Mock-Objekte
        when(kontoMock1.getInhaber()).thenReturn(kundeMock1);
        when(kontoMock2.getInhaber()).thenReturn(kundeMock2);

        // Füge die Mock-Konten zur Bank hinzu
        //bank.mockEinfuegen(kontoMock1);
        //bank.mockEinfuegen(kontoMock2);
    }

    @Test
    void schenkungAnNeuerwachseneTest() {
        Geldbetrag geschenk = new Geldbetrag(1000);

        // Setze das Alter der Kunden
        when(kundeMock1.getGeburtstag()).thenReturn(LocalDate.now().minusYears(18));
        when(kundeMock2.getGeburtstag()).thenReturn(LocalDate.now().minusYears(20));

        // Schenken
        bank.schenkungAnNeuerwachsene(geschenk);

        // Verifiziere, dass einzahlen nur für den Kunden, der 18 Jahre alt ist, aufgerufen wurde
        verify(kontoMock1).einzahlen(geschenk);
        verify(kontoMock2, never()).einzahlen(geschenk);
    }

    @Test
    void getKundenMitLeeremKontoTest() {
        List<Kunde> expected = new ArrayList<>();
        expected.add(kundeMock1);

        // Setze die Kontostände
        when(kontoMock1.getKontostand()).thenReturn(new Geldbetrag(-500));
        when(kontoMock2.getKontostand()).thenReturn(new Geldbetrag(1000));

        List<Kunde> result = bank.getKundenMitLeeremKonto();

        assertEquals(expected, result);
    }

    @Test
    void getKundengeburtstageTest() {
        // Mock-Daten
        when(kundeMock1.getVorname()).thenReturn("Hans");
        when(kundeMock1.getGeburtstag()).thenReturn(LocalDate.of(1998, 1, 5));

        when(kundeMock2.getVorname()).thenReturn("Anna");
        when(kundeMock2.getGeburtstag()).thenReturn(LocalDate.of(1995, 10, 3));

        String expected = "Hans 05.01." + System.lineSeparator() + "Anna 03.10.";

        String result = bank.getKundengeburtstage();

        assertEquals(expected, result);
    }

    @Test
    void getAnzahlSeniorenTest() {
        // Setze das Alter der Kunden
        when(kundeMock1.getGeburtstag()).thenReturn(LocalDate.now().minusYears(70));
        when(kundeMock2.getGeburtstag()).thenReturn(LocalDate.now().minusYears(65));

        long result = bank.getAnzahlSenioren();

        assertEquals(1, result);
    }
}
