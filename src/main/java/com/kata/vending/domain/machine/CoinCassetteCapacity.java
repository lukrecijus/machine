package com.kata.vending.domain.machine;

import com.kata.vending.domain.VendingMachineConfiguration;
import com.kata.vending.domain.coin.Coin;
import com.kata.vending.domain.coin.CoinType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CoinCassetteCapacity {
    private static final Map<CoinType, Integer> tubeCapacity = new EnumMap<>(CoinType.class);
    
    static {
        tubeCapacity.put(CoinType.NICKEL, VendingMachineConfiguration.NICKEL_COIN_CAPACITY);
        tubeCapacity.put(CoinType.DIME, VendingMachineConfiguration.DIME_COIN_CAPACITY);
        tubeCapacity.put(CoinType.QUARTER, VendingMachineConfiguration.QUARTER_COIN_CAPACITY);
    }
    
    public static int getCoinCapacity(final CoinType coinType) {
        var capacity = tubeCapacity.get(coinType);
        
        if (capacity == null) {
            return 0;
        }
        
        return capacity;
    }

    public static boolean isCoinCassetteCapacityExceeded(
        final CoinType coinType,
        final List<Coin> totalCoins,
        final List<Coin> sessionCoins
    ) {
        int coinTypeCapacity = CoinCassetteCapacity.getCoinCapacity(coinType);
        return Stream
                .concat(totalCoins.stream(), sessionCoins.stream()).toList().stream()
                .filter(coin -> coin.getCoinType() == coinType)
                .count() >= coinTypeCapacity;
    }
}
