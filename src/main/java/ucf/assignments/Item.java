/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;


import java.math.BigDecimal;

public class Item {
    private String name, serial, price;


    public Item(String name, String serial, String price) {
        this.name = name;
        this.serial = serial;
        this.price = price;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }


    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPrice() {
        return price;
    }


    public void setPrice(String price) {
        BigDecimal money = BigDecimal.valueOf(Double.parseDouble(price));
        this.price = money.toString();
    }
}
