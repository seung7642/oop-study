package me.market.cart;

import me.market.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class CartTest {

    @DisplayName("장바구니에 상품을 추가한다.")
    @Test
    void addProducts() {
        // given
        Cart cart = new Cart();
        Product product1 = new Product("콜라", 1000);
        Product product2 = new Product("사이다", 2000);
        Product product3 = new Product("새우깡", 3000);

        // when
        cart.addProducts(List.of(product1, product2, product3));

        // then
        Assertions.assertThat(cart.getProducts())
                .hasSize(3)
                .extracting(Product::name, Product::price)
                .containsExactlyInAnyOrder(
                        Assertions.tuple("콜라", 1000),
                        Assertions.tuple("사이다", 2000),
                        Assertions.tuple("새우깡", 3000)
                );
    }

    @DisplayName("장바구니에 담긴 전체 상품들의 가격을 구한다.")
    @Test
    void getTotalPrice() {
        // given
        Cart cart = new Cart();
        Product product1 = new Product("콜라", 1000);
        Product product2 = new Product("사이다", 2000);
        Product product3 = new Product("새우깡", 3000);
        cart.addProducts(List.of(product1, product2, product3));

        // when / then
        Assertions.assertThat(cart.getTotalPrice()).isEqualTo(6000);
    }

    @DisplayName("장바구니에 담긴 전체 상품들을 꺼낸다.")
    @Test
    void takeOutAllProducts() {
        // given
        Cart cart = new Cart();
        Product product1 = new Product("콜라", 1000);
        Product product2 = new Product("사이다", 2000);
        Product product3 = new Product("새우깡", 3000);
        cart.addProducts(List.of(product1, product2, product3));

        // when / then
        Assertions.assertThat(cart.takeOutAllProducts())
                .hasSize(3)
                .extracting(Product::name, Product::price)
                .containsExactlyInAnyOrder(
                        Assertions.tuple("콜라", 1000),
                        Assertions.tuple("사이다", 2000),
                        Assertions.tuple("새우깡", 3000)
                );
        Assertions.assertThat(cart.getProducts()).isEmpty();
    }
}