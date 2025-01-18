package bankprojekt.verarbeitung;

public interface Beobachter {
    /**
     * wird aufgerufen, wenn sich Kontostand/Dispo veaendert
     * @param konto Nachricht die ausgegeben wird
     */
    void aktualisieren(Konto konto);
}
