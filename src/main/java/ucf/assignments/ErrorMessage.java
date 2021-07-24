/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Karina Narotam
 */

package ucf.assignments;

import javafx.scene.control.Alert;

// Alerts utilized when user inputs invalid data
public class ErrorMessage {

    public static void showErrorAlert(String title, String text)  {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
