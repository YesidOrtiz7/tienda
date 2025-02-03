package com.tienda.webConfigSecurity.totp;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;

public class TotpUtils {
    public static String generateSecret() {
        return Base32.random();
    }
    public static boolean validateTotp(String secret, String totpCode) {
        Totp totp= new Totp(secret);
        return totp.verify(totpCode);
    }
}
