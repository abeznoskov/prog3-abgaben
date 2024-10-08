package mathematik;

import java.util.Comparator;

// TODO: Kommentare

public class WinkelVergleich implements Comparator<KomplexeZahl> {

    @Override
    public int compare(KomplexeZahl k1, KomplexeZahl k2) {
        if(k1.getWinkelInGrad() == k2.getWinkelInGrad()) {
            return 0;
        }
        else if (k1.getWinkelInGrad() > k2.getWinkelInGrad()) {
            return -1;
        }
        return 1;
    }
}
