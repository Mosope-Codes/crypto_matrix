package com.benjamin.crypto_matrix.decrypt;

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

import com.benjamin.crypto_matrix.decrypt.dto.CreateDecryptionDto;
import com.benjamin.crypto_matrix.decrypt.service.CreateDecryptionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("decrypt")
public class DecryptController {
    private final CreateDecryptionService createDecryptionService;
    
    @PostMapping("/text")
    public ResponseEntity<Map<String, Object>> decryptText(@Valid @RequestBody CreateDecryptionDto createDecryptionDto){
        Map<String,Object> plainText = createDecryptionService.createDecryption(createDecryptionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(plainText);
    }

    @GetMapping("/decryptions")
    public ResponseEntity<Map<String, Object>> getAllDecryptions(){
        return ResponseEntity.ok(Map.of(
            "status", Response.SC_OK,
            "message", "Decryptions fetched successfully",
            "data", null
        ));
    }

    @GetMapping("/decryptionDetails")
    public ResponseEntity<Map<String, Object>> getDecryptionDetails(){
        return ResponseEntity.ok(Map.of(
            "status", Response.SC_OK,
            "message", "Decryption details fetched successfully",
            "data", null
        ));
    }

    @DeleteMapping("/deleteDecryption")
    public ResponseEntity<Map<String, Object>> deleteDecryption(){
        return ResponseEntity.ok(Map.of(
            "status", Response.SC_OK,
            "message", "Decryption deleted successfully"
        ));
    }
}
