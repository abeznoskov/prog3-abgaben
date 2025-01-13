package bankprojekt.verarbeitung;

import bankprojekt.geld.Waehrung;

import java.io.Serializable;
import java.sql.SQLOutput;

/**
 * Ein Girokonto, d.h. ein Konto mit einem Dispo und der Fähigkeit,
 * Überweisungen zu senden und zu empfangen.
 * Grundsätzlich sind Überweisungen und Abhebungen möglich bis
 * zu einem Kontostand von -this.dispo
 * @author Doro
 *
 */
public class Girokonto extends UeberweisungsfaehigesKonto implements Serializable, Beobachter {
	/**
	 * Wert, bis zu dem das Konto überzogen werden darf
	 */
	private Geldbetrag dispo;

	/**
	 * erzeugt ein leeres, nicht gesperrtes Standard-Girokonto
	 * von Max MUSTERMANN
	 */
	public Girokonto()
	{
		super(Kunde.MUSTERMANN, 99887766);
		this.dispo = new Geldbetrag(500);
	}
	
	/**
	 * erzeugt ein Girokonto mit den angegebenen Werten
	 * @param inhaber Kontoinhaber
	 * @param nummer Kontonummer
	 * @param dispo Dispo
	 * @throws IllegalArgumentException wenn der inhaber null ist oder der angegebene dispo negativ bzw. NaN ist
	 */
	public Girokonto(Kunde inhaber, long nummer, Geldbetrag dispo) throws IllegalArgumentException
	{
		super(inhaber, nummer);
		if(dispo == null || dispo.isNegativ())
			throw new IllegalArgumentException("Der Dispo ist nicht gültig!");
		this.dispo = dispo;
	}

	/**
	 * liefert den Dispo
	 * @return Dispo von this
	 */
	public Geldbetrag getDispo() {
		return dispo;
	}

	/**
	 * setzt den Dispo neu
	 * @param dispo muss größer sein als 0
	 * @throws IllegalArgumentException wenn dispo negativ bzw. NaN ist
	 */
	public void setDispo(Geldbetrag dispo) {
		if(dispo == null || dispo.isNegativ())
			throw new IllegalArgumentException("Der Dispo ist nicht gültig!");
		this.dispo = dispo;
		benachrichtigen();
	}
	
	@Override
    public boolean ueberweisungAbsenden(Geldbetrag betrag, 
    		String empfaenger, long nachKontonr, 
    		long nachBlz, String verwendungszweck) 
    				throws GesperrtException 
    {
      if (this.isGesperrt())
            throw new GesperrtException(this.getKontonummer());
        if (betrag == null || betrag.isNegativ()|| empfaenger == null || verwendungszweck == null)
            throw new IllegalArgumentException("Parameter fehlerhaft");
        if (!getKontostand().plus(dispo).minus(betrag).isNegativ())
        {
            setKontostand(getKontostand().minus(betrag));
			benachrichtigen();
            return true;
        }
        else
        	return false;
    }

    @Override
    public void ueberweisungEmpfangen(Geldbetrag betrag, String vonName, long vonKontonr, long vonBlz, String verwendungszweck)
    {
        if (betrag == null || betrag.isNegativ()|| vonName == null || verwendungszweck == null)
            throw new IllegalArgumentException("Parameter fehlerhaft");
        setKontostand(getKontostand().plus(betrag));
		benachrichtigen();
    }
    
    @Override
    public String toString()
    {
    	String ausgabe = "-- GIROKONTO --" + System.lineSeparator() +
    	super.toString()
    	+ "Dispo: " + this.dispo + System.lineSeparator();
    	return ausgabe;
    }

	@Override
	protected boolean pruefeAbhebung(Geldbetrag betrag) {
		return !getKontostand().plus(dispo).minus(betrag).isNegativ();
	}

	/**
	 * Sie wechselt die Währung, in der das Konto aktuell geführt wird
	 * @param neu die Zielwährung
	 */
	@Override
	public void waehrungswechsel(Waehrung neu) {
		if(neu == null)
			throw new IllegalArgumentException("Die Zielwährung darf nicht null sein!");
		dispo.umrechnen(neu);
	}

	/**
	 * wird aufgerufen, wenn sich Dispo veaendert
	 *
	 * @param konto Konto das den Dispo ausgeben soll
	 */
	@Override
	public void aktualisieren(Konto konto) {
		Geldbetrag aktuellerDispo = konto.getKontostand();

		// Vergleich der Dispos
		if (aktuellerDispo.compareTo(dispo) > 0) {
			System.out.println("Dispo wurde von " + dispo + " auf " + aktuellerDispo + " umgeaendert.");
		}
		else if (aktuellerDispo.compareTo(dispo) < 0) {
			System.out.println("Dispo wurde verringert von " + dispo + " auf " + aktuellerDispo + ".");
		}
		else { //optional, da Aufgabe es nicht verlangt
			System.out.println("Dispo bleibt unveraendert bei " + dispo + ".");
		}
		dispo = aktuellerDispo;
	}

}
