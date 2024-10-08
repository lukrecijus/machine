package com.kata.vending.infrastructure.email;

public class EmailServiceImpl implements EmailService {
    @Override
    public void send(String to,String text) {
        System.out.println("sending email to " + to + " " + text);
    }
}
