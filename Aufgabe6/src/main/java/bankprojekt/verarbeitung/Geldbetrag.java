package bankprojekt.verarbeitung;

import bankprojekt.geld.Waehrung;

/**
 * Ein Geldbetrag mit Währung
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
 */
public class Geldbetrag implements Comparable<Geldbetrag>{
	/**
	 * Betrag in der in waehrung angegebenen Währung
	 */
	private double betrag;
	/**
	 * Die Währung
	 */
	private Waehrung waehrung;


	/**
	 * erstellt den Betrag 0€
	 */
	public Geldbetrag() {
		// optional bzw. sie hatte es komplett leer, da sie EUR oben initialisiert hatte
		this.betrag = 0;
		this.waehrung = Waehrung.EUR;
	}
	
	/**
	 * erstellt einen Geldbetrag in der Währung Euro
	 * @param betrag Betrag in €
	 * @throws IllegalArgumentException wenn betrag unendlich oder NaN ist
	 */
	public Geldbetrag(double betrag)
	{
		if(!Double.isFinite(betrag))
			throw new IllegalArgumentException();

		this.betrag = betrag;
		this.waehrung = Waehrung.EUR;
	}

	/**
	 * erstellt einen Geldbetrag mit ausgewählter Währung
	 * @param betrag Betrag in entsprechender Währung
	 * @throws IllegalArgumentException wenn betrag unendlich oder NaN ist
	 */
	public Geldbetrag(double betrag, Waehrung w) {
		if(!Double.isFinite(betrag) || w == null)
			throw new IllegalArgumentException();
		
		this.betrag = betrag;
		this.waehrung = w;
	}

	/**
	 * Betrag von this
	 * @return Betrag in der Währung von this
	 */
	public double getBetrag() {
		return betrag;
	}

	/**
	 * Waehrung von this
	 * @return Waehrung
	 */
	public Waehrung getWaehrung(){
		return waehrung;
	}

	/**
	 * rechnet this + summand
	 * @param summand zu addierender Betrag
	 * @return this + summand in der Währung von this
	 * @throws IllegalArgumentException wenn summand null ist
	 */
	public Geldbetrag plus(Geldbetrag summand)
	{
		if(summand == null)
			throw new IllegalArgumentException();

		// tempoaeres Attribut für Summand
		Geldbetrag tempSummand = new Geldbetrag(summand.getBetrag(), summand.getWaehrung());

		if(this.waehrung != summand.getWaehrung())
			summand.umrechnen(this.waehrung);

		return new Geldbetrag(this.betrag + tempSummand.betrag, this.waehrung);
	}
	
	/**
	 * rechnet this - subtrahend
	 * @param subtrahend abzuziehender Betrag
	 * @return this - subtrahend in der Währung von this
	 * @throws IllegalArgumentException wenn subtrahend null ist
	 */
	public Geldbetrag minus(Geldbetrag subtrahend)
	{
		if(subtrahend == null)
			throw new IllegalArgumentException();

		// tempoaeres Attribut für Subtrahend
		Geldbetrag tempSubtrahend = new Geldbetrag(subtrahend.getBetrag(), subtrahend.getWaehrung());

		if(this.waehrung != subtrahend.getWaehrung())
			tempSubtrahend.umrechnen(this.waehrung);

		return new Geldbetrag(this.betrag - tempSubtrahend.betrag, this.waehrung);
	}

	/**
	 * Konvertiert gegebenen Geldbetrag zu der gewuenschten Waehrung
	 * Wenn es schon in der gewollten Waehrung ist, wird keine Änderung vorgenommen
	 * Von Euro wird es mit der Rate der Zielwaehrung multipliziert
	 * Ist die Waehrung nicht in Euro, so wird es im Dreisatz umgerechnet,
	 * also mit der eigenen Rate zu Euro dividiert und dann mit der Rate von der gewuenschten Waherung multipliziert
	 *
	 * @param zielwaehrung - Die gewuenschte Waehrung, die der Geldbetrag annehmen soll
	 */
	public void umrechnen(Waehrung zielwaehrung) {
		if(zielwaehrung == null) {
			throw new IllegalArgumentException();
		}
		if (this.waehrung == zielwaehrung) {
			return;
		}

        if (this.waehrung != Waehrung.EUR) {
            this.betrag = this.betrag / this.waehrung.getRate();
        }

        this.betrag = this.betrag * zielwaehrung.getRate();
        this.waehrung = zielwaehrung;
        return;
    }

	@Override
	public int compareTo(Geldbetrag o) {

		// Beide Betraege umrechnen in Euro
		double inEuro = this.betrag / this.waehrung.getRate();
		double oInEuro = o.betrag / o.waehrung.getRate();

		return Double.compare(inEuro, oInEuro);
	}

	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Geldbetrag)) return false;
		if(o == this) return true;
		return this.compareTo((Geldbetrag) o) == 0;
	}
	
	/**
	 * prüft, ob this einen negativen Betrag darstellt
	 * @return true, wenn this negativ ist
	 */
	public boolean isNegativ()
	{
		return this.betrag < 0;
	}

	@Override
	public String toString() {
		return String.format("%, .2f " + waehrung.getSymbol(), this.betrag);
	}
}
