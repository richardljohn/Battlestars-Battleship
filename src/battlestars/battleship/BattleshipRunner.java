package battlestars.battleship;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import battlestars.battleship.Board;
import battlestars.battleship.Cell;

public class BattleshipRunner extends Application {

    private Random com = new Random();
    private Board playerBoard, enemyBoard;
    //private Label remainingShipsLabel, messageLabel;
    private boolean online = false;
    private boolean enemyMove = false;
    private int shipsLeft = 5;

    private Parent initializeGame(){
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 800);
        root.setRight(new Text("RIGHT SIDEBAR - CONTROLS"));

        //messageLabel = new Label("Welcome to BattleStar Battleship!");

        enemyBoard = new Board(true, event -> {
            if(!online) {
                return;
            }

            Cell c = (Cell) event.getSource();
            if(c.wasShot()){
                return;
            }

            enemyMove = !c.gotShot();

            if(enemyBoard.getShips() == 0){
                //messageLabel.setText("Congratulations!!! You win!");
                System.out.println("Congratulations!!! You win!");
                System.exit(0);
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

        VBox gameBoard = new VBox(50, enemyBoard, playerBoard);
        gameBoard.setAlignment(Pos.CENTER);
        root.setCenter(gameBoard);
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
                System.exit(0);
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
    }

    public static void main(String[] args) {
        launch(args);
    }
}