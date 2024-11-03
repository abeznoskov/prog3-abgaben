package verwaltung;

import bankprojekt.verarbeitung.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bank fuer das Speichern und Verwalten von Konten
 *
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
 *
 */
public class Bank {

    private final long bankleitzahl; // BLZ in DE starten bei 10000000
    private Map<Long, Konto> kontoListe = new HashMap<>();
    private long vergebeneKontoNr = 100000000000L;
    private final Geldbetrag  standardDispo = new Geldbetrag(200);

    /**
     * Konstruktor mit Zuweisung der positiven Bankleitzahl
     *
     * @param bankleitzahl die Bankleitzahl
     *
     */
    public Bank(long bankleitzahl) {
        if (bankleitzahl < 10000000L || bankleitzahl > 99999999L)
            throw new IllegalArgumentException("Bankleitzahlen in DE starten ab 10000000 bis max 99999999");

        this.bankleitzahl = bankleitzahl;
    }

    /**
     * Getter-Methode der Bankleitzahl
     *
     * @return die Bankleitzahl
     *
     */
    public long getBankleitzahl() {
        return bankleitzahl;
    }

    /**
     * Erstellt ein Girokonto für den angegebenen Kunden
     *
     * @param inhaber der Kunde
     * @return die zugewiesene Kontonummer
     * @throws IllegalArgumentException wenn inhaber null
     *
     */
    public long girokontoErstellen(Kunde inhaber) throws NullPointerException{
        if (inhaber == null)
            throw new IllegalArgumentException();

        this.vergebeneKontoNr += 1;

        Girokonto k = new Girokonto(inhaber, vergebeneKontoNr, standardDispo);
        this.kontoListe.put(vergebeneKontoNr, k);

        return vergebeneKontoNr;
    }

    /**
     * Erstellt ein Sparbuch für den angegebenen Kunden
     *
     * @param inhaber der Kunde
     * @return die zugewiesene Kontonummer
     * @throws IllegalArgumentException wenn inhaber null
     *
     */
    public long sparbuchErstellen(Kunde inhaber) throws NullPointerException {
        if (inhaber == null)
            //throw new NullPointerException();
            throw new IllegalArgumentException();

        this.vergebeneKontoNr += 1;

        Sparbuch s = new Sparbuch(inhaber, vergebeneKontoNr);
        kontoListe.put(vergebeneKontoNr, s);

        return vergebeneKontoNr;
    }

    /**
     * Gibt alle Konten als ihr *.toString() zurück
     *
     * @return alle Konten als String
     *
     */
    public String getAlleKonten() {
        StringBuilder alleKonten = new StringBuilder();
        for(Konto k: kontoListe.values()) {
            alleKonten.append(k.toString());
        }
        return alleKonten.toString();
    }

    /**
     * Gibt alle Kontonummern als List aus Long(s) zurück
     *
     * @return alle Kontonummern als List
     *
     */
    public List<Long> getAlleKontonummern() {
        List<Long> alleKontonummern = new ArrayList<>();
        for(Map.Entry<Long, Konto> k: kontoListe.entrySet())
            alleKontonummern.add(k.getKey());
        return alleKontonummern;
    }

    /**
     * Hebt Geld vom Konto mit der entpsprechender KontoNr ab
     *
     * @param von KontoNr
     * @param betrag Betrag
     * @return true  -> wenn Abheben erfolgreich
     *         false -> wenn Abheben fehlgeschlagen
     * @throws GesperrtException wenn Konto gesperrt
     * @throws IllegalArgumentException wenn von ungueltig ist
     *                                  wenn konto NULL ist
     *
     */
    public boolean geldAbheben(long von, Geldbetrag betrag) throws GesperrtException {
        if (betrag == null || betrag.getBetrag() < 0)
            throw new IllegalArgumentException("Betrag darf nicht null oder negativ sein.");

        if (von < 100000000000L || von > 999999999999L)
            throw new IllegalArgumentException("Kontonummer ist zwischen 100000000000 und 999999999999.");

        Konto konto = kontoListe.get(von);

        if (konto == null)
            throw new IllegalArgumentException("Kontonummer wurde nicht gefunden.");

        if (konto.isGesperrt())
            throw new GesperrtException(konto.getKontonummer());

        return konto.abheben(betrag);
    }

    /**
     * Geldeinzahlung auf das Konto mit der entsprechenden KontoNr
     *
     * @param auf KontoNr
     * @param betrag Betrag
     * @throws GesperrtException wenn Konto gesperrt
     * @throws IllegalArgumentException wenn auf ungueltig ist
     *                                  wenn konto NULL ist
     *
     */
    public void geldEinzahlen(long auf, Geldbetrag betrag) throws GesperrtException {
        if (betrag == null)
            throw new IllegalArgumentException("Betrag darf nicht null oder negativ sein.");

        if (auf < 100000000000L || auf > 999999999999L)
            throw new IllegalArgumentException("Kontonummer ist zwischen 100000000000 und 999999999999.");

        Konto konto = kontoListe.get(auf);

        if (konto == null)
            throw new IllegalArgumentException("Kontonummer wurde nicht gefunden.");

        if (konto.isGesperrt())
            throw new GesperrtException(konto.getKontonummer());

        konto.einzahlen(betrag);
    }

    /**
     * Löscht das Konto mit der entsprechenden KontoNr
     *
     * @param nummer KontoNr
     * @return true  -> wenn Löschen erfolgreich
     *         false -> wenn Konto nicht gefunden
     * @throws IllegalArgumentException wenn Kontonummer negativ ist
     *
     */
    public boolean kontoLoeschen(long nummer) {
        if (nummer < 0)
            throw new IllegalArgumentException("Kontonummer darf nicht negativ sein.");

        return kontoListe.remove(nummer) != null;
    }

    /**
     * Gibt den Kontostand des Kontos mit der entsprechenden KontoNr aus
     *
     * @param nummer KontoNr
     * @return Kontostand
     * @throws IllegalArgumentException wenn Kontonummer negativ ist
     * @throws NullPointerException wenn Konto nicht existiert
     *
     */
    public Geldbetrag getKontostand(long nummer) {
        if (nummer < 0)
            throw new IllegalArgumentException("Kontonummer darf nicht negativ sein.");

        Konto konto = kontoListe.get(nummer);

        if (konto == null)
            throw new NullPointerException("Konto mit der angegebenen Kontonummer wurde nicht gefunden.");

        //return kontoListe.get(nummer).getKontostand();
        return konto.getKontostand();
    }

    /**
     * Überweisen von Geld (Bank-intern)
     *
     * @param vonKontonr KontoNr von der überwiesen wird
     * @param nachKontonr KontoNr auf die überwiesen wird
     * @param betrag der zu überweisende Betrag
     * @param verwendungszweck Verwendunszweck
     * @return true  -> wenn Überweisen erfolgreich
     *         false -> wenn Überweisen fehlgeschlagen
     * @throws GesperrtException wenn das Konto gesperrt
     *
     */
    public boolean geldUeberweisen(long vonKontonr,
                                   long nachKontonr,
                                   Geldbetrag betrag,
                                   String verwendungszweck) throws GesperrtException {

        Konto von = kontoListe.get(vonKontonr);
        Konto an = kontoListe.get(nachKontonr);

        if (von == null | an == null)
            return false;

        if (von.isGesperrt())
            throw new GesperrtException(von.getKontonummer());

        if (an.isGesperrt())
            throw new GesperrtException(an.getKontonummer());

        if(!von.abheben(betrag))
            return false;

        an.einzahlen(betrag);

        return true;
    }
}
