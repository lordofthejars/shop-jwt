package com.lordofthejars.shop;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class JwtVerifierProducer {

    @Inject
    Algorithm algorithm;

    @Produces
    public JWTVerifier createJwtVerifier() {
        return JWT.require(algorithm)
            .withIssuer(AlgorithmProducer.ISSUER)
            .build();
    }

}
