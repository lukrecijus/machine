package com.kata.vending.domain;

import com.kata.vending.domain.coin.CoinType;

/*
 * Administrator interface to interact with the vending machine
 * Provides debug information about the current state of the vending machine
 */
public interface IVendingMachineAdmin {
    int getTotalCoinCount();
    int getSessionCoinCount();
    int getCoinReturnSize();
    boolean isAcceptedCoin(CoinType coinType);
}
