package Logic;

import Visual.GameBoard;

import java.util.*;

public class MoveObjectModel {
    private int screenUpdateBetweenSteps, currentUpdateCount, currentDeadIterations;
    private volatile int health, damageTaken;
    private volatile State state;
    private MyPoint maximalCoordinate, currentCoordinate;
    private Queue <MyPoint> trajectory;
    private Random random = new Random();
    private int comeBackCount, scoreForKill;

    public MoveObjectModel(int screenUpdateBetweenSteps, int health, int damageTaken, MyPoint maximalCoordinate, int scoreForKill){
        state = State.WAIT_MOVE;
        this.screenUpdateBetweenSteps = screenUpdateBetweenSteps;
        this.health = health;
        this.damageTaken = damageTaken;
        this.maximalCoordinate = maximalCoordinate;
        this.scoreForKill = scoreForKill*(Game.MAXIMAL_SCREEN_UPDATE_BETWEEN_STEPS - screenUpdateBetweenSteps + 1);

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
            case MOVE_LEFT:
                x = maximalCoordinate.getX();
            case MOVE_RIGHT:
                y = random.nextInt(maximalCoordinate.getY() - TrajectoryMeter.OUT_OF_RANGE);
                break;

            case MOVE_UP:
                y = maximalCoordinate.getY();
            case MOVE_DOWN:
                x = random.nextInt(maximalCoordinate.getX() - TrajectoryMeter.OUT_OF_RANGE);
                break;
        }

        currentCoordinate = new MyPoint(x, y);
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

        if (isDead() && state != State.DEAD && state != State.WAIT_REMOVING) {
            state = State.DEAD;
        }
        else
        if (state == State.DEAD){
            state = State.WAIT_REMOVING;
        }
        else
            if (state == State.WAIT_REMOVING){
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
                if (comeBackCount < COME_BACK_ITERATIONS) {
                    comeBackCount++;
                    goBack();
                }
                else
                    state = State.OUT_OF_RANGE;
            }
        }
        else
            state = State.WAIT_MOVE;
    }

    private void goBack(){
        screenUpdateBetweenSteps = random.nextInt(GameBoard.MAXIMAL_TIME_BETWEEN_UPDATE) + 1;
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

    public int getScoreForKill(){
        return scoreForKill;
    }

    public State getState(){
        return state;
    }

    public MyPoint getCurrentCoordinate(){
        return currentCoordinate;
    }

    public enum State {
        DEAD, MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, WAIT_MOVE, OUT_OF_RANGE, TRASH, WAIT_REMOVING
    }

    private static final int DEAD_ITERATIONS = 200;
    private static final int COME_BACK_ITERATIONS = 3;
    private static final int OUT_OF_RANGE = 140;
}