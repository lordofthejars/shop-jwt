package com.lordofthejars.shop;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/cart")
public class ShopEndpoint {

    @Inject
    Algorithm algorithm;

    @Inject
    JWTVerifier jwtVerifier;

    @PUT
    @Path("{productId}")
    @Produces("application/json")
    public Response addToCart(@HeaderParam("x-cart") String token, @PathParam("productId") String productId) {

        List<String> currentProducts = new ArrayList<>();

        if (token != null) {
            final DecodedJWT verify = jwtVerifier.verify(token);
            final Claim products = verify.getClaim("products");
            currentProducts.addAll(products.asList(String.class));
        }

        try {
            currentProducts.add(productId + "@" + InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        final String newToken = JWT.create()
            .withIssuer(AlgorithmProducer.ISSUER)
            .withArrayClaim("products", currentProducts.toArray(new String[currentProducts.size()]))
            .sign(algorithm);
        return Response.ok().header("x-cart", newToken).build();
    }
}