package com.changent.services;

import com.changent.entities.Product;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceMockTest {

    @Test
    void getByOnMockImplementation() {
        ProductService service = new ProductServiceMockImpl();
        Product expected = new Product("Corn Flakes", Double.valueOf("2.52"));

        assertEquals(expected, service.getBy("cornflakes").get());
        assertEquals(Optional.empty(), service.getBy("invalid-product-title"));
    }
}