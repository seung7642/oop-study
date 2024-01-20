package me.market;

import me.market.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class CustomerTest {

    @DisplayName("장바구니에 상품을 담는다.")
    @Test
    void putInCart() {
        // given
        Customer customer = new Customer();
        Product product1 = new Product("콜라", 1000);
        Product product2 = new Product("사이다", 1000);
        Product product3 = new Product("새우깡", 1000);

        // when
        customer.putInCart(List.of(product1, product2, product3));

        // then
        Assertions.assertThat(customer.getAllInCart())
                .hasSize(3)
                .extracting(Product::name, Product::price)
                .containsExactlyInAnyOrder(
                        Assertions.tuple("콜라", 1000),
                        Assertions.tuple("사이다", 1000),
                        Assertions.tuple("새우깡", 1000)
                );
    }

    @DisplayName("장바구니에 상품이 없으면 예외가 발생한다.")
    @Test
    void buyWithEmptyCart() {
        // given
        Customer customer = new Customer();

        // when / then
        Assertions.assertThatThrownBy(customer::buy)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("카트가 비어있습니다.");
    }

    @DisplayName("결제를 요청한다.")
    @Test
    void buy() {
        // given
        Customer customer = new Customer();
        customer.putInCart(List.of(new Product("콜라", 1000), new Product("사이다", 2000)));

        // when
        customer.buy();

        // then
        Assertions.assertThat(customer.getAllInInventory())
                .hasSize(2)
                .extracting(Product::name, Product::price)
                .containsExactlyInAnyOrder(
                        Assertions.tuple("콜라", 1000),
                        Assertions.tuple("사이다", 2000)
                );
    }
}