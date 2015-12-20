package Model;

import java.util.ArrayList;

/**
 * Created by Rogdan on 20.12.2015.
 */
public class TrajectoryMeter {
    private static final int STEP_SIZE_IN_PIXELS = 2;

    public static ArrayList<Point> runThrowX(Point startCoordinate, Point maximalCoordinate, boolean append){
        int x = startCoordinate.getX();
        int y = startCoordinate.getY();

        ArrayList <Point> trajectory = new ArrayList();

        for (int i = 0; i < maximalCoordinate.getX(); i++) {
            if (append)
                x += STEP_SIZE_IN_PIXELS;
            else
                x -= STEP_SIZE_IN_PIXELS;

            trajectory.add(new Point(x, y));
        }

        return trajectory;
    }

    public static ArrayList <Point> runThrowY(Point startCoordinate, Point maximalCoordinate, boolean append){
        int x = startCoordinate.getX();
        int y = startCoordinate.getY();

        ArrayList <Point> trajectory = new ArrayList();

        for (int i = 0; i < maximalCoordinate.getY(); i++) {
            if (append)
                y += STEP_SIZE_IN_PIXELS;
            else
                y -= STEP_SIZE_IN_PIXELS;

            trajectory.add(new Point(x, y));
        }

        return trajectory;
    }
}
