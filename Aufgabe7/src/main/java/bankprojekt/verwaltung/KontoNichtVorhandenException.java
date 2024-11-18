package bankprojekt.verwaltung;

/**
 * tritt ein Wenn Konto in der Bank nicht gefunden
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
 *
 */
public class KontoNichtVorhandenException extends Exception {

    /**
     * Konto nicht gefunden in der Bank
     */
    public KontoNichtVorhandenException(long kontonummer)
    {
        super("Konto mit Kontonr.: "+ kontonummer +" wurde nicht gefunden.");
    }


}
