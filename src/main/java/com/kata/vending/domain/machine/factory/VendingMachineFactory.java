package com.kata.vending.domain.machine.factory;

import com.kata.vending.domain.IVendingMachineAdmin;
import com.kata.vending.domain.VendingMachineConfiguration;
import com.kata.vending.domain.coin.CoinType;
import com.kata.vending.domain.machine.VendingMachine;
import com.kata.vending.domain.product.ProductType;

public class VendingMachineFactory {
    public IVendingMachineAdmin CreateStandardVendingMachine() {
        return VendingMachine.getBuilder()
                .setCoinCapacity(CoinType.DIME, VendingMachineConfiguration.DIME_COIN_CAPACITY)
                .setCoinCapacity(CoinType.NICKEL, VendingMachineConfiguration.NICKEL_COIN_CAPACITY)
                .setCoinCapacity(CoinType.QUARTER, VendingMachineConfiguration.QUARTER_COIN_CAPACITY)
                .setProductCapacity(ProductType.Cola, VendingMachineConfiguration.COLA_CAPACITY)
                .setProductCapacity(ProductType.Candy, VendingMachineConfiguration.CANDY_CAPACITY)
                .setProductCapacity(ProductType.Chips, VendingMachineConfiguration.CHIPS_CAPACITY)
                .build();
    }
}
