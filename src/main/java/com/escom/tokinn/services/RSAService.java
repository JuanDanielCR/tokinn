package com.escom.tokinn.services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service("rsaService")
public class RSAService {
	
	//FUNCIÓN PARA GENERAR PAR DE LLAVES
    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);      
        return keyPairGenerator.genKeyPair();
    }	
	
    //KeyPair kp = RSA.buildKeyPair(); 
    //PublicKey publicKey = kp.getPublic();
    //PrivateKey privateKey = kp.getPrivate();

    //FUNCION PARA CIFRAR
    //byte [] RSAencrypted = RSAencrypt(privateKey,Files.readAllBytes(Paths.get(fileName3)));//encrypt(privateKey, "This is a secret message");     
    //Files.write(Paths.get(fileName4),RSAencrypted);//System.out.println(new String(encrypted));  // <<encrypted message>>    
    public static byte[] RSAencrypt(PrivateKey privateKey,byte[] message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  

        return cipher.doFinal(message);  
    }

    //FUNCION PARA DECIFRAR
    //byte[] RSAdecrypted = RSAdecrypt(publicKey, RSAencrypted);                                 
    //Files.write(Paths.get(fileName5),RSAdecrypted);    
    public static byte[] RSAdecrypt(PublicKey publicKey, byte [] RSAencrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        
        return cipher.doFinal(RSAencrypted);
    }
    
    
    
}
