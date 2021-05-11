package battlestars.battleship;

public class Ship {

    private int type;
    private int health;
    private boolean vertical;

    public Ship(int t, boolean v){
        type = t;
        health = t;
        vertical = v;
    }

    public void setType(int t){
        type = t;
        health = t;
    }

    public void setVertical(boolean v){
        vertical = v;
    }

    public int getHealth(){
        return health;
    }

    public int getType(){
        return type;
    }

    public boolean getVertical(){
        return vertical;
    }
    
    public void hit(){
        health--;
    }

    public boolean isAlive(){
        return health > 0;
    }

}
