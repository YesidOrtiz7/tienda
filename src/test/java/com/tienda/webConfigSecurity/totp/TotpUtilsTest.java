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
    void validateTotp() {
    }
}