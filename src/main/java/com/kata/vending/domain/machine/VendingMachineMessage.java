package com.kata.vending.domain.machine;

import com.kata.vending.domain.coin.Money;
import com.kata.vending.domain.product.ProductType;

public class VendingMachineMessage {
    public static final String CassetteFull = "CASSETTE IS FULL, SORRY";
    public static final String InsertCoins = "INSERT COINS";
    public static final String ThankYou = "THANK YOU";
    public static String Credit(Money amount) {
        return "CREDIT: " + String.format("%.2f", amount.getValue().doubleValue()) + "$";
    }
    public static String ProductPrice(Money amount) {
        return "PRICE: " + String.format("%.2f", amount.getValue().doubleValue()) + "$";
    }
    public static String OutOfStock(ProductType productType) {
        return String.format("NO %s, SORRY", productType);
    }
}
