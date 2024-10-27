package bankprojekt.geld;

/**
 * Enum fuer Waehrung
 *
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
 * */
public enum Waehrung {
    EUR(1.0, "EUR"),
    ESCUDO(109.8269, "Esc"),
    DOBRA(24304.7429, "Db"),
    FRANC(490.92, "FRF"),;

    private final double rate;
    private final String symbol;

    private Waehrung(double rate, String symbol) {
        this.rate = rate;
        this.symbol = symbol;
    }

    public double getRate() {
        return this.rate;
    }

    public String getSymbol() {return this.symbol;}
}