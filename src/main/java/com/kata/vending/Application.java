package com.kata.vending;

import com.kata.vending.domain.coin.Coin;
import com.kata.vending.domain.coin.CoinType;
import com.kata.vending.domain.machine.facade.VendingMachineFacade;

public class Application {
    public static void main(String[] args) {
        var vendingMachine = new VendingMachineFacade();
        var userVendingMachineInterface = vendingMachine.getVendingMachine();
        userVendingMachineInterface.acceptCoin(new Coin(CoinType.DIME));
        var adminVendingMachineInterface = vendingMachine.getVendingMachineAdmin();
        assert adminVendingMachineInterface.getSessionCoinCount() == 1;
    }
}
