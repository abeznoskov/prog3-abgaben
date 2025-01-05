package bankprojekt.verarbeitung;

/**
 * Fabrik fuer Sparbuch
 */
public class Sparbuchfabrik extends Kontofabrik {
    @Override
    public Konto erstellen(long kontonummer, Kunde kunde) {
        return new Sparbuch(kunde, kontonummer);
    }
}
