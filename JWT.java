import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.print.attribute.standard.PageRanges;

/**
 * JWT
 */
public class JWT {
    String token;
    public JWT (byte[] token) {
        this.token = new String(token);
    }

    public boolean valid(){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String payload = getHeaders()+"."+getPayload();
            byte[] hash = digest.digest(payload.getBytes(StandardCharsets.UTF_8));
            Signature signature1 = Signature.getInstance("SHA256withRSA");
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            File file = new File("cert.pem");
            FileInputStream is = new FileInputStream(file);
            X509Certificate cer = (X509Certificate) fact.generateCertificate(is);
            PublicKey key = cer.getPublicKey();
            signature1.initVerify(key);
            signature1.update(payload.getBytes());
            return signature1.verify(getSignature());  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        
    }

    private String getHeaders(){
        return token.split("\\.")[0];
    }

        
    private String getPayload(){
        return token.split("\\.")[1];   
    }


    
    private byte[] getSignature()throws Exception{
        Base64.Decoder d = Base64.getUrlDecoder();
        return d.decode(token.split("\\.")[2].getBytes());
    }
}