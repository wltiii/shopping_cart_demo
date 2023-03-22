package com.changent.entities;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.Objects;
import java.util.logging.Logger;


public final class Product {
    public Product(String title, Double unitPrice) {
        logger.info("Product(title: " + title + ", unitPrice: " + unitPrice);
        Objects.requireNonNull(title, "Title is invalid.");
        Objects.requireNonNull(unitPrice, "Price is invalid.");

        if (title.isBlank()) throw new IllegalArgumentException("Title is invalid.");
        if (isNegative(unitPrice)) throw new IllegalArgumentException("Price is invalid.");

        this.title = title;
        this.unitPrice = unitPrice;
    }

    private static final Logger logger = Logger.getLogger("Product");

    @Contract(pure = true)
    private static boolean isNegative(Double unitPrice) {
        return !(unitPrice.compareTo(0.0) >= 0);
    }

    private final String title;
    private final Double unitPrice;


    public String title() {
        return title;
    }

    public Double unitPrice() {
        return unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return title.equals(product.title) && unitPrice.equals(product.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, unitPrice);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }

    @Contract("_ -> new")
    public static @NotNull Product fromJson(String json) {
        StringReader stringReader = new StringReader(json);
        JsonReader reader = Json.createReader(new StringReader(json));

        JsonObject jsonObject = reader.readObject();

        reader.close();
        stringReader.close();

        String title = jsonObject.getString("title");
        double price = jsonObject.getJsonNumber("price").doubleValue();

        return new Product(title, price);
    }
}
