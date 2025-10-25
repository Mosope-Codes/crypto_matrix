package com.benjamin.crypto_matrix.utils;

import org.ejml.simple.SimpleMatrix;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MatrixSerializerUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String serialize(SimpleMatrix matrix) throws JsonProcessingException {
        double[][] array = to2D(matrix);
        return objectMapper.writeValueAsString(array);
    }

    public static SimpleMatrix deserialize(String json) throws JsonProcessingException {
        double[][] data = objectMapper.readValue(json, double[][].class);
        return new SimpleMatrix(data);
    }

    private static double[][] to2D(SimpleMatrix matrix) {
        int rows = matrix.getNumRows();
        int cols = matrix.getNumCols();
        double[][] array = new double[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                array[r][c] = matrix.get(r, c);
            }
        }
        return array;
    }
    
}


