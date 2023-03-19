package com.changent.services;

import com.changent.entities.Product;

import java.util.Optional;

public interface ProductService {
    Optional<Product> getBy(String title);

}
