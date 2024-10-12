package mathematik;

import trigonometrie.Winkel;

/**
 * Klasse für Definition und Berechnung von komplexen Zahlen.
 * Jede Komplexezahl wird durch
 *      - realTeil          (reeller Teil der komplexenZahl)
 *      - imaginearTeil     (imaginearer Teil der komplexenZahl)
 *      - winkel            (winkel des Vektors der komplexenZahl)
 *      - betrag            (Betrag =Länge des Vektors der komplexenZahl)
 * beschrieben.
 *
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
 *
 */
public class KomplexeZahl {
    private static final KomplexeZahl ZAHL = new KomplexeZahl(1,0);
    private static final KomplexeZahl KOMPLEXE_ZAHL = new KomplexeZahl(0,1);

    private double realTeil;
    private double imaginaerTeil;
    private Winkel winkel;
    private double betrag;

    /**
     * Leerer default Konstruktor.
     *
     */
    public KomplexeZahl() {

    }

    /**
     * Konstruktor zum definieren einer komplexen Zahl anhand des realen und imaginären Teils.
     *
     * @param realTeil reeler Bestandteil einer komplexen Zahl
     * @param imaginaerTeil imaginärer Bestandteil einer komplexen Zahl
     */
    public KomplexeZahl(double realTeil, double imaginaerTeil) {
        this.realTeil = realTeil;
        this.imaginaerTeil = imaginaerTeil;

        this.winkel = winkelBerechnen(realTeil, imaginaerTeil);
        this.betrag = betragBerechnen(realTeil, imaginaerTeil);
    }

    /**
     * Konstruktor zum definieren einer komplexen Zahl anhand des Winkels und Betrags (Vektor).
     *
     * @param winkel winkel des Vektors einer komplexen Zahl
     * @param betrag betrag (= Länge) des vektors einer komplexen Zahl
     */
    public KomplexeZahl(Winkel winkel, double betrag) {
        this.winkel = winkel;
        this.betrag = betrag;

        this.realTeil = realTeilBerechnen(betrag, winkel);
        this.imaginaerTeil = imaginaerTeilBerechnen(betrag, winkel);
    }

    /**
     * Getter Methode für realTeil
     *
     * @return gibt den reelen Teil der komplexen Zahl wieder
     */
    public double getRealTeil() {
        return realTeil;
    }

    /**
     * Getter Methode für imaginaerTeil
     *
     * @return gibt den imaginären Teil der komplexen Zahl wieder
     */
    public double getImaginaerTeil() {
        return imaginaerTeil;
    }

    /**
     * Getter Methode für betrag
     *
     * @return gibt den Betrag des Vektors der komplexen Zahl wieder
     */
    public double getBetrag() {
        return betrag;
    }

    /**
     * Getter Methode für winkel in Grad
     *
     * @return gibt den winkel als Winkel object vom Vektor der komplexen Zahl wieder
     */
    public double getWinkelInGrad() {
        return this.winkel.getWinkelInGrad();
    }


    /**
     * Setter Methode für realTeil mit Anpassung der Vektordarstellung
     *
     * @param realTeil der neue realTeil
     */
    public void setRealTeil(double realTeil) {
        this.realTeil = realTeil;
        this.betrag = betragBerechnen(this.realTeil, this.imaginaerTeil);
        this.winkel = winkelBerechnen(this.realTeil, this.imaginaerTeil);
    }

    /**
     * Setter Methode für imaginaerTeil mit Anpassung der Vektordarstellung
     *
     * @param imaginaerTeil der neue imaginaerTeil
     */
    public void setImaginaerTeil(double imaginaerTeil) {
        this.imaginaerTeil = imaginaerTeil;
        this.betrag = betragBerechnen(this.realTeil, this.imaginaerTeil);
        this.winkel = winkelBerechnen(this.realTeil, this.imaginaerTeil);
    }

    /**
     * Setter Methode für betrag mit Anpassung von real- und imaginaerTeil
     *
     * @param betrag der neue betrag
     */
    public void setBetrag(double betrag) {
        this.betrag = betrag;
        this.realTeil = realTeilBerechnen(this.betrag, this.winkel);
        this.imaginaerTeil = imaginaerTeilBerechnen(this.betrag, this.winkel);
    }

    /**
     * Setter Methode für winkel mit Anpassung von real- und imaginaerTeil
     *
     * @param winkel der neue winkel
     */
    public void setWinkel(Winkel winkel) {
        this.winkel = winkel;
        this.realTeil = realTeilBerechnen(this.betrag, this.winkel);
        this.imaginaerTeil = imaginaerTeilBerechnen(this.betrag, this.winkel);
    }

