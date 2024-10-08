package com.kata.vending;

import com.kata.vending.domain.coin.CoinType;
import com.kata.vending.domain.coin.Money;
import com.kata.vending.domain.machine.ChangeCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeCalculatorTests {
    @Test
    public void shouldReturnForEqualChange() {
        assertEquals(0.25, CoinType.GetCoinValue(CoinType.QUARTER).getValue().doubleValue());
        var result = ChangeCalculator.calculateChange(new Money(0.25));
        assertFalse(result.isEmpty());
        assertEquals(1, result.get(CoinType.QUARTER));
    }

    @Test
    public void shouldReturnForNonEqualChange() {
        assertEquals(0.25, CoinType.GetCoinValue(CoinType.QUARTER).getValue().doubleValue());
        assertEquals(0.50, CoinType.GetCoinValue(CoinType.DIME).getValue().doubleValue());
        var result = ChangeCalculator.calculateChange(new Money(0.75));
        assertFalse(result.isEmpty());
        assertEquals(1, result.get(CoinType.QUARTER));
        assertEquals(1, result.get(CoinType.DIME));
    }
}
