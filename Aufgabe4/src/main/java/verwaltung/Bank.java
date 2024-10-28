package verwaltung;

import bankprojekt.verarbeitung.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bank {

    private final long bankleitzahl;
    private Map<Long, Konto> kontoListe;
    private long vergebeneKontoNr = 0;
    private final Geldbetrag  standardDispo = new Geldbetrag(200);

    public Bank(long bankleitzahl) {
        this.bankleitzahl = bankleitzahl;
    }

    public long getBankleitzahl() {
        return bankleitzahl;
    }

    public long girokontoErstellen(Kunde inhaber) {
        Girokonto k = new Girokonto(inhaber, vergebeneKontoNr +1, standardDispo);
        kontoListe.put(vergebeneKontoNr, k);
        return vergebeneKontoNr;
    }

    public long sparbuchErstellen(Kunde inhaber) {
        Sparbuch s = new Sparbuch(inhaber, vergebeneKontoNr +1);
        kontoListe.put(vergebeneKontoNr, s);
        return vergebeneKontoNr;
    }

    public String getAlleKonten() {
        StringBuilder alleKonten = new StringBuilder();
        for(Konto k: kontoListe.values())
            alleKonten.append(k.toString()).append(" ");
        return alleKonten.toString();
    }

    public List<Long> getAlleKontonummern() {
        List<Long> alleKontonummern = new ArrayList<>();
        for(Map.Entry<Long, Konto> k: kontoListe.entrySet())
            alleKontonummern.add(k.getKey());
        return alleKontonummern;
    }

    public boolean geldAbheben(long von, Geldbetrag betrag) throws GesperrtException {
        return kontoListe.get(von).abheben(betrag);
    }

    public void geldEinzahlen(long auf, Geldbetrag betrag) {
        kontoListe.get(auf).einzahlen(betrag);
    }

    public boolean kontoLoeschen(long nummer) {
        kontoListe.remove(nummer);
        return true;
    }

    public Geldbetrag getKontostand(long nummer) {
        return kontoListe.get(nummer).getKontostand();
    }

    public boolean geldUeberweisen(long vonKontonr,
                                   long nachKontonr,
                                   Geldbetrag betrag,
                                   String verwendungszweck) throws GesperrtException {
        kontoListe.get(vonKontonr).abheben(betrag);
        kontoListe.get(nachKontonr).einzahlen(betrag);
        return true;
    }
}