    /**
     * berechnet den reelen Teil der komplexen Zahl anhand des Betrags und Winkels
     *
     * @param betrag des Vektors
     * @param winkel des Vektors
     * @return reeller Teil
     */
    private double realTeilBerechnen(double betrag, Winkel winkel) {
        return betrag * Math.cos(winkel.getWinkelImBogenmass());
    }

    /**
     * berechnet den imaginären Teil der komplexen Zahl anhand des Betrags und Winkels
     *
     * @param betrag des Vektors
     * @param winkel des Vektors
     * @return imaginärer Teil
     */
    private double imaginaerTeilBerechnen(double betrag, Winkel winkel) {
        return betrag * Math.sin(winkel.getWinkelImBogenmass());
    }

    /**
     * berechnet den Winkel vom Vektor (a,b)
     *
     * @param a vom Vektor
     * @param b vom Vektor
     * @return Winkel des Vektors
     */
    private Winkel winkelBerechnen(double a, double b) {
        /*
        Realteil kann 0 sein. Mathematisch geht es nicht, da nicht durch 0 dividiert werden kann. 
        Jedoch kann man es geometrisch interpretiert: a = 0, also x = 0 auf der x-Achse.
        */
        return new Winkel(Math.toDegrees(Math.atan2(b, a)));
    }

    /**
     * berechnet den Betrag (=Länge) vom Vektor (a,b)
     *
     * @param a vom Vektor
     * @param b vom Vektor
     * @return den Betrag des Vektors
     */
    private double betragBerechnen(double a, double b) {
        // Exception, da man keine Wurzel einer negativen Zahl ziehen kann
        if(Math.sqrt(a * a + b * b) < 0){
            throw new IllegalArgumentException("Man kann keine Wurzel einer negativen Zahl ziehen!");
        }
        return Math.sqrt(a * a + b * b);
    }

    /**
     * Multiplikation von der komplexen Zahl mit einer anderen komplexen Zahl k
     *
     * @param k beliebige komplexe Zahl
     * @return das Produkt der Multiplikation
     */
    public KomplexeZahl multiplizieren(KomplexeZahl k) {
        double a = this.realTeil;
        double b = this.imaginaerTeil;
        double c = k.getRealTeil();
        double d = k.getImaginaerTeil();

        double neuerRealTeil = ((a * c) - (b * d));
        double neuerImaginaerTeil = ((a * d) + (b * c));

        return new KomplexeZahl(neuerRealTeil, neuerImaginaerTeil);
    }

    /**
     * Division einer komplexen Zahl mit einer anderen komplexen Zahl k
     *
     * @param k beliebige komplexe Zahl (!= 0)
     * @return der Quotient der Division
     */
    public KomplexeZahl dividieren(KomplexeZahl k) {
        double a = this.realTeil;
        double b = this.imaginaerTeil;
        double c = k.getRealTeil();
        double d = k.getImaginaerTeil();

        // Exception, um nicht durch 0 zu teilen
        if ((c*c + d*d) == 0) {
            throw new ArithmeticException("Nenner ist 0!");
        }

        double neuerRealTeil = (a*c + b*d) / (c*c + d*d);
        double neuerImaginaerTeil = (b*c - a*d) / (c*c + d*d);

        return new KomplexeZahl(neuerRealTeil, neuerImaginaerTeil);
    }

    /**
     * Potenzieren der komplexen Zahl mit einem beliebigen Exponenten
     *
     * @param exponent der Exponent
     * @return das Ergebnis der Potenz
     */
    public KomplexeZahl potenzieren(int exponent) {
        double neuerBetrag = Math.pow(this.betrag, exponent);

        double neuerWinkelBogenmass = Math.toRadians(this.getWinkelInGrad()) * exponent;
        Winkel neuerWinkel = new Winkel(Math.toDegrees(neuerWinkelBogenmass));

        return new KomplexeZahl(realTeilBerechnen(neuerBetrag, neuerWinkel),
                                imaginaerTeilBerechnen(neuerBetrag, neuerWinkel));
    }

    /**
     * toString() der Klasse KomplexeZahl
     *
     * @return gibt die komplexe Zahl als (a + bi) aus
     */
    @Override
    public String toString() {
        return this.realTeil + " + " + imaginaerTeil + "i";
    }
}
