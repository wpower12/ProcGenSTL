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
    private final int SCALE = 10;
    
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
            Point[] points=new Point[4];
            points[0] = new Point( i+face.f0i, j+face.f0j, k+face.f0k );
            points[1] = new Point( i+face.f0i, j+face.f0j+face.fnj, k+face.f0k+face.fnk );
            points[2] = new Point( i+face.f0i+face.fni, j+face.f0j, k+face.f0k+face.fnk );
            points[3] = new Point( i+face.f0i+face.fni, j+face.f0j+face.fnj, k+face.f0k );
            
            Point.sort( points );
            
            //'Bottom' Triangle
            fw.write("facet normal "+face.NORMAL+"\n");
            fw.write("outer loop\n");
            
            fw.write("vector "+points[0].vectorString(SCALE)+"\n");
            fw.write("vector "+points[1].vectorString(SCALE)+"\n");
            fw.write("vector "+points[3].vectorString(SCALE)+"\n");
            
            fw.write("endloop\n endfacet\n");
            
            //'Top' Triangle
            fw.write("facet normal "+face.NORMAL+"\n");
            fw.write("outer loop\n");
            
            fw.write("vector "+points[0].vectorString(SCALE)+"\n");
            fw.write("vector "+points[2].vectorString(SCALE)+"\n");
            fw.write("vector "+points[3].vectorString(SCALE)+"\n");
            
            fw.write("endloop\n endfacet\n");
        } catch (IOException ex) {
            Logger.getLogger(STLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void close() {
        try {
            fw.write("solid \n");
        } catch (IOException ex) {
            Logger.getLogger(STLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
