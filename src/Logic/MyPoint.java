package Logic;

/**
 * Created by Rogdan on 20.12.2015.
 */
public class MyPoint {
    private int x, y;
    private MoveObjectModel.State relativeToThePreviousPoint;

    public MyPoint(){
    }

    public MyPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setRelativeToThePreviousPoint(MoveObjectModel.State relative){
        relativeToThePreviousPoint = relative;
    }

    public MoveObjectModel.State getRelativeToThePreviousPoint(){
        return relativeToThePreviousPoint;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}