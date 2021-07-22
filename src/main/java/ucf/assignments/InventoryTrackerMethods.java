/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;

import com.sun.javafx.collections.ObservableSetWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.io.IOException;


public class InventoryTrackerMethods {


    // Constraints: Item name between 2 - 256 characters; Serial Number in format XXXXXXXXXX alphanumeric
    public ObservableList<SimpleItem> addItem(String name, String serial, String price, ObservableList<SimpleItem> inventory) throws IOException {
        boolean duplicate = false;
        if (name.length() >= 2 && name.length() <= 256) {
            if (serial.matches("[a-zA-Z0-9]+") && serial.length() == 10) {
                try {
                    SimpleItem newItem = new SimpleItem(name, serial, price);
                    System.out.println("size: " + inventory.size());
                    newItem.setPrice(price);
                    for (int i = 0; i < inventory.size(); i++) {
                        if (inventory.get(i).getSerialNumber().equals(newItem.getSerialNumber())) {
                            ErrorMessage.showErrorAlert("Invalid Input", "Please enter a unique serial number");
                            duplicate = true;
                        }
                    }
                    if (!duplicate)
                        inventory.add(newItem);

                } catch (NumberFormatException e) {
                    ErrorMessage.showErrorAlert("Invalid Input", "Please enter a value in US dollars");
                }
            } else {
                ErrorMessage.showErrorAlert("Invalid Input", "Please enter alphanumeric serial number with 10 characters");
            }
        } else {
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter name within 2-256 characters");
        }
        return inventory;
    }

    public void deleteItem(SimpleItem selected, ObservableList<Item> inventory) {

    }

    public void searchItem(String text, ObservableList inventory) {

    }


}
