package mathematik;

/**
 * Klasse Intervall, die ein mathematisches Intervall auf
 * einer linear geordneten Menge darstellt
 *
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
 *
 */
public class Intervall<T extends Comparable<T>> {
    /**
     * untere Grenze eines Intervalls
     */
    private T untereGrenze;
    /**
     * obere Grenze eines Intervalls
     */
    private T obereGrenze;
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
    public <E extends T> boolean enthaelt(E wert) {
        if (wert == null){
            throw new NullPointerException();
        }

        return untereGrenze.compareTo(wert) <= 0 && obereGrenze.compareTo(wert) >= 0;
    }

    /**
     * erstellt ein neues Intervall, das die Schnittmenge zweier Intervall-Objekte enthaelt
     * @param <A> der Typ des anderen Intervalls, welcher vom Typ dieses Intervalls abgeleitet ist
     * @param anderes das zweite Intervall
     * @return ein neues Intervall, das die Schnittmenge enthaelt oder leeres Intervall, wenn es keine Schnittmenge gibt
     * @throws NullPointerException wenn anderes Intervall NULL ist
     *
     */
    public <A extends T>  Intervall<T> schnitt(Intervall<? extends A> anderes) {
        if (anderes == null)
            throw new NullPointerException();

        T neueObereGrenze = obereGrenze.compareTo(anderes.untereGrenze) < 0 ? obereGrenze : anderes.obereGrenze;
        T neueUntereGrenze = untereGrenze.compareTo(anderes.untereGrenze) > 0 ? untereGrenze : anderes.untereGrenze;

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
