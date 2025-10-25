package com.benjamin.crypto_matrix.utils;

import java.math.BigInteger;


import org.ejml.simple.SimpleMatrix;

public class MatrixModUtil {

    private static final int MOD = AlphabethUtil.size(); // for mod 27 arithmetic

    // ✅ Compute adjugate of 3x3 matrix
    public static SimpleMatrix adjugate(SimpleMatrix m) {
        double a = m.get(0,0), b = m.get(0,1), c = m.get(0,2);
        double d = m.get(1,0), e = m.get(1,1), f = m.get(1,2);
        double g = m.get(2,0), h = m.get(2,1), i = m.get(2,2);

        double[][] adj = {
            {(e * i - f * h), -(b * i - c * h),  (b * f - c * e)},
            {-(d * i - f * g), (a * i - c * g), -(a * f - c * d)},
            {(d * h - e * g),  -(a * h - b * g),  (a * e - b * d)}
        };

        return new SimpleMatrix(adj).transpose(); // adjugate = transpose(cofactor matrix)
    }

    // ✅ Modular multiplicative inverse of determinant mod 27
    private static int modInverse(int det, int mod) {
        return BigInteger.valueOf(det).modInverse(BigInteger.valueOf(mod)).intValue();
    }

    // ✅ Invert matrix mod 27
    public static SimpleMatrix invertMod(SimpleMatrix matrix) {

        int det = (int) Math.round(matrix.determinant());
        int detMod = mod(det, MOD);      
        int detInv = modInverse(detMod, MOD);

        SimpleMatrix adj = adjugate(matrix);

        double[][] invData = new double[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int val = (int) Math.round(adj.get(row, col));
                invData[row][col] = mod(detInv * val, MOD);
            }
        }

        return new SimpleMatrix(invData);
    }

    // helper mod
    private static int mod(int x, int m) {
        return (x % m + m) % m;
    }
}

