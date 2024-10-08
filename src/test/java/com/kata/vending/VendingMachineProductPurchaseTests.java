package com.kata.vending;

import com.kata.vending.domain.coin.Coin;
import com.kata.vending.domain.coin.CoinType;
import com.kata.vending.domain.machine.VendingMachine;
import com.kata.vending.domain.product.Product;
import com.kata.vending.domain.product.ProductType;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VendingMachineProductPurchaseTests {
    private final VendingMachine vendingMachine = new VendingMachine();

    @Test
    public void shouldRejectWhenCapacityExceeded() {
        var result = vendingMachine.purchaseProduct(ProductType.Cola);
        assertEquals(Optional.empty(), result);
        assertEquals("PRICE: 1.00$", vendingMachine.getDisplay());
        assertEquals("INSERT COINS", vendingMachine.getDisplay());
    }

    @Test
    public void shouldPurchaseWhenSufficientFunds() {
        Coin coin = new Coin(CoinType.DIME);
        Product product = new Product(ProductType.Cola);
        assertEquals(0.5, coin.getMoney().getValue().doubleValue());
        assertEquals(1.0, product.getMoney().getValue().doubleValue());
        vendingMachine.acceptCoin(coin);
        vendingMachine.acceptCoin(coin);
        var result = vendingMachine.purchaseProduct(ProductType.Cola);
        assertTrue(result.isPresent());
        assertEquals(result.get().getProductType(), ProductType.Cola);
        assertEquals("THANK YOU", vendingMachine.getDisplay());
        assertEquals("INSERT COINS", vendingMachine.getDisplay());
    }

    @Test
    public void shouldReturnChangeAfterPurchase() {
        Coin coin1 = new Coin(CoinType.DIME);
        Coin coin2 = new Coin(CoinType.DIME);
        Coin coin3 = new Coin(CoinType.DIME);
        Coin coin4 = new Coin(CoinType.NICKEL);
        assertEquals(0.5, coin1.getMoney().getValue().doubleValue());
        assertEquals(0.15, coin4.getMoney().getValue().doubleValue());
        Product product = new Product(ProductType.Cola);
        assertEquals(1.0, product.getMoney().getValue().doubleValue());
        vendingMachine.acceptCoin(coin1);
        vendingMachine.acceptCoin(coin2);
        vendingMachine.acceptCoin(coin3);
        vendingMachine.acceptCoin(coin4);
        var result = vendingMachine.purchaseProduct(ProductType.Cola);
        assertTrue(result.isPresent());
        assertEquals(result.get().getProductType(), ProductType.Cola);
        assertEquals(2, vendingMachine.getCoinReturnSize());
        var change = vendingMachine.collectChange();
        assertEquals(CoinType.DIME, change.get(0).getCoinType());
        assertEquals(CoinType.NICKEL, change.get(1).getCoinType());
    }
}