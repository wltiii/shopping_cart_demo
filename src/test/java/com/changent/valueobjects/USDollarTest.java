package com.changent.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class USDollarTest {

    @Test
    @DisplayName("constructs valid USDollar from BigDecimal and verifies getters")
    void constructFromBigDecimal() {
        BigDecimal givenAmount = new BigDecimal("22.43");
        final USDollar result = new USDollar(givenAmount);

        assertEquals(givenAmount, result.get());
    }

    @Test
    @DisplayName("constructs valid USDollar from String and verifies getters")
    void constructFromString() {
        String givenAmountString = "22.43";
        BigDecimal givenAmount = new BigDecimal(givenAmountString);
        final USDollar result = new USDollar(givenAmountString);

        assertEquals(givenAmount, result.get());
    }

    @Test
    @DisplayName("constructs valid USDollar from double and verifies getters")
    void constructFromDouble() {
        double givenAmountDouble = 8.43;
        BigDecimal expectedAmount = new BigDecimal("8.43");
        final USDollar result = new USDollar(givenAmountDouble);

        assertEquals(expectedAmount, result.get());
    }

    @Test
    @DisplayName("fails construction when BigDecimal amount is null")
    void constructionFailsOnNullBigDecimalAmount() {
        BigDecimal givenAmount = null;

        Exception thrownByBlank = assertThrows(
                IllegalArgumentException.class,
                () -> new USDollar(givenAmount),
                "Expected constructor to throw on null BigDecimal."
        );

        assertEquals("Amount cannot be null.", thrownByBlank.getMessage());
    }

    @Test
    @DisplayName("fails construction when amount scale is invalid")
    void constructionFailsOnInvalidScale() {
        BigDecimal givenAmount = new BigDecimal("1.333");

        Exception thrownByBlank = assertThrows(
                IllegalArgumentException.class,
                () -> new USDollar(givenAmount),
                "Expected constructor to throw on invalid scale amount."
        );

        assertEquals("Number of decimals is 3. US Dollars can only have two.", thrownByBlank.getMessage());
    }

    @Test
    @DisplayName("fails construction when String amount is null")
    void constructionFailsOnNullStringAmount() {
        String givenAmount = null;

        Exception thrownByBlank = assertThrows(
                IllegalArgumentException.class,
                () -> new USDollar(givenAmount),
                "Expected constructor to throw on null String."
        );

        assertEquals("Amount cannot be null.", thrownByBlank.getMessage());
    }

    @Test
    @DisplayName("fails construction when String amount is empty")
    void constructionFailsOnEmptyStringAmount() {
        String givenAmount = "    ";

        assertThrows(
                NumberFormatException.class,
                () -> new USDollar(givenAmount),
                "Expected constructor to throw on empty String."
        );
    }

    @Test
    @DisplayName("fails construction when String amount is not a valid decimal string")
    void constructionFailsOnInvalidDecimalStringAmount() {
        String givenAmount = "e.z";

        assertThrows(
                NumberFormatException.class,
                () -> new USDollar(givenAmount),
                "Expected constructor to throw on empty String."
        );
    }

    @Test
    @DisplayName("returns rounded USDollar amount by multiplying this by the factor.")
    void multiplyWithBankersRounding() {
        String givenAmountString = "15.02";
        final USDollar result = new USDollar(givenAmountString);
        final USDollar expectedAmount = new USDollar("1.88");

        assertEquals(expectedAmount, result.multiplyUsingBankersRoundingBy(0.125));

    }

    @Test
    void testHashCode() {
        USDollar x = new USDollar("1.88");
        USDollar y = new USDollar("1.88");
        USDollar z = new USDollar("42.23");

        assertEquals(x.hashCode(), y.hashCode());
        assertNotEquals(x.hashCode(), z.hashCode());
    }
}