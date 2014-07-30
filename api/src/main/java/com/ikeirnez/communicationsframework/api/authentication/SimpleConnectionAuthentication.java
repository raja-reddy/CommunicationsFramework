package com.ikeirnez.communicationsframework.api.authentication;

import org.apache.commons.lang3.Validate;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by iKeirNez on 30/07/2014.
 */
public class SimpleConnectionAuthentication implements ConnectionAuthentication {

    private String key;

    public SimpleConnectionAuthentication(String key){
        try {
            Validate.notNull(key, "Password cannot be null.");
            this.key = new String(MessageDigest.getInstance("SHA-256").digest(key.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error whilst encrypting password for authentication", e);
        }
    }

    public String getKey(){
        return key;
    }

}
