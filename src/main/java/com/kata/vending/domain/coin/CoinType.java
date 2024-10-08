package com.kata.vending.domain.coin;

import java.util.stream.Stream;

public enum CoinType {
    NICKEL("n"),
    DIME("d"),
    QUARTER("q"),
    PENNY("p");

    CoinType(String name) {
        this.shortName = name;
    }

    private final String shortName;

    public String GetShortName() {
        return shortName;
    }

    public static CoinType getByName(String shortName) {
        return Stream
                .of(values())
                .filter(coin -> coin.shortName.equals(shortName))
                .findAny()
                .orElse(null);
    }

    public static Money GetCoinValue(CoinType coinType) {
        var coin = new Coin(coinType);
        return coin.getMoney();
    }
}
