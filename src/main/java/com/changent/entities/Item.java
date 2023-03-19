package com.changent.entities;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;

public final class Item {

    public Item(Product product, int quantity) {
        Objects.requireNonNull(product, "Product is invalid.");
        if (isNegative(quantity)) throw new IllegalArgumentException("Quantity is invalid.");

        this.product = product;
        this.quantity = quantity;
    }

    private final Product product;
    private final int quantity;

    public String title() {
        return product.title();
    }

    public Double unitPrice() {
        return product.unitPrice();
    }

    public int quantity() {
        return quantity;
    }

    @Contract(value = "_ -> new", pure = true)
    public @NotNull Item incrementAmountBy(int incrementAmount) {
        if (isNegative(incrementAmount))
            throw new IllegalArgumentException("Cannot increment by a negative amount. Use method decrementAmountBy() instead.");

        if (incrementAmount == 0) return this;

        return new Item(
                this.product,
                (this.quantity + incrementAmount)
        );
    }

    @Contract(value = "_ -> new", pure = true)
    public @NotNull Item decrementAmountBy(int decrementAmount) {
        if (isNegative(decrementAmount))
            throw new IllegalArgumentException("Cannot decrement by a negative amount. Use method decrementAmountBy() instead.");

        if (decrementAmount == 0) return this;

        int updateTo = this.quantity - decrementAmount;
        if (updateTo < 0) {
            updateTo = 0;
        }
        return new Item(
                this.product,
                updateTo
        );
    }

    @Contract(pure = true)
    private static boolean isNegative(int quantity) {
        return quantity < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return quantity == item.quantity && Objects.equals(product, item.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "Item{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }

    @Contract("_ -> new")
    public static @NotNull Item fromJson(String json) {
        StringReader stringReader = new StringReader(json);
        JsonReader reader = Json.createReader(new StringReader(json));

        JsonObject jsonObject = reader.readObject();

        reader.close();
        stringReader.close();

        String title = jsonObject.getString("title");
        double price = jsonObject.getJsonNumber("price").doubleValue();
        int quantity = jsonObject.getJsonNumber("quantity").intValue();

        return new Item(new Product(title, price), quantity);
    }

    public String toJson() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(stringWriter);

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("title", title())
                .add("price", unitPrice())
                .add("quantity", quantity())
                .build();

        jsonWriter.writeObject(jsonObject);
        jsonWriter.close();
        String json = stringWriter.toString();
        stringWriter.close();
        return json;
    }
}
