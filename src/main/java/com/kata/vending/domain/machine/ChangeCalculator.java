package com.kata.vending.domain.machine;

import com.kata.vending.domain.coin.Coin;
import com.kata.vending.domain.coin.CoinType;
import com.kata.vending.domain.coin.Money;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChangeCalculator {
    public static Map<CoinType, Integer> calculateChange(Money change) {
        Money d = CoinType.GetCoinValue(CoinType.DIME);
        Money q = CoinType.GetCoinValue(CoinType.QUARTER);
        Money n = CoinType.GetCoinValue(CoinType.NICKEL);
        
        assert d.compareTo(q) > 0;
        assert q.compareTo(n) > 0;
        assert d.compareTo(n) > 0;
        
        var m = new EnumMap<CoinType, Integer>(CoinType.class);
        
        m.put(CoinType.DIME, 0);
        m.put(CoinType.QUARTER, 0);
        m.put(CoinType.NICKEL, 0);

        return calculateChangeRecursive(m, new Money(change.getValue().doubleValue()), d, q, n);
    }
    
    public static List<Coin> getChange(Money change) {
        Map<CoinType, Integer> m = calculateChange(change);
        return m.entrySet().stream()
                .flatMap(entry -> IntStream.range(0, entry.getValue()).mapToObj(i -> new Coin(entry.getKey())))
                .collect(Collectors.toList());
    }

    private static Map<CoinType, Integer> calculateChangeRecursive(Map<CoinType, Integer> map, Money change, Money d, Money q, Money n) {
        if (change.isZero()) {
            return map;
        }
        
        if (change.compareTo(d) >= 0) {
            map.put(CoinType.DIME, map.get(CoinType.DIME) + 1);
            calculateChangeRecursive(map, new Money(change.getValue().doubleValue() - d.getValue().doubleValue()), d, q, n);
        } else if (change.compareTo(q) >= 0) {
            map.put(CoinType.QUARTER, map.get(CoinType.QUARTER) + 1);
            calculateChangeRecursive(map, new Money(change.getValue().doubleValue() - q.getValue().doubleValue()), d, q, n);
        } else if (change.compareTo(n) >= 0) {
            map.put(CoinType.NICKEL, map.get(CoinType.NICKEL) + 1);
            calculateChangeRecursive(map, new Money(change.getValue().doubleValue() - n.getValue().doubleValue()), d, q, n); 
        }
        
        return map;
    }
}
