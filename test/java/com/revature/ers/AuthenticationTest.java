package com.revature.ers;

import junit.framework.TestCase;


public class AuthenticationTest extends TestCase {
    String hashed, password;
    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {

    }

    public void testHash() throws Exception {
        password = "12345";
        hashed = Authentication.hash(password);
        System.out.println(hashed);
    }

    public void testValidatePassword() throws Exception {
        password = "12345";
        hashed = Authentication.hash(password);
        System.out.println(hashed);
        System.out.println(Authentication.validatePassword(password,hashed));
        password = "12345";
        System.out.println(Authentication.validatePassword(password,hashed));
    }

}