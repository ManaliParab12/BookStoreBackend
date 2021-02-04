package com.bridgelabz.onlinebookstore.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;


public class Token {
    private static String SECRET_KEY = "secret";
    
    public static String generateToken(int id) {
    	String token = null;
    	try {
    		Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    		token = JWT.create()
    				.withClaim("userId", id)
    				.sign(algorithm);
    	} catch(JWTCreationException exception) {
    		System.out.println("Exception while creating token" +exception.getMessage());
    	} return token;
    }
    
    public static int decodeToken(String token) {
    	
    	Verification verification = JWT.require(Algorithm.HMAC256(SECRET_KEY));
        JWTVerifier jwtverifier=verification.build();
        //to decode token
        DecodedJWT decodedjwt=jwtverifier.verify(token);
        //retrive data
        Claim claim=decodedjwt.getClaim("userId");
        int userId = claim.asInt();  
        return userId;
    	
    }
}
  

    
