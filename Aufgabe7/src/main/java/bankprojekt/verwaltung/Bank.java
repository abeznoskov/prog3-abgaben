package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.*;
import java.time.LocalDate;
import java.time.Period;

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
    private static final double standardDispo = 200;

    /**
     * Konstruktor mit Zuweisung der positiven Bankleitzahl
     *
     * @param bankleitzahl die Bankleitzahl
     * @throws IllegalArgumentException Wenn Bankleitzahl nicht zulässig ist
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
     * @throws IllegalArgumentException wenn Kunde null
     *
     */
    public long girokontoErstellen(Kunde inhaber) throws IllegalArgumentException{
        if (inhaber == null)
            throw new IllegalArgumentException();

        this.vergebeneKontoNr += 1;

        Girokonto k = new Girokonto(inhaber, vergebeneKontoNr, new Geldbetrag(standardDispo));
        this.kontoListe.put(vergebeneKontoNr, k);

        return vergebeneKontoNr;
    }

    /**
     * Erstellt ein Sparbuch für den angegebenen Kunden
     *
     * @param inhaber der Kunde
     * @return die zugewiesene Kontonummer
     * @throws IllegalArgumentException wenn Kunde null
     *
     */
    public long sparbuchErstellen(Kunde inhaber) throws IllegalArgumentException {
        if (inhaber == null)
            throw new IllegalArgumentException();

        this.vergebeneKontoNr += 1;

        Sparbuch s = new Sparbuch(inhaber, vergebeneKontoNr);
        kontoListe.put(vergebeneKontoNr, s);

        return vergebeneKontoNr;
    }


    /**
     * Fuegt gegebene Konto k in die Kontenliste der Bank ein und
     * liefert die dabei von der Bank vergebene Kontonummer zurueck
     *
     * @param k das einzufuehrende Mockkonto
     * @return die zugewiesene Kontonummer
     * @throws IllegalArgumentException wenn Konto null
     */
    public long mockEinfuegen(Konto k) {
        if (k == null) {
            throw new IllegalArgumentException();
        }
        this.vergebeneKontoNr += 1;

        this.kontoListe.put(vergebeneKontoNr, k);

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
            alleKonten.append(k.getKontonummerFormatiert())
                    .append("  ")
                    .append(k.getKontostand())
                    .append("  ")
                    .append(System.lineSeparator());
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
     * @throws IllegalArgumentException wenn von ungueltig ist
     *                                  wenn konto NULL ist
     * @throws GesperrtException wenn Konto gesperrt
     * @throws KontoNichtVorhandenException wenn Konto nicht existiert
     *
     */
    public boolean geldAbheben(long von, Geldbetrag betrag) throws KontoNichtVorhandenException, GesperrtException {
        if (betrag == null || betrag.getBetrag() < 0)
            throw new IllegalArgumentException("Betrag darf nicht null oder negativ sein.");

        Konto konto = kontoListe.get(von);

        if (konto == null)
            throw new KontoNichtVorhandenException(von);

        return konto.abheben(betrag);
    }

    /**
     * Geldeinzahlung auf das Konto mit der entsprechenden KontoNr
     *
     * @param auf KontoNr
     * @param betrag Betrag
     * @throws IllegalArgumentException wenn Kontonummer nicht gefunden
     *                                  wenn Betrag null
     * @throws KontoNichtVorhandenException wenn Konto nicht existiert
     *
     */
    public void geldEinzahlen(long auf, Geldbetrag betrag) throws IllegalArgumentException, KontoNichtVorhandenException {
        if (betrag == null)
            throw new IllegalArgumentException("Betrag darf nicht null oder negativ sein.");

        Konto konto = kontoListe.get(auf);

        if (konto == null)
            throw new KontoNichtVorhandenException(auf);

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
    public boolean kontoLoeschen(long nummer) throws IllegalArgumentException {
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
     * @throws KontoNichtVorhandenException wenn Konto nicht existiert
     *
     */
    public Geldbetrag getKontostand(long nummer) throws IllegalArgumentException, KontoNichtVorhandenException {
        if (nummer < 0)
            throw new IllegalArgumentException("Kontonummer darf nicht negativ sein.");

        Konto konto = kontoListe.get(nummer);

        if (konto == null)
            throw new KontoNichtVorhandenException(nummer);

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
     * @throws KontoNichtVorhandenException wenn Konto nicht vorhanden
     *
     */
    public boolean geldUeberweisen(long vonKontonr,
                                   long nachKontonr,
                                   Geldbetrag betrag,
                                   String verwendungszweck) throws GesperrtException, KontoNichtVorhandenException {

        if(betrag == null || verwendungszweck == null)
            throw new IllegalArgumentException("betrag oder verwendungszweck null");

        Konto von = kontoListe.get(vonKontonr);
        Konto an = kontoListe.get(nachKontonr);

        if(von == null)
            throw new KontoNichtVorhandenException(vonKontonr);

        if(an == null)
            throw new KontoNichtVorhandenException(nachKontonr);

        if (!(von instanceof UeberweisungsfaehigesKonto) || !(an instanceof UeberweisungsfaehigesKonto))
            return false;

        if (von.isGesperrt()) {
            throw new GesperrtException(vonKontonr);
        }


        if (!((UeberweisungsfaehigesKonto) von).ueberweisungAbsenden(
                betrag, an.getInhaber().toString(), an.getKontonummer(), this.bankleitzahl, verwendungszweck)) {
            return false;
        }


        ((UeberweisungsfaehigesKonto) an).ueberweisungEmpfangen(
                betrag, von.getInhaber().toString(), von.getKontonummer(), this.bankleitzahl, verwendungszweck);
        return true;
    }

    /**
     * Ueberprueft, ob ein Konto mit angegebenen Kontonummer existiert
     *
     * @param kontonummer Kontonummer des Kontos
     * @return true, wenn ein Konto gefunden, sonst false
     */
    public boolean kontoVorhanden(long kontonummer) {
        return kontoListe.containsKey(kontonummer);
    }

    // Aufgabe 7:

    /**
     * Methode zahlt auf alle Konten von Kunden, die in diesem Jahr 18 werden, den betrag ein
     * @param betrag Geldbetrag der geschenkt wird
     * @throws IllegalArgumentException wenn betrag negativ
     */
    public void schenkungAnNeuerwachsene(Geldbetrag betrag) throws IllegalArgumentException{
        if (betrag.isNegativ())
            throw new IllegalArgumentException();

        LocalDate now = LocalDate.now();
        kontoListe.values().stream()
                .filter(k -> k.getInhaber().getGeburtstag().getYear() == LocalDate.now().getYear() - 18)
                .forEach(k -> k.einzahlen(betrag));
    }

    /**
     * Die Methode liefert eine Liste aller Kunden, die ein Konto mit negativem Kontostand haben.
     * @return Liste der Kunden mit negativem Kontostand
     */
    public List<Kunde> getKundenMitLeeremKonto() {
        return kontoListe.values().stream()
                .filter(k -> k.getKontostand().isNegativ())
                .map(konto -> konto.getInhaber())
                .collect(Collectors.toList());
    }

    /**
     * liefert die Namen und Geburtstage aller Kunden der Bank. Doppelte Kunden sollen dabei aussortiert werden.
     * Sortieren Sie die Liste nach Monat und Tag des Geburtstages (nicht nach dem Geburtsjahr!)
     * Format: Hans 05.01.,
     *         Anna 03.10, ...
     * @return Kunden mit Geburtstagen
     */
    public String getKundengeburtstage() {
        return kontoListe.values().stream()
                .map(konto -> konto.getInhaber())
                .distinct()
                .map(k -> String.format("%s %td.%tm.", k.getName(), k.getGeburtstag(), k.getGeburtstag()) + System.lineSeparator())
                .collect(Collectors.joining()); // Fuege Strings zusammen
    }

    /**
     * liefert die Anzahl der Kunden, die jetzt mindestens 67 sind.
     * @return Anzahl Senioren
     */
    public long getAnzahlSenioren() {
        return kontoListe.values().stream()
                .map(konto -> konto.getInhaber())
                .distinct()
                .filter(k -> Period.between(k.getGeburtstag(), LocalDate.now()).getYears() >= 67) // Filtere Senioren
                .count();
    }

}
