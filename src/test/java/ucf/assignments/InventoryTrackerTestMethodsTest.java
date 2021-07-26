/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test Methods utilizing classes with ArrayLists and Item objects

class InventoryTrackerTestMethodsTest {

    // Note: Since tableView allows for sorting, this functionality does not need testing


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

    // Uses random number generator in order to create 100 unique serial numbers
    @DisplayName("Store 100 items")
    @Test
    void store100() {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuwxyz";
        ArrayList<Item> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            StringBuilder newString = new StringBuilder(10);
            for (int j = 0; j < 10; j++) {
                int index = (int)(alphaNumericString.length()*Math.random());
                newString.append((alphaNumericString.charAt(index)));
            }
            String serialNum = newString.toString();
            newMethods.addItem("name", serialNum, "1", list);
        }
        assert(list.size() == 100);
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
            cellSelected.setSerial(newSerial);

        assert(cellSelected.getSerial().equals(newSerial));
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
            cellSelected.setSerial(newSerial);

        assert(!cellSelected.getSerial().equals(newSerial));

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
            cellSelected.setSerial(newSerial);

        assert(!cellSelected.getSerial().equals(newSerial));

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
        String newPrice = "dinero";
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
        String newName = "name";
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

    @DisplayName("Test Search - 2 items match")
    @Test
    void searchTest() {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        ArrayList<Item> filtered;
        Item item1 = new Item("Bears", "0123456789", "500");
        Item item2 = new Item("Beets", "1234567890", "2000");
        Item item3 = new Item("Battlestar galactica", "2345678901", "100");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        filtered = newMethods.searchItem("be", list);
        assert(filtered.size() == 2);
    }

    @DisplayName("Test Search - 1 item match")
    @Test
    void searchTest2() {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        ArrayList<Item> filtered;
        Item item1 = new Item("Bears", "0123456789", "500");
        Item item2 = new Item("Beets", "1234567890", "2000");
        Item item3 = new Item("Battlestar galactica", "2345678901", "100");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        filtered = newMethods.searchItem("ba", list);
        assert(filtered.size() == 1);
    }

    @DisplayName("Test Search - 0 items match")
    @Test
    void searchTest3() {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        ArrayList<Item> filtered;
        Item item1 = new Item("Bears", "0123456789", "500");
        Item item2 = new Item("Beets", "1234567890", "2000");
        Item item3 = new Item("Battlestar galactica", "2345678901", "100");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        filtered = newMethods.searchItem("dwight", list);
        assert(filtered.size() == 0);
    }


    @DisplayName("Test TSV Save")
    @Test
    void saveTSVTest() throws IOException {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        Item item1 = new Item("iPad", "0123456789", "500");
        Item item2 = new Item("macbook", "1234567890", "2000");
        Item item3 = new Item("Apple Pencil", "2345678901", "100");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        File file = File.createTempFile("tsvTest", ".txt");
        newMethods.tsvFormat(list, file);
        String actual = Files.readString(file.toPath());
        String expected = String.format("%s\t%s\t%s%n", "Price", "Serial Number", "Name");
        expected += String.format("%s\t%s\t%s%n", item1.getPrice(), item1.getSerial(), item1.getName());
        expected += String.format("%s\t%s\t%s%n", item2.getPrice(), item2.getSerial(), item2.getName());
        expected += String.format("%s\t%s\t%s%n", item3.getPrice(), item3.getSerial(), item3.getName());
        assertEquals(expected, actual);
        file.delete();

    }

    @DisplayName("Test TSV Load")
    @Test
    void loadTSVTest() throws IOException {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        ArrayList<Item> loadedList;
        Item item1 = new Item("iPad", "0123456789", "500");
        Item item2 = new Item("macbook", "1234567890", "2000");
        Item item3 = new Item("Apple Pencil", "2345678901", "100");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        File file = File.createTempFile("tsvTest", ".txt");
        newMethods.tsvFormat(list, file);
        loadedList = newMethods.tsvReader(file);
        boolean sameData = true;
        for (int i = 0; i < list.size(); i++) {
           if (!loadedList.get(i).getName().strip().equals(list.get(i).getName()))
               sameData = false;
           else if (!loadedList.get(i).getSerial().strip().equals(list.get(i).getSerial()))
               sameData = false;
           else if (!loadedList.get(i).getPrice().strip().equals(list.get(i).getPrice()))
               sameData = false;
        }
        assert(sameData);
        file.delete();
    }

