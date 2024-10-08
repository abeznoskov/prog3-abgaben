package mathematik;

import java.util.Comparator;

// TODO: Kommentare

public class BetragVergleich implements Comparator<KomplexeZahl> {

    @Override
    public int compare(KomplexeZahl k1, KomplexeZahl k2) {
        if(k1.getBetrag() == k2.getBetrag()) {
            return 0;
        }
        else if (k1.getBetrag() > k2.getBetrag()) {
            return -1;
        }
        return 1;
    }
}
