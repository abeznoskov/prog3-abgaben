package bankprojekt.verarbeitung;

import java.time.LocalDate;

/**
 * ein Sparbuch, d.h. ein Konto, das nur recht eingeschränkt genutzt
 * werden kann. Insbesondere darf man monatlich nur höchstens 2000€
 * abheben, wobei der Kontostand nie unter 0,50€ fallen darf. 
 * @author Doro
 *
 */
public class Sparbuch extends Konto {
	/**
	 * Zinssatz, mit dem das Sparbuch verzinst wird. 0,03 entspricht 3%
	 */
	private double zinssatz;
	
	/**
	 * Monatlich erlaubter Gesamtbetrag für Abhebungen
	 */
	public static final Geldbetrag ABHEBESUMME = new Geldbetrag(2000);
	
	/**
	 * Betrag, der nach einer Abhebung mindestens auf dem Konto bleiben muss
	 */
	public static final Geldbetrag MINIMUM = new Geldbetrag(0.5);
	
	/**
	 * Betrag, der im aktuellen Monat bereits abgehoben wurde
	 */
	private Geldbetrag bereitsAbgehoben = new Geldbetrag();

	/**
	 * Monat und Jahr der letzten Abhebung
	 */
	private LocalDate zeitpunkt = LocalDate.now();
	
	/**
	* ein Standard-Sparbuch
	*/
	public Sparbuch() {
		zinssatz = 0.03;
	}

	/**
	* ein Standard-Sparbuch, das inhaber gehört und die angegebene Kontonummer hat
	* @param inhaber der Kontoinhaber
	* @param kontonummer die Wunsch-Kontonummer
	* @throws IllegalArgumentException wenn inhaber null ist
	*/
	public Sparbuch(Kunde inhaber, long kontonummer) {
		super(inhaber, kontonummer);
		zinssatz = 0.03;
	}
	
	@Override
	public String toString()
	{
    	String ausgabe = "-- SPARBUCH --" + System.lineSeparator() +
    	super.toString()
    	+ "Zinssatz: " + this.zinssatz * 100 +"%" + System.lineSeparator();
    	return ausgabe;
	}

	@Override
	public boolean abheben (Geldbetrag betrag) throws GesperrtException{
		if (betrag == null || betrag.isNegativ()) {
			throw new IllegalArgumentException("Betrag ungültig");
		}
		if(this.isGesperrt())
		{
			GesperrtException e = new GesperrtException(this.getKontonummer());
			throw e;
		}
		LocalDate heute = LocalDate.now();
		if(heute.getMonth() != zeitpunkt.getMonth() || heute.getYear() != zeitpunkt.getYear())
		{
			this.bereitsAbgehoben = new Geldbetrag();
		}
		Geldbetrag neu = getKontostand().minus(betrag);
		if (neu.compareTo(Sparbuch.MINIMUM) >= 0 && 
				 bereitsAbgehoben.plus(betrag).compareTo(Sparbuch.ABHEBESUMME)<= 0)
		{
			setKontostand(neu);
			bereitsAbgehoben = bereitsAbgehoben.plus(betrag);
			this.zeitpunkt = LocalDate.now();
			return true;
		}
		else
			return false;
	}

}
