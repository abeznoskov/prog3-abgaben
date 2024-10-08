package trigonometrie;


/**
 * Stellt einen Winkel dar, der immer zwischen 0° (inklusive) 
 * und 360° (exklusive) liegt
 */
public class Winkel {
	private double winkelInGrad;
	
	/**
	 * erstellt einen Winkel zwischen 0° (inklusive) und 360°
	 * @param winkelInGrad der Winkel in Grad
	 */
	public Winkel(double winkelInGrad) {
		double winkel = winkelInGrad % 360;
		if(winkel < 0)
			winkel = 360 + winkel;
		this.winkelInGrad = winkel;
	}

	/**
	 * Winkel in Grad
	 * @return Winkel in Grad
	 */
	public double getWinkelInGrad() {
		return winkelInGrad;
	}
	
	/**
	 * Winkel im Bogenmaß
	 * @return Winkel im Bogenmaß
	 */
	public double getWinkelImBogenmass() {
		return Math.toRadians(this.winkelInGrad);
	}
	
	@Override
	public String toString()
	{
		return String.format("%.2f°", this.winkelInGrad);
	}
	
	/** 
	 * Summe aus this und summand
	 * @param summand zu addierender Winkel
	 * @return this + summand
	 */
	public Winkel addieren(Winkel summand)
	{
		return new Winkel(winkelInGrad + summand.winkelInGrad);
	}
	/** 
	 * Differenz von this und subtrahend
	 * @param subrahent zu subtrahierender Winkel
	 * @return this - subtrahend
	 */
	public Winkel subtrahieren(Winkel subrahent)
	{
		return new Winkel(winkelInGrad - subrahent.winkelInGrad);
	}
	/** 
	 * Produkt aus this und zahl
	 * @param zahl Faktor
	 * @return this * zahl
	 */
	public Winkel multiplizieren(double zahl)
	{
		return new Winkel(zahl * winkelInGrad);
	}
	
}
