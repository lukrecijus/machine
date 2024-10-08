package com.kata.vending;

import com.kata.vending.domain.coin.Coin;
import com.kata.vending.domain.coin.CoinType;
import com.kata.vending.domain.machine.facade.VendingMachineFacade;
import com.kata.vending.domain.machine.VendingMachine;
import com.kata.vending.domain.machine.VendingMachineStorage;
import com.kata.vending.domain.product.ProductType;
import com.kata.vending.infrastructure.email.EmailServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendingMachineProductStockTests {
    private final VendingMachine vendingMachine = new VendingMachine(new EmailServiceImpl());

    @Test
    public void shouldBeFilledToFullCapacityInitially() {
        var result = vendingMachine.getRemainingStock(ProductType.Cola);
        var capacity = VendingMachineStorage.getTotalCapacity(ProductType.Cola);
        assertEquals(5, result);
        assertEquals(5, capacity);
    }

    @Test
    public void shouldPreventPurchaseWhenNoStockAvailable() {
        var capacity = VendingMachineStorage.getTotalCapacity(ProductType.Candy);
        var result = vendingMachine.purchaseProduct(ProductType.Candy);
        assertEquals(Optional.empty(), result);
        assertEquals(vendingMachine.getDisplay(), "NO Candy, SORRY");
        assertEquals(0, capacity);
    }

    @Test
    public void shouldReduceAndRefillStock() {
        var capacity = VendingMachineStorage.getTotalCapacity(ProductType.Cola);
        assertEquals(5, capacity);
        var coin = new Coin(CoinType.DIME);
        for (var i = 0; i < 10; i++) {
            vendingMachine.acceptCoin(coin);
        }
        var display = vendingMachine.getDisplay();
        assertEquals("CREDIT: 5.00$", display);
        assertEquals(vendingMachine.getRemainingStock(ProductType.Cola), 5);
        vendingMachine.purchaseProduct(ProductType.Cola);
        vendingMachine.purchaseProduct(ProductType.Cola);
        vendingMachine.purchaseProduct(ProductType.Cola);
        vendingMachine.purchaseProduct(ProductType.Cola);
        vendingMachine.purchaseProduct(ProductType.Cola);
        assertEquals(0, vendingMachine.getRemainingStock(ProductType.Cola));
        var result = vendingMachine.purchaseProduct(ProductType.Cola);
        assertEquals(Optional.empty(), result);
        assertEquals(vendingMachine.getDisplay(), "NO Cola, SORRY");
        var vendorVendingMachine = VendingMachineFacade.toVendorVendingMachine(vendingMachine);
        vendorVendingMachine.refill(ProductType.Cola);
        assertEquals(vendingMachine.getRemainingStock(ProductType.Cola), 5);
    }
}
