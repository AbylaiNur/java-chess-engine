package org.abylai;

public class Bitboard {

    // File masks
    public static final long FILE_A = 0x0101010101010101L;
    public static final long FILE_B = FILE_A << 1;
    public static final long FILE_C = FILE_A << 2;
    public static final long FILE_D = FILE_A << 3;
    public static final long FILE_E = FILE_A << 4;
    public static final long FILE_F = FILE_A << 5;
    public static final long FILE_G = FILE_A << 6;
    public static final long FILE_H = FILE_A << 7;

    // Rank masks
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

    // Attack mask directions for the given squares
    public static long northOne(long bb)   { return bb << 8;  }
    public static long southOne(long bb)   { return bb >>> 8; }
    public static long eastOne(long bb)    { return (bb << 1) & ~FILE_A; }
    public static long westOne(long bb)    { return (bb >>> 1) & ~FILE_H; }
    public static long northEast(long bb)  { return (bb << 9) & ~FILE_A; }
    public static long northWest(long bb)  { return (bb << 7) & ~FILE_H; }
    public static long southEast(long bb)  { return (bb >>> 7) & ~FILE_A; }
    public static long southWest(long bb)  { return (bb >>> 9) & ~FILE_H; }

    /**
     * Knight attack mask for the given square
     */
    public static long knightAttacks(int sq) {
        long b = SQUARE_BB[sq];
        long attacks = 0L;

        attacks |= (b << 17) & ~FILE_A;
        attacks |= (b << 15) & ~FILE_H;
        attacks |= (b << 10) & ~(FILE_A | FILE_B);
        attacks |= (b << 6)  & ~(FILE_H | FILE_G);
        attacks |= (b >>> 17) & ~FILE_H;
        attacks |= (b >>> 15) & ~FILE_A;
        attacks |= (b >>> 10) & ~(FILE_H | FILE_G);
        attacks |= (b >>> 6)  & ~(FILE_A | FILE_B);

        return attacks;
    }

    /**
     * King attack mask for the given square
     */
    public static long kingAttacks(int sq) {
        long b = SQUARE_BB[sq];
        long attacks = 0L;

        attacks |= northOne(b);
        attacks |= southOne(b);
        attacks |= eastOne(b);
        attacks |= westOne(b);
        attacks |= northEast(b);
        attacks |= northWest(b);
        attacks |= southEast(b);
        attacks |= southWest(b);

        return attacks;
    }

    /**
     * Pawn capture mask for the given square
     * @param sq       starting square of the pawn
     * @param isWhite  true if the pawn is white (moving north)
     */
    public static long pawnAttacks(int sq, boolean isWhite) {
        long b = SQUARE_BB[sq];
        return isWhite ? (northEast(b) | northWest(b))
                : (southEast(b) | southWest(b));
    }

    /**
     * Rook attack mask for the given square
     */
    public static long rookAttacks(int sq, long occupancy) {
        long attacks = 0L;
        int rank = sq / 8;
        int file = sq & 7;

        // North
        for (int r = rank + 1; r <= 7; r++) {
            int idx = r * 8 + file;
            attacks |= SQUARE_BB[idx];
            if ((occupancy & SQUARE_BB[idx]) != 0) break;
        }
        // South
        for (int r = rank - 1; r >= 0; r--) {
            int idx = r * 8 + file;
            attacks |= SQUARE_BB[idx];
            if ((occupancy & SQUARE_BB[idx]) != 0) break;
        }
        // East
        for (int f = file + 1; f <= 7; f++) {
            int idx = rank * 8 + f;
            attacks |= SQUARE_BB[idx];
            if ((occupancy & SQUARE_BB[idx]) != 0) break;
        }
        // West
        for (int f = file - 1; f >= 0; f--) {
            int idx = rank * 8 + f;
            attacks |= SQUARE_BB[idx];
            if ((occupancy & SQUARE_BB[idx]) != 0) break;
        }

        return attacks;
    }

    /**
     * Bishop attack mask for the given square
     */
    public static long bishopAttacks(int sq, long occupancy) {
        long attacks = 0L;
        int rank = sq / 8;
        int file = sq & 7;

        // North‑East
        for (int r = rank + 1, f = file + 1; r <= 7 && f <= 7; r++, f++) {
            int idx = r * 8 + f;
            attacks |= SQUARE_BB[idx];
            if ((occupancy & SQUARE_BB[idx]) != 0) break;
        }
        // North‑West
        for (int r = rank + 1, f = file - 1; r <= 7 && f >= 0; r++, f--) {
            int idx = r * 8 + f;
            attacks |= SQUARE_BB[idx];
            if ((occupancy & SQUARE_BB[idx]) != 0) break;
        }
        // South‑East
        for (int r = rank - 1, f = file + 1; r >= 0 && f <= 7; r--, f++) {
            int idx = r * 8 + f;
            attacks |= SQUARE_BB[idx];
            if ((occupancy & SQUARE_BB[idx]) != 0) break;
        }
        // South‑West
        for (int r = rank - 1, f = file - 1; r >= 0 && f >= 0; r--, f--) {
            int idx = r * 8 + f;
            attacks |= SQUARE_BB[idx];
            if ((occupancy & SQUARE_BB[idx]) != 0) break;
        }

        return attacks;
    }

    /**
     * Queen attack mask for the given square
     */
    public static long queenAttacks(int sq, long occupancy) {
        return rookAttacks(sq, occupancy) | bishopAttacks(sq, occupancy);
    }
}
