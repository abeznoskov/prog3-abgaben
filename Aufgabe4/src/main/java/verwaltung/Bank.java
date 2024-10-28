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

    private final long bankleitzahl;
    private Map<Long, Konto> kontoListe = new HashMap<>();
    private long vergebeneKontoNr = 0;
    private final Geldbetrag  standardDispo = new Geldbetrag(200);

    /**
     * Konstruktor mit Zuweisung der Bankleitzahl
     *
     * @param bankleitzahl die Bankleitzahl
     *
     */
    public Bank(long bankleitzahl) {
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
     *
     */
    public long girokontoErstellen(Kunde inhaber) {
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
     *
     */
    public long sparbuchErstellen(Kunde inhaber) {
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
     *
     */
    public boolean geldAbheben(long von, Geldbetrag betrag) throws GesperrtException {
        return kontoListe.get(von).abheben(betrag);
    }

    /**
     * Geldeinzahlung auf das Konto mit der entsprechenden KontoNr
     *
     * @param auf KontoNr
     * @param betrag Betrag
     *
     */
    public void geldEinzahlen(long auf, Geldbetrag betrag) {
        kontoListe.get(auf).einzahlen(betrag);
    }

    /**
     * Löscht das Konto mit der entsprechenden KontoNr
     *
     * @param nummer KontoNr
     * @return true  -> wenn Löschen erfolgreich
     *         false -> wenn Konto nicht gefunden
     *
     */
    public boolean kontoLoeschen(long nummer) {
        return kontoListe.remove(nummer) != null;
    }

    /**
     * Gibt den Kontostand des Kontos mit der entsprechenden KontoNr aus
     *
     * @param nummer KontoNr
     * @return Kontostand
     */
    public Geldbetrag getKontostand(long nummer) {
        return kontoListe.get(nummer).getKontostand();
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

        if(!von.abheben(betrag))
            return false;

        an.einzahlen(betrag);

        return true;
    }
}
