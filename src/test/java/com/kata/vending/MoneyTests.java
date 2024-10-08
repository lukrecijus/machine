package com.kata.vending;

import org.junit.jupiter.api.Test;

import com.kata.vending.domain.coin.Money;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTests {

    @Test
    public void shouldBeEmpty() {
        assertTrue(new Money(0).isZero());
    }

    @Test
    public void shouldBeComparable() {
        assertEquals(new Money(1), new Money(1));
        assertNotEquals(new Money(2), new Money(3));
    }

    @Test
    public void shouldBePrintable() {
        assertEquals("Money(5.0)", new Money(5).toString());
    }
}
