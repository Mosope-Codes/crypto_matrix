package com.benjamin.crypto_matrix.encrypt;

import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.benjamin.crypto_matrix.encrypt.dto.CreateEncryptionDto;
import com.benjamin.crypto_matrix.encrypt.service.CreateEncryptionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("encrypt")
public class EncryptController {

    private final CreateEncryptionService createEncryptionService;

    @PostMapping("/text")
    public ResponseEntity<Map<String, Object>> encryptText(@Valid @RequestBody CreateEncryptionDto createEncryptionDto){
        Map<String,Object> cipherText = createEncryptionService.createEncryption(createEncryptionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cipherText);
    }

    @GetMapping("/encryptions")
    public ResponseEntity<Map<String, Object>> getAllEncryptions(){
        return ResponseEntity.ok(Map.of(
            "status", Response.SC_OK,
            "message", "Encryptions fetched successfully",
            "data", null
        ));
    }

    @GetMapping("/encryptionDetails")
    public ResponseEntity<Map<String, Object>> getEncryptionDetails(){
        return ResponseEntity.ok(Map.of(
            "status", Response.SC_OK,
            "message", "Encryption details fetched successfully",
            "data", null
        ));
    }

    @DeleteMapping("/deleteEncryption")
    public ResponseEntity<Map<String, Object>> deleteEncryption(){
        return ResponseEntity.ok(Map.of(
            "status", Response.SC_OK,
            "message", "Encryption deleted successfully"
        ));
    }
}
