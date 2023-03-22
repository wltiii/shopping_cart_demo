package com.changent.services;

import com.changent.entities.Product;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(WireMockExtension.class)
class ProductServiceIntTest {

    @Test
    @DisplayName("a successful endpoint call returns a Product")
    public void testGetByIsSuccessful() {
        String json = "{\n" +
                "  \"title\": \"Corn Flakes\",\n" +
                "  \"price\": 2.52\n" +
                "}";

        stubFor(get(urlEqualTo("/cornflakes.json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(json)));

        ProductService service = new ProductServiceImpl();
        Optional<Product> result = service.getBy("cornflakes");

        assertTrue(result.isPresent());
        assertEquals("Corn Flakes", result.get().title());
        assertEquals(2.52, result.get().unitPrice());
        // TODO(wltiii): validate number of calls!
    }

    @Test
    @DisplayName("a failing endpoint call returns an Optional")
    public void testGetByFails() {
        stubFor(get(urlEqualTo("/failure.json"))
                .willReturn(aResponse()
                        .withStatus(404)));

        ProductService service = new ProductServiceImpl();
        Optional<Product> result = service.getBy("failure");

        assertFalse(result.isPresent());
        assertTrue(result.isEmpty());
    }

}