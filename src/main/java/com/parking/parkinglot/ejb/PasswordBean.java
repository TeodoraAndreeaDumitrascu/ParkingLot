package com.parking.parkinglot.ejb;

import jakarta.ejb.Stateless;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class PasswordBean {

    private static final Logger LOG = Logger.getLogger(PasswordBean.class.getName());

    public String convertToSha256(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] digest = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            final StringBuilder builder = new StringBuilder();
            for (final byte b : digest) {
                builder.append(String.format("%02x", b));
            }

            String hex = builder.toString();
            if (hex.length() == 0) {
                LOG.log(Level.SEVERE, "Hex string is empty");
            }
            hex = hex.toUpperCase();

            return hex;
        } catch (NoSuchAlgorithmException ex) {
            LOG.log(Level.SEVERE, "Could not convert password!", ex);
            return null;
        }
    }
}