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
            Point[] points=new Point[4];
            points[0] = new Point( i+face.f0i, j+face.f0j, k+face.f0k );
            points[1] = new Point( i+face.f1i, j+face.f1j, k+face.f1k );
            points[2] = new Point( i+face.f2i, j+face.f2j, k+face.f2k );
            points[3] = new Point( i+face.f3i, j+face.f3j, k+face.f3k );
            
            //'Bottom' Triangle
            fw.write("facet normal "+face.NORMAL+"\n");
            fw.write("\touter loop\n");
            
            fw.write("\t\tvertex "+points[0].vectorString(SCALE)+"\n");
            fw.write("\t\tvertex "+points[1].vectorString(SCALE)+"\n");
            fw.write("\t\tvertex "+points[2].vectorString(SCALE)+"\n");
            
            fw.write("\tendloop\nendfacet\n");
            
            //'Top' Triangle
            fw.write("facet normal "+face.NORMAL+"\n");
            fw.write("\touter loop\n");
            
            fw.write("\t\tvertex "+points[1].vectorString(SCALE)+"\n");
            fw.write("\t\tvertex "+points[3].vectorString(SCALE)+"\n");
            fw.write("\t\tvertex "+points[2].vectorString(SCALE)+"\n");
            
            fw.write("\tendloop\nendfacet\n");
        } catch (IOException ex) {
            Logger.getLogger(STLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void close() {
        try {
            fw.write("endsolid\n");
        } catch (IOException ex) {
            Logger.getLogger(STLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
