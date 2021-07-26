/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SimpleItem {
    private SimpleStringProperty name, serial, price;

    public SimpleItem(String name, String serial, String price) {
        this.name = new SimpleStringProperty(name);
        this.serial = new SimpleStringProperty(serial);
        this.price = new SimpleStringProperty(price);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getSerialNumber() {
        return serial.get();
    }


    public void setSerialNumber(String serial) {
        this.serial = new SimpleStringProperty(serial);
    }

    public String getPrice() {
        return price.get();
    }


    public void setPrice(String price) {
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(price));
        value = value.setScale(2, RoundingMode.HALF_UP);
        this.price = new SimpleStringProperty("$" + value);

    }
}
