package battlestars.battleship;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.beans.EventHandler;
import java.io.File;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BattleshipRunner extends Application {

    private Random com = new Random();
    private Board playerBoard, enemyBoard;
    private HBox playerSide, enemySide;
    //private VBox right;
    private Label remainingShipsLabel, messageLabel;
    private boolean online = false;
    private boolean enemyMove = false;
    private int shipsLeft = 5;

    private Parent initializeGame(){

        BorderPane root = new BorderPane();
        root.setPrefSize(600, 800);

        Text welcome = new Text("\n Team BattleStar's Battleship");
        welcome.setFont(Font.font("times new roman", FontWeight.BOLD, FontPosture.REGULAR, 20));
        root.setTop(welcome);
        root.setAlignment(root.getTop(), Pos.CENTER);

        enemyBoard = new Board(true, event -> {
            if(!online) {
                return;
            }

            Cell c = (Cell) event.getSource();
            if(c.wasShot()){
                //mediaPlayer.setAutoPlay(true);
                return;
            }

            enemyMove = !c.gotShot();

            if(enemyBoard.getShips() == 0){
                Text win = new Text("Congratulations!!! You win!");
                win.setFont(Font.font("times new roman", FontWeight.BOLD, FontPosture.REGULAR, 20));
                win.setFill(Color.WHITE);
                root.setBottom(win);
                root.setAlignment(root.getBottom(), Pos.CENTER);

                //  System.out.println("Congratulations!!! You win!");
                online = false;
                //System.exit(0);
            }
            if(enemyMove){
                enemyAction();
            }
        });

        playerBoard = new Board(false, event -> {
            if(online) {
                return;
            }

            Cell c = (Cell) event.getSource();
            if(playerBoard.placeShip(new Ship(shipsLeft, event.getButton() == MouseButton.PRIMARY), c.getXCoord(), c.getYCoord())){
                if(--shipsLeft == 0){
                    startGame();
                }
            }
        });

        Label leftLabel = new Label("\tSave & Load");
        Label rightLabel = new Label("                 ");
        Button sButton = new Button("Save");
        Button lButton = new Button("Load");
        Button rButton = new Button("Restart");

        VBox right = new VBox(3);
        VBox left = new VBox(5);
        Text instructions = new Text("You can place your ships horizontally by right clicking your map");


        left.getChildren().addAll(leftLabel, sButton, lButton, rButton);
        left.setAlignment(Pos.CENTER_RIGHT);
        root.setLeft(left);
        root.setAlignment(root.getLeft(), Pos.CENTER_RIGHT);
        right.getChildren().add(rightLabel);
        root.setRight(right);
        root.setAlignment(root.getRight(), Pos.CENTER_LEFT);

        root.setRight(right);


        //String soundFile = "hitsound.wav";
        //Media sound = new Media(new File(soundFile).toURI().toString());
        //MediaPlayer mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.play();

        //messageLabel = new Label("Welcome to BattleStar Battleship!");

        //VBox gameBoard = new VBox(50, enemyBoard, playerBoard);
        //gameBoard.setAlignment(Pos.CENTER);
        //root.setPrefSize(600, 800);
        //root.setRight(new Text("RIGHT SIDEBAR - CONTROLS"));
        //root.setCenter(gameBoard);


        //Horizontal boxes. One for Player. One for Computer.
        enemySide  = new HBox(10);
        playerSide = new HBox(10);

        enemySide.setAlignment(Pos.TOP_CENTER);
        enemySide.getChildren().addAll(enemyBoard);

        playerSide.setAlignment(Pos.TOP_CENTER);
        playerSide.getChildren().addAll(playerBoard);

        VBox gameBoard = new VBox(100);
        //gameBoard.setAlignment(Pos.CENTER_LEFT);
        gameBoard.getChildren().addAll(enemySide, playerSide);
        gameBoard.setAlignment(Pos.CENTER);

        root.setCenter(gameBoard);

        //Background color
        //root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(0), Insets.EMPTY)));
        BackgroundImage myBI= new BackgroundImage(new Image("Background.png",0,0,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));
        //root.setPadding(new Insets(10));

        //root.getChildren().addAll(messageLabel);

        return root;
    }

    private void enemyAction(){
        while(enemyMove){
            int x = com.nextInt(10);
            int y = com.nextInt(10);

            Cell c = playerBoard.getCell(x, y);
            if(c.wasShot())
                continue;

            enemyMove = c.gotShot();

            if(playerBoard.getShips() == 0){
                //messageLabel.setText("Sorry. You lost.");
                System.out.println("Sorry. You lost.");
                //System.exit(0);
                online = false;
            }
        }
    }

    public void startGame(){
        int shipType = 5;

        while(shipType > 0){
            int x = com.nextInt(10);
            int y = com.nextInt(10);

            if(enemyBoard.placeShip(new Ship(shipType, Math.random() < 0.5), x, y)){
                shipType--;
            }
        }
        online = true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(initializeGame());
        primaryStage.setTitle("BattleStars - Battleship");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        showAlert();
    }

    public void showAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome to BattleStar's Battleship!");
        alert.setHeaderText("Here is how to start the game!");
        alert.setContentText("You have 5 ships! Please place them on your board! \nIn order to set them horizontally use your right mouse button. \nTo set your ship vertically use your left mouse button!");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}