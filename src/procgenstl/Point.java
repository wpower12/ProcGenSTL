/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procgenstl;

import java.text.DecimalFormat;

/**
 *
 * @author wpower
 */
public class Point {
    
    private static DecimalFormat df = new DecimalFormat("0.000000E000");
    
    static void sort(Point[] points) {
    
    }
    
    private int i, j, k;
    
    Point(int i, int j, int k) {
        this.i = i; this.j =j; this.k = k;
    }

    String vectorString(int SCALE) {
        String ret;
        ret = df.format(this.i*SCALE);
        ret += " " + df.format(this.j*SCALE);
        ret += " " + df.format(this.k*SCALE);
        return ret.replace("E", "e+");
    }
    
}
