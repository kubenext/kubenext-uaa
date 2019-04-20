package com.github.kubenext.uaa;

import org.junit.Test;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/**
 * @author shangjin.li
 */
public class JwtTest {

    @Test
    public void testPublicKey() {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbIm9wZW5pZCJdLCJleHAiOjE1NTU3MDYzMDQsImlhdCI6MTU1NTY3MDMwNCwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiIyZTE2NzQ1Zi02MTIwLTQ2MmUtOTZkYS1iMGJkYWZjYmRkMDciLCJjbGllbnRfaWQiOiJ3ZWIifQ.obJfU8olN59GdwddGyKoS8zdTA9OJCh4HQ3pleNsGPZBGPNRT0M555JDQyDKviwivHNZszIORX8n7DQPflUAfL7o9HDCOxMq3ndkvd2jkKkoYXm5QUtq58-xE9meGJqM8xO96KuCkqanKfdZ9bA6AzDVBQgiaSrIJ4g-pQGJ7xqksll37DRyV6dXoD6nWTlbh7WbjgMk4O-X5HHm2zOYhPJkMYG-LEDodb2Dt59Ct7fDZ4OLKhU_qO7tW0lyd7GD9L12F1vTc3tNiz0K7ZYzU9Y1_laSnRQNwSETagw7rYCRaUeLxM6Tp_rPmNICVBHr7GO1OntI02mDTKhOXw1Z9g";
        String pk = "-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxPG8PTlTx/ApPYTBafr6Y5T0BR5N52LCafuqSOsNPj9E8IMRQUaxvQFNAl/lzcfiHhzJV75zvQWORS9SszwvOjbMw1HLb7apeZ7Pzs9HrwrayTpndBioS73NxVlmuC5KImjK+6agnq/f3IFnB7unhzSO1Dk8QfTS8zmbGlK9O+dw6mNkPjqwBbhWCifGQ6gEaRKTgmgz4xK1y7pk14M96lr7Dm8dgxaTKhjV0TB+F1CBhb1cc99FRaB/0QzqHI/UAamCwpPJR+4p5iF4Ujp/zRfbYP0c2t6YwWh77DShBDXi/weHW5mUbfA5G5mc/OAZMvxX885FvlN6nkY4uJZyTQIDAQAB\n-----END PUBLIC KEY-----";
        System.out.println(JwtHelper.decodeAndVerify(token, new RsaVerifier(pk)));
    }

}
