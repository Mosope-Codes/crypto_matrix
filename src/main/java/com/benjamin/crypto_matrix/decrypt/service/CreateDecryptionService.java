package com.benjamin.crypto_matrix.decrypt.service;

import java.util.Map;

import org.ejml.simple.SimpleMatrix;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.benjamin.crypto_matrix.decrypt.dto.CreateDecryptionDto;
import com.benjamin.crypto_matrix.user.User;
import com.benjamin.crypto_matrix.user.UserRepository;
import com.benjamin.crypto_matrix.utils.AlphabethUtil;
import com.benjamin.crypto_matrix.utils.MatrixModUtil;
import com.benjamin.crypto_matrix.utils.MatrixSerializerUtil;
import com.benjamin.crypto_matrix.utils.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CreateDecryptionService {
    private final UserRepository userRepository;

    public Map<String, Object> createDecryption(CreateDecryptionDto createDecryptionDto){
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(userEmail);
        try {
            SimpleMatrix publicKeyMatrix = MatrixSerializerUtil.deserialize(user.getKeyMatrix().getPublicKeyMatrix());
            String plainText = decrypt(createDecryptionDto.getCiphertext(), publicKeyMatrix);
            return ResponseUtil.createSuccessResponse("Decryption successful", plainText);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error during decryption: " + e.getMessage());
        }
    }



    public static String decrypt(String ciphertext, SimpleMatrix key) {
        int mod = AlphabethUtil.size();
        int keyRowSize = key.getNumRows();
        StringBuilder plaintext = new StringBuilder();
        SimpleMatrix inverseKey = MatrixModUtil.modularInverse3x3(key);

        for (int i = 0; i < ciphertext.length(); i += keyRowSize) {
            double[][] vector = new double[keyRowSize][1];
            for (int j = 0; j < keyRowSize; j++) {
                vector[j][0] = AlphabethUtil.charToIndex(ciphertext.charAt(i + j));
            }

            SimpleMatrix multiplied = inverseKey.mult(new SimpleMatrix(vector));

            for (int r = 0; r < multiplied.getNumRows(); r++) {
                int index = (int) Math.round(multiplied.get(r, 0));
                plaintext.append(AlphabethUtil.indexToChar(((index % mod) + mod) % mod));
            }
        }

        return plaintext.toString().replace("_", " ");
    }

}
