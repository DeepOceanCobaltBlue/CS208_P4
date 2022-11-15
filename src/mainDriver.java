/* mainDriver.java - main driver for the game, builds the board and handles the game logic.
 *
 * */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class mainDriver extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        //basePane is the highest level pane, the root node of the scene graph
        Pane basePane = new Pane();
        Scene scene = new Scene(basePane, 800, 700);

        //vertContainer is the vertical container for the toolbar and the two horizontal containers
        VBox vertContainer = new VBox();
        vertContainer.setPrefSize(800, 636);
        basePane.getChildren().add(vertContainer);

        //toolBar at the top of the screen, contains the start and exit buttons
        ToolBar toolBar = new ToolBar();
        toolBar.setPrefSize(800, 36);
        Button startButton = new Button("Start Game");
        Button exitButton = new Button("Exit Game");
        exitButton.setTranslateX(630);
        toolBar.getItems().addAll(startButton, exitButton);
        vertContainer.getChildren().add(toolBar);

        //topRoomsContainer is the horizontal container for the top left and top right rooms
        HBox topRoomsContainer = new HBox();
        topRoomsContainer.setPrefSize(800, 300);
        vertContainer.getChildren().add(topRoomsContainer);

        //topLeftRoom
        Pane topLeftRoom = new Pane();
        topLeftRoom.setPrefSize(400, 300);
        topLeftRoom.setMaxSize(400, 300);
        topLeftRoom.setStyle("-fx-background-color: #000000; -fx-border-color: #ff2a21; -fx-border-width: 2px; " + "-fx-border-style: solid;");

        //creating/adding the first 'teleport' and a first 'player' to the topLeftRoom as children
        Runner player1 = new Runner(12, 12, 10, Color.RED, 13);
        player1.setLayoutX(player1.getCenterX());
        player1.setLayoutY(player1.getCenterY());
        Teleporter teleport1 = new Teleporter(175, 25, 20, Color.BLUE, 1);
        teleport1.setLayoutX(teleport1.getCenterX());
        teleport1.setLayoutY(teleport1.getCenterY());


        Tagger tagger1 = new Tagger(25, 75, 15, Color.GREEN, 1);
        topLeftRoom.getChildren().addAll(player1, teleport1, tagger1);

        //topRightRoom with just a teleport currently
        Pane topRightRoom = new Pane();
        topRightRoom.setPrefSize(400, 300);
        topRightRoom.setMaxSize(400, 300);
        topRightRoom.setStyle("-fx-background-color: #000000; -fx-border-color: #ff2a21; -fx-border-width: 2px; " + "-fx-border-style: solid;");
        Teleporter teleport2 = new Teleporter(175, 25, 20, Color.BLUE, 1);
        teleport2.setLayoutX(teleport2.getCenterX());
        teleport2.setLayoutY(teleport2.getCenterY());
        topRightRoom.getChildren().add(teleport2);

        //adding the two top rooms to topRoomsContainer
        topRoomsContainer.getChildren().addAll(topLeftRoom, topRightRoom);

        //creating the horizontal container for the bottom left and bottom right rooms and adding it to vertContainer
        HBox bottomRoomsContainer = new HBox();
        bottomRoomsContainer.setPrefSize(800, 300);
        vertContainer.getChildren().add(bottomRoomsContainer);

        //creating bottom left and bottom right rooms and a teleport for each
        Pane bottomLeftRoom = new Pane();
        bottomLeftRoom.setPrefSize(400, 300);
        bottomLeftRoom.setMaxSize(400, 300);
        bottomLeftRoom.setStyle("-fx-background-color: #000000; -fx-border-color: #ff2a21; -fx-border-width: 2px; " + "-fx-border-style: solid;");
        Teleporter teleport3 = new Teleporter(175, 25, 20, Color.BLUE, 1);
        teleport3.setLayoutX(teleport3.getCenterX());
        teleport3.setLayoutY(teleport3.getCenterY());
        bottomLeftRoom.getChildren().add(teleport3);

        Pane bottomRightRoom = new Pane();
        bottomRightRoom.setPrefSize(400, 300);
        bottomRightRoom.setMaxSize(400, 300);
        bottomRightRoom.setStyle("-fx-background-color: #000000; -fx-border-color: #ff2a21; -fx-border-width: 2px; " + "-fx-border-style: solid;");
        Teleporter teleport4 = new Teleporter(175, 25, 20, Color.BLUE, 1);
        teleport4.setLayoutX(teleport4.getCenterX());
        teleport4.setLayoutY(teleport4.getCenterY());
        bottomRightRoom.getChildren().add(teleport4);

        //adding the two bottom rooms to bottomRoomsContainer
        bottomRoomsContainer.getChildren().addAll(bottomLeftRoom, bottomRightRoom);


        /**
         * ObservableMap testing
         * Key is the NPC object whether it be runner or tagger, value is the room it is in (Pane)
         * eventListener prints to console any changes to the map for debugging
         */
        ObservableMap<NPC, Pane> npcMap = FXCollections.observableHashMap();
        npcMap.put(player1, topLeftRoom);
        npcMap.put(tagger1, topLeftRoom);

        npcMap.addListener((MapChangeListener<NPC, Pane>) change -> {
            if (change.wasAdded()) {
                System.out.println("Added: " + change.getKey() + " -> " + change.getValueAdded());
            }
            if (change.wasRemoved()) {
                System.out.println("Removed: " + change.getKey() + " -> " + change.getValueRemoved());
            }
            System.out.println();
        });


        //event handler for the start button
        startButton.setOnAction(e -> {

            //creating the timeline for the game loop
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

                //player1 starting position and movement velocity
                double dx = 4; //velocity in the x direction
                double dy = 2; //velocity in the y direction

                @Override
                public void handle(ActionEvent t) {

                    //move the ball
                    player1.setLayoutX(player1.getLayoutX() + dx);
                    player1.setLayoutY(player1.getLayoutY() + dy);

//                    player1.setLayoutX(x);
//                    player1.setLayoutY(y);


                    Bounds bounds = topLeftRoom.getLayoutBounds();
                    System.out.println("bounds: " + bounds);
                    System.out.println(bounds.getMaxX() + " " + bounds.getMaxY());
                    if (player1.getLayoutX() <= (bounds.getMinX() + player1.getRadius()) ||
                            player1.getLayoutX() >= (bounds.getMaxX() - player1.getRadius())) {
                        dx = -dx;
                    }

                    //check if the ball hits a 'wall' and if so, reverse the velocity
                    //if the ball reaches the left or right border make the step negative
                    /*if (player1.getLayoutX() > 375 || (player1.getLayoutX() - (player1.getBoundsInLocal().getWidth() / 2)) < 0) {
                        dx = -dx;
                    }*/
/*                    if (player1.intersects(topLeftRoom.getBoundsInLocal())) {
                        dx = -dx;
                    }*/

                    //if the ball reaches the bottom or top border make the step negative
                    if (player1.getLayoutY() > 275 || (player1.getLayoutY() - (player1.getBoundsInLocal().getWidth() / 2)) < 0) {
                        dy = -dy;
                    }

                    //check if the ball hits a teleport and if so, teleport it to the next room
                    if (player1.getBoundsInParent().intersects(teleport1.getBoundsInParent())) {


                        if (npcMap.get(player1) == topLeftRoom) {
                            topLeftRoom.getChildren().remove(player1);
                            topRightRoom.getChildren().add(player1);
                            npcMap.replace(player1, topLeftRoom, topRightRoom);
//                            npcMap.put(player1, topRightRoom);
                        } /*else {
                            topLeftRoom.getChildren().add(player1);
                            npcMap.put(player1, topLeftRoom);
                        }*/
                        player1.relocate(25, 25);
                    }


                    if (player1.getBoundsInParent().intersects(teleport2.getBoundsInParent())) {

                        if (npcMap.get(player1) == topRightRoom) {
                            topRightRoom.getChildren().remove(player1);
                            bottomLeftRoom.getChildren().add(player1);
                            npcMap.replace(player1, topRightRoom, bottomLeftRoom);
                        }
//                        player1.relocate(25, 25);
                    }

                    if (player1.getBoundsInParent().intersects(teleport3.getBoundsInParent())) {

                        if (npcMap.get(player1) == bottomLeftRoom) {
                            bottomLeftRoom.getChildren().remove(player1);
                            bottomRightRoom.getChildren().add(player1);
                            npcMap.replace(player1, bottomLeftRoom, bottomRightRoom);
                        }
                        player1.relocate(25, 25);
                    }

                    if (player1.getBoundsInParent().intersects(teleport4.getBoundsInParent())) {

                        if (npcMap.get(player1) == bottomRightRoom) {
                            bottomRightRoom.getChildren().remove(player1);
                            topLeftRoom.getChildren().add(player1);
                            npcMap.replace(player1, bottomRightRoom, topLeftRoom);
                        }
                    }


                }
            }));

            //set the timeline to loop indefinitely
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();


        });//startButton

        //event handler for the exit button
        exitButton.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });


        primaryStage.setResizable(false);
        primaryStage.setTitle("Game of Tag!");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
