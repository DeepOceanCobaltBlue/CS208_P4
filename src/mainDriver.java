/* CS208.004 Data Structures
 * P4 - Hashing
 * mainDriver.java - Driver program for the digital game of 'Tag'. Uses javaFX to build and display the game
 * animations, and uses a hash table to store the players and their locations.
 *
 * @author Kevin Pinto - Wrote the game board and components in javaFX, the core game loop via javaFX's animation
 * Timelines, and initial player movement/collision detection. Also wrote the 'win' and 'lose' conditions, and the
 * initial implementation of the hash map.
 *
 * @modified by Christopher Peters
 *
 * */

import javafx.animation.AnimationTimer;
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
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        topLeftRoom.setId("topLeftRoom");
        topRightRoom.setId("topRightRoom");
        bottomLeftRoom.setId("bottomLeftRoom");
        bottomRightRoom.setId("bottomRightRoom");

        // styling rooms
        for (Pane p : roomList) {
            p.setPrefSize(400, 300);
            p.setMaxSize(400, 300);
            p.setStyle("-fx-background-color: #ffffff; -fx-border-color: rgb(0,0,0); -fx-border-width: 2px; " + "-fx-border-style: solid;");
        }

        // Init teleporters
        ArrayList<Teleporter> teleList = new ArrayList<>();
        Teleporter teleport1 = new Teleporter(175, 25, 20, Color.BLUE, 1);
        Teleporter teleport2 = new Teleporter(175, 25, 20, Color.BLUE, 2);
        Teleporter teleport3 = new Teleporter(175, 25, 20, Color.BLUE, 3);
        Teleporter teleport4 = new Teleporter(175, 25, 20, Color.BLUE, 4);
        teleList.add(teleport1);
        teleList.add(teleport2);
        teleList.add(teleport3);
        teleList.add(teleport4);

        for (Teleporter tele : teleList) {
            tele.setLayoutX(tele.getCenterX());
            tele.setLayoutY(tele.getCenterY());
        }

        // init players
        int numRunner = 100;
        Random rng = new Random();
        ArrayList<Runner> runnerList = new ArrayList<>();
        for (int i = 0; i < numRunner; i++) {
            // create and add runner to list
            Runner r = new Runner(25, 25, 10, Color.RED, i + 1);
            runnerList.add(r);

            // randomize starting position
            r.setCenterX(rng.nextInt(187) + 7);
            r.setCenterY(rng.nextInt(134) + 7);

            // randomize starting direction
            int dir = rng.nextInt(3);
            if (dir > 1) {
                r.setSpeedX(r.getSpeedX() * -1);
            }
            dir = rng.nextInt(3);
            if (dir > 1) {
                r.setSpeedY(r.getSpeedX() * -1);
            }

            r.setLayoutX(r.getCenterX());
            r.setLayoutY(r.getCenterY());
        }

        // init taggers
        ArrayList<Tagger> taggersList = new ArrayList<>();
        for (int c = 0; c < 4; c++) {
            // 1 per room
            Tagger t = new Tagger(25, 25, 10, Color.GOLDENROD, c + 1);
            taggersList.add(t);

            // randomize starting position
            t.setCenterX(rng.nextInt(187) + 7);
            t.setCenterY(rng.nextInt(134) + 7);

            // randomize starting direction
            int dir2 = rng.nextInt(3);
            if (dir2 > 1) {
                t.setSpeedX(t.getSpeedX() * -1);
            }
            dir2 = rng.nextInt(3);
            if (dir2 > 1) {
                t.setSpeedY(t.getSpeedX() * -1);
            }

            t.setLayoutX(t.getCenterX());
            t.setLayoutY(t.getCenterY());
        }

        // basePane is the highest level pane, the root node of the scene graph
        Pane basePane = new Pane();
        Scene scene = new Scene(basePane, 1200, 700);

        // vertContainer is the vertical container for the toolbar and the two horizontal containers
        VBox vertContainer = new VBox();
        vertContainer.setPrefSize(800, 700);
        basePane.getChildren().add(vertContainer);

        // toolBar at the top of the screen, contains the start and exit buttons
        ToolBar toolBar = new ToolBar();
        toolBar.setPrefSize(800, 36);
        Button startButton = new Button("Start Game");
        Button exitButton = new Button("Exit Game");
        Button pauseButton = new Button("Pause");
        exitButton.setTranslateX(630);
        toolBar.getItems().addAll(startButton, exitButton, pauseButton);
        vertContainer.getChildren().add(toolBar);

        // adding a label to display the elapsed time on the toolbar
        Label timeLabel = new Label("Time elapsed: 0");
        timeLabel.setTranslateX(150);
        toolBar.getItems().add(timeLabel);

        // topRoomsContainer is the horizontal container for the top left and top right rooms
        HBox topRoomsContainer = new HBox();
        topRoomsContainer.setPrefSize(800, 300);
        vertContainer.getChildren().add(topRoomsContainer);

        // creating the horizontal container for the bottom left and bottom right rooms and adding it to vertContainer
        HBox bottomRoomsContainer = new HBox();
        bottomRoomsContainer.setPrefSize(800, 300);
        vertContainer.getChildren().add(bottomRoomsContainer);

        Text resultsText = new Text("Results: ");
        resultsText.setFont(new Font(20));

        TextArea results = new TextArea();
        results.setPrefSize(300, 50);
        vertContainer.getChildren().addAll(resultsText, results);

        VBox statContainer = new VBox();
        statContainer.setPrefSize(400, 700);
        statContainer.setLayoutX(800);
        statContainer.setSpacing(10);
        Text text1 = new Text("Top-Right count: ");
        Text text2 = new Text("Top-Left count: ");
        Text text3 = new Text("Bottom-Right count: ");
        Text text4 = new Text("Bottom-Left count: ");
        text1.setFont(new Font(20));
        text2.setFont(new Font(20));
        text3.setFont(new Font(20));
        text4.setFont(new Font(20));
        TextArea textArea1 = new TextArea();
        TextArea textArea2 = new TextArea();
        TextArea textArea3 = new TextArea();
        TextArea textArea4 = new TextArea();
        textArea1.setPrefRowCount(10);
        textArea1.setWrapText(true);
        textArea2.setPrefRowCount(10);
        textArea2.setWrapText(true);
        textArea3.setPrefRowCount(10);
        textArea3.setWrapText(true);
        textArea4.setPrefRowCount(10);
        textArea4.setWrapText(true);
        statContainer.getChildren().addAll(text1, textArea1, text2, textArea2, text3, textArea3, text4, textArea4);
        basePane.getChildren().add(statContainer);

        // adding the two top rooms to topRoomsContainer
        topRoomsContainer.getChildren().addAll(topLeftRoom, topRightRoom);
        // adding the two bottom rooms to bottomRoomsContainer
        bottomRoomsContainer.getChildren().addAll(bottomLeftRoom, bottomRightRoom);

        Button invisibleButton = new Button();
        invisibleButton.setDisable(true);
        invisibleButton.setVisible(false);
        vertContainer.getChildren().add(invisibleButton);

        /**
         * playerRoomMap maps the runners to the room they are in.
         * Key = runner hashcode (Integer) *replace with Josue's hashcode*
         * Value = room Id (String)
         * */
        ObservableMap<Integer, String> playerRoomMap = FXCollections.observableHashMap();
        GameMap<NPC, Integer> playerMap = new GameMap<>();

        playerRoomMap.addListener((MapChangeListener<Integer, String>) change -> {
            if (playerMap.getRoomCount(5) == 99) {
                invisibleButton.fire();
                // pauseButton.fire();
                String winner = "";
                for (int i = 1; i < 5; i++) {
                    if (!(playerMap.getRoom(i).equals(""))) {
                        winner = playerMap.getRoom(i);
                        System.out.println(winner);
                    }
                }
            }
        });

        // add players to rooms by cycling through rooms
        int roomIndex = 0;
        for (Runner runr : runnerList) {
            // each runner gets a unique color based on starting room
            switch (roomIndex) {
                case 0:
                    runr.setFill(Color.AQUA);
                    playerRoomMap.put(runr.hashCode(), topLeftRoom.getId());
                    playerMap.put(runr, 1);
                    break;
                case 1:
                    runr.setFill(Color.FORESTGREEN);
                    playerRoomMap.put(runr.hashCode(), topRightRoom.getId());
                    playerMap.put(runr, 2);
                    break;
                case 2:
                    runr.setFill(Color.CRIMSON);
                    playerRoomMap.put(runr.hashCode(), bottomLeftRoom.getId());
                    playerMap.put(runr, 3);
                    break;
                case 3:
                    runr.setFill(Color.DARKVIOLET);
                    playerRoomMap.put(runr.hashCode(), bottomRightRoom.getId());
                    playerMap.put(runr, 4);
                    break;
            }
            roomList.get(roomIndex).getChildren().add(runr);
            roomIndex++;
            if (roomIndex == 4) {
                roomIndex = 0;
            }
        }

        // add teleporters to rooms
        topLeftRoom.getChildren().add(teleport1);
        topRightRoom.getChildren().add(teleport2);
        bottomLeftRoom.getChildren().add(teleport3);
        bottomRightRoom.getChildren().add(teleport4);

        // add taggers to rooms
        topLeftRoom.getChildren().add(taggersList.get(0));
        topRightRoom.getChildren().add(taggersList.get(1));
        bottomLeftRoom.getChildren().add(taggersList.get(2));
        bottomRightRoom.getChildren().add(taggersList.get(3));

        // iterator List for NPC's
        ArrayList<NPC> npcList = new ArrayList<>();
        npcList.addAll(runnerList);
        npcList.addAll(taggersList);


        // prints the hashcode of the runner and the room they are in at the start of the game
        playerRoomMap.forEach((k, v) -> System.out.println(k + " : " + v));

        //event handler for the start button
        startButton.setOnAction(e -> {

            // prevents the user from starting the game multiple times
            startButton.setDisable(true);

/**
 * Creating a timer to track elapsed time and display it on the game board.
 */
            final double[] time = {0};
            Timeline timer = new Timeline(new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ae) {
                    time[0]++;
                    timeLabel.setText("Time elapsed: " + time[0] / 10); // displays as 0.0

                }
            }));

            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();


            //creating the timeline for the game loop
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {


                @Override
                public void handle(ActionEvent t) {

                    Bounds bounds = topLeftRoom.getLayoutBounds();
                    ArrayList<Runner> teleportMe = new ArrayList<>();
                    ArrayList<Runner> gotTaggedList = new ArrayList<>();

                    // ___ update position ___
                    for (NPC npc : npcList) {
                        npc.setLayoutX(npc.getLayoutX() + npc.getSpeedX());
                        npc.setLayoutY(npc.getLayoutY() + npc.getSpeedY());

                        // if runner reaches left or right border => invert horizontal direction
                        if (npc.getLayoutX() <= (bounds.getMinX() + ((npc.getRadius()) - npc.getCenterX())) || npc.getLayoutX() >= (bounds.getMaxX() - ((npc.getRadius()) + npc.getCenterX()))) {
                            npc.setSpeedX(npc.getSpeedX() * -1);
                        }

                        // if runner reaches the bottom or top border => invert vertical direction
                        if (npc.getLayoutY() <= (bounds.getMinY() + ((npc.getRadius()) - npc.getCenterY())) || npc.getLayoutY() >= (bounds.getMaxY() - ((npc.getRadius()) + npc.getCenterY()))) {
                            npc.setSpeedY(npc.getSpeedY() * -1);
                        }

                        // ___ check for collisions ___
                        for (Teleporter tele : teleList) {
                            if (Circle.intersect(npc, tele).getBoundsInLocal().getWidth() != -1) {
                                // if intersect shape contains any width => intersection => move Runner
                                if (npc.getCanTeleport()) {
                                    teleportMe.add((Runner) npc);
                                    textArea1.setText(playerMap.getRoom(1));
                                    textArea2.setText(playerMap.getRoom(2));
                                    textArea3.setText(playerMap.getRoom(3));
                                    textArea4.setText(playerMap.getRoom(4));
                                    int unTagged = playerMap.getRoomCount(1) + playerMap.getRoomCount(2) + playerMap.getRoomCount(3) + playerMap.getRoomCount(4);
                                    text1.setText("Runners: " + unTagged + " Tagged: " + playerMap.getRoomCount(5) + "\nTop-Left: " + playerMap.getRoomCount(1));
                                    text2.setText("Top-Right: " + playerMap.getRoomCount(2));
                                    text3.setText("Bottom-Left: " + playerMap.getRoomCount(3));
                                    text4.setText("Bottom-Right: " + playerMap.getRoomCount(4));
                                }
                            }
                        }


                        for (Tagger tagr : taggersList) {
                            if (Circle.intersect(npc, tagr).getBoundsInLocal().getWidth() != -1) {
                                // if intersect shape contains any width => intersection => move Runner
                                if (npc.getCanTeleport()) {
                                    gotTaggedList.add((Runner) npc);
                                }
                            }
                        }

                    }


                    // if there is a runner to teleport
                    if (teleportMe.size() > 0) {

                        // for each runner in teleportMe
                        for (int b = 0; b < teleportMe.size(); b++) {
                            boolean teleportComplete = false;

                            // for each room
                            for (int a = 3; a >= 0; a--) {
                                // check if runner is within room
                                if (roomList.get(a).getChildren().contains(teleportMe.get(b))) {
                                    if (!teleportComplete) { // if this runner has not already teleported
                                        // remove runner from current room
                                        roomList.get(a).getChildren().remove(teleportMe.get(b));
                                        // add runner to next room and update playerRoomMap
                                        switch (a) {
                                            case 0:
                                                roomList.get(1).getChildren().add(teleportMe.get(b));
                                                playerRoomMap.put(teleportMe.get(b).hashCode(), roomList.get(1).getId());
                                                playerMap.put(teleportMe.get(b), 2);
                                                break;

                                            case 1:
                                                roomList.get(2).getChildren().add(teleportMe.get(b));
                                                playerRoomMap.put(teleportMe.get(b).hashCode(), roomList.get(2).getId());
                                                playerMap.put(teleportMe.get(b), 3);
                                                break;

                                            case 2:
                                                roomList.get(3).getChildren().add(teleportMe.get(b));
                                                playerRoomMap.put(teleportMe.get(b).hashCode(), roomList.get(3).getId());
                                                playerMap.put(teleportMe.get(b), 4);
                                                break;

                                            case 3:
                                                roomList.get(0).getChildren().add(teleportMe.get(b));
                                                playerRoomMap.put(teleportMe.get(b).hashCode(), roomList.get(0).getId());
                                                playerMap.put(teleportMe.get(b), 1);
                                                break;
                                        }

                                        // don't teleport the same runner twice
                                        teleportComplete = true;
                                        // starting position in new room
                                        teleportMe.get(b).setLayoutX(25);
                                    }
                                }
                            }
                        }
                        // clear tracker of runners that need to be teleported
                        teleportMe.clear();

                    }

                    // if a runner got tagged
                    if (gotTaggedList.size() > 0) {


                        // for each runner in gotTaggedList
                        for (int d = 0; d < gotTaggedList.size(); d++) {
                            boolean removalComplete = false;
                            // for each room
                            for (int e = 3; e >= 0; e--) {
                                // find which room contains runner who got tagged
                                if (roomList.get(e).getChildren().contains(gotTaggedList.get(d))) {
                                    if (!removalComplete) {
                                        // remove runner from current room
                                        roomList.get(e).getChildren().remove(gotTaggedList.get(d));
                                        // remove runner from playerRoomMap
                                        playerRoomMap.remove(gotTaggedList.get(d).hashCode());
                                        playerMap.put(gotTaggedList.get(d), 5);
                                        invisibleButton.fire();
                                        removalComplete = true;
                                    }
                                }
                            }
                        }
                        gotTaggedList.clear();
                    }

                }
            }));

            //set the timeline to loop indefinitely
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();


            //event handler for the pause button
            pauseButton.setOnAction(event -> {
                if (pauseButton.getText().equals("Pause")) {
                    timeline.pause();
                    timer.pause();
                    pauseButton.setText("Unpause");
                } else if (pauseButton.getText().equals("Unpause")) {
                    timeline.play();
                    timer.play();
                    pauseButton.setText("Pause");
                }
            });

            //event handler for the invisibleButton
            invisibleButton.setOnAction(event -> {
                if (playerMap.getRoomCount(5) == 99) {
                    timer.pause();
                    timeline.pause();
                    String winner = "";
                    for (int i = 1; i < 5; i++) {
                        if (!(playerMap.getRoom(i).equals(""))) {
                            winner = playerMap.getRoom(i);
                            System.out.println(winner);
                        }
                    }
                }
            });

        });//end of start method

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
