package mathematik;

/**
 * Klasse Intervall, die ein mathematisches Intervall auf
 * einer linear geordneten Menge darstellt
 * Hauptprogramm unter package spielereien
 *
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
 * @param <T> Datentyp der Intervallgrenzen.
 * Die Elemente von T müssen vergleichbar sein mit einer mit equals() konsistenten Ordnung.
 */
public class Intervall<T extends Comparable<? super T>> {

    /**
     * untere Grenze eines Intervalls
     */
    private final T untereGrenze;
    /**
     * obere Grenze eines Intervalls
     */
    private final T obereGrenze;

    /**
     * Initialisiert ein Intervall-Objekt anhand der gegebenen untereGrenze und oberGrenze
     * @param untereGrenze untere Grenze des Intervalls
     * @param obereGrenze obere Grenze des Intervalls
     * @throws NullPointerException wenn die untereGrenze oder obereGrenze NULL ist
     */
    public Intervall(T untereGrenze, T obereGrenze) {
        if (untereGrenze == null || obereGrenze == null)
            throw new NullPointerException();

        this.untereGrenze = untereGrenze;
        this.obereGrenze = obereGrenze;
    }

    /**
     * Getter für die untereGrenze
     * @return untere Grenze des Intervalls
     */
    public T getUntereGrenze() {
        return untereGrenze;
    }

    /**
     * Getter für die obereGrenze
     * @return obere Grenze des Intervalls
     */
    public T getObereGrenze() {
        return obereGrenze;
    }

    /**
     * prüft, ob das Intervall leer ist
     * @return false, wenn die obereGrenze kleiner ist als die unterGrenze
     * oder wert NULL ist, sonst true
     */
    public boolean isLeer() {
        return obereGrenze.compareTo(untereGrenze) < 0;
    }

    /**
     * prüft, ob der angegebene Wert im Intervall liegt
     * @param wert der gesuchte Wert
     * @return true, wenn der Wert im Intervall liegt, sonst false
     * @throws NullPointerException wenn wert NULL ist
     */
    public <E extends Comparable<? super T>> boolean enthaelt(E wert) {
        if (wert == null){ // Comparable hat eine NPE Kontrolle
            throw new NullPointerException();
        }

        // mit nur <E extends T>, Timestamp wurde nicht korrekt kontrolliert
        // return untereGrenze.compareTo(wert) <= 0 && obereGrenze.compareTo(wert) >= 0;
        return wert.compareTo(this.untereGrenze) >= 0 && wert.compareTo(this.obereGrenze) <= 0;
    }

    /**
     * erstellt ein neues Intervall, das die Schnittmenge zweier Intervall-Objekte enthaelt
     * @param <A> der Typ des anderen Intervalls, welcher vom Typ dieses Intervalls abgeleitet ist
     * @param anderes das zweite Intervall
     * @return ein neues Intervall, das die Schnittmenge enthaelt oder leeres Intervall, wenn es keine Schnittmenge gibt
     * @throws NullPointerException wenn anderes Intervall NULL ist
     *
     */
    public <A extends T>  Intervall<T> schnitt(Intervall<A> anderes) {
        if (anderes == null)
            throw new NullPointerException();

        T neueObereGrenze = obereGrenze.compareTo(anderes.getObereGrenze()) < 0 ? obereGrenze : anderes.getObereGrenze();
        T neueUntereGrenze = untereGrenze.compareTo(anderes.getUntereGrenze()) > 0 ? untereGrenze : anderes.getUntereGrenze();

        /*
        T min = this.untereGrenze;
        if(min.compareTo(anderes.untereGrenze)>0)
            min = anderes.untereGrenze;

        T max = this.obereGrenze;
        ...
        */
        return new Intervall<>(neueUntereGrenze, neueObereGrenze);
    }


    /**
     * Gibt einen String des Intervall-Objekts zurueck
     * @return Ein String, der die Werte von untereGrenze und obereGrenze
     *         im Format "Intervall{untere Grenze: Wert, obere Grenze: Wert}" enthaelt
     */
    @Override
    public String toString() {
        return "Intervall{" +
                "untere Grenze: " + untereGrenze +
                ", obere Grenze: " + obereGrenze +
                '}';
    }
}
