/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;


public class InventoryTrackerMethods {


    // Constraints: Item name between 2 - 256 characters; Serial Number in format XXXXXXXXXX alphanumeric and unique
    // Price valid monetary value
    public String addItem(String name, String serial, String price, ObservableList<SimpleItem> inventory) {
        SimpleItem newItem = new SimpleItem(name, serial, price);
        String result = "";
        if (!checkPrice(price, newItem)) {
            result = "Please enter a valid monetary price in US dollars";
        } else if (!checkName(name)) {
            result = "Please enter name within 2-256 characters";
        } else if (!checkSerialUnique(serial, inventory)) {
            result = "Please enter a unique serial number";
        } else if (!checkSerialFormatting(serial)) {
            result = "Please enter alphanumeric serial number with 10 characters";
        } else {
            inventory.add(newItem);
        }
        return result;
    }

    // Valid monetary values can be parsed into double
    // Does not allow negative values to be added (when edited, assumes user inputs $ character)
    public Boolean checkPrice(String price, SimpleItem newItem) {
        try {
            newItem.setPrice(price);
            if (Double.parseDouble(newItem.getPrice().substring(1)) < 0)
                return false;

        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public Boolean checkName(String name) {
        return name.length() >= 2 && name.length() <= 256;
    }

    public Boolean checkSerialUnique(String serial, ObservableList<SimpleItem> inventory) {
        for (SimpleItem simpleItem : inventory) {
            if (simpleItem.getSerialNumber().equals(serial)) {
                return false;
            }
        }
        return true;
    }

    public Boolean checkSerialFormatting(String serial) {
        return serial.matches("[a-zA-Z0-9]+") && serial.length() == 10;
    }

    public ObservableList<SimpleItem> deleteItem(SimpleItem selected, ObservableList<SimpleItem> inventory) {
        inventory.remove(selected);
        return inventory;
    }

    public ObservableList<SimpleItem> searchItem(String search, ObservableList<SimpleItem> inventory) {
        ObservableList<SimpleItem> filtered = FXCollections.observableArrayList();
        for (SimpleItem item : inventory) {
            if (item.getName().toLowerCase().contains(search.toLowerCase()) || item.getSerialNumber().toLowerCase().contains(search.toLowerCase())) {
                filtered.add(item);
            }
        }
        return filtered;
    }


    // Print writer used to write to tsv file
    public void tsvFormat(ObservableList<SimpleItem> inventory, File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.printf("%s\t%s\t%s%n", "Price", "Serial Number", "Name");
        for (SimpleItem aItem : inventory) {
            writer.printf("%s\t%s\t%s%n", aItem.getPrice(), aItem.getSerialNumber(), aItem.getName());
        }
        writer.close();
    }

    public ObservableList<SimpleItem> tsvReader(File file) throws IOException {
        ObservableList<SimpleItem> inventory = FXCollections.observableArrayList();
        String data = Files.readString(file.toPath());
        String[] items = data.split("\n");
        for (int i = 1; i < items.length; i++) {
            String[] attributes = items[i].split("\t");
            SimpleItem newItem = new SimpleItem(attributes[2], attributes[1], attributes[0]);
            inventory.add(newItem);
        }
        return inventory;
    }

    public void HTMLFormat(ObservableList<SimpleItem> inventory, File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.println("""
                <style>
                table, th, td {
                \tborder: 1px solid black;
                \tborder-collapse: collapse;
                }
                th, td {
                \tpadding: 15px;
                 text-align: left;
                 }
                </style>""");
        writer.println("<table style=\"width:100%\">");
        writer.println("<tr>");
        writer.println("\t<th>Price</th>");
        writer.println("\t<th>Serial Number</th>");
        writer.println("\t<th>Name</th>");
        writer.println("</tr>");

        for (SimpleItem item : inventory) {
            writer.println("<tr>");
            writer.printf("\t<td>%s</th>\n", item.getPrice());
            writer.printf("\t<td>%s</th>\n", item.getSerialNumber());
            writer.printf("\t<td>%s</th>\n", item.getName());
            writer.println("</tr>");
        }
        writer.println("</table>");
        writer.close();
    }

    // JSOUP utilized to parse HTML Table
    public ObservableList<SimpleItem> readHTML(File file) throws IOException {
        ObservableList<SimpleItem> inventory = FXCollections.observableArrayList();
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
            SimpleItem newItem = new SimpleItem(name, serial, price);
            inventory.add(newItem);
        }
        return inventory;
    }


    // Methods for JSON Implementation

    // JSON files can become serializable with ArrayList, cannot be ObservableList
    public String serializeInventory(ObservableList<SimpleItem> inventory) {
        ArrayList<Item> arrayList = new ArrayList<>();
        for (SimpleItem item : inventory) {
            arrayList.add(new Item(item.getName(), item.getSerialNumber(), item.getPrice()));
        }
        Gson gson = new Gson();
        return gson.toJson(arrayList);
    }


    // Content to method is passed from serializeInventory() in order to create json content
    public void saveTexttoFile(String content, File file) throws FileNotFoundException {
        PrintWriter writer;
        writer = new PrintWriter(file);
        writer.write("{\"Inventory\" : " + content + "}");
        writer.close();
    }


    // Json deserialization from Assignment 3 exercise 44
    public ObservableList<SimpleItem> deserialize(File file) {
        ObservableList<SimpleItem> inventory = FXCollections.observableArrayList();
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

                SimpleItem newItem = new SimpleItem(name, serial, price);
                inventory.add(newItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventory;
    }


}
