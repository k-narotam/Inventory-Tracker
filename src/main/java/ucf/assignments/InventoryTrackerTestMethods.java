/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */

package ucf.assignments;

import java.util.ArrayList;

// Testable functions - Use of ArrayList and Item instead of ObservableArrayList and SimpleItem designed for javafx
// Duplicate logical code of InventoryTrackerMethods

public class InventoryTrackerTestMethods {
    InventoryTrackerMethods methods = new InventoryTrackerMethods();
    public ArrayList<Item> addItem(String name, String serial, String price, ArrayList<Item> inventory) {
        Item newItem = new Item(name, serial, price);
        if (!checkPrice(price, newItem)) {
            System.out.println("Invalid Input - Please enter a value in US dollars");
        }
        else if (!checkName(name)) {
            System.out.println("Invalid Input - Please enter name within 2-256 characters");
        }
        else if (!checkSerialUnique(serial, inventory)) {
            System.out.println("Invalid Input - Please enter a unique serial number");
        }
        else if (!checkSerialFormatting(serial)) {
            System.out.println("Invalid Input - Please enter alphanumeric serial number with 10 characters");
        }
        else {
            inventory.add(newItem);
        }
        return inventory;
    }

    public Boolean checkName(String name) {
        if (name.length() >= 2 && name.length() <= 256) {
            return true;
        }
        return false;
    }

    public Boolean checkPrice(String price, Item newItem) {
        try {
            newItem.setPrice(price);

        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public Boolean checkSerialUnique(String serial, ArrayList<Item> inventory) {
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



    public ArrayList<Item> deleteItem(Item selected, ArrayList<Item> inventory) {
        inventory.remove(selected);
        return inventory;
    }


}
