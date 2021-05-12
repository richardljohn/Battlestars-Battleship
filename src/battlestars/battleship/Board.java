package battlestars.battleship;

import java.awt.*;
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

    public boolean isEnemy(){
        return enemy;
    }

    public void setEnemy(boolean e){
        enemy = e;
    }

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

    private Cell[] getNeighbors(int x, int y){
        Point2D[] cells = new Point2D[]{
                new Point2D(x-1, y),
                new Point2D(x+1, y),
                new Point2D(x, y-1),
                new Point2D(x, y+1)
        };

        List<Cell> n = new ArrayList<Cell>();
        for(Point2D c : cells){
            if(isValidPoint(c)){
                n.add(getCell((int)c.getX(), (int)c.getY()));
            }
        }

        return n.toArray(new Cell[0]);
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

                for(Cell n : getNeighbors(x, i)){
                    if(!pointValidity(x, i)){
                        return false;
                    }
                    if(n.getShip() != null){
                        return false;
                    }
                }

            }
        }
        else {
            for(int i = x; i < x + len; i++){
                if(!pointValidity(i, y)){
                    return false;
                }

                Cell c = getCell(i, y);
                if(c.getShip() != null){
                    return false;
                }

                for(Cell n : getNeighbors(i, y)){
                    if(!pointValidity(i, y)){
                        return false;
                    }
                    if(n.getShip() != null){
                        return false;
                    }
                }

            }
        }
        return true;
    }

    public boolean placeShip(Ship s, int x, int y){
        if(canPlaceShip(s, x, y)){
            int len = s.getType();
            if(s.isVertical()){
                for(int i = y; i < y + len; i++){
                    Cell c = getCell(x, i);
                    c.setShip(s);
                    if(!isEnemy()){
                        c.setFill(Color.GREEN);
                        c.setStroke(Color.BLACK);
                    }
                }
            }
            else{
                for(int i = x; i < x + len; i++){
                    Cell c = getCell(i, y);
                    c.setShip(s);
                    if(!isEnemy()){
                        c.setFill(Color.GREEN);
                        c.setStroke(Color.BLACK);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
