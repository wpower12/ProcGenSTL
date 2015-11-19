package procgenstl;

import com.sudoplay.joise.Joise;
import com.sudoplay.joise.module.ModuleAutoCorrect;
import com.sudoplay.joise.module.ModuleBasisFunction;
import com.sudoplay.joise.module.ModuleBasisFunction.BasisType;
import com.sudoplay.joise.module.ModuleScaleDomain;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates a STL File representing a procedurally generated terrain over a
 * voxel space. Generating noise provided by the Joise library:
 *
 * https://github.com/SudoPlayGames/Joise
 *
 * @author wpower
 */
public class ProcGenSTL {

    private final int DIM = 50;
    Cell[][][] cells = new Cell[DIM][DIM][DIM];

    public void write(String fn) {
        initilize();
        generate();
        print(fn);
    }

    /**
     * Private Methods
     */
    private void initilize() {
        //Each cell should be a new 0-value cell
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                for (int k = 0; k < DIM; k++) {
                    cells[i][j][k] = new Cell();
                }
            }
        }
    }

    private void generate() {
        perlinDensity();
        //waterTable( 5 );
    }

    private void print(String fn) {
        try {
            STLWriter stl = new STLWriter(fn);
            stl.writeHeader(2 * faceCount());
            addFaces(stl);
            stl.endSave();
        } catch (IOException ex) {
            Logger.getLogger(ProcGenSTL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Private Methods - Terrain Procedures
     */
    private void perlinDensity() {
        Random r = new Random();

        ModuleBasisFunction basis = new ModuleBasisFunction();
        basis.setType(BasisType.SIMPLEX);
        basis.setSeed(r.nextLong());

        ModuleAutoCorrect correct = new ModuleAutoCorrect();
        correct.setSource(basis);
        correct.setSampleScale(0.5);
        correct.calculate();

        ModuleScaleDomain scaleDomain = new ModuleScaleDomain();
        scaleDomain.setSource(correct);
        scaleDomain.setScaleX(0.0275);
        scaleDomain.setScaleY(0.055);
        scaleDomain.setScaleZ(0.0275);

        //Joise lets us save a set of chained procedures
        Joise ourGenerator = new Joise(scaleDomain.getModuleMap());

        int val;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                for (int k = 0; k < DIM; k++) {
                    //If our generators value is greater than some a threshold value, place empty space (0)
                    //If not, place solid (1)
                    //We want this to be harder near the bottom of our space, and easier at the top
                    //To get this effect, we scale the threshold with the dimension we choose as height (j)
                    // rand( s.x * i, s.y * j, s.z * k) > T * t( j ),  t(j) = B/(j+C)
                    val = (ourGenerator.get(i, j, k) > 0.2 * ((float) DIM / (float) (j + 4))) ? 0 : 1;
                    cells[i][j][k].type = val;
                }
            }
        }
    }

    private void waterTable(int h) {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                for (int k = 0; k < DIM; k++) {
                    cells[i][j][k].type = 2;
                }
            }
        }
    }

    /**
     * Private Methods - Writing Faces to the stl writer class.
     */
    private int faceCount() {
        int facecount = 0;
        Cell c;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                for (int k = 0; k < DIM; k++) {
                    c = cells[i][j][k];
                    if (c.type != 0) {
                        for (Cell.Face f : Cell.Face.values()) {
                            if (!neighbor(i, j, k, f)) {
                                facecount++;
                            }
                        }
                    }
                }
            }
        }
        return facecount;
    }

    private void addFaces(STLWriter stl) throws IOException {
        Cell c;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                for (int k = 0; k < DIM; k++) {
                    c = cells[i][j][k];
                    if (c.type != 0) {
                        for (Cell.Face f : Cell.Face.values()) {
                            if (!neighbor(i, j, k, f)) {
                                stl.addFace_Bin(i, j, k, f, c.type);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean neighbor(int i, int j, int k, Cell.Face face) {
        //Looking at cell ijk, see if a cell exists at face 
        int ni = i + face.di;
        int nj = j + face.dj;
        int nk = k + face.dk;
        if (inBounds(ni, nj, nk)) {
            if (cells[ni][nj][nk].type != 0) {
                return true;
            }
        }
        return false;
    }

    private boolean inBounds(int ni, int nj, int nk) {
        return (ni > 0) && (ni < DIM - 1) && (nj > 0) && (nj < DIM - 1) && (nk > 0) && (nk < DIM - 1);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProcGenSTL gen = new ProcGenSTL();
        gen.write("test");
    }

}
