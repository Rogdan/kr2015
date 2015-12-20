package Model;

import java.util.*;

/**
 * Created by Rogdan on 20.12.2015.
 */
public class MovingObjectModel {
    private int speed;
    private volatile int health, state;
    private Point maximalCoordinate, startCoordinate;

    private ArrayList <Point> trajectory;

    private Observable myObservable = new Observable(){
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }
    };

    public void addObserver(Observer observer){
        myObservable.addObserver(observer);
    }

    public MovingObjectModel(int speed, int health, Point maximalCoordinate){
        this.speed = speed;
        this.health = health;
        this.maximalCoordinate = maximalCoordinate;

        Random random = new Random();
        state = random.nextInt(4) + 1;
    }

    public void init(){
        initMovingObjectOnBoard();
        flee();
    }

    private void initMovingObjectOnBoard(){
        Random random = new Random();

        int x = 0, y = 0;

        switch (state){
            case MOVING_RIGHT:
                y = random.nextInt(maximalCoordinate.getY());
                break;

            case MOVING_LEFT:
                x = maximalCoordinate.getX();
                y = random.nextInt(maximalCoordinate.getY());
                break;

            case MOVING_UP:
                x = random.nextInt(maximalCoordinate.getX());
                y = maximalCoordinate.getY();
                break;

            case MOVING_DOWN:
                x = random.nextInt(maximalCoordinate.getX());
                break;
        }

        startCoordinate = new Point(x, y);
        myObservable.notifyObservers(startCoordinate);
    }

    public void bump(int damage){
        health -= damage;

        if (isDead(health))
            myObservable.notifyObservers("dead");
    }

    private boolean isDead(int health){
        return health <= 0;
    }

    private void flee(){
        initTrajectory();

        for (Point point : trajectory) {
            myObservable.notifyObservers(point);
            waitNextStep();
        }

        myObservable.notifyObservers("out of range");
    }

    private void waitNextStep(){
        try {
            Thread.sleep(speed);
        } catch (InterruptedException ignored) {
        }
    }

    private void initTrajectory(){
        trajectory = new ArrayList<>();

        switch (state) {
            case 1:
                trajectory = TrajectoryMeter.runThrowX(startCoordinate, maximalCoordinate, true);
                break;
            case 2:
                trajectory = TrajectoryMeter.runThrowX(startCoordinate, maximalCoordinate, false);
                break;

            case 3:
                trajectory = TrajectoryMeter.runThrowY(startCoordinate, maximalCoordinate, true);
                break;
            case 4:
                trajectory = TrajectoryMeter.runThrowY(startCoordinate, maximalCoordinate, false);
                break;
        }
    }

    public int getState(){
        return state;
    }

    public final static int MOVING_RIGHT = 1;
    public final static int MOVING_LEFT = 2;
    public final static int MOVING_DOWN = 3;
    public final static int MOVING_UP = 4;
}
