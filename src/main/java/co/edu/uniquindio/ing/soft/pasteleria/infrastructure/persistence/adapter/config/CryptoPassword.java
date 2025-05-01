package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptoPassword {

    public static String encriptarPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
