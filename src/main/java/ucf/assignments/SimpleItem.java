/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SimpleItem {
    private SimpleStringProperty name, serialNumber, price;

    public SimpleItem(String name, String serialNumber, String price) {
        this.name = new SimpleStringProperty(name);
        this.serialNumber = new SimpleStringProperty(serialNumber);
        this.price = new SimpleStringProperty(price);
    }


    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public SimpleStringProperty serialNumberProperty() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = new SimpleStringProperty(serialNumber);
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
