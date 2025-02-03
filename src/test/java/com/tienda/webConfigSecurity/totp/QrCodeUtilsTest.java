package com.tienda.webConfigSecurity.totp;

import org.junit.jupiter.api.Test;


class QrCodeUtilsTest {

    @Test
    void generateQrCodeUrl() {
        System.out.println(QrCodeUtils.generateQrCodeUrl("202411","UEC3MJBRFYDG3YCR","Tienda"));
    }
}