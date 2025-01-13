package bankprojekt.verarbeitung;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * ein Sparbuch, d.h. ein Konto, das nur recht eingeschränkt genutzt
 * werden kann. Insbesondere darf man monatlich nur höchstens 2000€
 * abheben, wobei der Kontostand nie unter 0,50€ fallen darf. 
 * @author Doro
 *
 */
public class Sparbuch extends Konto implements Serializable, Beobachter {
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

 /**
  * Ueberprueft, ob die Abhebung moeglich ist, nur wenn:
  * Guthaben auf dem Sparbuch nicht negativ gehen darf und das Monatslimit nicht ueberschritten wird
  * @param betrag Geld was abgehoben werden soll
  * @return true wenn Abhebung moeglich ist, sonst false
  */
@Override
protected boolean pruefeAbhebung(Geldbetrag betrag) {
    aktualisiereMonatslimit(); // Monatslimit Kontrolle
    return getKontostand().minus(betrag).compareTo(MINIMUM) >= 0 &&
            bereitsAbgehoben.plus(betrag).compareTo(ABHEBESUMME) <= 0;
}
 /**
  * Methode um Monatslimit zu aktualisieren, mit Kontrolle
  */
 private void aktualisiereMonatslimit() {
     LocalDate heute = LocalDate.now();
     if (heute.getMonth() != zeitpunkt.getMonth() || heute.getYear() != zeitpunkt.getYear()) {
         bereitsAbgehoben = new Geldbetrag(); // Reset des monatlichen Limits
         zeitpunkt = heute;
     }
 }

	@Override
	protected void nachAbhebung(Geldbetrag betrag) {
		// eventuell die current Month hier kontrollieren?
		bereitsAbgehoben = bereitsAbgehoben.plus(betrag);
		benachrichtigen();
		zeitpunkt = LocalDate.now();
	}

	/**
	 * wird aufgerufen, wenn sich Kontostand veaendert
	 *
	 * @param konto Konto das den Kontostand ausgeben soll
	 */
	@Override
	public void aktualisieren(Konto konto) {
		// Hole den aktuellen Kontostand des Kontos
		Geldbetrag aktuellerKontostand = konto.getKontostand();

		// Vergleich mit dem bisherigen Kontostand
		if (aktuellerKontostand.compareTo(this.getKontostand()) > 0) {
			System.out.println("Der Kontostand des Sparbuchs hat sich erhoeht: Neuer Kontostand ist " + aktuellerKontostand + ".");
		}
		else if (aktuellerKontostand.compareTo(this.getKontostand()) < 0) {
			System.out.println("Der Kontostand des Sparbuchs hat sich verringert: Neuer Kontostand ist " + aktuellerKontostand + ".");
		}
		else { // ebenso optional, da aufgabe es nicht verlangt
			System.out.println("Der Kontostand des Sparbuchs bleibt unveraendert bei " + aktuellerKontostand + ".");
		}
		this.setKontostand(aktuellerKontostand);
	}

}
