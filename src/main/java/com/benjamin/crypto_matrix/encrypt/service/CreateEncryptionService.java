package com.benjamin.crypto_matrix.encrypt.service;

import java.util.Map;

import org.ejml.simple.SimpleMatrix;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.benjamin.crypto_matrix.encrypt.Encrypt;
import com.benjamin.crypto_matrix.encrypt.EncryptRepository;
import com.benjamin.crypto_matrix.encrypt.dto.CreateEncryptionDto;
import com.benjamin.crypto_matrix.user.User;
import com.benjamin.crypto_matrix.user.UserRepository;
import com.benjamin.crypto_matrix.utils.AlphabethUtil;
import com.benjamin.crypto_matrix.utils.MatrixSerializerUtil;
import com.benjamin.crypto_matrix.utils.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateEncryptionService {
    private final UserRepository userRepository;
    private final EncryptRepository encryptRepository;

    public Map<String, Object> createEncryption(CreateEncryptionDto createEncryptionDto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userRepository.findByEmail(userEmail);
        User recieverUser = userRepository.findByEmail(createEncryptionDto.getRecieverEmail());
        Encrypt encrypt = new Encrypt();

        if (recieverUser == null) {
            throw new RuntimeException("Reciever email not found");
        }

        encrypt.setPlaintext(createEncryptionDto.getPlaintext());
        encrypt.setKeyMatrix(recieverUser.getKeyMatrix());
        encrypt.setUser(user);

        try {
            SimpleMatrix publicKeyMatrix = MatrixSerializerUtil.deserialize(recieverUser.getKeyMatrix().getPublicKeyMatrix());
            String cipherText = encrypt(createEncryptionDto.getPlaintext(), publicKeyMatrix);
            encrypt.setCiphertext(cipherText);
            encryptRepository.save(encrypt);
            return ResponseUtil.createSuccessResponse("Encryption successful", cipherText);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error during encryption: " + e.getMessage());
        }

    }


    public static String encrypt(String plaintext, SimpleMatrix key) {
        int mod = AlphabethUtil.size();
        plaintext = plaintext.toUpperCase().replace(" ", "_");

        int keyRowSize = key.getNumRows();
        StringBuilder ciphertext = new StringBuilder();

        while (plaintext.length() % keyRowSize != 0) {
            plaintext += "_";
        }

        for (int i = 0; i < plaintext.length(); i += keyRowSize) {
            double[][] vector = new double[keyRowSize][1];
            for (int j = 0; j < keyRowSize; j++) {
                vector[j][0] = AlphabethUtil.charToIndex(plaintext.charAt(i + j));
            }

            SimpleMatrix multiplied = key.mult(new SimpleMatrix(vector));

            for (int r = 0; r < multiplied.getNumRows(); r++) {
                int index = (int) Math.round(multiplied.get(r, 0));
                ciphertext.append(AlphabethUtil.indexToChar(((index % mod) + mod) % mod));
            }
        }

        return ciphertext.toString();
    }

}
