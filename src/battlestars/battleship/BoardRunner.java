package battlestars.battleship;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import battlestars.battleship.Board;
import battlestars.battleship.Cell;

public class BoardRunner extends Application {

    private Board playerBoard, enemyBoard;
    private boolean online = false;
    private int shipsLeft = 5;
    private boolean enemyMove = false;
    private Random randomizer = new Random();

    private Parent initialize(){
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 800);

        Text controls = new Text("RIGHT SIDEBAR - CONTROLS");
        root.setRight(controls);

        enemyBoard = new Board(true, event -> {
            if(!online){
                return;
            }

            Cell c = (Cell) event.getSource();
            if(c.gotShot()){
                return;
            }

            enemyMove = !c.wasItShot();

            if(enemyBoard.getShips() == 0){
                System.out.println("Congratulations!!! You are the winner!");
                System.exit(0);
            }

            if(enemyMove) {
                enemyAction();
            }
        });

        playerBoard = new Board(false, event -> {
           if(online){
               return;
           }

           Cell c = (Cell) event.getSource();
           if(playerBoard.placeShip(new Ship(shipsLeft, event.getButton() == MouseButton.PRIMARY), c.getXCoord(), c.getYCoord())){
               if(--shipsLeft == 0){
                   commenceGame();
               }
           }
        });

        VBox vbox = new VBox(50, enemyBoard, playerBoard);
        vbox.setAlignment(Pos.CENTER);
        root.setCenter(vbox);

        return root;
    }

    private void enemyAction(){
        while(enemyMove){
            int x = randomizer.nextInt(10);
            int y = randomizer.nextInt(10);

            Cell target = playerBoard.getCell(x, y);
            if(target.wasItShot()){
                continue;
            }
            enemyMove = target.gotShot();
            if(playerBoard.getShips() == 0){
                System.out.println("You Lost...");
                System.exit(0);
            }
        }
    }

    private void commenceGame(){
        int shipType = 5;
        while(shipType > 0){
            int x = randomizer.nextInt(10);
            int y = randomizer.nextInt(10);

            if(enemyBoard.placeShip(new Ship(shipType, Math.random() < 0.5), x, y)){
                shipType--;
            }
        }
        online = true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(initialize());
        primaryStage.setTitle("BattleStars - Battleship");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
