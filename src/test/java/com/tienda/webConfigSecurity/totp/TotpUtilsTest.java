package com.tienda.webConfigSecurity.totp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TotpUtilsTest {

    @Test
    void generateSecret() {
        //UEC3MJBRFYDG3YCR
        //WDPTU4FNFJMA52SS
        System.out.println(TotpUtils.generateSecret());
    }

    @Test
    void generateQrUrl() {
        System.out.println(TotpUtils.generateQrUrl("202411","UEC3MJBRFYDG3YCR","Tienda"));
    }

    @Test
    void validateTotp() {
    }
}