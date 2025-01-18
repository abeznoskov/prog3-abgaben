package bankprojekt.verarbeitung;

/**
 * Eine Fabrik fuer Konten
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
 */
public abstract class Kontofabrik {

    /**
     * Erstellt ein neues Konto
     * @param kontonummer des Kontos
     * @param kunde inhaber des Kontos
     * @throws IllegalArgumentException bei leerem kunde
     */
    public abstract Konto erstellen(long kontonummer, Kunde kunde);
}
