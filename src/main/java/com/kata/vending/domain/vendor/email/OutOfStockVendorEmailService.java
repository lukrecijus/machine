package com.kata.vending.domain.vendor.email;

import com.kata.vending.domain.VendingMachineConfiguration;
import com.kata.vending.domain.product.ProductType;
import com.kata.vending.infrastructure.email.EmailService;

public class OutOfStockVendorEmailService {
    EmailService emailService;
    
    public OutOfStockVendorEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
    
    public void sendEmail(ProductType productType) {
        String email = getVendorEmail(productType);
        emailService.send(email,  productType + "is out of stock, please refill");
    }
    
    private String getVendorEmail(ProductType productType) {
        return switch (productType) {
            case Cola -> VendingMachineConfiguration.COLA_VENDOR_EMAIL;
            case Chips -> VendingMachineConfiguration.CHIPS_VENDOR_EMAIL;
            case Candy -> VendingMachineConfiguration.CANDY_VENDOR_EMAIL;
            default -> throw new IllegalStateException("Unexpected value: " + productType);
        };
    }
}