    @DisplayName("Test HTML Save")
    @Test
    void saveHTMLTest() throws IOException {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        Item item1 = new Item("iPad", "0123456789", "500");
        Item item2 = new Item("macbook", "1234567890", "2000");
        Item item3 = new Item("Apple Pencil", "2345678901", "100");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        File file = File.createTempFile("saveHTML", ".html");
        newMethods.HTMLFormat(list, file);
        String actual = Files.readString(file.toPath());
        String expected = """
                <style>
                table, th, td {
                \tborder: 1px solid black;
                \tborder-collapse: collapse;
                }
                th, td {
                \tpadding: 15px;
                 text-align: left;
                 }
                </style>
                <table style="width:100%">
                <tr>
                \t<th>Price</th>
                \t<th>Serial Number</th>
                \t<th>Name</th>
                </tr>
                <tr>
                \t<td>500</th>
                \t<td>0123456789</th>
                \t<td>iPad</th>
                </tr>
                <tr>
                \t<td>2000</th>
                \t<td>1234567890</th>
                \t<td>macbook</th>
                </tr>
                <tr>
                \t<td>100</th>
                \t<td>2345678901</th>
                \t<td>Apple Pencil</th>
                </tr>
                </table>
                """;
        assertEquals(expected, actual);
        file.delete();
    }

    @DisplayName("Test HTML Load")
    @Test
    void loadHTMLTest() throws IOException {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        ArrayList<Item> loadedList;
        Item item1 = new Item("iPad", "0123456789", "500");
        Item item2 = new Item("macbook", "1234567890", "2000");
        Item item3 = new Item("Apple Pencil", "2345678901", "100");
        list.add(item1);
        list.add(item2);
        list.add(item3);
        File file = File.createTempFile("loadHTML", ".html");
        newMethods.HTMLFormat(list, file);
        loadedList = newMethods.readHTML(file);
        boolean sameData = true;
        for (int i = 0; i < list.size(); i++) {
            if (!loadedList.get(i).getName().strip().equals(list.get(i).getName()))
                sameData = false;
            else if (!loadedList.get(i).getSerial().strip().equals(list.get(i).getSerial()))
                sameData = false;
            else if (!loadedList.get(i).getPrice().strip().equals(list.get(i).getPrice()))
                sameData = false;
        }
        assert(sameData);
        file.delete();
    }

    @DisplayName("Test JSON Save")
    @Test
    void saveJSONTest() throws IOException {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        Item item1 = new Item("iPad", "0123456789", "500");
        Item item2 = new Item("macbook", "1234567890", "2000");
        list.add(item1);
        list.add(item2);
        File file = File.createTempFile("jsonSave", ".json");
        String jsonString = newMethods.serializeInventory(list);
        newMethods.saveTexttoFile(jsonString, file);
        String actual = Files.readString(file.toPath());
        String expected = "{\"Inventory\" : [{\"name\":\"iPad\",\"serial\":\"0123456789\",\"price\":\"500\"},{\"name\":\"macbook\",\"serial\":\"1234567890\",\"price\":\"2000\"}]}";
        assertEquals(expected, actual);
        file.delete();
    }

    @DisplayName("Test JSON Load")
    @Test
    void loadJSONTest() throws IOException {
        InventoryTrackerTestMethods newMethods = new InventoryTrackerTestMethods();
        ArrayList<Item> list = new ArrayList<>();
        ArrayList<Item> loadedList;
        Item item1 = new Item("iPad", "0123456789", "500");
        Item item2 = new Item("macbook", "1234567890", "2000");
        list.add(item1);
        list.add(item2);
        File file = File.createTempFile("loadJSON", ".json");
        String jsonString = newMethods.serializeInventory(list);
        newMethods.saveTexttoFile(jsonString, file);
        loadedList = newMethods.deserialize(file);
        boolean sameData = true;
        for (int i = 0; i < list.size(); i++) {
            if (!loadedList.get(i).getName().strip().equals(list.get(i).getName()))
                sameData = false;
            else if (!loadedList.get(i).getSerial().strip().equals(list.get(i).getSerial()))
                sameData = false;
            else if (!loadedList.get(i).getPrice().strip().equals(list.get(i).getPrice()))
                sameData = false;
        }
        assert(sameData);
        file.delete();
    }




}