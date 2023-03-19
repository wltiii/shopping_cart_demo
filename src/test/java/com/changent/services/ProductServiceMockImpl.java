package com.changent.services;

import com.changent.entities.Product;

import java.util.Map;
import java.util.Optional;

public class ProductServiceMockImpl  implements ProductService {

    public ProductServiceMockImpl() {}

    @Override
    public Optional<Product> getBy(String title) {
        return Optional.ofNullable(productMap.get(title));
    }

    Map<String, Product> productMap = Map.of(
            "cheerios", new Product("Cheerios", Double.valueOf("8.43")),
            "cornflakes", new Product("Corn Flakes", Double.valueOf("2.52")),
            "frosties", new Product("Frosties", Double.valueOf("4.99")),
            "shreddies", new Product("Shreddies", Double.valueOf("4.68")),
            "weetabix", new Product("Weetabix", Double.valueOf("9.98"))
    );

}
