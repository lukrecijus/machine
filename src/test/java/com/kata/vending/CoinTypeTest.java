package com.kata.vending;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.kata.vending.domain.coin.CoinType;

class CoinTypeTest {

    @Test
    void shouldGetShortName() {
        assertEquals("d", CoinType.DIME.GetShortName());
        assertEquals("n", CoinType.NICKEL.GetShortName());
        assertEquals("p", CoinType.PENNY.GetShortName());
        assertEquals("q", CoinType.QUARTER.GetShortName());
    }

    @Test
    void shouldGetByShortName() {
        assertEquals(CoinType.DIME, CoinType.getByName("d"));
        assertEquals(CoinType.NICKEL, CoinType.getByName("n"));
        assertEquals(CoinType.PENNY, CoinType.getByName("p"));
        assertEquals(CoinType.QUARTER, CoinType.getByName("q"));
    }
}
