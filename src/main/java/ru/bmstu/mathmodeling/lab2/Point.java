package ru.bmstu.mathmodeling.lab2;

public class Point {
    private int x;
    private int y;
    private long zCode;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;

        byte[] bytes = new byte[Long.SIZE];
        for (int i = 0; i < Integer.SIZE; i++) {
            bytes[i * 2] = getBit(x, i);
            bytes[i * 2 + 1] = getBit(y, i);
        }

        zCode = 0;
        for (int i = 0; i < bytes.length; i++) {
            zCode += (1 << i) * (bytes[i] * 0xff);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public long getZCode() {
        return zCode;
    }

    private byte getBit(int num, int position) {
        return (byte) ((num >> position) & 1);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", zCode=" + zCode +
                '}';
    }
}
