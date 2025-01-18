package bankprojekt.verarbeitung;

/**
 * Fabrik fuer Sparbuch
 */
public class Sparbuchfabrik extends Kontofabrik {
    /**
     * Erstellt ein neues Konto
     * @param kontonummer des Kontos
     * @param kunde inhaber des Kontos
     * return Sparbuch
     * @throws IllegalArgumentException bei leerem kunde
     */
    @Override
    public Konto erstellen(long kontonummer, Kunde kunde) {
        return new Sparbuch(kunde, kontonummer);
    }
}
