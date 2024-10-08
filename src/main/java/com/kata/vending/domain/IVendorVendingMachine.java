package com.kata.vending.domain;

import com.kata.vending.domain.product.ProductType;

public interface IVendorVendingMachine {
    void refill(ProductType productType);
}
