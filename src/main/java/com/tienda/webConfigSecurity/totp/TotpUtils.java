package com.tienda.webConfigSecurity.totp;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;

public class TotpUtils {
    public static String generateSecret() {
        return Base32.random();
    }
    public static String generateQrUrl(String userEmail, String secret, String issuer) {
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer, userEmail, secret, issuer);
    }
    public static boolean validateTotp(String secret, String totpCode) {
        Totp totp= new Totp(secret);
        return totp.verify(totpCode);
    }
}
