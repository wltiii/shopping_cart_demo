package com.changent.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

// TODO(wltiii): USDollar should have a method to add two USDollars. I see this as static
// method that returns a new USDollar
// TODO(wltiii): USDollar, in the real world, should extend/implement a Currency class. This
// then may need to know things like symbols. This could get quite interesting in itself, for
// you would want to know the type of currency by locale (most likely) and have some sort of
// currency converter.
public class USDollar {
    public USDollar(String amount) {
        this(validateState(amount));
    }

    public USDollar(BigDecimal amount) {
        validateState(amount);

        this.amount = amount;
    }

    public USDollar(double amount) {
        this(BigDecimal.valueOf(amount));
    }

    private final BigDecimal amount;

    public BigDecimal get() {
        return amount;
    }

    public USDollar multiplyUsingBankersRoundingBy(double amount){
        BigDecimal newAmount = this.amount.multiply(asBigDecimal(amount));
        newAmount = newAmount.setScale(2, RoundingMode.HALF_EVEN);
        return new USDollar(newAmount);
    }

    private static BigDecimal validateState(String amount){
        if( amount == null ) {
            throw new IllegalArgumentException("Amount cannot be null.");
        }

        return validateState(new BigDecimal(amount));
    }

    private static BigDecimal validateState(BigDecimal amount){
        if( amount == null ) {
            throw new IllegalArgumentException("Amount cannot be null.");
        }

        if ( amount.scale() > 2 ) {
            throw new IllegalArgumentException(
                    "Number of decimals is " + amount.scale() + ". US Dollars can only have two."
            );
        }

        return amount;
    }

    private BigDecimal asBigDecimal(double aDouble){
        String asString = Double.toString(aDouble);
        return new BigDecimal(asString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        USDollar usDollar = (USDollar) o;
        return Objects.equals(amount, usDollar.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
