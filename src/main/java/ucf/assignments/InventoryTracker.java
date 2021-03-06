/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryTracker implements Initializable {
    InventoryTrackerMethods methods = new InventoryTrackerMethods();

    @FXML private TableView<SimpleItem> tableView;
    @FXML private TableColumn<SimpleItem, String> serialColumn;
    @FXML private TableColumn<SimpleItem, String> nameColumn;
    @FXML private TableColumn<SimpleItem, String> priceColumn;
    @FXML private TextField serialTextField;
    @FXML private TextField nameTextfield;
    @FXML private TextField priceTextfield;
    @FXML private TextField searchField;
    @FXML MenuBar listMenu;

    private ObservableList<SimpleItem> inventory = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        serialColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        tableView.setItems(getList());
        tableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        serialColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        inventory.addAll(getList());

    }

    // Allows description field to be edited
    // Character constraints added
    @FXML
    public void changeNameCellEvent(TableColumn.CellEditEvent cell) {
        SimpleItem cellSelected = tableView.getSelectionModel().getSelectedItem();
        String name = cell.getNewValue().toString();
        boolean nameCheck = methods.checkName(name);
        if (nameCheck)
            cellSelected.setName(cell.getNewValue().toString());
        else
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter name within 2-256 characters");
        tableView.refresh();
    }

    // Allows date field to be edited
    @FXML
    public void changePriceCellEvent(TableColumn.CellEditEvent cell) {
        String price = cell.getNewValue().toString();
        SimpleItem cellSelected = tableView.getSelectionModel().getSelectedItem();
        boolean priceCheck = methods.checkPrice(price, cellSelected);
        if (!priceCheck)
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter a value in US dollars");
        tableView.refresh();
    }

    // Allows serial field to be edited - checks against constraints
    @FXML
    public void changeSerialCellEvent(TableColumn.CellEditEvent cell) {
        String serial = cell.getNewValue().toString();
        SimpleItem cellSelected = tableView.getSelectionModel().getSelectedItem();
        boolean format = methods.checkSerialFormatting(serial);
        boolean unique = methods.checkSerialUnique(serial, inventory);

        if (serial.equals(cell.getOldValue())) {
            cellSelected.setSerialNumber(serial);
        }
        else if (format && unique) {
            cellSelected.setSerialNumber(serial);
        }
        else if (!unique){
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter a unique serial number");
        }
        else{
            ErrorMessage.showErrorAlert("Invalid Input", "Please enter serial with 10 characters");
        }

        tableView.refresh();
    }

    public ObservableList<SimpleItem> getList() {
        return inventory;
    }

    @FXML
    public void deleteSelectedClicked(ActionEvent actionEvent) {
        SimpleItem selected = tableView.getSelectionModel().getSelectedItem();
        inventory = methods.deleteItem(selected, inventory);
        tableView.refresh();
    }

    // Adds new item to inventory, if valid input
    @FXML
    public void addNewClicked(ActionEvent actionEvent) {
        String result = methods.addItem(nameTextfield.getText(), serialTextField.getText(), priceTextfield.getText(), inventory);
        if (!result.equals("")) {
            ErrorMessage.showErrorAlert("Invalid Input", result);
        }
        tableView.refresh();
    }

    @FXML
    public void saveTSV(ActionEvent actionEvent) throws FileNotFoundException {
        // File Chooser
        Stage stage = (Stage)listMenu.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as TSV");
        // Extension
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("TSV File", "*.txt");
        fileChooser.getExtensionFilters().add(extension);
        File theFile = fileChooser.showSaveDialog(stage);

        methods.tsvFormat(inventory, theFile);
    }

    @FXML
    public void saveHTML(ActionEvent actionEvent) throws FileNotFoundException {
        // File Chooser
        Stage stage = (Stage)listMenu.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as HTML");
        // Extension
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("HTML File", "*.html");
        fileChooser.getExtensionFilters().add(extension);
        File theFile = fileChooser.showSaveDialog(stage);

        methods.HTMLFormat(inventory, theFile);
    }

    @FXML
    public void saveJSON(ActionEvent actionEvent) throws FileNotFoundException {
        // File Chooser
        Stage stage = (Stage)listMenu.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as JSON");

        // Ensure file has .json extension
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Json File", "*.json");
        fileChooser.getExtensionFilters().add(extension);
        File theFile = fileChooser.showSaveDialog(stage);

        // Serialize observable list into json formatting
        String jsonString = methods.serializeInventory(inventory);
        methods.saveTexttoFile(jsonString, theFile);
    }

    @FXML
    public void loadTSV(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)listMenu.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load TSV File");

        // Ensure .json files are highlighted for upload
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("TSV File", "*.txt");
        fileChooser.getExtensionFilters().add(extension);
        File theFile = fileChooser.showOpenDialog(stage);
        // update inventory
        inventory = methods.tsvReader(theFile);
        tableView.setItems(inventory);
        tableView.refresh();

    }

    @FXML
    public void loadHTML(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)listMenu.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load HTML File");

        // Ensure .json files are highlighted for upload
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("HTML File", "*.html");
        fileChooser.getExtensionFilters().add(extension);
        File theFile = fileChooser.showOpenDialog(stage);
        // update inventory
        inventory = methods.readHTML(theFile);
        tableView.setItems(inventory);
        tableView.refresh();
    }

    @FXML
    public void loadJSON(ActionEvent actionEvent) {
        // parse function from InventoryTrackerMethods
        // File Chooser
        Stage stage = (Stage) listMenu.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load JSON File");

        // Ensure .json files are highlighted for upload
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Json File", "*.json");
        fileChooser.getExtensionFilters().add(extension);
        File theFile = fileChooser.showOpenDialog(stage);

        // Update inventory
        inventory = methods.deserialize(theFile);
        tableView.setItems(inventory);
        tableView.refresh();
    }

    public void inputtedSearch(KeyEvent keyEvent) {
        ObservableList<SimpleItem> filtered;
        String searched = searchField.getText();
        filtered = methods.searchItem(searched, inventory);
        // update inventory
        tableView.setItems(filtered);
        tableView.refresh();
    }

}
