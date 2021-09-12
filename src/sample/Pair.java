package sample;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Pair {
    private String s;
    private double d;

    Pair(String s, double d) {
        this.s = s;
        this.d = d;
    }

    double d() {
        return d;
    }
    String present(){
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        return s + " : " + df.format(d);
    }
}
