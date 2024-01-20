package me.market;

import me.market.cart.Cart;
import me.market.payment.Card;
import me.market.payment.Payment;
import me.market.product.Product;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private final Owner owner;
    private final Cart cart;
    private final Payment payment;
    private final List<Product> inventory;

    public Customer() {
        this(new Card());
    }

    public Customer(Payment payment) {
        this.owner = new Owner();
        this.cart = new Cart();
        this.payment = payment;
        this.inventory = new ArrayList<>();
    }

    public List<Product> getAllInCart() {
        return new ArrayList<>(cart.getProducts());
    }

    public List<Product> getAllInInventory() {
        return new ArrayList<>(inventory);
    }

    public void putInCart(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("상품이 없습니다.");
        }
        cart.addProducts(products);
    }

    public void buy() {
        if (cart.isEmpty()) {
            throw new IllegalStateException("카트가 비어있습니다.");
        }
        inventory.addAll(owner.pay(cart, payment));
    }
}
