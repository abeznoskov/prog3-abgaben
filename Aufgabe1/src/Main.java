import mathematik.BetragVergleich;
import mathematik.KomplexeZahl;
import mathematik.WinkelVergleich;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        KomplexeZahl[] komplexeZahlen = new KomplexeZahl[5];

        komplexeZahlen[0] = new KomplexeZahl(1,3);
        komplexeZahlen[1] = new KomplexeZahl(4,5);
        komplexeZahlen[2] = new KomplexeZahl(2,5);
        komplexeZahlen[3] = new KomplexeZahl(4,2);
        komplexeZahlen[4] = new KomplexeZahl(6,0);

        BetragVergleich b = new BetragVergleich();
        Arrays.sort(komplexeZahlen, b);
        System.out.println(Arrays.toString(komplexeZahlen));

        WinkelVergleich w = new WinkelVergleich();
        Arrays.sort(komplexeZahlen, w.reversed());
        System.out.println(Arrays.toString(komplexeZahlen));

        KomplexeZahl z = new KomplexeZahl(3,5);
        System.out.println(z.getWinkelInGrad());
        System.out.println(Math.toRadians(z.getWinkelInGrad()));

    }
}
