package com.kata.vending.domain;

import com.kata.vending.domain.coin.Coin;
import com.kata.vending.domain.product.Product;
import com.kata.vending.domain.product.ProductType;

import java.util.List;
import java.util.Optional;

/*
 * Vending machine for purchasing products using coins
 */
public interface IVendingMachine {
    /*
     * Insert coin into a machine, it increases amount for current purchase session
     */
    void acceptCoin(Coin coin);
    /*
     * Get change after purchase is made if current balance is higher than the price of the product
     */
    List<Coin> collectChange();
    /*
     * Get information about current state of the session of interaction with the vending machine
     */
    String getDisplay();
    /*
     * Purchase a selected product from the machine
     */
    Optional<Product> purchaseProduct(final ProductType productType);
    /*
     * Gets the remaining stock of a product, before it needs to be refilled by a vendor
     */
    int getRemainingStock(final ProductType productType);
}
