package battlestars.battleship;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
    private int x, y;
    private Ship ship = null;
    private boolean wasShot = false;
    private Board board;

    Cell(int x, int y, Board b){
        super(30, 30);
        this.x = x;
        this.y = y;
        board = b;
        setFill(Color.LIGHTBLUE);
        setStroke(Color.BLACK);
    }

    public boolean gotShot(){
        wasShot = true;
        Color color = Color.BLACK;
        if(ship != null){
            ship.hit();
            color = Color.RED;
            if(!ship.isAlive()){
                board.setShips(board.getShips()-1);
            }
            setFill(color);
            return true;
        }
        setFill(color);
        return false;
    }

    public int getXCoord(){
        return x;
    }

    public int getYCoord() {
        return y;
    }


    public Ship getShip(){
        return ship;
    }

    public boolean wasItShot(){
        return wasShot;
    }

    public void setXCoord(int x){
        this.x = x;
    }

    public void setYCoord(int y){
        this.y = y;
    }

    public void setShip(Ship s){
        ship = s;
    }

}
