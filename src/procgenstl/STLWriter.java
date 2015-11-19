/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procgenstl;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author wpower
 */
class STLWriter {

    protected DataOutputStream ds;
    private final int SCALE = 1;
    private byte[] buf;

    STLWriter(String fn) throws IOException {
        ds = new DataOutputStream(new FileOutputStream(fn + ".bin.stl"));
        buf = new byte[4];
    }

    public void writeHeader(int num) throws IOException {
        byte[] header = new byte[80];
        ds.write(header, 0, 80);
        writeInt(num);
    }

    /**
     * Prints the triangles corresponding to the face to the output byte stream.
     */
    public void addFace_Bin(int i, int j, int k, Cell.Face face, int type) throws IOException {
        int rgb = 14430770;
        Point[] points = new Point[4];
        points[0] = new Point(i + face.f0i, j + face.f0j, k + face.f0k);
        points[1] = new Point(i + face.f1i, j + face.f1j, k + face.f1k);
        points[2] = new Point(i + face.f2i, j + face.f2j, k + face.f2k);
        points[3] = new Point(i + face.f3i, j + face.f3j, k + face.f3k);
        Point normal = new Point(face.di, face.dj, face.dk);
        writeVector(normal);
        writeVector(points[0]);
        writeVector(points[1]);
        writeVector(points[2]);
        writeShort(formatRGB(rgb));
        writeVector(normal);
        writeVector(points[1]);
        writeVector(points[3]);
        writeVector(points[2]);
        writeShort(formatRGB(rgb));
    }

    public void endSave() {
        try {
            ds.flush();
            ds.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Private Methods - Mostly using the byte buffer to correctly write to the
     * output byte stream.
     */
    private final void prepareBuffer(int a) {
        buf[3] = (byte) (a >>> 24);
        buf[2] = (byte) (a >> 16 & 0xff);
        buf[1] = (byte) (a >> 8 & 0xff);
        buf[0] = (byte) (a & 0xff);
    }

    private int formatRGB(int rgb) {
        int col15bits = (rgb >> 3 & 0x1f);
        col15bits |= (rgb >> 11 & 0x1f) << 5;
        col15bits |= (rgb >> 19 & 0x1f) << 10;
        col15bits |= 0x8000;
        return col15bits;
    }

    private void writeVector(Point p) throws IOException {
        writeFloat(p.i * SCALE);
        writeFloat(p.j * SCALE);
        writeFloat(p.k * SCALE);
    }

    private void writeFloat(float a) throws IOException {
        prepareBuffer(Float.floatToRawIntBits(a));
        ds.write(buf, 0, 4);
    }

    private void writeInt(int a) throws IOException {
        prepareBuffer(a);
        ds.write(buf, 0, 4);
    }

    private void writeShort(int a) throws IOException {
        ds.writeByte(a & 0xff);
        ds.writeByte(a >> 8 & 0xff);
    }
}
