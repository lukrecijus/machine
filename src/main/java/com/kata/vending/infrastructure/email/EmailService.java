package com.kata.vending.infrastructure.email;

public interface EmailService {
    void send(String to, String text);
}
