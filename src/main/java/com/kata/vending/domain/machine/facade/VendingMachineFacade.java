package com.kata.vending.domain.machine.facade;

import com.kata.vending.domain.IVendingMachine;
import com.kata.vending.domain.IVendingMachineAdmin;
import com.kata.vending.domain.IVendorVendingMachine;
import com.kata.vending.domain.machine.VendingMachine;

public class VendingMachineFacade {
    private final VendingMachine vendingMachine = new VendingMachine();
    
    public IVendingMachine getVendingMachine() {
        return vendingMachine;
    }
    
    public IVendingMachineAdmin getVendingMachineAdmin() {
        return vendingMachine;
    }

    public IVendorVendingMachine getVendingMachineVendor() {
        return vendingMachine;
    }

    public static IVendorVendingMachine toVendorVendingMachine(VendingMachine vendingMachine) {
        return vendingMachine;
    }

    public static IVendingMachineAdmin toVendingMachineAdmin(VendingMachine vendingMachine) {
        return vendingMachine;
    }

    public static IVendingMachine toVendingMachine(VendingMachine vendingMachine) {
        return vendingMachine;
    }
}
