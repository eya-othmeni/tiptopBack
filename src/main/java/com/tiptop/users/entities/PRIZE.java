package com.tiptop.users.entities;

public enum PRIZE {


    P1("Infuseur the", 60),
    P2("Boite 100g the détox ou infusion", 20),
    P3("Boite 100g the signature", 10),
    P4("coffret découverte d’une valeur de 39€", 6),
    P5("coffret découverte d’une valeur de 69€", 4);
    public int percentage;
    public String prize;

    PRIZE(String prize, int percentage) {
        this.percentage = percentage;
        this.prize = prize;
    }
}
