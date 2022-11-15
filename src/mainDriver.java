/* mainDriver.java - main driver for the game, builds the board and handles the game logic.
 *
 * */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


public class mainDriver extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Init rooms
        ArrayList<Pane> roomList = new ArrayList<>();
        Pane topLeftRoom = new Pane();
        Pane topRightRoom = new Pane();
        Pane bottomLeftRoom = new Pane();
        Pane bottomRightRoom = new Pane();
        roomList.add(topLeftRoom);
        roomList.add(topRightRoom);
        roomList.add(bottomLeftRoom);
        roomList.add(bottomRightRoom);

        for(Pane p : roomList) {
            p.setPrefSize(400, 300);
            p.setMaxSize(400, 300);
            p.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ff2a21; -fx-border-width: 2px; " + "-fx-border-style: solid;");
        }

        // Init teleporters
        ArrayList<Teleporter> teleList = new ArrayList<>();
        Teleporter teleport1 = new Teleporter(175, 25, 20, Color.BLUE, 1);
        Teleporter teleport2 = new Teleporter(175, 25, 20, Color.BLUE, 1);
        Teleporter teleport3 = new Teleporter(175, 25, 20, Color.BLUE, 1);
        Teleporter teleport4 = new Teleporter(175, 25, 20, Color.BLUE, 1);
        teleList.add(teleport1);
        teleList.add(teleport2);
        teleList.add(teleport3);
        teleList.add(teleport4);

        for(Teleporter tele : teleList) {
            tele.setLayoutX(tele.getCenterX());
            tele.setLayoutY(tele.getCenterY());
        }

        // init players
        int numRunner = 100;
        Random rng = new Random();
        ArrayList<Runner> runnerList = new ArrayList<>();
        for(int i = 0; i < numRunner; i++) {
            Runner r = new Runner(25, 25, 10, Color.RED, i+1);
            r.setCenterX(rng.nextInt(187)+7);
            r.setCenterY(rng.nextInt(134)+7);
            runnerList.add(r);

            int dir = rng.nextInt(3);
            if(dir > 1) {
                r.setSpeedX(r.getSpeedX()*-1);
            }
            dir = rng.nextInt(3);
            if(dir > 1) {
                r.setSpeedY(r.getSpeedX()*-1);
            }


            r.setLayoutX(r.getCenterX());
            r.setLayoutY(r.getCenterY());
        }

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

        //creating the horizontal container for the bottom left and bottom right rooms and adding it to vertContainer
        HBox bottomRoomsContainer = new HBox();
        bottomRoomsContainer.setPrefSize(800, 300);
        vertContainer.getChildren().add(bottomRoomsContainer);

        //adding the two top rooms to topRoomsContainer
        topRoomsContainer.getChildren().addAll(topLeftRoom, topRightRoom);
        //adding the two bottom rooms to bottomRoomsContainer
        bottomRoomsContainer.getChildren().addAll(bottomLeftRoom, bottomRightRoom);

        // add players to rooms
        int roomIndex = 0;
        for(Runner runr : runnerList) {
            switch(roomIndex) {
                case 0:
                    runr.setFill(Color.AQUA);
                    break;
                case 1:
                    runr.setFill(Color.FORESTGREEN);
                    break;
                case 2:
                    runr.setFill(Color.CRIMSON);
                    break;
                case 3:
                    runr.setFill(Color.DARKVIOLET);
                    break;
            }
            roomList.get(roomIndex).getChildren().add(runr);
            roomIndex++;
            if(roomIndex == 4) {
                roomIndex = 0;
            }
        }

        // add teleporters to rooms
        topLeftRoom.getChildren().add(teleport1);
        topRightRoom.getChildren().add(teleport2);
        bottomLeftRoom.getChildren().add(teleport3);
        bottomRightRoom.getChildren().add(teleport4);

        //event handler for the start button
        startButton.setOnAction(e -> {

            //creating the timeline for the game loop
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {


                @Override
                public void handle(ActionEvent t) {

                    Bounds bounds = topLeftRoom.getLayoutBounds();
                    ArrayList<Runner> teleportMe = new ArrayList<>();

                    for(Runner runner : runnerList) {
                        // ___ update position ___
                        runner.setLayoutX(runner.getLayoutX() + runner.getSpeedX());
                        runner.setLayoutY(runner.getLayoutY() + runner.getSpeedY());

                        if (    runner.getLayoutX() <= (bounds.getMinX() + ((runner.getRadius()) - runner.getCenterX())) ||
                                runner.getLayoutX() >= (bounds.getMaxX() - ((runner.getRadius()) + runner.getCenterX()))  ) {
                            runner.setSpeedX(runner.getSpeedX()*-1);
                        }

                        //if the ball reaches the bottom or top border make the step negative
                        if (    runner.getLayoutY() <= (bounds.getMinY() + ((runner.getRadius()) - runner.getCenterY())) ||
                                runner.getLayoutY() >= (bounds.getMaxY() - ((runner.getRadius()) + runner.getCenterY()))  ) {
                            runner.setSpeedY(runner.getSpeedY()*-1);
                        }

                        // ___ check for collisions ___
                        for(Teleporter tele : teleList) {
                            if (Circle.intersect(runner, tele).getBoundsInLocal().getWidth() != -1) {
                                // if intersect shape contains any width => intersection => move Runner
                                teleportMe.add(runner);
                            }
                        }

                    }

                    if(teleportMe.size() > 0) {
                        // for each runner in teleportMe
                        for (int b = 0; b < teleportMe.size(); b++) {
                            boolean teleportComplete = false;

                            // for each room
                            for (int a = 3; a >= 0; a--) {
                                    // check if runner is within room
                                if (roomList.get(a).getChildren().contains(teleportMe.get(b))) {
                                    if(!teleportComplete) {
                                        // remove runner from room
                                        roomList.get(a).getChildren().remove(teleportMe.get(b));
                                        // add runner to next room
                                        switch (a) {
                                            case 0:
                                                roomList.get(1).getChildren().add(teleportMe.get(b));
                                                break;

                                            case 1:
                                                roomList.get(2).getChildren().add(teleportMe.get(b));
                                                break;

                                            case 2:
                                                roomList.get(3).getChildren().add(teleportMe.get(b));
                                                break;

                                            case 3:
                                                roomList.get(0).getChildren().add(teleportMe.get(b));
                                                break;
                                        }

                                        // starting position in new room
                                        teleportComplete = true; // only move each runner once
                                        teleportMe.get(b).setLayoutX(25);
                                        teleportMe.get(b).setLayoutY(25);
                                    }
                                }
                            }
                        }
                        teleportMe.clear();

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
