/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;


import javafx.collections.ObservableList;


public class InventoryTrackerMethods {

    // Constraints: Item name between 2 - 256 characters; Serial Number in format XXXXXXXXXX alphanumeric and unique
    // Price valid monetary value
    public ObservableList<SimpleItem> addItem(String name, String serial, String price, ObservableList<SimpleItem> inventory) {
        SimpleItem newItem = new SimpleItem(name, serial, price);
        if (!checkPrice(price, newItem)) {
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter a value in US dollars");
        }
        else if (!checkName(name)) {
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter name within 2-256 characters");
        }
        else if (!checkSerialUnique(serial, inventory)) {
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter a unique serial number");
        }
        else if (!checkSerialFormatting(serial)) {
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter alphanumeric serial number with 10 characters");
        }
        else {
            inventory.add(newItem);
        }
        return inventory;
    }
    public Boolean checkPrice(String price, SimpleItem newItem) {
        try {
            newItem.setPrice(price);

        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public Boolean checkName(String name) {
        if (name.length() >= 2 && name.length() <= 256) {
            return true;
        }
        return false;
    }

    public Boolean checkSerialUnique(String serial, ObservableList<SimpleItem> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getSerialNumber().equals(serial)) {
                return false;
            }
        }
        return true;
    }

    public Boolean checkSerialFormatting(String serial) {
        if (serial.matches("[a-zA-Z0-9]+") && serial.length() == 10) {
            return true;
        }
        return false;
    }

    public ObservableList<SimpleItem> deleteItem(SimpleItem selected, ObservableList<SimpleItem> inventory) {
        inventory.remove(selected);
        return inventory;
    }



    public void searchItem(String text, ObservableList inventory) {

    }


}
