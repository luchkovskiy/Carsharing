package com.luchkovskiy.util;

import com.luchkovskiy.security.config.CardConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class EncryptionUtils {

    private final CardConfig cardConfig;


    public String encrypt(String value) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(cardConfig.getSecret().getBytes(), cardConfig.getAlgorithm());
        Cipher cipher = Cipher.getInstance(cardConfig.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedValue = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public String decrypt(String encryptedValue) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(cardConfig.getSecret().getBytes(), cardConfig.getAlgorithm());
        Cipher cipher = Cipher.getInstance(cardConfig.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedValue = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(decryptedValue);
    }

}
