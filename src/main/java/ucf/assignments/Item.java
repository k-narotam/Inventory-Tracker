/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;


import java.math.BigDecimal;

public class Item {
    private String name, serialNumber, price;


    public Item(String name, String serialNumber, String price) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.price = price;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }


    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPrice() {
        return price;
    }


    public void setPrice(String price) {
        BigDecimal money = BigDecimal.valueOf(Double.parseDouble(price));
        this.price = money.toString();
    }
}
