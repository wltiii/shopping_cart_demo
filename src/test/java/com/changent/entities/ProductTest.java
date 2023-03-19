package com.changent.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {
    @Test
    @DisplayName("constructs valid product and verifies getters")
    void construct() {
        final String givenTitle = "aTitle";
        final Double givenPrice = 4.49;
        Product product = new Product(givenTitle, givenPrice);
        assertEquals(givenTitle, product.title());
        assertEquals(givenPrice, product.unitPrice());
    }

    @Test
    @DisplayName("constructs free product")
    void constructWithZeroPrice() {
        final String givenTitle = "aTitle";
        final Double givenPrice = 0.00;
        Product product = new Product(givenTitle, givenPrice);
        assertEquals(givenTitle, product.title());
        assertEquals(givenPrice, product.unitPrice());
    }

    @Test
    @DisplayName("construct fails when title is null")
    void constructThrowsOnNullTitle() {
        final String nullTitle = null;
        final Double givenPrice = 1.00;

        Exception thrownByNull = assertThrows(
                NullPointerException.class,
                () -> new Product(nullTitle, givenPrice),
                "Expected constructor to throw on null title."
        );

        assertEquals(thrownByNull.getMessage(),"Title is invalid.");
    }

    @Test
    @DisplayName("construct fails when title is blank")
    void constructThrowsOnBlankTitle() {
        final String blankTitle = "    ";
        final Double givenPrice = 1.00;

        Exception thrownByBlank = assertThrows(
                IllegalArgumentException.class,
                () -> new Product(blankTitle, givenPrice),
                "Expected constructor to throw on blank title."
        );

        assertEquals(thrownByBlank.getMessage(),"Title is invalid.");
    }

    @Test
    @DisplayName("construct fails when price is null")
    void constructThrowsOnNullPrice() {
        final String givenTitle = "aTitle";
        final Double givenNullPrice = null;

        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> new Product(givenTitle, givenNullPrice),
                "Expected constructor to throw on invalid price."
        );

        assertEquals(thrown.getMessage(),"Price is invalid.");
    }

    @Test
    @DisplayName("construct fails when price is negative")
    void constructThrowsOnNegativePrice() {
        final String givenTitle = "aTitle";
        final Double givenNegativePrice = -1.00;

        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Product(givenTitle, givenNegativePrice),
                "Expected constructor to throw on invalid price."
        );

        assertEquals(thrown.getMessage(),"Price is invalid.");
    }

    @Test
    @DisplayName("constructs from JSON")
    void constructFromJson() {
        String json = "{\"title\": \"Cheerios\",\"price\": 8.43}";

        final Product result = Product.fromJson(json);

        assertEquals(result.title(), "Cheerios");
        assertEquals(result.unitPrice(), 8.43);
    }

//    @Test
//    @DisplayName("converts product to JSON") {
//        StringWriter stringWriter = new StringWriter();
//        JsonWriter jsonWriter = Json.createWriter(stringWriter);
//        jsonWriter.writeObject(jsonObject);
//        jsonWriter.close();
//        String json = stringWriter.toString();
//    }
}