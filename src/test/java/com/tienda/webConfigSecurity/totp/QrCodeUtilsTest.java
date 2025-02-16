package com.tienda.webConfigSecurity.totp;

import org.junit.jupiter.api.Test;


class QrCodeUtilsTest {

    @Test
    void generateQrCodeUrl() {
        //otpauth://totp/Tienda:202411?secret=UEC3MJBRFYDG3YCR&issuer=Tienda
        System.out.println(QrCodeUtils.generateQrCodeUrl("202411","UEC3MJBRFYDG3YCR","Tienda"));
    }
}