package com.revature.ers;

import org.mindrot.jbcrypt.BCrypt;

public abstract class Authentication {
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean validatePassword(String candidate, String hashed) {
        return BCrypt.checkpw(candidate, hashed);
    }
}
