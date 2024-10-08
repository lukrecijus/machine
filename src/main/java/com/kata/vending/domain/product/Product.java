package com.kata.vending.domain.product;

import com.kata.vending.domain.coin.Money;
import com.kata.vending.domain.VendingMachineConfiguration;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private ProductType productType;

    public Money getMoney() {
        return switch (productType) {
            case Cola -> new Money(VendingMachineConfiguration.COLA_PRICE);
            case Candy -> new Money(VendingMachineConfiguration.CANDY_PRICE);
            case Chips -> new Money(VendingMachineConfiguration.CHIPS_PRICE);
            default -> throw new IllegalStateException("Unexpected value: " + productType);
        };
    }
    
    public Product(ProductType productType) {
        this.productType = productType;
    }
}