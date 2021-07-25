/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */

package ucf.assignments;

import com.google.gson.*;
import javafx.collections.FXCollections;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

// Testable functions - Use of ArrayList and Item instead of ObservableArrayList and SimpleItem designed for javafx
// Duplicate logical code of InventoryTrackerMethods
// (Program modeled similarly to Assignment 4 Structure)

public class InventoryTrackerTestMethods {

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
            if (inventory.get(i).getSerial().equals(serial)) {
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

    public void tsvFormat(ArrayList<Item> inventory, File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.printf("%-10s\t%-15s\t%-75s%n", "Price", "Serial Number", "Name");
        for (Item aItem : inventory) {
            writer.printf("%-10s\t%-15s\t%-75s%n", aItem.getPrice(), aItem.getSerial(), aItem.getName());
        }
        writer.close();
    }

    public ArrayList<Item> tsvReader(File file) throws IOException {
        ArrayList<Item> inventory = new ArrayList<>();
        String data = Files.readString(file.toPath());
        String[] items = data.split("\n");
        for (int i = 1; i < items.length; i++) {
            String[] attributes = items[i].split("\t");
            Item newItem = new Item(attributes[2], attributes[1], attributes[0]);
            inventory.add(newItem);
        }
        return inventory;
    }

    public void HTMLFormat(ArrayList<Item> inventory, File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.println("<style>\ntable, th, td {\n\tborder: 1px solid black;\n\tborder-collapse: collapse;" +
                "\n}\nth, td {\n\tpadding: 15px;\n text-align: left;\n }\n</style>");
        writer.println("<table style=\"width:100%\">");
        writer.println("<tr>");
        writer.println("\t<th>Price</th>");
        writer.println("\t<th>Serial Number</th>");
        writer.println("\t<th>Name</th>");
        writer.println("</tr>");

        for (Item item : inventory) {
            writer.println("<tr>");
            writer.printf("\t<td>%s</th>\n", item.getPrice());
            writer.printf("\t<td>%s</th>\n", item.getSerial());
            writer.printf("\t<td>%s</th>\n", item.getName());
            writer.println("</tr>");
        }
        writer.println("</table>");
        writer.close();
    }

    public ArrayList<Item> readHTML(File file) throws IOException {
        ArrayList<Item> inventory = new ArrayList<>();
        String data = Files.readString(file.toPath());
        Document doc = Jsoup.parse(data);

        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            String price = cols.get(0).text();
            String serial = cols.get(1).text();
            String name =cols.get(2).text();
            Item newItem = new Item(name, serial, price);
            inventory.add(newItem);
        }
        return inventory;
    }

    public String serializeInventory(ArrayList<Item> inventory) {
        ArrayList<Item> arrayList = new ArrayList();
        for (int i = 0; i < inventory.size(); i++) {
            arrayList.add(new Item(inventory.get(i).getName(), inventory.get(i).getSerial(), inventory.get(i).getPrice()));
        }
        Gson gson = new Gson();
        String arrayJson = gson.toJson(arrayList);
        return arrayJson;
    }

    public void saveTexttoFile(String content, File file) throws FileNotFoundException {
        PrintWriter writer;
        writer = new PrintWriter(file);
        writer.write("{\"Inventory\" : " + content + "}");
        writer.close();
    }

    public ArrayList<Item> deserialize(File file) {
        ArrayList<Item> inventory = new ArrayList<>();
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(file));
            JsonObject fileObject = fileElement.getAsJsonObject();

            // process products
            JsonArray jsonArrayProducts = fileObject.get("Inventory").getAsJsonArray();

            for (JsonElement productElement : jsonArrayProducts) {
                JsonObject productJsonObject = productElement.getAsJsonObject();
                // extract data
                String serial = productJsonObject.get("serial").getAsString();
                String name = productJsonObject.get("name").getAsString();
                String price = productJsonObject.get("price").getAsString();

                Item newItem = new Item(name, serial, price);
                inventory.add(newItem);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventory;
    }


}
