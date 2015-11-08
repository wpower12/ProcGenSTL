 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procgenstl;

import com.sudoplay.joise.module.ModuleAutoCorrect;
import com.sudoplay.joise.module.ModuleBasisFunction;
import com.sudoplay.joise.module.ModuleBasisFunction.BasisType;
import com.sudoplay.joise.module.ModuleScaleDomain;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wpower
 */
public class ProcGenSTL {

    private final int DIM = 50;
    Cell[][][] cells = new Cell[DIM][DIM][DIM];

    /**
     * Public Methods
     */
    public void write(String fn) {
        FileWriter fw;
        try {
            fw = new FileWriter(fn);
            write(fw);
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(ProcGenSTL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Private Methods - Main
     */
    private void write(FileWriter writer) {
        //Initilize terrain data structure
        initilize();
        //Perform Generation Procedures
        generate();
        //Print terrain data to stl
        print(writer);
    }

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
        //For now the only procedure is to make some random cells 1
        //randomCells();
        perlinDensity();
    }

    private void print(FileWriter writer) {
        STLWriter stl = new STLWriter(writer);
        stl.writeHeader("ProcGenTest");

        //For each cell
        //For each face
        //If neighbor-less, add triangles for that face.
        Cell c;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                for (int k = 0; k < DIM; k++) {
                    c = cells[i][j][k];
                    if (c.type != 0) {
                        for (Cell.Face f : Cell.Face.values()) {
                            if (!neighbor(i, j, k, f)) {
                                stl.addFace(i, j, k, f);
                            }
                        }
                    }
                }
            }
        }
        stl.close();
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
     * Private Methods - Terrain Procedures
     */
    private void randomCells() {
        Random r = new Random();
        //Iterate and set.
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                for (int k = 0; k < DIM; k++) {
                    cells[i][j][k].type = r.nextInt(2);
                }
            }
        }
    }

    private void perlinDensity() {

        ModuleBasisFunction basis = new ModuleBasisFunction();
        basis.setType(BasisType.SIMPLEX);

        ModuleAutoCorrect correct = new ModuleAutoCorrect();
        correct.setSource(basis);

        correct.setSampleScale(1.0);

        correct.calculate();

        ModuleScaleDomain scaleDomain = new ModuleScaleDomain();
        scaleDomain.setSource(correct);
        scaleDomain.setScaleX(0.025);
        scaleDomain.setScaleY(0.055);
        scaleDomain.setScaleZ(0.025);
        
        int val;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                for (int k = 0; k < DIM; k++) {
                    val = (scaleDomain.get(i, j, k) > 0.2*((float)DIM/(float)(j+1))) ? 0 : 1;
                    cells[i][j][k].type = val;
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProcGenSTL gen = new ProcGenSTL();
        gen.write("test.stl");
    }
}
