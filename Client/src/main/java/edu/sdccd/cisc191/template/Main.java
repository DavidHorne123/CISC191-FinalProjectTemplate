package edu.sdccd.cisc191.template;
import edu.sdccd.cisc191.template.PlayerData.PlayerDataManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The main class that will load player data and load the JavaFx UI.
 * @author Tim Tran
 * @version 1.0
 */
public class Main extends Application
{
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args)
    {
        PlayerDataManager manager = new PlayerDataManager();
        manager.loadPlayerData();
        launch(args);
        manager.savePlayerData();
        sendShutdownRequest();
    }

    /**
     *
     * @param stage the primary stage for this application, onto which
     * the application scene can be set. The primary stage will be embedded in
     * the browser if the application was launched as an applet.
     * Applications may create other stages, if needed, but they will not be
     * primary stages and will not be embedded in the browser.
     */
    public void start(Stage stage)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("Launcher.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("CISC191 Java Game");
            stage.show();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * The method sends a "SHUTDOWN" requests to a server, waits for a response
     * handles any exceptions that may occur
     */
    private static void sendShutdownRequest()
    {
        // Try catch block that makes a connection to the server
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()))
        {

            // Send shutdown request
            ServerRequest request = new ServerRequest("SHUTDOWN");
            outputStream.writeObject(request);
            outputStream.flush();
            System.out.println((String) inputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Shutting Down...");
        }
    }
} //end class Server