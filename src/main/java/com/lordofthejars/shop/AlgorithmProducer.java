package com.lordofthejars.shop;

import com.auth0.jwt.algorithms.Algorithm;
import java.io.UnsupportedEncodingException;
import javax.enterprise.inject.Produces;

public class AlgorithmProducer {

    //Don't do that on production
    private static final String SECRET = "my_super_secret";

    static final String ISSUER = "shop";

    @Produces
    public Algorithm createAlgorithm() {
        try {
            return Algorithm.HMAC256(SECRET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
