/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */
package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

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
    }

    public ObservableList<SimpleItem> getList() {
        return inventory;
    }


    public void deleteSelectedClicked(ActionEvent actionEvent) {

    }

    @FXML
    public void addNewClicked(ActionEvent actionEvent) throws IOException {
        inventory = methods.addItem(nameTextfield.getText(), serialTextField.getText(), priceTextfield.getText(), inventory);
        tableView.setItems(inventory);
    }
}
