package bankprojekt.verarbeitung;

/**
 * Fabrik fuer Girokonto
 */
public class Girokontofabrik extends Kontofabrik {
    /**
     * Erstellt ein neues Konto
     * @param kontonummer des Kontos
     * @param kunde inhaber des Kontos
     * return girokonto
     * @throws IllegalArgumentException bei leerem kunde
     */
    @Override
    public Konto erstellen(long kontonummer, Kunde kunde) {
        return new Girokonto(kunde, kontonummer, new Geldbetrag() );
    }
}
