package edu.sdccd.cisc191.template;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameNotification {

    /**
     * GameNotification class is a multipurpose notification/alert that uses two strings to display a popup.
     * I thought it should be used for when the game ends/winner is declared.
     * It can be called like this:
     *
     *              new GameNotification("Header Text", "Message Text");
     *              or
     *              new GameNotification("Game Over", getCurrentTurn() + " wins!");
     *
     * @param title = String from whenever GameNotification is called.
     * @param message = String from whenever GameNotification is called.
     */
    //Constructor to display a popup with a custom title and message
    public GameNotification(String title, String message) {
        showPopup(title, message); //Automatically show popup when class is instantiated
    }

    // Method to display the notification popup
    public void showPopup(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION); //Create an information popup
        alert.setTitle(title); //Set the title of the popup
        alert.setHeaderText(null); //No header text, can be changed if you want a header?
        alert.setContentText(message); //Set the message/content of the popup

        alert.showAndWait(); //Show the popup and wait for the user to close it
    }
}
