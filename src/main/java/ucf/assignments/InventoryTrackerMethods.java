/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;


public class InventoryTrackerMethods {

    // Constraints: Item name between 2 - 256 characters; Serial Number in format XXXXXXXXXX alphanumeric
    public void addItem(String name, String serial, String price, ObservableList inventory) {
        if (name.length() >= 1 && name.length() <= 256 && serial.matches("[a-zA-Z0-9]+"))  {

            SimpleItem newItem = new SimpleItem(name, serial, price);
            if (!inventory.contains(newItem))
                inventory.add(newItem);
        }
    }



}
