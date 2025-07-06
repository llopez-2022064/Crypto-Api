package com.lesterlopez.encryptor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CryptoService {
    @Value("${crypto.secret-key}")
    private String secretKey;

    private SecretKeySpec getKeySpec() {
        return new SecretKeySpec(secretKey.getBytes(), "AES");
    }

    // 🔒 Encriptar con IV aleatorio
    public String encrypt(String plainText) throws Exception {
        // 1. Generar IV aleatorio de 16 bytes
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 2. Inicializar cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getKeySpec(), ivSpec);

        // 3. Cifrar texto
        byte[] encrypted = cipher.doFinal(plainText.getBytes());

        // 4. Concatenar IV + texto cifrado y codificar en Base64
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    // 🔓 Desencriptar usando el IV del texto cifrado
    public String decrypt(String encryptedText) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedText);

        // 1. Separar IV (primeros 16 bytes)
        byte[] iv = new byte[16];
        byte[] encrypted = new byte[combined.length - 16];
        System.arraycopy(combined, 0, iv, 0, 16);
        System.arraycopy(combined, 16, encrypted, 0, encrypted.length);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 2. Desencriptar
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, getKeySpec(), ivSpec);
        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted);
    }
}
