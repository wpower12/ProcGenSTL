/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procgenstl;

/**
 *
 * @author wpower
 */
class Cell {

    public static enum Face {
        TOP     ( 0,  0,  1, "0.000000e+000 0.000000e+000 1.000000e+000", 0, 0, 1,  1,  1, 0),
        BOTTOM  ( 0,  0, -1, "0.000000e+000 0.000000e+000 -1.000000e+000", 0, 0, 0,  1,  1, 0),
        LEFT    (-1,  0,  0, "-1.000000e+000 0.000000e+000 0.000000e+000", 0, 1, 0,  0, -1, 1),
        RIGHT   ( 1,  0,  0, "1.000000e+000 0.000000e+000 0.000000e+000", 1, 0, 0,  1,  0, 1),
        FRONT   ( 0,  1,  0, "0.000000e+000 1.000000e+000 0.000000e+000", 0, 0, 0,  1,  0, 1),
        BACK    ( 0, -1,  0, "0.000000e+000 0.000000e+000 1.000000e+000", 1, 1, 0, -1,  0, 1);

        //Relative neighbor locations in 3 space.
        public final int di, dj, dk;

        //Normal string
        public final String NORMAL;

        //Vector to face-origin relative to cell origin
        public final int f0i, f0j, f0k;

        //Vector to opposing face-corner relative to face-origin
        public final int fni, fnj, fnk;

        Face(int di,  int dj,  int dk, String n,
             int f0i, int f0j, int f0k,
             int fni, int fnj, int fnk) {
            this.di = di;
            this.dj = dj;
            this.dk = dk;
            this.NORMAL = n;
            this.f0i = f0i;
            this.f0j = f0j;
            this.f0k = f0k;
            this.fni = fni;
            this.fnj = fnj;
            this.fnk = fnk;
        }
    }
    
    public int type;
}
