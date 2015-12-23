package Model;

import java.util.*;

/**
 * Created by Rogdan on 20.12.2015.
 */
public class MovingObjectModel {
    private int screenUpdateBetweenSteps, currentUpdateCount, currentDeadIterations;
    private volatile int health, damageTaken;
    private volatile State state;
    private Point maximalCoordinate, currentCoordinate;
    private Queue <Point> trajectory;
    private Random random = new Random();
    private boolean isComeBack;

    public MovingObjectModel(int screenUpdateBetweenSteps, int health, int damageTaken, Point maximalCoordinate, boolean isComeBack){
        state = State.WAIT_MOVE;
        this.screenUpdateBetweenSteps = screenUpdateBetweenSteps;
        this.health = health;
        this.damageTaken = damageTaken;
        this.maximalCoordinate = maximalCoordinate;
        this.isComeBack = isComeBack;

        setStartState();
        initMovingObjectOnBoard();
        initTrajectory();
    }

    private void setStartState(){
        int startState = random.nextInt(4) + 1;
        switch (startState) {
            case 1:
                state = State.MOVE_RIGHT;
                break;
            case 2:
                state = State.MOVE_LEFT;
                break;
            case 3:
                state = State.MOVE_UP;
                break;
            case 4:
                state = State.MOVE_DOWN;
                break;
        }
    }

    private void initMovingObjectOnBoard(){
        int x = 0, y = 0;
        switch (state){
            case MOVE_RIGHT:
                y = random.nextInt(maximalCoordinate.getY());
                break;

            case MOVE_LEFT:
                x = maximalCoordinate.getX();
                y = random.nextInt(maximalCoordinate.getY());
                break;

            case MOVE_UP:
                x = random.nextInt(maximalCoordinate.getX());
                y = maximalCoordinate.getY();
                break;

            case MOVE_DOWN:
                x = random.nextInt(maximalCoordinate.getX());
                break;
        }

        currentCoordinate = new Point(x, y);
    }

    public void bump(){
        health -= damageTaken;
    }

    private boolean isDead(){
        return health <= 0;
    }

    public void makeStep(){
        currentUpdateCount++;
        currentUpdateCount %= screenUpdateBetweenSteps;

        if (isDead()) {
            state = State.DEAD;
        }

        if (state == State.DEAD){
            currentDeadIterations++;

            if (currentDeadIterations == DEAD_ITERATIONS) {
                state = State.TRASH;
            }
        }
        else
        if (currentUpdateCount == 0) {
            if (trajectory.size() > 0) {
                currentCoordinate = trajectory.poll();
                state = currentCoordinate.getRelativeToThePreviousPoint();
            }
            else {
                if (isComeBack)
                    goBack();
                else
                    state = State.OUT_OF_RANGE;
            }
        }
        else
            state = State.WAIT_MOVE;
    }

    private void goBack(){
        setStartState();
        initMovingObjectOnBoard();
        initTrajectory();
    }

    private void initTrajectory(){
        trajectory = new ArrayDeque<>();

        switch (state) {
            case MOVE_RIGHT:
                trajectory = TrajectoryMeter.runThrowX(currentCoordinate, maximalCoordinate, true);
                break;
            case MOVE_LEFT:
                trajectory = TrajectoryMeter.runThrowX(currentCoordinate, maximalCoordinate, false);
                break;
            case MOVE_DOWN:
                trajectory = TrajectoryMeter.runThrowY(currentCoordinate, maximalCoordinate, true);
                break;
            case MOVE_UP:
                trajectory = TrajectoryMeter.runThrowY(currentCoordinate, maximalCoordinate, false);
                break;
        }
    }

    public State getState(){
        return state;
    }

    public Point getCurrentCoordinate(){
        return currentCoordinate;
    }

    public enum State{
        DEAD, MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, WAIT_MOVE, OUT_OF_RANGE, TRASH
    }

    private static final int DEAD_ITERATIONS = 200;
}
