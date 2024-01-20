package me.market.payment;

public interface Payment {

    Boolean isType(PaymentType type);

    long getAvailableAmounts();

    void deduct(long price);
}
