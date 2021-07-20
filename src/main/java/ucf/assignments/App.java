/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */

/*
Each inventory item shall have a value representing its monetary value in US dollars
Each inventory item shall have a unique serial number in the format of XXXXXXXXXX where X can be either a letter or digit
Each inventory item shall have a name between 2 and 256 characters in length (inclusive)
The user shall be able to add a new inventory item
The application shall display an error message if the user enters an existing serial number for the new item
The user shall be able to remove an existing inventory item
The user shall be able to edit the value of an existing inventory item
The user shall be able to edit the serial number of an existing inventory item
The application shall prevent the user from duplicating the serial number
The user shall be able to edit the name of an existing inventory item
The user shall be able to sort the inventory items by value
The user shall be able to sort inventory items by serial number
The user shall be able to sort inventory items by name
The user shall be able to search for an inventory item by serial number
The user shall be able to search for an inventory item by name
The user shall be able to save their inventory items to a file
The user shall be able to select the file format from among the following set of options: TSV (tab-separated value), HTML, JSON
TSV files shall shall list one inventory item per line, separate each field within an inventory item using a tab character, and end with the extension .txt
HTML files shall contain valid HTML and end with the extension .html
JSON files shall contain valid JSON and end with the extension .json
The user shall provide the file name and file location of the file to save
The user shall be able to load inventory items from a file that was previously created by the application.
The user shall provide the file name and file location of the file to load
 */



package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("InventoryTracker.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Tracker");
        stage.setScene(scene);
        stage.show();
    }

}

