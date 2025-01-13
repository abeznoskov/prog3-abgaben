package uhr;

import java.beans.PropertyChangeListener;

public interface Beobachter extends PropertyChangeListener {
    /**
     * wird aufgerufen, Uhrzeit sich veaendert
     * @param zeit Uhrzeit die ausgegeben wird
     */
    void aktualisieren(Zeit zeit);
    void uhrAn();
    void beenden();

}

