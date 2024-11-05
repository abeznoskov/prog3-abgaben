package mathematik;

public class Intervall<T extends Comparable<T>> {
    private T untereGrenze;
    private T obereGrenze;

    public Intervall(T untereGrenze, T obereGrenze) {
        this.untereGrenze = untereGrenze;
        this.obereGrenze = obereGrenze;
    }

    public T getUntereGrenze() {
        return untereGrenze;
    }

    public T getObereGrenze() {
        return obereGrenze;
    }

    public boolean isLeer() {
        return obereGrenze.compareTo(untereGrenze) < 0;
    }

    public <E> boolean enthaelt(E wert) {
        if (wert == null || !wert.getClass().equals(untereGrenze.getClass())) {
            return false; 
        }
        T wertT = (T) wert;


        return (wertT.compareTo(untereGrenze) >= 0) && (wertT.compareTo(obereGrenze) <= 0);
    }

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
