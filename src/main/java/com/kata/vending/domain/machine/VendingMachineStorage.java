package com.kata.vending.domain.machine;

import com.kata.vending.domain.VendingMachineConfiguration;
import com.kata.vending.domain.product.ProductType;

import java.util.EnumMap;
import java.util.Map;

public class VendingMachineStorage {
    private final Map<ProductType, Integer> storageMap = new EnumMap<>(ProductType.class);
    private static final Map<ProductType, Integer> capacityMap = new EnumMap<>(ProductType.class);
    
    public VendingMachineStorage() {
        storageMap.put(ProductType.Cola, VendingMachineConfiguration.COLA_CAPACITY);
        storageMap.put(ProductType.Chips, VendingMachineConfiguration.CHIPS_CAPACITY);
        storageMap.put(ProductType.Candy, VendingMachineConfiguration.CANDY_CAPACITY);
        capacityMap.put(ProductType.Cola, VendingMachineConfiguration.COLA_CAPACITY);
        capacityMap.put(ProductType.Chips, VendingMachineConfiguration.CHIPS_CAPACITY);
        capacityMap.put(ProductType.Candy, VendingMachineConfiguration.CANDY_CAPACITY);
    }
    
    public int getCurrentStorage(ProductType productType) {
        return storageMap.getOrDefault(productType, 0);
    }
    
    public void reduceStock(ProductType productType) throws IllegalStateException {
        var current = getCurrentStorage(productType);
        
        if (current > 0) {
            storageMap.put(productType, current - 1);
        } else {
            if (current != 0) {
                throw new IllegalStateException();
            }
        }
    }
    
    public static int getTotalCapacity(ProductType productType) {
        return capacityMap.getOrDefault(productType, 0);
    }
    
    public void refill(ProductType productType) {
        storageMap.put(productType, capacityMap.getOrDefault(productType, 0));
    }
}
