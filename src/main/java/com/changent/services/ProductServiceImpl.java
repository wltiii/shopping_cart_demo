package com.changent.services;

import com.changent.entities.Product;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

public class ProductServiceImpl implements ProductService {
    @Override
    public Optional<Product> getBy(String productName) {
        try {
            URL url = new URL("https://equalexperts.github.io/backend-take-home-test-data/" + productName + ".json");

            Scanner scanner = new Scanner(url.openStream(), StandardCharsets.UTF_8);
            String json = scanner.useDelimiter("\\A").next();
            return Optional.of(Product.fromJson(json));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
