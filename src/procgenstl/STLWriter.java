/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procgenstl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wpower
 */
class STLWriter {
    private final FileWriter fw;
    private final int SCALE = 1;
    
    STLWriter(FileWriter writer) {
        fw = writer;
    }

    void writeHeader(String solidname) {
        try {
            //Write out the proper header for the STL spec.
            fw.write("solid "+solidname+"\n");
        } catch (IOException ex) {
            Logger.getLogger(STLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addFace(int i, int j, int k, Cell.Face face) {
        try {
            //Calculate the 4 points needed

            //Write the 2 Triangles
            //'Bottom' Triangle
            fw.write("facet normal "+face.NORMAL+"\n");
            fw.write("outer loop\n");
            
            fw.write("endloop\n endfacet\n");
            
            //'Top' Triangle
            fw.write("facet normal "+face.NORMAL+"\n");
            fw.write("outer loop\n");
            
            fw.write("endloop\n endfacet\n");
        } catch (IOException ex) {
            Logger.getLogger(STLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
