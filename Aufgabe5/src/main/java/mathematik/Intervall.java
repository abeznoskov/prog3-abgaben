package mathematik;

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
     */
    public <E> boolean enthaelt(E wert) {
        if (wert == null || !wert.getClass().equals(untereGrenze.getClass())) {
            return false; 
        }
        T wertT = (T) wert;


        return (wertT.compareTo(untereGrenze) >= 0) && (wertT.compareTo(obereGrenze) <= 0);
    }

    /**
     * erstellt ein neues Intervall, das die Schnittmenge zweier Intervall-Objekte enthaelt
     * @param anderes das zweite Intervall
     * @return ein neues Intervall, das die Schnittmenge enthaelt oder null, wenn es keine Schnittmenge gibt
     * @throws IllegalArgumentException wenn der angegebene Wert nicht vom gleichen Typ ist wie die untere
     */
    public <A extends Comparable<A>> Intervall<T> schnitt(Intervall<A> anderes) {
        if (anderes == null || anderes.untereGrenze.getClass() != this.untereGrenze.getClass()) {
            return null;
        }

        T neueUntereGrenze = (this.untereGrenze.compareTo((T) anderes.untereGrenze) > 0) ? this.untereGrenze : (T) anderes.untereGrenze;
        T neueObereGrenze = (this.obereGrenze.compareTo((T) anderes.obereGrenze) < 0) ? this.obereGrenze : (T) anderes.obereGrenze;

        if (neueUntereGrenze.compareTo(neueObereGrenze) > 0) {
            return new Intervall<>(null, null);
        }

        return new Intervall<>(neueUntereGrenze, neueObereGrenze);
    }
}
