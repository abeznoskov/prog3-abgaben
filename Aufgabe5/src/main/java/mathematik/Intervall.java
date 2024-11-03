package mathematik;

public class Intervall<T> {
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
        return null != untereGrenze && null != obereGrenze;
    }

    public <E> boolean enthaelt(E wert) {
        return false;
    }

    public <A> Intervall<T> schnitt(Intervall<A> anderes) {
        return null;
    }
    
}
