package bankprojekt.verarbeitung;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Ein Konto, das den Handel mit Aktien ermöglicht.
 * Erworbene Konten werden, in einem depot gespeichert
 *
 */
public class Aktienkonto extends Konto {

    private final Map<String, Integer> depot = new HashMap<>();
    private static final ExecutorService executor = Executors.newCachedThreadPool();

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
            if (aktie == null) return new Geldbetrag(0);

            while (aktie.getKurs().compareTo(hoechstpreis) > 0) {
                Thread.sleep(500);
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
                Thread.sleep(500);
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
     * @throws GesperrtException         wenn das Konto gesperrt ist
     * @throws IllegalArgumentException wenn der Betrag negativ oder null ist
     *
     */
    @Override
    public boolean abheben(Geldbetrag betrag) throws GesperrtException {
        if (this.isGesperrt()) {
            throw new GesperrtException(this.getKontonummer());
        }

        if (betrag == null || betrag.isNegativ()) {
            throw new IllegalArgumentException("Der Betrag darf nicht negativ, unendlich oder NaN sein.");
        }

        if (!getKontostand().minus(betrag).isNegativ())
        {
            setKontostand(getKontostand().minus(betrag));
            return true;
        }
        return false;
    }
}
