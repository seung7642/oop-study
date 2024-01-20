package me.market.payment;

public class Card implements Payment {

    private final PaymentType type = PaymentType.CARD;
    private final long limit;
    private long accumulatedAmount;

    public Card() {
        this(1_000_000);
    }

    public Card(long limit) {
        this.limit = limit;
        this.accumulatedAmount = 0;
    }

    @Override
    public Boolean isType(PaymentType type) {
        return this.type == type;
    }

    @Override
    public long getAvailableAmounts() {
        return limit - accumulatedAmount;
    }

    @Override
    public void deduct(long price) {
        if (price < 0) {
            throw new IllegalArgumentException("금액이 유효하지 않습니다.");
        }
        if (price > getAvailableAmounts()) {
            throw new IllegalStateException("금액이 한도를 초과합니다.");
        }
        accumulatedAmount += price;
    }
}
