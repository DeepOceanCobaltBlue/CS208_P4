/* mainDriver.java - main driver for the game, builds the board and handles the game logic.
 *
 * */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        Scene scene = new Scene(basePane, 800, 750);

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
        Circle player1 = new Circle(20, Color.RED);
        player1.setLayoutX(26);
        player1.setLayoutY(26);
        Circle teleport1 = new Circle(20, Color.BLUE);
        teleport1.setLayoutX(361);
        teleport1.setLayoutY(39);
        topLeftRoom.getChildren().addAll(player1, teleport1);

        //topRightRoom with just a teleport currently
        Pane topRightRoom = new Pane();
        topRightRoom.setPrefSize(400, 300);
        topRightRoom.setMaxSize(400, 300);
        topRightRoom.setStyle("-fx-background-color: #000000; -fx-border-color: #ff2a21; -fx-border-width: 2px; " + "-fx-border-style: solid;");
        Circle teleport2 = new Circle(20, Color.BLUE);
        teleport2.setLayoutX(361);
        teleport2.setLayoutY(39);
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
        Circle teleport3 = new Circle(20, Color.BLUE);
        teleport3.setLayoutX(361);
        teleport3.setLayoutY(39);
        bottomLeftRoom.getChildren().add(teleport3);

        Pane bottomRightRoom = new Pane();
        bottomRightRoom.setPrefSize(400, 300);
        bottomRightRoom.setMaxSize(400, 300);
        bottomRightRoom.setStyle("-fx-background-color: #000000; -fx-border-color: #ff2a21; -fx-border-width: 2px; " + "-fx-border-style: solid;");
        Circle teleport4 = new Circle(20, Color.BLUE);
        teleport4.setLayoutX(361);
        teleport4.setLayoutY(39);
        bottomRightRoom.getChildren().add(teleport4);

        //adding the two bottom rooms to bottomRoomsContainer
        bottomRoomsContainer.getChildren().addAll(bottomLeftRoom, bottomRightRoom);


        startButton.setOnAction(e -> {

            Timeline timeline = new Timeline( new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {

                double x = 26;
                double y = 26;
                double dx = 1;
                double dy = 1;

                @Override
                public void handle(ActionEvent t) {
                    //move the ball
                    x += dx;
                    y += dy;
                    player1.setLayoutX(x);
                    player1.setLayoutY(y);

                    //check if the ball moves over the bounds
                    //if the ball reaches the left or right border make the step negative
                    if(x > 375 || (x - (player1.getBoundsInLocal().getWidth() / 2)) < 0) {
                        dx = -dx;
                    }

                    //if the ball reaches the bottom or top border make the step negative
                    if(y > 275 || (y- (player1.getBoundsInLocal().getWidth() / 2)) < 0) {
                        dy = -dy;
                    }
                }
            }));


            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();



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
