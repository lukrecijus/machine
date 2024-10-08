package com.kata.vending.domain.coin;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Money implements Comparable<Money> {
    private final BigDecimal value;

    public Money(double value) {
        this.value = BigDecimal.valueOf(value);
    }

    public String format() {
        return value.toString();
    }

    public boolean isZero() {
        return this.equals(new Money(0));
    }

    @Override
    public String toString() {
        return "Money(" + value + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Money)) {
            return false;
        }

        return value.compareTo(((Money) object).getValue()) == 0;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Money o) {
        return value.compareTo(o.getValue());
    }
}
