package com.kata.vending.domain.coin;

import com.kata.vending.domain.VendingMachineConfiguration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coin {
    private CoinType coinType;
    
    public Money getMoney() {
        return switch (coinType) {
            case DIME -> new Money(VendingMachineConfiguration.DIME_VALUE);
            case NICKEL -> new Money(VendingMachineConfiguration.NICKEL_VALUE);
            case QUARTER -> new Money(VendingMachineConfiguration.QUARTER_VALUE);
            case PENNY -> new Money(VendingMachineConfiguration.PENNY_VALUE);
            default -> throw new IllegalStateException("Unexpected value: " + coinType);
        };
    }
    
    public Coin(CoinType coinType) {
        this.coinType = coinType;
    }
}
