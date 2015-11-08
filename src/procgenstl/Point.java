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
        for (int d = 0; d < 3; d++) {
            for (int i = 1; i < 4; i++) {
                int j = i;
                Point temp;
                while ((j > 0) && (points[j - 1].get(d) > points[j].get(d))) {
                    temp = points[j - 1];
                    points[j - 1] = points[j];
                    points[j] = temp;
                    j--;
                }
            }
        }
    }

    static void print(Point[] points) {
        System.out.print("{");
        Point p;
        for (int i = 0; i < 4; i++) {
            p = points[i];
            System.out.format("(%d, %d, %d)", p.i, p.j, p.k);
        }
        System.out.print("}\n");
    }

    public int i, j, k;

    Point(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    String vectorString(int SCALE) {
        String ret;
        ret = df.format(this.i * SCALE);
        ret += " " + df.format(this.j * SCALE);
        ret += " " + df.format(this.k * SCALE);
        return String.format("%f %f %f", (float)this.i, (float)this.j, (float)this.k);
        //return ret.replace("E", "e+");
    }

    public int get(int dim) {
        int ret = 0;
        switch (dim) {
            case 0:
                ret = i;
                break;
            case 1:
                ret = j;
                break;
            case 2:
                ret = k;
                break;
            default:
                break;
        }
        return ret;
    }

    public boolean compareTo(Point p) {
        if (this.i != p.i) {
            return this.i < p.i;
        }
        if (this.j != p.j) {
            return this.i < p.j;
        }
        return this.k < p.k;
    }

}
