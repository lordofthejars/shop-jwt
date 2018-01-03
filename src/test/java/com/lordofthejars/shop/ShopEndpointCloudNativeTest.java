package com.lordofthejars.shop;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import java.io.IOException;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.Test;

public class ShopEndpointCloudNativeTest {

    public static final MediaType JSON
        = MediaType.parse("application/json; charset=utf-8");

    @Test
    public void should_add_products_to_cart() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Response response = addProductToCart(client, null, "1234");
        String cartToken = response.header("x-cart");
        printAllCart(cartToken);

        response = addProductToCart(client, cartToken, "5678");
        cartToken = response.header("x-cart");
        printAllCart(cartToken);

        response = addProductToCart(client, cartToken, "9012");
        cartToken = response.header("x-cart");
        printAllCart(cartToken);

        response = addProductToCart(client, cartToken, "3456");
        cartToken = response.header("x-cart");
        printAllCart(cartToken);

        response = addProductToCart(client, cartToken, "7890");
        cartToken = response.header("x-cart");
        printAllCart(cartToken);

    }

    private void printAllCart(String cartToken) {
        final Claim products = JWT.decode(cartToken).getClaim("products");
        final List<String> listOfProducts = products.asList(String.class);
        listOfProducts.stream().forEach(System.out::println);
        System.out.println("**********************");
    }

    private Response addProductToCart(OkHttpClient client, String cartToken, String productId) throws IOException {
        RequestBody body = RequestBody.create(JSON, "");
        final Request.Builder requestBuilder = new Request.Builder()
            .url(String.format("http://shop-myproject.192.168.64.21.nip.io/cart/%s", productId))
            .put(body);

        if (cartToken != null) {
            requestBuilder.header("x-cart", cartToken);
        }

        return client.newCall(requestBuilder.build()).execute();
    }
}
