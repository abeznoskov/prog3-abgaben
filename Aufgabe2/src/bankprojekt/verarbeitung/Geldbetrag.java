package bankprojekt.verarbeitung;

import bankprojekt.geld.Waehrung;

/**
 * TODO: Alle Methoden anpassen damit sie die Währung beachten
 *		 public void waehrungswechsel(Waehrung neu) -> in Klasse Konto
 * Ein Geldbetrag mit Währung
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
	public Geldbetrag() {}
	
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
		if(!Double.isFinite(betrag))
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
	 * rechnet this + summand
	 * @param summand zu addierender Betrag
	 * @return this + summand in der Währung von this
	 * @throws IllegalArgumentException wenn summand null ist
	 */
	public Geldbetrag plus(Geldbetrag summand)
	{
		if(summand == null)
			throw new IllegalArgumentException();
		return new Geldbetrag(this.betrag + summand.betrag);
	}
	
	/**
	 * rechnet this - divisor
	 * @param divisor abzuziehender Betrag
	 * @return this - divisor in der Währung von this
	 * @throws IllegalArgumentException wenn divisor null ist
	 */
	public Geldbetrag minus(Geldbetrag divisor)
	{
		if(divisor == null)
			throw new IllegalArgumentException();
		return new Geldbetrag(this.betrag - divisor.betrag);
	}

	public void umrechnen(Waehrung zielwaehrung) {
		if (this.waehrung == zielwaehrung) {
			return;
		}
		else if (this.waehrung == Waehrung.EUR) {
			this.betrag = this.betrag * zielwaehrung.getRate();
			this.waehrung = zielwaehrung;
			return;
		}
		else {
			this.betrag = this.betrag / zielwaehrung.getRate();
			this.betrag = this.betrag * zielwaehrung.getRate();
			this.waehrung = zielwaehrung;
			return;
		}
	}

	@Override
	public int compareTo(Geldbetrag o) {
		return Double.compare(this.betrag, o.betrag);
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
	public String toString()
	{
		return String.format("%,.2f €", this.betrag);
	}
}
