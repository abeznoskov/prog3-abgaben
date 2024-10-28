package verwaltung;

import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.Kunde;

import java.util.List;

public class Bank {
    public Bank(long bankleitzahl) {

    }

    public long getBankleitzahl() {
        return 0;
    }

    public long girokontoErstellen(Kunde inhaber) {
        return 0;
    }

    public long sparbuchErstellen(Kunde inhaber) {
        return 0;
    }

    public String getAlleKonten() {
        return "";
    }

    public List<Long> getAlleKontonummern() {
        return null;
    }

    public boolean geldAbheben(long von, Geldbetrag betrag) {
        return false;
    }

    public void geldEinzahlen(long auf, Geldbetrag betrag) {

    }

    public boolean kontoLoeschen(long nummer) {
        return false;
    }

    public Geldbetrag getKontostand(long nummer) {
        return null;
    }

    public boolean geldUeberweisen(long vonKontonr,
                                   long nachKontonr,
                                   Geldbetrag betrag,
                                   String verwendungszweck) {
        return false;
    }
}
