package me.market.cart;

import me.market.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void addProducts(List<Product> products) {
        this.products.addAll(products);
    }

    public long getTotalPrice() {
        return products.stream()
                .mapToLong(Product::price)
                .sum();
    }

    public List<Product> takeOutAllProducts() {
        List<Product> products1 = new ArrayList<>(products);
        products.clear();
        return products1;
    }
}
