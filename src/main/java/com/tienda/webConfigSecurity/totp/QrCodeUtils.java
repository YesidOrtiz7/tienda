package com.tienda.webConfigSecurity.totp;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QrCodeUtils {
    public static String generateQrCodeUrl(String user, String secret, String issuer) {
        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                URLEncoder.encode(issuer, StandardCharsets.UTF_8),
                URLEncoder.encode(user, StandardCharsets.UTF_8),
                URLEncoder.encode(secret, StandardCharsets.UTF_8),
                URLEncoder.encode(issuer, StandardCharsets.UTF_8)
        );
    }
}
