package com.benjamin.crypto_matrix.user.service;

import java.util.Random;

import org.ejml.data.Matrix;
import org.ejml.simple.SimpleMatrix;
import org.springframework.stereotype.Service;

import com.benjamin.crypto_matrix.user.KeyMatrix;
import com.benjamin.crypto_matrix.user.KeyMatrixRepository;
import com.benjamin.crypto_matrix.utils.AlphabethUtil;
import com.benjamin.crypto_matrix.utils.MatrixSerializerUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenerateKeyMatrixService {
    private final KeyMatrixRepository keyMatrixRepository;

    public KeyMatrix generateKeyMatrixForUser() throws JsonProcessingException {
        KeyMatrix keyMatrix = new KeyMatrix();
        SimpleMatrix randomIntegerMatrix = generateRandomIntegerMatrix(3, 3, 0, 26);

        String publicKeyString = MatrixSerializerUtil.serialize(randomIntegerMatrix);
        String privateKeyString = MatrixSerializerUtil.serialize(randomIntegerMatrix.invert());

        keyMatrix.setPublicKeyMatrix(publicKeyString);
        keyMatrix.setPrivateKeyMatrix(privateKeyString);
        keyMatrixRepository.save(keyMatrix);
        return keyMatrix;
    }

    // public SimpleMatrix generateRandomIntegerMatrix(int rows, int cols, int min,
    // int max) {
    // Random random = new Random();
    // int MOD = AlphabethUtil.size();

    // double[][] data = new double[rows][cols];
    // for (int i = 0; i < cols; i++) {
    // for (int j = 0; j < rows; j++) {
    // data[i][j] = random.nextInt(MOD);
    // }
    // }

    // SimpleMatrix matrixData = new SimpleMatrix(data);

    // int determinant = (int) Math.round(matrixData.determinant());
    // do {
    // return matrixData;
    // } while (Math.abs(matrixData.determinant()) < 1e-9 &&
    // java.math.BigInteger.valueOf(determinant)
    // .gcd(java.math.BigInteger.valueOf(MOD))
    // .intValue() == 1);

    // }

    public SimpleMatrix generateRandomIntegerMatrix(int rows, int cols, int min,
            int max) {
        Random random = new Random();
        int MOD = AlphabethUtil.size();

        while (true) {
            double[][] data = new double[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    data[i][j] = random.nextInt((max - min) + 1) + min;
                }
            }

            SimpleMatrix matrixData = new SimpleMatrix(data);

            int det = (int) Math.round(matrixData.determinant());
            int detMod = ((det % MOD) + MOD) % MOD;

            if (detMod != 0 &&
                    java.math.BigInteger.valueOf(detMod)
                            .gcd(java.math.BigInteger.valueOf(MOD))
                            .intValue() == 1) {
                return matrixData;
            }

        }
    }

}
