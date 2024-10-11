package mathematik;

import java.util.Comparator;

/**
 * Eine implementierte Comparator Klasse für den Winkelvergleich von zwei komplexen Zahlen in Vektordarstellung.
 *
 * @author Andreas Beznoskov,
 *         Dinh Tuan Anh Nguyen
 *
 */
public class WinkelVergleich implements Comparator<KomplexeZahl> {
    /**
     * Vergleicht zwei Beträge von komplexen Zahlen.
     *
     * @param k1 die erste zu vergleichende KomplexeZahl.
     * @param k2 die zweite zu vergleichende KomplexeZahl.
     * @return -1 - wenn k1 kleiner als k2 ist.
     *          0 - wenn k1 == k2 ist.
     *          1 - wenn k1 größer als k2 ist.
     */
    @Override
    public int compare(KomplexeZahl k1, KomplexeZahl k2) {
        if(k1.getWinkelInGrad() == k2.getWinkelInGrad()) {
            return 0;
        }
        else if (k1.getWinkelInGrad() < k2.getWinkelInGrad()) {
            return -1;
        }
        return 1;
    }
}
