package com.kata.vending;

import com.kata.vending.domain.coin.Coin;
import com.kata.vending.domain.machine.CoinCassetteCapacity;
import com.kata.vending.domain.coin.CoinType;
import com.kata.vending.domain.machine.VendingMachine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendingMachineCoinCapacityTests {
    private final VendingMachine vendingMachine = new VendingMachine();

    @Test
    public void shouldRejectWhenCapacityExceeded() {
        assertEquals(2, CoinCassetteCapacity.getCoinCapacity(CoinType.QUARTER));
        var coin1 = new Coin(CoinType.QUARTER);
        var coin2 = new Coin(CoinType.QUARTER);
        var coin3 = new Coin(CoinType.QUARTER);
        vendingMachine.acceptCoin(coin1);
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(1, vendingMachine.getSessionCoinCount());
        vendingMachine.acceptCoin(coin2);
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(2, vendingMachine.getSessionCoinCount());
        vendingMachine.acceptCoin(coin3);
        assertEquals(1, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(2, vendingMachine.getSessionCoinCount());
    }

    @Test
    public void shouldDisplayWhenCapacityExceeded() {
        assertEquals(2, CoinCassetteCapacity.getCoinCapacity(CoinType.QUARTER));
        var coin1 = new Coin(CoinType.QUARTER);
        var coin2 = new Coin(CoinType.QUARTER);
        var coin3 = new Coin(CoinType.QUARTER);
        assertEquals(0.25, coin1.getMoney().getValue().doubleValue());
        vendingMachine.acceptCoin(coin1);
        vendingMachine.acceptCoin(coin2);
        vendingMachine.acceptCoin(coin3);
        assertEquals("CASSETTE IS FULL, SORRY", vendingMachine.getDisplay());
        assertEquals("CREDIT: 0.50$", vendingMachine.getDisplay());
    }
}
