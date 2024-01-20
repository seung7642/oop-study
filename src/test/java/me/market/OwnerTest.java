package me.market;

import me.market.cart.Cart;
import me.market.payment.Card;
import me.market.payment.PaymentType;
import me.market.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class OwnerTest {

    @DisplayName("빈 장바구니로 결제를 요청하면 예외가 발생한다.")
    @Test
    void payWithEmptyCart() {
        // given
        Owner owner = new Owner();

        // when / then
        Assertions.assertThatThrownBy(() -> owner.pay(new Cart(), new Card()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("카트가 비어있습니다.");
    }

    @DisplayName("결제 수단이 없는 상태로 결제를 요청하면 예외가 발생한다.")
    @Test
    void payWithNoPayment() {
        // given
        Owner owner = new Owner();
        Cart cart = new Cart();
        cart.addProducts(List.of(new Product("콜라", 1000)));

        // when / then
        Assertions.assertThatThrownBy(() -> owner.pay(cart, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("결제수단이 없습니다.");
    }

    @DisplayName("유효하지 않은 결제 수단으로 결제를 요청하면 예외가 발생한다.")
    @Test
    void payWithInvalidPayment() {
        // given
        Owner owner = new Owner(List.of(PaymentType.CASH));
        Cart cart = new Cart();
        cart.addProducts(List.of(new Product("콜라", 1000)));

        // when / then
        Assertions.assertThatThrownBy(() -> owner.pay(cart, new Card()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("결제수단이 유효하지 않습니다.");
    }

    @DisplayName("결제 후 장바구니에 담긴 상품들을 꺼낸다.")
    @Test
    void pay() {
        // given
        Owner owner = new Owner();
        Cart cart = new Cart();
        cart.addProducts(List.of(new Product("콜라", 1000), new Product("사이다", 2000)));

        // when
        List<Product> products = owner.pay(cart, new Card());

        // then
        Assertions.assertThat(products)
                .hasSize(2)
                .extracting(Product::name, Product::price)
                .containsExactlyInAnyOrder(
                        Assertions.tuple("콜라", 1000),
                        Assertions.tuple("사이다", 2000)
                );
    }
}