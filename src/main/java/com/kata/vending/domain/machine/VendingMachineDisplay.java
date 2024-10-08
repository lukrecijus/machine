package com.kata.vending.domain.machine;

import com.kata.vending.domain.coin.Money;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


public class VendingMachineDisplay {
    @Setter
    private String display = VendingMachineMessage.InsertCoins;
    @Getter
    private boolean cassetteFullDisplayShown;
    @Getter
    private boolean insufficientFundsDisplayShown;
    @Getter
    private boolean thankYouDisplayShown;

    private void setDefaultDisplay(boolean isSessionCoinsEmpty, Money sessionAmount) {
        display = isSessionCoinsEmpty ? VendingMachineMessage.InsertCoins : VendingMachineMessage.Credit(sessionAmount);
    }
    
    public String getDisplay(boolean isSessionCoinsEmpty, Money sessionAmount) {
        if (display.equals(VendingMachineMessage.CassetteFull)) {
            if (cassetteFullDisplayShown) {
                setDefaultDisplay(isSessionCoinsEmpty, sessionAmount);
                insufficientFundsDisplayShown = false;
            } else {
                cassetteFullDisplayShown = true;
            }
        }

        if (display.toLowerCase().startsWith("price")) {
            if (insufficientFundsDisplayShown) {
                setDefaultDisplay(isSessionCoinsEmpty, sessionAmount);
                insufficientFundsDisplayShown = false;
            } else {
                insufficientFundsDisplayShown = true;
            }
        }

        if (Objects.equals(display, VendingMachineMessage.ThankYou)) {
            if (thankYouDisplayShown) {
                setDefaultDisplay(isSessionCoinsEmpty, sessionAmount);
                thankYouDisplayShown = false;
            } else {
                thankYouDisplayShown = true;
            }
        }

        return display;
    }
}
