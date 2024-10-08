package com.kata.vending.domain.machine.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendingMachineProductCapacityConfiguration {
    public int colaCapacity;
    public int chipsCapacity;
    public int candyCapacity;
}
