package com.changent.entities;

import com.changent.services.ProductServiceMockImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    @Test
    @DisplayName("constructs valid cart and verifies getters")
    void construct() {
        Cart result = new Cart(new ProductServiceMockImpl());

        assertInstanceOf(Cart.class, result);
    }

    @Test
    void constructionFailsWhenNullService() {
        assertThrows (NullPointerException.class, () -> new Cart(null));
    }

    @Test
    @DisplayName("add one product.")
    void addProductToCart() {
        final String givenProductName = "cheerios";
        final int givenQuantity = 1;
        final int expectItemsInCart = 1;

        Cart cart = new Cart(new ProductServiceMockImpl());
        cart.addProduct(givenProductName, givenQuantity);

        assertEquals(expectItemsInCart, cart.getCountForProduct(givenProductName));
        assertEquals(expectItemsInCart, cart.getTotalProductCount());
    }

    @Test
    @DisplayName("adding a product when it already exists returns summed total.")
    void addProductToCartTwice() {
        final String givenProductName = "cheerios";
        final int givenQuantity = 1;
        final int expectItemsInCart = 2;

        Cart cart = new Cart(new ProductServiceMockImpl());
        cart.addProduct(givenProductName, givenQuantity);
        cart.addProduct(givenProductName, givenQuantity);

        assertEquals(expectItemsInCart, cart.getCountForProduct(givenProductName));
        assertEquals(expectItemsInCart, cart.getTotalProductCount());
    }

    @Test
    @DisplayName("adding a product when it already exists returns summed total.")
    void addTwoProductsToCart() {
        final String givenFirstProduct = "cheerios";
        final String givenSecondProduct = "cornflakes";
        final int givenFirstQuantity = 1;
        final int givenSecondQuantity = 2;
        final int expectFirstProductInCart = 1;
        final int expectSecondProductInCart = 2;
        final int expectItemsInCart = 3;

        Cart cart = new Cart(new ProductServiceMockImpl());
        cart.addProduct(givenFirstProduct, givenFirstQuantity);
        cart.addProduct(givenSecondProduct, givenSecondQuantity);

        assertEquals(expectFirstProductInCart, cart.getCountForProduct(givenFirstProduct));
        assertEquals(expectSecondProductInCart, cart.getCountForProduct(givenSecondProduct));
        assertEquals(expectItemsInCart, cart.getTotalProductCount());
    }

    @Test
    @DisplayName("add item entirely from cart")
    void addItem() {
        final String givenProductName = "cheerios";
        final int givenQuantity = 2;
        final int expectItemsForProduct = 2;
        final int expectItemsInCart = 2;
        final Product givenProduct = new Product(givenProductName, 2.95);
        Cart cart = new Cart(new ProductServiceMockImpl());

        final Item givenItem = new Item(givenProduct, givenQuantity);

        cart.addItem(givenItem);

        assertEquals(expectItemsForProduct, cart.getCountForProduct(givenProductName));
        assertEquals(expectItemsInCart, cart.getTotalProductCount());
    }

    @Test
    @DisplayName("remove item entirely from cart")
    void removeItem() {
        final String givenProductName = "cheerios";
        final int givenQuantity = 3;
        final int expectItemsForProduct = 0;
        final int expectItemsInCart = 0;
        final Product givenProduct = new Product(givenProductName, 2.95);
        Cart cart = new Cart(new ProductServiceMockImpl());

        final Item givenItem = new Item(givenProduct, givenQuantity);

        cart.addProduct(givenProductName, givenQuantity);
        cart.removeItem(givenItem);

        assertEquals(expectItemsForProduct, cart.getCountForProduct(givenItem.title()));
        assertEquals(expectItemsInCart, cart.getTotalProductCount());
    }

    @Test
    @DisplayName("remove item entirely from cart")
    void removeItemQuantity() {
        final String givenProductName = "cheerios";
        final int givenQuantity = 3;
        final int givenReductionQuantity = 1;
        final int expectItemsForProduct = 2;
        final int expectItemsInCart = 2;
        final Product givenProduct = new Product(givenProductName, 2.95);
        Cart cart = new Cart(new ProductServiceMockImpl());

        final Item givenAddedItem = new Item(givenProduct, givenQuantity);
        final Item givenRemovedItem = new Item(givenProduct, givenReductionQuantity);

        cart.addItem(givenAddedItem);
        cart.removeItem(givenRemovedItem);

        assertEquals(expectItemsForProduct, cart.getCountForProduct(givenAddedItem.title()));
        assertEquals(expectItemsInCart, cart.getTotalProductCount());
    }

    @Test
    @DisplayName("reduce the product when it already exists returns summed total.")
    void reduceProductQuantity() {
        final String givenProductName = "cheerios";
        final int givenQuantity = 3;
        final int givenReductionQuantity = 1;
        final int expectItemsForProduct = 2;
        final int expectItemsInCart = 2;

        Cart cart = new Cart(new ProductServiceMockImpl());
        cart.addProduct(givenProductName, givenQuantity);
        cart.removeProduct(givenProductName, givenReductionQuantity);

        assertEquals(expectItemsForProduct, cart.getCountForProduct(givenProductName));
        assertEquals(expectItemsInCart, cart.getTotalProductCount());
    }

    @Test
    @DisplayName("reduce the product when it already exists returns summed total.")
    void reduceAllOfProduct() {
        final String givenProductName = "cheerios";
        final int givenQuantity = 3;
        final int givenReductionQuantity = 3;
        final int expectItemsForProduct = 0;
        final int expectItemsInCart = 0;

        Cart cart = new Cart(new ProductServiceMockImpl());
        cart.addProduct(givenProductName, givenQuantity);
        cart.removeProduct(givenProductName, givenReductionQuantity);

        assertEquals(expectItemsForProduct, cart.getCountForProduct(givenProductName));
        assertEquals(expectItemsInCart, cart.getTotalProductCount());
    }

    @Test
    @DisplayName("reduce the product quantity to 0 when subtracting more than is in cart.")
    void reduceByMoreThanProductQuantity() {
        final String givenProductName = "cheerios";
        final int givenQuantity = 3;
        final int givenReductionQuantity = 30;
        final int expectItemsForProduct = 0;
        final int expectItemsInCart = 0;

        Cart cart = new Cart(new ProductServiceMockImpl());
        cart.addProduct(givenProductName, givenQuantity);
        cart.removeProduct(givenProductName, givenReductionQuantity);

        assertEquals(expectItemsForProduct, cart.getCountForProduct(givenProductName));
        assertEquals(expectItemsInCart, cart.getTotalProductCount());
    }

    // remove from product not in cart
    @Test
    @DisplayName("reduce the product quantity to 0 when subtracting more than is in cart.")
    void reduceProductNotInCart() {
        final String givenExistingProduct = "cheerios";
        final String givenNonExistingProductName = "cornflakes";
        final int givenQuantity = 3;
        final int givenReductionQuantity = 30;
        final int expectItemsForProduct = 0;
        final int expectItemsInCart = 3;

        Cart cart = new Cart(new ProductServiceMockImpl());
        cart.addProduct(givenExistingProduct, givenQuantity);
        cart.removeProduct(givenNonExistingProductName, givenReductionQuantity);

        assertEquals(expectItemsForProduct, cart.getCountForProduct(givenNonExistingProductName));
        assertEquals(expectItemsInCart, cart.getTotalProductCount());
    }

    @Test
    void getSubtotal() {
        final String givenProductName = "cheerios";
        final int givenQuantity = 1;
        final double expectedSubtotal = 8.43;

        Cart cart = new Cart(new ProductServiceMockImpl());
        cart.addProduct(givenProductName, givenQuantity);

        assertEquals(expectedSubtotal, cart.getSubtotal().get().doubleValue());
    }

    @Test
    void getTaxPayable() {
        final String givenProductName = "cheerios";
        final int givenQuantity = 1;
        final double expectedTax = 1.05;

        Cart cart = new Cart(new ProductServiceMockImpl());
        cart.addProduct(givenProductName, givenQuantity);

        assertEquals(expectedTax, cart.getTaxPayable().get().doubleValue());
    }

    @Test
    void getTotalPayable() {
        final String givenProductName = "cheerios";
        final int givenQuantity = 1;
        final double expectedTotal = 9.48;

        Cart cart = new Cart(new ProductServiceMockImpl());
        cart.addProduct(givenProductName, givenQuantity);

        assertEquals(expectedTotal, cart.getTotalPayable().get().doubleValue());

    }

    @Test
    void confirmationTest() {
        Cart cart = new Cart(new ProductServiceMockImpl());
//  Add 1 × cornflakes @2.52 each
        cart.addProduct("cornflakes", 1);
//  Add another 1 x cornflakes @2.52 each
        cart.addProduct("cornflakes", 1);
//  Add 1 × weetabix @9.98 each
        cart.addProduct("weetabix", 1);
//
//  Then:
//
//  Cart contains 2 x conflakes
        assertEquals(2, cart.getCountForProduct("cornflakes"));
//  Cart contains 1 x weetabix
        assertEquals(1, cart.getCountForProduct("weetabix"));
//  Subtotal = 15.02
        assertEquals(15.02, cart.getSubtotal().get().doubleValue());
//  Tax = 1.88
        assertEquals(1.88, cart.getTaxPayable().get().doubleValue());
//  Total = 16.90
        assertEquals(16.90, cart.getTotalPayable().get().doubleValue());
    }
}