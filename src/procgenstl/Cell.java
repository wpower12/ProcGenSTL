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

    public static enum Face {                                           //   p0         p1         p2        p3
        TOP     ( 0,  0,  1, "0.0 0.0 0.0",  0, 0, 1,  1,  0, 1,  0,1,1,  1,1,1),
        BOTTOM  ( 0,  0, -1, "0.0 0.0 -1.0", 1, 1, 0,  1,  0, 0,  0,1,0,  0,0,0),
        LEFT    (-1,  0,  0, "-1.0 0.0 0.0", 0, 0, 0,  0, 0, 1,  0,1,0,  0,1,1),
        RIGHT   ( 1,  0,  0, "1.0 0.0 0.0",  1, 1, 1,  1,  0, 1,   1,1,0,  1,0,0),
        FRONT   ( 0,  1,  0, "0.0 -1.0 0.0",  0, 0, 0,  1,  0, 0,   0,0,1,  1,0,1),
        BACK    ( 0, -1,  0, "0.0 1.0 0.0",  0, 1, 0,  0, 1, 1,   1,1,0,  1,1,1);

        //Relative neighbor locations in 3 space.
        public final int di, dj, dk;

        //Normal string
        public final String NORMAL;

        //Vector to face-origin relative to cell origin
        public final int f0i, f0j, f0k;

        //Vector to opposing face-corner relative to face-origin
        public final int f1i, f1j, f1k;
        public final int f2i, f2j, f2k;
        public final int f3i, f3j, f3k;

        Face(int di,  int dj,  int dk, String n,
             int f0i, int f0j, int f0k,
             int f1i, int f1j, int f1k,
             int f2i, int f2j, int f2k,
             int f3i, int f3j, int f3k) {
            this.di = di;
            this.dj = dj;
            this.dk = dk;
            this.NORMAL = n;
            this.f0i = f0i;
            this.f0j = f0j;
            this.f0k = f0k;
            this.f1i = f1i;
            this.f1j = f1j;
            this.f1k = f1k;
            this.f2i = f2i;
            this.f2j = f2j;
            this.f2k = f2k;
            this.f3i = f3i;
            this.f3j = f3j;
            this.f3k = f3k;
        }
    }
    
    public int type;
}
