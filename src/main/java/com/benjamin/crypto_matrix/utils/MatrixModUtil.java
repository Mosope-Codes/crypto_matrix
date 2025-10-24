package com.benjamin.crypto_matrix.utils;

import org.ejml.simple.SimpleMatrix;

public class MatrixModUtil {

    private static final int MOD = 27;

    public static SimpleMatrix modularInverse3x3(SimpleMatrix key) {
        // Step 1: determinant
        int det = (int) Math.round(key.determinant()) % MOD;
        if (det < 0) det += MOD;

        // Step 2: modular multiplicative inverse of det mod 27
        int detInv = modInverse(det, MOD);
        if (detInv == -1) {
            throw new IllegalArgumentException("Matrix determinant not invertible mod 27");
        }

        // Step 3: adjugate of the key
        SimpleMatrix adj = adjugate3x3(key);

        // Step 4: multiply adjugate by determinant inverse
        SimpleMatrix inv = adj.scale(detInv);

        // Step 5: mod 27 each entry
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int val = ((int) Math.round(inv.get(r, c)) % MOD);
                if (val < 0) val += MOD;
                inv.set(r, c, val);
            }
        }

        return inv;
    }

    // Helper: modular inverse of a number modulo m (brute-force since m=27 is small)
    private static int modInverse(int a, int m) {
        a %= m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1; // not invertible
    }

    // Helper: adjugate of 3x3 matrix
    private static SimpleMatrix adjugate3x3(SimpleMatrix m) {
        double a = m.get(0,0), b = m.get(0,1), c = m.get(0,2);
        double d = m.get(1,0), e = m.get(1,1), f = m.get(1,2);
        double g = m.get(2,0), h = m.get(2,1), i = m.get(2,2);

        return new SimpleMatrix(3, 3, true, new double[]{
            (e*i - f*h), -(b*i - c*h),  (b*f - c*e),
            -(d*i - f*g), (a*i - c*g), -(a*f - c*d),
            (d*h - e*g), -(a*h - b*g), (a*e - b*d)
        }).transpose(); // adj = cofactor^T
    }
}

