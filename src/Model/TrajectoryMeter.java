package Model;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Rogdan on 20.12.2015.
 */
public class TrajectoryMeter {
    private static final int STEP_SIZE_IN_PIXELS = 2;

    public static Queue <Point> runThrowX(Point startCoordinate, Point maximalCoordinate, boolean append){
        int x = startCoordinate.getX();
        int y = startCoordinate.getY();

        Queue <Point> trajectory = new ArrayDeque<>();

        for (int i = 0; i < maximalCoordinate.getX() / STEP_SIZE_IN_PIXELS; i++) {
            Point p = new Point();

            if (append) {
                x += STEP_SIZE_IN_PIXELS;
                p.setRelativeToThePreviousPoint(MovingObjectModel.State.MOVE_RIGHT);
            }
            else {
                x -= STEP_SIZE_IN_PIXELS;
                p.setRelativeToThePreviousPoint(MovingObjectModel.State.MOVE_LEFT);
            }

            p.setX(x);
            p.setY(y);

            trajectory.add(p);
        }

        return trajectory;
    }

    public static Queue <Point> runThrowY(Point startCoordinate, Point maximalCoordinate, boolean append){
        int x = startCoordinate.getX();
        int y = startCoordinate.getY();

        Queue <Point> trajectory = new ArrayDeque<>();

        for (int i = 0; i < maximalCoordinate.getY() / STEP_SIZE_IN_PIXELS; i++) {
            Point p = new Point();

            if (append){
                y += STEP_SIZE_IN_PIXELS;
                p.setRelativeToThePreviousPoint(MovingObjectModel.State.MOVE_DOWN);
            }
            else {
                y -= STEP_SIZE_IN_PIXELS;
                p.setRelativeToThePreviousPoint(MovingObjectModel.State.MOVE_UP);
            }

            p.setX(x);
            p.setY(y);

            trajectory.add(p);
        }

        return trajectory;
    }
}
