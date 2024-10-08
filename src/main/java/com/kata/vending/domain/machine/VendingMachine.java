package com.kata.vending.domain.machine;

import com.kata.vending.domain.IVendingMachine;
import com.kata.vending.domain.IVendingMachineAdmin;
import com.kata.vending.domain.IVendorVendingMachine;
import com.kata.vending.domain.coin.Coin;
import com.kata.vending.domain.coin.CoinType;
import com.kata.vending.domain.coin.Money;
import com.kata.vending.domain.machine.config.VendingMachineCoinCapacityConfiguration;
import com.kata.vending.domain.machine.config.VendingMachineProductCapacityConfiguration;
import com.kata.vending.domain.product.Product;
import com.kata.vending.domain.product.ProductType;
import com.kata.vending.domain.vendor.email.OutOfStockVendorEmailService;
import com.kata.vending.infrastructure.email.EmailService;

import java.math.RoundingMode;
import java.util.*;

public class VendingMachine implements IVendingMachine, IVendingMachineAdmin, IVendorVendingMachine {
    private final List<Coin> totalCoins = new ArrayList<>();
    private final List<Coin> sessionCoins = new ArrayList<>();
    private final List<Coin> coinReturn = new ArrayList<>();
    private final VendingMachineDisplay display = new VendingMachineDisplay();
    private final VendingMachineStorage storage = new VendingMachineStorage();
    private OutOfStockVendorEmailService outOfStockVendorEmailService;
    private VendingMachineProductCapacityConfiguration vendingMachineProductCapacityConfiguration;
    private VendingMachineCoinCapacityConfiguration vendingMachineCoinCapacityConfiguration;
    public static List<CoinType> acceptedCoinTypes = List.of(CoinType.NICKEL, CoinType.DIME, CoinType.QUARTER);

    public VendingMachine() {}
    
    public VendingMachine(EmailService emailService) {
        outOfStockVendorEmailService = new OutOfStockVendorEmailService(emailService);
    }
    
    private VendingMachine(
        final VendingMachineProductCapacityConfiguration vendingMachineProductCapacityConfiguration,
        final VendingMachineCoinCapacityConfiguration vendingMachineCoinCapacityConfiguration
    ) {
        this.vendingMachineProductCapacityConfiguration = vendingMachineProductCapacityConfiguration;
        this.vendingMachineCoinCapacityConfiguration = vendingMachineCoinCapacityConfiguration;
    }
    
    public void acceptCoin(Coin coin) {
        if (sessionCoins.isEmpty()) {
            display.setDisplay(VendingMachineMessage.InsertCoins);
        }

        if (!acceptedCoinTypes.contains(coin.getCoinType())) {
            coinReturn.add(coin);
            return;
        }

        if (CoinCassetteCapacity.isCoinCassetteCapacityExceeded(coin.getCoinType(), totalCoins, sessionCoins)) {
            coinReturn.add(coin);
            display.setDisplay(VendingMachineMessage.CassetteFull);
            return;
        }

        sessionCoins.add(coin);
        display.setDisplay(VendingMachineMessage.Credit(getTotalSessionAmount()));
    }

    @Override
    public List<Coin> collectChange() {
        if (!sessionCoins.isEmpty()) {
            coinReturn.addAll(sessionCoins);
        }

        var coins = new ArrayList<>(coinReturn);
        
        sessionCoins.clear();
        coinReturn.clear();
        
        display.setDisplay(VendingMachineMessage.InsertCoins);
        
        coins.sort(Comparator.comparing(Coin::getMoney).reversed());
        
        return coins;
    }

    @Override
    public Optional<Product> purchaseProduct(final ProductType productType) {
        if (getRemainingStock(productType) == 0) {
            display.setDisplay(VendingMachineMessage.OutOfStock(productType));
            
            new Thread(() -> {
                if (outOfStockVendorEmailService != null) {
                    outOfStockVendorEmailService.sendEmail(productType);
                }
            }).start();
            
            return Optional.empty();
        }
        
        if (!coinReturn.isEmpty()) { // enable continuous purchase
            sessionCoins.addAll(coinReturn);
        }

        var amount = getTotalSessionAmount();
        var product = new Product(productType);

        if (product.getMoney().compareTo(amount) > 0) {
            display.setDisplay(VendingMachineMessage.ProductPrice(product.getMoney())); 
            return Optional.empty();
        }

        var change = amount.getValue().subtract(product.getMoney().getValue());

        if (change.doubleValue() > 0) {
            var rounded = change.setScale(2, RoundingMode.HALF_UP);
            var changeCoins = ChangeCalculator.getChange(new Money(rounded.doubleValue()));
            coinReturn.addAll(changeCoins);
        }

        display.setDisplay(VendingMachineMessage.ThankYou);
        storage.reduceStock(productType);
        
        totalCoins.addAll(ChangeCalculator.getChange(product.getMoney()));
        sessionCoins.clear();

        return Optional.of(product);
    }

    @Override
    public int getRemainingStock(final ProductType productType) {
        return storage.getCurrentStorage(productType);
    }

    private Money getTotalSessionAmount() {
        if (sessionCoins.isEmpty()) {
            return new Money(0);
        }

        var sum = (Double) sessionCoins
                .stream()
                .mapToDouble(m -> m.getMoney().getValue().doubleValue())
                .sum();

        return new Money(sum);
    }

    @Override
    public String getDisplay() {
        return display.getDisplay(sessionCoins.isEmpty(), getTotalSessionAmount());
    }

    @Override
    public boolean isAcceptedCoin(final CoinType coinType) {
        return acceptedCoinTypes.contains(coinType);
    }

    @Override
    public int getTotalCoinCount() {
        return totalCoins.size();
    }

    @Override
    public int getSessionCoinCount() {
        return sessionCoins.size();
    }

    @Override
    public int getCoinReturnSize() {
        return coinReturn.size();
    }

    @Override
    public void refill(ProductType productType) {
        storage.refill(productType);
    }

    public static VendingMachineBuilder getBuilder() { return new VendingMachineBuilder(); }
    
    public static class VendingMachineBuilder {
        private final VendingMachineProductCapacityConfiguration productCapacityConfiguration = new VendingMachineProductCapacityConfiguration();
        private final VendingMachineCoinCapacityConfiguration vendingMachineCoinCapacityConfiguration = new VendingMachineCoinCapacityConfiguration();

        public VendingMachineBuilder setProductCapacity(ProductType productType, int capacity) {
            switch (productType) {
                case Candy -> productCapacityConfiguration.setCandyCapacity(capacity);
                case Cola -> productCapacityConfiguration.setColaCapacity(capacity);
                case Chips -> productCapacityConfiguration.setChipsCapacity(capacity);
            }

            return this;
        }

        public VendingMachineBuilder setCoinCapacity(CoinType productType, int capacity) {
            switch (productType) {
                case NICKEL -> vendingMachineCoinCapacityConfiguration.setNickelCoinCapacity(capacity);
                case DIME -> vendingMachineCoinCapacityConfiguration.setDimeCoinCapacity(capacity);
                case QUARTER -> vendingMachineCoinCapacityConfiguration.setQuarterCoinCapacity(capacity);
            }

            return this;
        }

        public VendingMachine build() {
            return new VendingMachine(productCapacityConfiguration, vendingMachineCoinCapacityConfiguration);
        }
    }
}
