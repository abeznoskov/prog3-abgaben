package bankprojekt.verarbeitung;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Aktienkonto extends Konto {

    private final Map<String, Integer> depot = new HashMap<>();
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public Aktienkonto(Kunde inhaber, long kontoNr) {
        super(inhaber, kontoNr);
    }

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
                Geldbetrag erlös = new Geldbetrag(aktie.getKurs().getBetrag() * anzahl);

                this.einzahlen(erlös);
                depot.remove(wkn);
                return erlös;
            }
        });
    }

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
