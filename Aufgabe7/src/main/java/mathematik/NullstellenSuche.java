package mathematik;

import java.util.function.Function;

public class NullstellenSuche {

    /**
     * Nullstellenberechnung basierend auf der Bisektion
     *
     * @param funktion Funktion
     * @param a Intervallsgrenze A
     * @param b Intervallsgrenze B
     * @param toleranz Genauigkeit/Toleranz
     * @return Genährte Nullstelle
     */
    public static double findeNullstelle(Function<Double, Double> funktion, double a, double b, double toleranz) {

        if (funktion.apply(a) * funktion.apply(b) >= 0) {
            throw new IllegalArgumentException("Die Funktion muss an den Intervallgrenzen unterschiedliche Vorzeichen haben.");
        }

        double mitte;
        while ((b - a) >= toleranz) {
            mitte = (a + b) / 2;

            double funktionswertMitte = funktion.apply(mitte);
            if (Math.abs(funktionswertMitte) < toleranz) {
                return mitte;
            }

            if (funktion.apply(a) * funktionswertMitte < 0) {
                b = mitte;
            } else {
                a = mitte;
            }
        }

        return (a + b) / 2; // Genähert Nullstelle
    }
}
