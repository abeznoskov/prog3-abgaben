package bankprojekt.verarbeitung;

/**
 * Eine Fabrik fuer Konten
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
 */
public abstract class Kontofabrik {
    public abstract Konto erstellen(long kontonummer, Kunde kunde);
}
