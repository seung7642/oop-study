package me.market;

import me.market.cart.Cart;
import me.market.payment.Payment;
import me.market.payment.PaymentType;
import me.market.product.Product;

import java.util.List;

public class Owner {

    private final List<PaymentType> validPayments;

    public Owner() {
        this(List.of(PaymentType.CARD));
    }

    public Owner(List<PaymentType> validPayments) {
        this.validPayments = validPayments;
    }

    public List<Product> pay(Cart cart, Payment payment) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalStateException("카트가 비어있습니다.");
        }
        if (payment == null) {
            throw new IllegalArgumentException("결제수단이 없습니다.");
        }
        if (!isValidPayment(payment)) {
            throw new IllegalArgumentException("결제수단이 유효하지 않습니다.");
        }

        payment.deduct(cart.getTotalPrice());
        return cart.takeOutAllProducts();
    }

    private boolean isValidPayment(Payment payment) {
        return validPayments.stream()
                .anyMatch(payment::isType);
    }

}
