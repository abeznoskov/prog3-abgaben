package bankprojekt.geld;

public enum Waehrung {
    EUR(1.0),
    ESCUDO(109.8269),
    DOBRA(24304.7429),
    FRANC(490.92);

    private final double rate;

    private Waehrung(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}