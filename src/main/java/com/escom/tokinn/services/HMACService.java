package com.escom.tokinn.services;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;
import com.escom.tokinn.component.HMAC;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Service("hmacService")
public class HMACService {

	class AES {
        public byte[] cipherFile;
        public String hmacKey;
        public AES(byte[] c, String k) {
            cipherFile = c;
            hmacKey = k;
        }
    }


	/*Esta función es la que se manda llmar, se encarga de cifrar y firmar cada documento, la llave del HMAC está compuesta por el ID
    del usuario y el úlrimo bloque del cifrado AES del archivo a enviar, los parametros son el ID del usuario, el nombre del archivo 
    a cifrar y el nombre del archivo cifrado y firmado*/
    public HMAC signFile(String idUsuario, byte[]pdfBytes) throws Throwable {
        AES aesFile = encryptAES(idUsuario, pdfBytes);
        String plainKey = idUsuario + aesFile.hmacKey;
        SecretKey hmacKey = new SecretKeySpec(plainKey.getBytes("UTF-8"), "HMACSHA512");
        byte[] firmaDocumento = HMAC(hmacKey, pdfBytes);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(firmaDocumento);
        out.write(aesFile.cipherFile);
        HMAC res = new HMAC(hmacKey.getEncoded(), firmaDocumento, out.toByteArray());
        out.close();
        return res;
    }

    /*Esta función regresa la firma del documento, recibe la llave y el arrglo de bytes del archivo*/
    public byte[] HMAC(SecretKey hmacKey, byte[] file) throws InvalidKeyException, IOException, NoSuchAlgorithmException {
        Mac mac = Mac.getInstance("HMACSHA512");
        mac.init(hmacKey);
        return mac.doFinal(file);
    }

    /*Esta función retorna el archivo cifrado, la llave del cfrado es el ID del usuario*/
    private AES encryptAES(String idUsuario, byte[]pdfBytes) throws Throwable {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(idUsuario.getBytes("UTF-8"), "AES"));
        InputStream input = new ByteArrayInputStream(pdfBytes);
        //FileInputStream fis = new FileInputStream(nombreArchivo);
        CipherInputStream cis = new CipherInputStream(input, cipher);
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] content = new byte[8];
        int numbytes;
        while ((numbytes = cis.read(content)) != -1) {
            output.write(content, 0, numbytes);
        }
        cis.close();
        input.close();
        
        Encoder encoder = Base64.getEncoder();
        //Se codifica el ultimo bloque guardado en content[] para ser usado como llave
        AES res = new AES(output.toByteArray(), encoder.encodeToString(content));
        output.close();
        return res;
    }

    /*Esta función decifra el archivo vaidando la firma del documento, el archivo lo recibe como cadenas de bytes*/
    public boolean decryptVerify(String key, byte[] inFile, String outFile) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, UnsupportedEncodingException, IOException {
        boolean res = false;
        ByteArrayInputStream fis = new ByteArrayInputStream(inFile);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("UTF-8"), "AES"));
        CipherOutputStream cos = new CipherOutputStream(output, cipher);
        byte[] hmac = new byte[64];
        byte[] content = new byte[8];
        int numbytes = fis.read(hmac);
        Base64.Encoder encoder = Base64.getEncoder();
        while ((numbytes = fis.read(content)) != -1) {
            cos.write(content, 0, numbytes);
        }
        cos.close();
        fis.close();
        String k = key + encoder.encodeToString(content);
        SecretKey skey = new SecretKeySpec(k.getBytes("UTF-8"), "HMACSHA512");
        if (Arrays.equals(hmac, HMAC(skey, output.toByteArray()))) {
            try (FileOutputStream out = new FileOutputStream(outFile)) {
                out.write(output.toByteArray());
                out.close();
            }
            res = true;
        }
        output.close();
        return res;
    }
    
}
