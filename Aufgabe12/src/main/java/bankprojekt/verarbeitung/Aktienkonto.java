package bankprojekt.verarbeitung;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Ein Konto, das den Handel mit Aktien ermöglicht.
 * Erworbene Konten werden, in einem depot gespeichert
 *
 */
public class Aktienkonto extends Konto implements Serializable {

    private static final long serialVersionUID = 1L; // Versionsnummer für die Serialisierung
    private final Map<String, Integer> depot = new ConcurrentHashMap<>();
    private static transient ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * Erstellt ein neues Aktienkonto.
     *
     * @param inhaber der Inhaber des Kontos
     * @param kontoNr die Kontonummer
     *
     */
    public Aktienkonto(Kunde inhaber, long kontoNr) {
        super(inhaber, kontoNr);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // Initialisiere das ExecutorService nach der Deserialisierung
        executor = Executors.newCachedThreadPool();
    }

    /**
     * Erstellt einen Kaufauftrag für eine Aktie. Wartet darauf, dass der Kurs der
     * gewünschten Aktie unter den angegebenen Höchstpreis fällt, bevor sie
     * gekauft wird.
     *
     * @param wkn          die Wertpapierkennnummer der Aktie
     * @param anzahl       die Anzahl der zu kaufenden Aktien
     * @param hoechstpreis der maximale Kurs, zu dem die Aktie gekauft werden soll
     * @return ein Future, das den Gesamtpreis der gekauften Aktien
     *         zurückgibt, oder 0, wenn der Kauf nicht erfolgreich war
     *
     */
    public Future<Geldbetrag> kaufauftrag(String wkn, int anzahl, Geldbetrag hoechstpreis) {
        return executor.submit(() -> {
            Aktie aktie = Aktie.getAktie(wkn);
            if (aktie == null || anzahl <= 0 || hoechstpreis == null || hoechstpreis.isNegativ())
                //return new Geldbetrag(0);
                throw new IllegalArgumentException();

            while (aktie.getKurs().compareTo(hoechstpreis) > 0) {
                Thread.sleep(500); // kein sleep nutzen

                // lieber: kursVeraendert.awaitUninterruptException();
            }

            synchronized (this) {
                Geldbetrag gesamtpreis = new Geldbetrag(aktie.getKurs().getBetrag() * anzahl);

                if (this.getKontostand().compareTo(gesamtpreis) >= 0) {
                    this.abheben(gesamtpreis);
                    depot.put(wkn, depot.getOrDefault(wkn, 0) + anzahl);
                    return gesamtpreis;
                }

            }
            return null;
        });
    }

    /**
     * Erstellt einen Verkaufsauftrag für eine Aktie. Wartet darauf, dass der Kurs
     * der Aktie mindestens den angegebenen Minimalpreis erreicht, bevor sie
     * verkauft wird.
     *
     * @param wkn          die Wertpapierkennnummer der Aktie
     * @param minimalpreis der minimale Kurs, zu dem die Aktie verkauft werden soll
     * @return ein Future, das den Gesamterlös aus dem Verkauf der Aktien
     *         zurückgibt, oder 0, wenn der Verkauf nicht erfolgreich war
     *
     */
    public Future<Geldbetrag> verkaufauftrag(String wkn, Geldbetrag minimalpreis) {
        return executor.submit(() -> {
            Integer anzahl = depot.getOrDefault(wkn, 0);
            if (anzahl == 0) return new Geldbetrag(0);

            Aktie aktie = Aktie.getAktie(wkn);
            if (aktie == null) return new Geldbetrag(0);

            while (aktie.getKurs().compareTo(minimalpreis) < 0) {
                Thread.sleep(500); // kein sleep nutzen

                // lieber: kursVeraendert.awaitUninterruptException();
            }

            synchronized (this) {
                Geldbetrag erloes = new Geldbetrag(aktie.getKurs().getBetrag() * anzahl);

                this.einzahlen(erloes);
                depot.remove(wkn);
                return erloes;
            }
        });
    }

    /**
     * Hebt den angegebenen Betrag vom Konto ab
     *
     * @param betrag der abzuhebende Betrag
     * @return true, wenn die Abhebung erfolgreich war,
     *         false, wenn die Abhebung abgelehnt wurde
     * @throws IllegalArgumentException wenn der Betrag negativ oder null ist
     *
     */
    @Override
    protected boolean pruefeAbhebung(Geldbetrag betrag) {
        return !getKontostand().minus(betrag).isNegativ();
    }
}
