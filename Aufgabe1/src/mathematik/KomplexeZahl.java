package mathematik;
import trigonometrie.Winkel;

// TODO: Kommentare

public class KomplexeZahl {
    private static final KomplexeZahl ZAHL = new KomplexeZahl(1,0);
    private static final KomplexeZahl KOMPLEXE_ZAHL = new KomplexeZahl(0,1);

    private double realTeil;
    private double imaginaerTeil;
    private Winkel winkel;
    private double betrag;

    public KomplexeZahl() {
        this.realTeil = 0;
        this.imaginaerTeil = 0;

        this.winkel = new Winkel(0);
        this.betrag = 0;
    }

    public KomplexeZahl(double realTeil, double imaginaerTeil) {
        this.realTeil = realTeil;
        this.imaginaerTeil = imaginaerTeil;

        this.winkel = winkelBerechnen(realTeil, imaginaerTeil);
        this.betrag = betragBerechnen(realTeil, imaginaerTeil);
    }

    public KomplexeZahl(Winkel winkel, double betrag) {
        this.winkel = winkel;
        this.betrag = betrag;

        this.realTeil = realTeilBerechnen(betrag, winkel);
        this.imaginaerTeil = imaginaerTeilBerechnen(betrag, winkel);
    }

    public double getRealTeil() {
        return realTeil;
    }

    public double getImaginaerTeil() {
        return imaginaerTeil;
    }

    public double getBetrag() {
        return betrag;
    }

    public double getWinkelInGrad() {
        return this.winkel.getWinkelInGrad();
    }

    public void setRealTeil(double realTeil) {
        this.realTeil = realTeil;
        this.betrag = betragBerechnen(this.realTeil, this.imaginaerTeil);
        this.winkel = winkelBerechnen(this.realTeil, this.imaginaerTeil);
    }

    public void setImaginaerTeil(double imaginaerTeil) {
        this.imaginaerTeil = imaginaerTeil;
        this.betrag = betragBerechnen(this.realTeil, this.imaginaerTeil);
        this.winkel = winkelBerechnen(this.realTeil, this.imaginaerTeil);
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
        this.realTeil = realTeilBerechnen(this.betrag, this.winkel);
        this.imaginaerTeil = imaginaerTeilBerechnen(this.betrag, this.winkel);
    }

    public void setWinkel(Winkel winkel) {
        this.winkel = winkel;
        this.realTeil = realTeilBerechnen(this.betrag, this.winkel);
        this.imaginaerTeil = imaginaerTeilBerechnen(this.betrag, this.winkel);
    }

    private double realTeilBerechnen(double betrag, Winkel winkel) {
        return betrag * Math.cos(winkel.getWinkelImBogenmass());
    }

    private double imaginaerTeilBerechnen(double betrag, Winkel winkel) {
        return betrag * Math.sin(winkel.getWinkelImBogenmass());
    }

    private Winkel winkelBerechnen(double a, double b) {
        return new Winkel(Math.atan2(a, b));
    }

    private double betragBerechnen(double a, double b) {
        return Math.sqrt(a * a + b * b);
    }

    public KomplexeZahl multiplizieren(KomplexeZahl k) {
        double a = this.realTeil;
        double b = this.imaginaerTeil;
        double c = k.getRealTeil();
        double d = k.getImaginaerTeil();

        double neuerRealTeil = ((a * c) - (b * d));
        double neuerImaginaerTeil = ((a * d) + (b * c));

        return new KomplexeZahl(neuerRealTeil, neuerImaginaerTeil);
    }

    public KomplexeZahl dividieren(KomplexeZahl k) {
        double a = this.realTeil;
        double b = this.imaginaerTeil;
        double c = k.getRealTeil();
        double d = k.getImaginaerTeil();

        double neuerRealTeil = (a*c + b*d) / (c*c + d*d);
        double neuerImaginaerTeil = (b*c - a*d) / (c*c + d*d);

        return new KomplexeZahl(neuerRealTeil, neuerImaginaerTeil);
    }

    public KomplexeZahl potenzieren(int exponent) {
        KomplexeZahl produkt = new KomplexeZahl(this.realTeil, this.imaginaerTeil);

        for (int i = 0; i < exponent; i++) {
            produkt.multiplizieren(this);
        }

        return produkt;
    }

    @Override
    public String toString() {
        return this.realTeil + " + " + imaginaerTeil + "i";
    }
}
