package com.kata.vending;

import com.kata.vending.domain.coin.Coin;
import com.kata.vending.domain.coin.CoinType;
import com.kata.vending.domain.machine.VendingMachine;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineAcceptCoinTests {
    private final VendingMachine vendingMachine = new VendingMachine();

    @Test
    public void shouldDisplayInitialDisplay() {
        assertEquals("INSERT COINS", vendingMachine.getDisplay());
    }
    
    @Test
    public void shouldNotChangeDisplayWhenCoinRejected() {
        var coin = new Coin(CoinType.PENNY);
        assertFalse(vendingMachine.isAcceptedCoin(CoinType.PENNY));
        vendingMachine.acceptCoin(coin);
        assertEquals("INSERT COINS", vendingMachine.getDisplay());
    }
    
    @Test
    public void rejectedCoinShouldBePlacedInCoinReturn() {
        var coin = new Coin(CoinType.PENNY);
        assertFalse(vendingMachine.isAcceptedCoin(CoinType.PENNY));
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(0, vendingMachine.getSessionCoinCount());
        vendingMachine.acceptCoin(coin);
        assertEquals(1, vendingMachine.getCoinReturnSize());
    }
    
    @Test
    public void rejectedCoinShouldBeCollected() {
        var coin = new Coin(CoinType.PENNY);
        assertFalse(vendingMachine.isAcceptedCoin(CoinType.PENNY));
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(0, vendingMachine.getSessionCoinCount());
        vendingMachine.acceptCoin(coin);
        assertEquals(1, vendingMachine.getCoinReturnSize());
        List<Coin> change = vendingMachine.collectChange();
        assertArrayEquals(new Coin[]{coin}, change.toArray());
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(0, vendingMachine.getSessionCoinCount());
    }

    @Test
    public void shouldAcceptAcceptableCoin() {
        var coin = new Coin(CoinType.NICKEL);
        assertTrue(vendingMachine.isAcceptedCoin(CoinType.NICKEL));
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(0, vendingMachine.getSessionCoinCount());
        vendingMachine.acceptCoin(coin);
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(1, vendingMachine.getSessionCoinCount());
    }

    @Test
    public void shouldReturnAcceptedCoinOnReturnRequest() {
        var coin = new Coin(CoinType.NICKEL);
        assertTrue(vendingMachine.isAcceptedCoin(CoinType.NICKEL));
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(0, vendingMachine.getSessionCoinCount());
        vendingMachine.acceptCoin(coin);
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(1, vendingMachine.getSessionCoinCount());
        vendingMachine.collectChange();
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(0, vendingMachine.getSessionCoinCount());
    }

    @Test
    public void shouldUpdateDisplayAfterAcceptCoin() {
        var coin = new Coin(CoinType.NICKEL);
        assertTrue(vendingMachine.isAcceptedCoin(CoinType.NICKEL));
        vendingMachine.acceptCoin(coin);
        assertEquals(0.15, coin.getMoney().getValue().doubleValue());
        assertEquals("CREDIT: 0.15$", vendingMachine.getDisplay());
    }
    
    @Test
    public void shouldChangeDisplayAfterReturn() {
        var coin = new Coin(CoinType.NICKEL);
        assertTrue(vendingMachine.isAcceptedCoin(CoinType.NICKEL));
        vendingMachine.acceptCoin(coin);
        vendingMachine.collectChange();
        assertEquals("INSERT COINS", vendingMachine.getDisplay());
    }
    
    @Test
    public void shouldAcceptMultipleCoins() {
        var coin1 = new Coin(CoinType.NICKEL);
        var coin2 = new Coin(CoinType.NICKEL);
        assertEquals(0.15, coin1.getMoney().getValue().doubleValue());
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(0, vendingMachine.getSessionCoinCount());
        vendingMachine.acceptCoin(coin1);
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(1, vendingMachine.getSessionCoinCount());
        vendingMachine.acceptCoin(coin2);
        assertEquals(0, vendingMachine.getCoinReturnSize());
        assertEquals(0, vendingMachine.getTotalCoinCount());
        assertEquals(2, vendingMachine.getSessionCoinCount());
        assertEquals("CREDIT: 0.30$", vendingMachine.getDisplay());
    }
}
