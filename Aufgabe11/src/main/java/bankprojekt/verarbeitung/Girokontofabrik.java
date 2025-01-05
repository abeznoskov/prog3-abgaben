package bankprojekt.verarbeitung;

/**
 * Fabrik fuer Girokonto
 */
public class Girokontofabrik extends Kontofabrik {
    @Override
    public Konto erstellen(long kontonummer, Kunde kunde) {
        return new Girokonto(kunde, kontonummer, new Geldbetrag() );
    }
}
