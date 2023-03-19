package com.changent.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    @DisplayName("constructs valid item and verifies getters")
    void construct() {
        final String givenTitle = "Cheerios";
        final Double givenPrice = 4.49;
        final Product givenProduct = new Product(
                givenTitle,
                givenPrice
        );
        final int givenQuantity = 3;

        Item item = new Item(
                givenProduct,
                givenQuantity
        );

        assertEquals(givenTitle, item.title());
        assertEquals(givenPrice, item.unitPrice());
        assertEquals(givenQuantity, item.quantity());
    }

    @Test
    @DisplayName("constructs when quantity is zero")
    void constructWhenQuantityIsZero() {
        final Product givenProduct = new Product(
                "Cheerios", 4.49
        );
        final int zeroQuantity = 0;

        Item item = new Item(
                givenProduct,
                zeroQuantity
        );

        assertEquals(zeroQuantity, item.quantity());
    }

    @Test
    @DisplayName("fails construction when quantity is negative")
    void constructThrowsOnNegativeQuantity() {
        final Product givenProduct = new Product(
                "Cheerios", 4.49
        );
        final int negativeQuantity = -1;

        Exception thrownByBlank = assertThrows(
                IllegalArgumentException.class,
                () -> new Item(
                        givenProduct,
                        negativeQuantity
                ),
                "Expected constructor to throw on negative quantity."
        );

        assertEquals(thrownByBlank.getMessage(),"Quantity is invalid." );
    }

    @Test
    @DisplayName("fails construction when product is null")
    void constructThrowsOnNullProduct() {
        final Product nullProduct = null;
        final int givenQuantity = 1;

        Exception thrownByBlank = assertThrows(
                NullPointerException.class,
                () -> new Item(
                        nullProduct,
                        givenQuantity
                ),
                "Expected constructor to throw on null product."
        );

        assertEquals(thrownByBlank.getMessage(),"Product is invalid.");
    }

    @Test
    @DisplayName("returns new instance of Item with incremented amount")
    void incrementAmountByValidAmount() {
        final Product givenProduct = new Product(
                "Cheerios",
                4.49
        );
        final int initialQuantity = 3;
        final int incrementAmount = 7;
        final int expectedQuantity = 10;

        Item item = new Item(
                givenProduct,
                initialQuantity
        );

        final Item result = item.incrementAmountBy(incrementAmount);

        assertNotEquals(item, result);
        assertEquals(result.title(), item.title());
        assertEquals(result.unitPrice(), item.unitPrice());
        assertEquals(result.quantity(), expectedQuantity);
    }

    @Test
    @DisplayName("incrementing by 0 returns same instance")
    void incrementAmountByZeroAmount() {
        final Product givenProduct = new Product(
                "Cheerios",
                4.49
        );
        final int initialQuantity = 3;
        final int incrementAmount = 0;

        Item item = new Item(
                givenProduct,
                initialQuantity
        );

        final Item result = item.incrementAmountBy(incrementAmount);

        assertEquals(item, result);
        assertSame(item, result);
    }


    @Test
    @DisplayName("throws exception if increment is less than 0")
    void incrementAmountByNegativeAmount() {
        final Product givenProduct = new Product(
                "Cheerios",
                4.49
        );
        final int initialQuantity = 3;
        final int negativeAmount = -1;

        Item item = new Item(
                givenProduct,
                initialQuantity
        );

        Exception thrownByBlank = assertThrows(
                IllegalArgumentException.class,
                () -> item.incrementAmountBy(negativeAmount),
                "Expected to throw on negative increment amount."
        );

        assertEquals(thrownByBlank.getMessage(),"Cannot increment by a negative amount. Use method decrementAmountBy() instead.");
    }

    @Test
    @DisplayName("returns new instance of Item with decremented amount")
    void decrementAmountByValidAmount() {
        final Product givenProduct = new Product(
                "Cheerios",
                4.49
        );
        final int initialQuantity = 3;
        final int decrementAmount = 2;
        final int expectedQuantity = 1;

        Item item = new Item(
                givenProduct,
                initialQuantity
        );

        final Item result = item.decrementAmountBy(decrementAmount);

        assertNotEquals(item, result);
        assertEquals(result.title(), item.title());
        assertEquals(result.unitPrice(), item.unitPrice());
        assertEquals(result.quantity(), expectedQuantity);
    }

    @Test
    @DisplayName("returns Item with zero on decrement greater than quanity")
    void decrementAmountByExcessAmount() {
        final Product givenProduct = new Product(
                "Cheerios",
                4.49
        );
        final int initialQuantity = 3;
        final int decrementAmount = 20;
        final int expectedQuantity = 0;

        Item item = new Item(
                givenProduct,
                initialQuantity
        );

        final Item result = item.decrementAmountBy(decrementAmount);

        assertNotEquals(item, result);
        assertEquals(result.title(), item.title());
        assertEquals(result.unitPrice(), item.unitPrice());
        assertEquals(result.quantity(), expectedQuantity);
    }

    @Test
    @DisplayName("decrementing by 0 returns same instance")
    void decrementAmountByZeroAmount() {
        final Product givenProduct = new Product(
                "Cheerios",
                4.49
        );
        final int initialQuantity = 3;
        final int decrementAmount = 0;

        Item item = new Item(
                givenProduct,
                initialQuantity
        );

        final Item result = item.decrementAmountBy(decrementAmount);

        assertEquals(item, result);
        assertSame(item, result);
    }


    @Test
    @DisplayName("throws exception if decrement is less than 0")
    void decrementAmountByNegativeAmount() {
        final Product givenProduct = new Product(
                "Cheerios",
                4.49
        );
        final int initialQuantity = 3;
        final int negativeAmount = -1;

        Item item = new Item(
                givenProduct,
                initialQuantity
        );

        Exception thrownByBlank = assertThrows(
                IllegalArgumentException.class,
                () -> item.decrementAmountBy(negativeAmount),
                "Expected to throw on negative decrement amount."
        );

        assertEquals(thrownByBlank.getMessage(),"Cannot decrement by a negative amount. Use method decrementAmountBy() instead.");
    }

    @Test
    void testEquals() {
        Item item1 = new Item(
                new Product(
                        "Cheerios", 4.49
                ),
                1
        );
        Item item2 = new Item(
                new Product(
                        "Cheerios", 4.49
                ),
                1
        );

        assertEquals(item1, item2);
        assertNotSame(item1, item2);
    }

    @Test
    void testHashCode() {
        Item x = new Item(
                new Product(
                        "Cheerios", 4.49
                ),
                1
        );
        Item y = new Item(
                new Product(
                        "Cheerios", 4.49
                ),
                1
        );
        Item z = new Item(
                new Product(
                        "Not Cheerios", 4.49
                ),
                1
        );

        assertEquals(x.hashCode(), y.hashCode());
        assertNotEquals(x.hashCode(), z.hashCode());
    }

    @Test
    void testToString() {
        Item item = new Item(
                new Product(
                        "Cheerios", 4.49
                ),
                1
        );

        assertEquals(item.toString(), "Item{product=Product{title='Cheerios', unitPrice=4.49}, quantity=1}");
    }

    @Test
    void fromJson() {
        /*
        {
                "title": "Cheerios",
                "price": 8.43,
                "quantity": 3
        }
         */
        String json = "{\"title\": \"Cheerios\",\"price\": 8.43, \"quantity\": 3}";

        final Item result = Item.fromJson(json);

        assertEquals(result.title(), "Cheerios");
        assertEquals(result.unitPrice(), 8.43);
        assertEquals(result.quantity(), 3);
    }

    @Test
    void toJson() {
        String expectedJson = "{\"title\":\"Cheerios\",\"price\":8.43,\"quantity\":3}";

        Item result = new Item(
                new Product(
                        "Cheerios", 8.43
                ),
                3
        );

        try {
            assertEquals(expectedJson, result.toJson());
        } catch (IOException e) {
            fail("test failed with exception ->" + e);
        }
    }
}