package com.kata.vending.domain.machine.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendingMachineCoinCapacityConfiguration{
    public int nickelCoinCapacity;
    public int dimeCoinCapacity;
    public int quarterCoinCapacity;
}
