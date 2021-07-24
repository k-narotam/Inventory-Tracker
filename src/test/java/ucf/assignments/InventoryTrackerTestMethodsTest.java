/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;


class InventoryTrackerTestMethodsTest {

    @DisplayName("Simple Test of addItem")
    @Test
    void addMethod()  {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        Item item1 = new Item("iPad", "0123456789", "500");
        Item item2 = new Item("macbook", "1234567890", "2000");
        Item item3 = new Item("Apple Pencil", "2345678901", "100");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        newMethods.addItem("iPhone", "3456789012", "1000", list);

        assert(list.size() == 4);
    }

    @DisplayName("Invalid name character length")
    @Test
    void addMethodDuplicateSerial()  {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        Item item1 = new Item("iPad", "0123456789", "500");
        Item item2 = new Item("macbook", "1234567890", "2000");
        Item item3 = new Item("Apple Pencil", "2345678901", "100");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        newMethods.addItem("i", "0123456789", "1000", list);

        assert(list.size() == 3);
    }

    @DisplayName("Simple Delete Item Test")
    @Test
    void deleteTest()  {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        Item item1 = new Item("iPad", "0123456789", "500");
        Item item2 = new Item("macbook", "1234567890", "2000");
        Item item3 = new Item("Apple Pencil", "2345678901", "100");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        newMethods.deleteItem(item3, list);

        assert(list.size() == 2);

    }
    @DisplayName("Edit serial number, non duplicate, correct format")
    @Test
    void editSerialTest() {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> inventory = new ArrayList<>();
        Item cellSelected = new Item("item", "XXXXXXXXXX", "3");
        inventory.add(cellSelected);
        String newSerial = "0123456789";
        boolean format = newMethods.checkSerialFormatting(newSerial);
        boolean unique = newMethods.checkSerialUnique(newSerial, inventory);


        if  (format && unique)
            cellSelected.setSerialNumber(newSerial);

        assert(cellSelected.getSerialNumber().equals(newSerial));
    }

    @DisplayName("Edit serial number, duplicate, correct format")
    @Test
    void editSerialTest2() {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> inventory = new ArrayList<>();
        Item item1 = new Item("item1", "0123456789", "5");
        inventory.add(item1);
        Item cellSelected = new Item("item2", "XXXXXXXXXX", "3");
        inventory.add(cellSelected);
        String newSerial = "0123456789";
        boolean format = newMethods.checkSerialFormatting(newSerial);
        boolean unique = newMethods.checkSerialUnique(newSerial, inventory);

        if  (format && unique)
            cellSelected.setSerialNumber(newSerial);

        assert(!cellSelected.getSerialNumber().equals(newSerial));

    }

    @DisplayName("Edit serial number, non duplicate, wrong format")
    @Test
    void editSerialTest3() {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> inventory = new ArrayList<>();
        Item item1 = new Item("item1", "0123456789", "5");
        inventory.add(item1);
        Item cellSelected = new Item("item2", "XXXXXXXXXX", "3");
        inventory.add(cellSelected);
        String newSerial = "/../";
        boolean format = newMethods.checkSerialFormatting(newSerial);
        boolean unique = newMethods.checkSerialUnique(newSerial, inventory);

        if  (format && unique)
            cellSelected.setSerialNumber(newSerial);

        assert(!cellSelected.getSerialNumber().equals(newSerial));

    }

    @DisplayName("Edit price, valid monetary value")
    @Test
    void editPrice1() {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> inventory = new ArrayList<>();
        Item item1 = new Item("item1", "0123456789", "5");
        inventory.add(item1);
        Item cellSelected = new Item("item2", "XXXXXXXXXX", "3");
        inventory.add(cellSelected);
        String newPrice = "10";
        assert (newMethods.checkPrice(newPrice, cellSelected));

    }

    @DisplayName("Edit price, invalid monetary value")
    @Test
    void editPrice2() {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> inventory = new ArrayList<>();
        Item item1 = new Item("item1", "0123456789", "5");
        inventory.add(item1);
        Item cellSelected = new Item("item2", "XXXXXXXXXX", "3");
        inventory.add(cellSelected);
        String newPrice = "blah";
        assert (!newMethods.checkPrice(newPrice, cellSelected));

    }

    @DisplayName("Edit name, valid characters")
    @Test
    void editName1() {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> inventory = new ArrayList<>();
        Item item1 = new Item("item1", "0123456789", "5");
        inventory.add(item1);
        Item cellSelected = new Item("item2", "XXXXXXXXXX", "3");
        inventory.add(cellSelected);
        String newName = "karina";
        assert (newMethods.checkName(newName));

    }

    @DisplayName("Edit name, invalid characters")
    @Test
    void editName2() {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> inventory = new ArrayList<>();
        Item item1 = new Item("item1", "0123456789", "5");
        inventory.add(item1);
        Item cellSelected = new Item("item2", "XXXXXXXXXX", "3");
        inventory.add(cellSelected);
        String newName = "k";
        assert (!newMethods.checkName(newName));

    }

}