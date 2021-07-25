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
    public ObservableList<SimpleItem> addItem(String name, String serial, String price, ObservableList<SimpleItem> inventory) {
        SimpleItem newItem = new SimpleItem(name, serial, price);
        if (!checkPrice(price, newItem)) {
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter a value in US dollars");
        } else if (!checkName(name)) {
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter name within 2-256 characters");
        } else if (!checkSerialUnique(serial, inventory)) {
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter a unique serial number");
        } else if (!checkSerialFormatting(serial)) {
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter alphanumeric serial number with 10 characters");
        } else {
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


    // Print writer used to write to tsv file
    public void tsvFormat(ObservableList<SimpleItem> inventory, File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.printf("%-10s\t%-15s\t%-75s%n", "Price", "Serial Number", "Name");
        for (SimpleItem aItem : inventory) {
            writer.printf("%-10s\t%-15s\t%-75s%n", aItem.getPrice(), aItem.getSerialNumber(), aItem.getName());
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
        writer.println("<style>\ntable, th, td {\n\tborder: 1px solid black;\n\tborder-collapse: collapse;" +
                "\n}\nth, td {\n\tpadding: 15px;\n text-align: left;\n }\n</style>");
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
        ArrayList<Item> arrayList = new ArrayList();
        for (int i = 0; i < inventory.size(); i++) {
            arrayList.add(new Item(inventory.get(i).getName(), inventory.get(i).getSerialNumber(), inventory.get(i).getPrice()));
        }
        Gson gson = new Gson();
        String arrayJson = gson.toJson(arrayList);
        return arrayJson;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventory;
    }


}
