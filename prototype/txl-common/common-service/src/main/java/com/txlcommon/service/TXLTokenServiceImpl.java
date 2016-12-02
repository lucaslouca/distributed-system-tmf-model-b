package com.txlcommon.service;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.jose4j.lang.JoseException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import com.txlcommon.TXLTokenService;

/**
 * <h1>TXLTokenServiceImpl</h1>
 * <p/>
 * TXLTokenService implementation for creating and validating user authentication tokens.
 *
 * @author Lucas Louca
 * @version 1.0
 * @since 18.08.2015
 */

public class TXLTokenServiceImpl implements TXLTokenService {

    public final String MAGIC_KEY = "obfuscate";

    public String createToken(UserDetails userDetails) {
        long expires = System.currentTimeMillis() + 1000L * 60 * 60;
        return userDetails.getUsername() + ":" + expires + ":" + computeSignature(userDetails, expires);

        // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
//        RsaJsonWebKey rsaJsonWebKey = null;
//        try {
//            rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
//        } catch (JoseException e) {
//            e.printStackTrace();
//        }
//
//        // Give the JWK a Key ID (kid), which is just the polite thing to do
//        rsaJsonWebKey.setKeyId("Trixl-JWT-Key");
//
//        // Create the Claims, which will be the content of the JWT
//        JwtClaims claims = new JwtClaims();
//        claims.setIssuer("Trixl-Auth");  // who creates the token and signs it
//        claims.setAudience(userDetails.getUsername()); // to whom the token is intended to be sent
//        claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
//        claims.setGeneratedJwtId(); // a unique identifier for the token
//        claims.setIssuedAtToNow();  // when the token was issued/created (now)
//        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
//        claims.setSubject("TEST1234"); // the subject/principal is whom the token is about
//        claims.setClaim("email","robert@rokube.de"); // additional claims/attributes about the subject can be added
//        List<String> groups = Arrays.asList("BMW", "Siemens", "UBIS");
//        claims.setStringListClaim("groups", groups); // multi-valued claims work too and will end up as a JSON array
//
//        // Symmetrically encrypt the claims
//        Key jweEncryptionKey = new AesKey(ByteUtil.randomBytes(16));
//        JsonWebEncryption jwe = new JsonWebEncryption();
//        jwe.setPayload(claims.toJson());
//        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
//        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
//        jwe.setKey(jweEncryptionKey);
//        String serializedJwe = null;
//        try {
//            serializedJwe = jwe.getCompactSerialization();
//        } catch (JoseException e) {
//            e.printStackTrace();
//        }
//
//
//
//        // A JWT is a JWS and/or a JWE with JSON claims as the payload.
//        // In this example it is a JWS so we create a JsonWebSignature object.
//        JsonWebSignature jws = new JsonWebSignature();
//
//        // The payload of the JWS is JSON content of the JWT Claims
//        jws.setPayload(serializedJwe);
//
//        // The JWT is signed using the private key
//        jws.setKey(rsaJsonWebKey.getPrivateKey());
//
//        // Set the Key ID (kid) header because it's just the polite thing to do.
//        // We only have one key in this example but a using a Key ID helps
//        // facilitate a smooth key rollover process
//        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
//
//        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
//        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
//
//        // Sign the JWS and produce the compact serialization or the complete JWT/JWS
//        // representation, which is a string consisting of three dot ('.') separated
//        // base64url-encoded parts in the form Header.Payload.Signature
//        // If you wanted to encrypt it, you can simply set this jwt as the payload
//        // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
//        String jwt = null;
//        try {
//            jwt = jws.getCompactSerialization();
//        } catch (JoseException e) {
//            e.printStackTrace();
//        }
//        return jwt;

    }

    public String computeSignature(UserDetails userDetails, long expires) {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(userDetails.getUsername()).append(":");
        signatureBuilder.append(expires).append(":");
        signatureBuilder.append(MAGIC_KEY);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }

    public String getUserNameFromToken(String authToken) {
        if (null == authToken) {
            return null;
        }
        String[] parts = authToken.split(":");
        return parts[0];
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];
        String signatureToMatch = computeSignature(userDetails, expires);
        return expires >= System.currentTimeMillis() && signature.equals(signatureToMatch);
    }

}
