/*
Day 0 Meeting:
    establish premise for game and discuss time for next meeting
    notes: setup github for project

Day 1 Meeting:
    github - https://github.com/CobaltyNSalty/CS208_P4.git
    meeting held @ 11 on 11/11 for 30 minutes
    Chris - implement basic class structure from initial UML
    Kevin - implement basic layout for game window
    Josue - determine and share how animation will work for later implementation
    notes: hold 2nd meeting over the weekend
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    JFrame frame;

    public Main() {
      
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Controller.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        primaryStage.setTitle("Catch me if you can!");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {

    }
    private void createGameWindow() {
        // components
        frame = new JFrame("Tag me if you can!");
        JPanel controlPanel = new JPanel(); // panel to hold display data like printing the hashtable
        JPanel gamePanel = new JPanel(); // where the game will take place
        JPanel menuPanel = new JPanel(); // panel to put buttons to control the flow of the game
        JButton refresh = new JButton("New Game");

        // component settings
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH); // FULLSCREEN GAME WINDOW

        // compose GUI
        menuPanel.add(refresh);
        frame.add(menuPanel, BorderLayout.NORTH);   // menu on top
        frame.add(controlPanel, BorderLayout.EAST); // display on right
        frame.add(gamePanel, BorderLayout.WEST);    // game on left

        // Nominal settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}