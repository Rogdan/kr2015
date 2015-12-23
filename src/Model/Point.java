package Model;

/**
 * Created by Rogdan on 20.12.2015.
 */
public class Point {
    private int x, y;
    private MovingObjectModel.State relativeToThePreviousPoint;

    public Point(){
    }

    public Point (int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setRelativeToThePreviousPoint(MovingObjectModel.State relative){
        relativeToThePreviousPoint = relative;
    }

    public MovingObjectModel.State getRelativeToThePreviousPoint(){
        return relativeToThePreviousPoint;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
