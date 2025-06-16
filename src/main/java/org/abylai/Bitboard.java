package org.abylai;

public class Bitboard {

    public static final long FILE_A = 0x0101010101010101L;
    public static final long FILE_B = FILE_A << 1;
    public static final long FILE_C = FILE_A << 2;
    public static final long FILE_D = FILE_A << 3;
    public static final long FILE_E = FILE_A << 4;
    public static final long FILE_F = FILE_A << 5;
    public static final long FILE_G = FILE_A << 6;
    public static final long FILE_H = FILE_A << 7;

    public static final long RANK_1 = 0xFFL;
    public static final long RANK_2 = RANK_1 << 8;
    public static final long RANK_3 = RANK_1 << 16;
    public static final long RANK_4 = RANK_1 << 24;
    public static final long RANK_5 = RANK_1 << 32;
    public static final long RANK_6 = RANK_1 << 40;
    public static final long RANK_7 = RANK_1 << 48;
    public static final long RANK_8 = RANK_1 << 56;

    public static final long[] SQUARE_BB = new long[64];

    static {
        for (int sq = 0; sq < 64; sq++) {
            SQUARE_BB[sq] = 1L << sq;
        }
    }

    public static long northOne(long bb)   { return bb << 8;  }
    public static long southOne(long bb)   { return bb >>> 8; }
    public static long eastOne(long bb)    { return (bb << 1) & ~FILE_A; }
    public static long westOne(long bb)    { return (bb >>> 1) & ~FILE_H; }
    public static long northEast(long bb)  { return (bb << 9) & ~FILE_A; }
    public static long northWest(long bb)  { return (bb << 7) & ~FILE_H; }
    public static long southEast(long bb)  { return (bb >>> 7) & ~FILE_A; }
    public static long southWest(long bb)  { return (bb >>> 9) & ~FILE_H; }
}
