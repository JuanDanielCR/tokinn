package com.escom.tokinn.component;
import java.util.Base64;

public class HMAC {

    public String sign;
    public String key;
    public byte[] document;

    public HMAC(byte[] k, byte[] s, byte[] d) {
        Base64.Encoder encoder = Base64.getEncoder();
        sign = encoder.encodeToString(s);
        key = encoder.encodeToString(k);
        document = d;
    }
}
