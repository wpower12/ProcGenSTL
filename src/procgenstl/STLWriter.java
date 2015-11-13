/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procgenstl;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
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
    //private final ByteBuffer bb;
    protected DataOutputStream ds;

    private final int SCALE = 1;

    private byte[] buf;

    STLWriter(String fn) throws IOException {
        fw = new FileWriter(fn);

        //Binary
        ds = new DataOutputStream(new FileOutputStream("bin_" + fn));

        buf = new byte[4];

    }

    void writeHeader(String solidname) {
        try {
            //Write out the proper header for the STL spec.
            fw.write("solid " + solidname + "\n");
        } catch (IOException ex) {
            Logger.getLogger(STLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void addFace(int i, int j, int k, Cell.Face face) {
        try {
            //Calculate the 4 points needed
            Point[] points = new Point[4];
            points[0] = new Point(i + face.f0i, j + face.f0j, k + face.f0k);
            points[1] = new Point(i + face.f1i, j + face.f1j, k + face.f1k);
            points[2] = new Point(i + face.f2i, j + face.f2j, k + face.f2k);
            points[3] = new Point(i + face.f3i, j + face.f3j, k + face.f3k);

            //'Bottom' Triangle
            fw.write("facet normal " + face.NORMAL + "\n");
            fw.write("\touter loop\n");

            fw.write("\t\tvertex " + points[0].vectorString(SCALE) + "\n");
            fw.write("\t\tvertex " + points[1].vectorString(SCALE) + "\n");
            fw.write("\t\tvertex " + points[2].vectorString(SCALE) + "\n");

            fw.write("\tendloop\nendfacet\n");

            //'Top' Triangle
            fw.write("facet normal " + face.NORMAL + "\n");
            fw.write("\touter loop\n");

            fw.write("\t\tvertex " + points[1].vectorString(SCALE) + "\n");
            fw.write("\t\tvertex " + points[3].vectorString(SCALE) + "\n");
            fw.write("\t\tvertex " + points[2].vectorString(SCALE) + "\n");

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

    void addFace_Bin(int i, int j, int k, Cell.Face face, int type) throws IOException {
        int rgb = 14430770;
        Point[] points = new Point[4];
        points[0] = new Point(i + face.f0i, j + face.f0j, k + face.f0k);
        points[1] = new Point(i + face.f1i, j + face.f1j, k + face.f1k);
        points[2] = new Point(i + face.f2i, j + face.f2j, k + face.f2k);
        points[3] = new Point(i + face.f3i, j + face.f3j, k + face.f3k);
        Point normal = new Point( face.di, face.dj, face.dk );
        writeVector( normal );
        writeVector(points[0]);
        writeVector(points[1]);
        writeVector(points[2]);
        writeShort(formatRGB(rgb));
        writeVector( normal );
        writeVector(points[1]);
        writeVector(points[3]);
        writeVector(points[2]);
        writeShort(formatRGB(rgb));
    }

    private final void prepareBuffer(int a) {
		buf[3] = (byte) (a >>> 24);
		buf[2] = (byte) (a >> 16 & 0xff);
		buf[1] = (byte) (a >> 8 & 0xff);
		buf[0] = (byte) (a & 0xff);
	}
    
    protected void writeFloat(float a) throws IOException {
        prepareBuffer(Float.floatToRawIntBits(a));
        ds.write(buf, 0, 4);
    }

    protected void writeHeader_Bin(int num) throws IOException {
        byte[] header = new byte[80];
        ds.write(header, 0, 80);
        writeInt(num);
    }

    protected void writeInt(int a) throws IOException {
        prepareBuffer(a);
        ds.write(buf, 0, 4);
    }

    protected void writeShort(int a) throws IOException {
        ds.writeByte(a & 0xff);
        ds.writeByte(a >> 8 & 0xff);
    }

    protected void writeVector(Point p) throws IOException {
        writeFloat(p.i * SCALE);
        writeFloat(p.j * SCALE);
        writeFloat(p.k * SCALE);
    }

    public int formatRGB(int rgb) {
        int col15bits = (rgb >> 3 & 0x1f);
        col15bits |= (rgb >> 11 & 0x1f) << 5;
        col15bits |= (rgb >> 19 & 0x1f) << 10;
        col15bits |= 0x8000;
        return col15bits;
    }
    public void endSave() {
		try {
			ds.flush();
			ds.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
