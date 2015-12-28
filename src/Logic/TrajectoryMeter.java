package Logic;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Rogdan on 20.12.2015.
 */
public class TrajectoryMeter {
    private static final int STEP_SIZE_IN_PIXELS = 2;

    public static Queue <MyPoint> runThrowX(MyPoint startCoordinate, MyPoint maximalCoordinate, boolean append){
        int x = startCoordinate.getX();
        int y = startCoordinate.getY();

        Queue <MyPoint> trajectory = new ArrayDeque<>();

        while (x < maximalCoordinate.getX() - OUT_OF_RANGE){
            MyPoint p = new MyPoint();

            if (append) {
                x += STEP_SIZE_IN_PIXELS;
                p.setRelativeToThePreviousPoint(MoveObjectModel.State.MOVE_RIGHT);
            }
            else {
                x -= STEP_SIZE_IN_PIXELS;
                p.setRelativeToThePreviousPoint(MoveObjectModel.State.MOVE_LEFT);
            }

            p.setX(x);
            p.setY(y);

            trajectory.add(p);
        }

        return trajectory;
    }

    public static Queue <MyPoint> runThrowY(MyPoint startCoordinate, MyPoint maximalCoordinate, boolean append){
        int x = startCoordinate.getX();
        int y = startCoordinate.getY();

        Queue <MyPoint> trajectory = new ArrayDeque<>();

        while (y < maximalCoordinate.getY() - OUT_OF_RANGE){
            MyPoint p = new MyPoint();

            if (append){
                y += STEP_SIZE_IN_PIXELS;
                p.setRelativeToThePreviousPoint(MoveObjectModel.State.MOVE_DOWN);
            }
            else {
                y -= STEP_SIZE_IN_PIXELS;
                p.setRelativeToThePreviousPoint(MoveObjectModel.State.MOVE_UP);
            }

            p.setX(x);
            p.setY(y);

            trajectory.add(p);
        }

        return trajectory;
    }

    public static final int OUT_OF_RANGE = 140;
}