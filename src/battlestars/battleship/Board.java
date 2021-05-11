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

public class Board extends Parent {

    private VBox rows = new VBox();
    private boolean enemy = false;
    private int ships = 5;

    public int getShips() {
        return ships;
    }

    public void setShips(int s){
        ships = s;
    }

    public Cell getCell(int x, int y){
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    Board(boolean e, EventHandler<? super MouseEvent> handler){
        enemy = e;
        for(int y = 0; y < 10; y++){
            HBox row = new HBox();
            for(int x = 0; x < 10; x++){
                Cell space = new Cell(x, y, this);
                space.setOnMouseClicked(handler);
                row.getChildren().add(space);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    private boolean pointValidity(double x, double y){
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public boolean isValidPoint(Point2D p){
        return pointValidity(p.getX(), p.getY());
    }


    public boolean canPlaceShip(Ship s, int x, int y){
        int len = s.getType();
        if(s.isVertical()){
            for(int i = y; i < y + len; i++){
                if(!pointValidity(x, i)){
                    return false;
                }
                Cell space = getCell(x, i);
                if(space.getShip() != null){
                    return false;
                }

                //for

            }
        }
    }

    public boolean placeShip(Ship s, int x, int y){
        //if()
    }

}
