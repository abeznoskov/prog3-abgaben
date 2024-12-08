package bankprojekt.verarbeitung;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Aktienkonto extends Konto {

    private Map<String, Integer> depot = new HashMap<>();
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public Aktienkonto(Kunde inhaber, long kontoNr) {
        super(inhaber, kontoNr);
    }

    public Future<Geldbetrag> kaufauftrag(String wkn, int anzahl, Geldbetrag hoechstpreis) {
        return executor.submit(() -> {
            Aktie aktie = Aktie.getAktie(wkn);
            if (aktie == null) return null;

            while (aktie.getKurs().compareTo(hoechstpreis) > 0) {
                Thread.sleep(1000); // Warten, bis der Preis fällt
            }

            synchronized (this) {
                Geldbetrag gesamtpreis = new Geldbetrag(); // aktie.getKurs().multipliziere(anzahl)
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
            Integer anzahl = depot.get(wkn);
            if (anzahl == null || anzahl == 0) return null;

            Aktie aktie = Aktie.getAktie(wkn);
            if (aktie == null) return null;

            while (aktie.getKurs().compareTo(minimalpreis) < 0) {
                Thread.sleep(1000); // Warten, bis der Preis steigt
            }

            synchronized (this) {
                Geldbetrag erlös = new Geldbetrag(aktie.getKurs().getBetrag() * anzahl); //aktie.getKurs().multipliziere(anzahl)
                this.einzahlen(erlös);
                depot.remove(wkn);
                return erlös;
            }
        });
    }

    @Override
    public boolean abheben(Geldbetrag betrag) throws GesperrtException {
        return false;
    }
}
