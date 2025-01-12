package uhr;

public interface Beobachter {
    /**
     * wird aufgerufen, Uhrzeit sich veaendert
     * @param zeit Uhrzeit die ausgegeben wird
     */
    void aktualisieren(Zeit zeit);

}

