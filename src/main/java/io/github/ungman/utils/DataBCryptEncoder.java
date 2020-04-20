package io.github.ungman.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataBCryptEncoder {

    private final int strength = 11;
    private final BCryptPasswordEncoder dataEncoder;

    public DataBCryptEncoder() {
        this.dataEncoder = new BCryptPasswordEncoder(this.strength);
    }

    public String decodeString(String data) {
        return dataEncoder.encode(data);
    }


}
