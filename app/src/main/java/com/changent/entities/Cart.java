package com.changent.entities;

import com.changent.services.ProductService;
import com.changent.valueobjects.USDollar;

import java.util.*;

public class Cart {
    public Cart(ProductService productService) {
        Objects.requireNonNull(productService, "ProductService is invalid.");

        this.productService = productService;
    }

    private final ProductService productService;
    private final HashMap<String, Item> items = new HashMap<>();

    public void addItem(Item item) {
        addProduct(item.title(), item.quantity());
    }

    /**
     * Adds a product with the quantity.
     * If the quantity is zero, it is ignored.
     * If the product is not found, it is ignored.
     * If the product already exists in cart, the quantity is
     * added to it.
     */
    public void addProduct(String productName, int quantity) {
        if (quantity == 0) return;
        if (quantity < 0) throw new IllegalArgumentException("Cannot add item with negative quantity.");

        Optional<Product> serviceResult = productService.getBy(productName);

        if(serviceResult.isEmpty()) return;

        Item item = items.get(productName);

        if (item == null) {
            items.put(productName, new Item(serviceResult.get(), quantity));
            return;
        }

        Item updated = item.incrementAmountBy(quantity);
        items.put(productName, updated);
    }

    public void removeItem(Item item) {
        removeProduct(item.title(), item.quantity());
    }

    /**
     * Subtracts the quantity from the product.
     * If the quantity is zero, it is ignored.
     * If the product is not found, it is ignored.
     * If the product is not in the cart, it is ignored.
     * If subtracting reduces the quantity to less than
     * one, it is removed from the cart.
     */
    public void removeProduct(String productName, int quantity) {
        if (quantity == 0) return;
        if (quantity < 0) throw new IllegalArgumentException("Cannot remove item with a negative quantity.");

        Item item = items.get(productName);

        if (item == null) return;

        Item updated = item.decrementAmountBy(quantity);
        if (updated.quantity() == 0) {
            items.remove(productName);
            return;
        }

        items.put(productName, updated);
    }

    public int getTotalProductCount() {
        List<Item> valueList = new ArrayList<>(this.items.values());

        if (valueList.size() == 0) return 0;

        // TODO: could use a stream. Is it simpler? More efficient? I like it, though.
        // It would mean not reassigning the local variable `count`. A good thing.
        int count = 0;

        for (Item item : valueList) {
            count += item.quantity();
        }

        return count;
    }

    public int getCountForProduct(String productName) {
        Item item = items.get(productName);

        if (item == null) return 0;

        return item.quantity();
    }

    public USDollar getSubtotal() {
        List<Item> valueList = new ArrayList<>(this.items.values());

        if (valueList.size() == 0) return new USDollar("0.0");

        double subtotal = 0;

        // TODO: item probably should have a total price method. That is, imo, where the
        // logic should be -> more cohesive. It would also simplify the streaming, I think.
        // TODO: could use a stream. This is not as straightforward as it would be in getTotalProductCount()
        // It would mean not reassigning the local variable `subtotal`. A good thing.
        for (Item item : valueList) {
            subtotal +=
                    item.quantity()
                            * item.unitPrice();
        }

        return new USDollar(subtotal);
    }

    public USDollar getTaxPayable() {
        // TODO(wltiii): Make tax a named constant (allowing for retrieval based on locale of user - requires capturing user info!)
        return getSubtotal().multiplyUsingBankersRoundingBy(0.125);
    }

    public USDollar getTotalPayable() {
        // TODO(wltiii): this has higher level coupling than I would like, though replicating the logic of the two calls
        // seems overkill. A somewhat better approach would be to create two private methods.
        // Still, technically having the coupling, but as it is private is it better? I don't
        // think so.
        // TODO(wltiii): USDollar should have a method to add two USDollars. As written, this
        // is a "train wreck".
        return new USDollar(
                        getSubtotal().get().doubleValue() +
                                getTaxPayable().get().doubleValue()
        );
    }
}
