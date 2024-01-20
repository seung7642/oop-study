package me.market.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardTest {

    @DisplayName("사용 가능한 금액을 구한다. (기본 한도 1_000,000원)")
    @Test
    void getAvailableAmountsWithDefaultLimit() {
        // given
        Card card = new Card();

        // when / then
        Assertions.assertThat(card.getAvailableAmounts()).isEqualTo(1_000_000);
    }

    @DisplayName("사용 가능한 금액을 구한다.")
    @Test
    void getAvailableAmounts() {
        // given
        Card card = new Card(500_000);
        card.deduct(100_000);

        // when / then
        Assertions.assertThat(card.getAvailableAmounts()).isEqualTo(400_000);
    }

    @DisplayName("유효하지 않은 금액 (0원 미만) 을 차감하면 예외가 발생한다. ")
    @Test
    void deductWithInvalidPrice() {
        // given
        Card card = new Card();

        // when / then
        Assertions.assertThatThrownBy(() -> card.deduct(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("금액이 유효하지 않습니다.");
    }

    @DisplayName("한도를 초과하는 금액을 차감하면 예외가 발생한다.")
    @Test
    void deductWithOverLimit() {
        // given
        Card card = new Card(500_000);

        // when / then
        Assertions.assertThatThrownBy(() -> card.deduct(600_000))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("금액이 한도를 초과합니다.");
    }

    @DisplayName("사용 금액을 누적한다.")
    @Test
    void deduct() {
        // given
        Card card = new Card(500_000);

        // when
        card.deduct(100_000);

        // then
        Assertions.assertThat(card.getAvailableAmounts()).isEqualTo(400_000);
    }
}