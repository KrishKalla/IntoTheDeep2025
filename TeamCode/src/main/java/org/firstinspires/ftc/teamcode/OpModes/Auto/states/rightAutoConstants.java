package org.firstinspires.ftc.teamcode.OpModes.Auto.states;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Point;

@Config
public class rightAutoConstants {

    public static double ROBOT_LENGTH = 16.1417;
    public static double ROBOT_WIDTH = 16.54;

    public enum RIGHT {
        INIT,
        START,

        NAVIGATE_PRELOAD,
        SCORE_PRELOAD,

        PUSH,
        ALIGN_1,
        PUSH_1,
        ALIGN_2,
        PUSH_2,

        PICKUP_CACHE,
        NAVIGATE_CACHE,
        SCORE_CACHE,

        PICKUP_1,
        NAVIGATE_1,
        SCORE_1,

        PICKUP_2,
        NAVIGATE_2,
        SCORE_2,

        PARK,

        IDLE
    }

    public static final Pose startPose = new Pose(8.57, 63.73, 0);
    public static final Point startPoint = new Point(startPose);


    public static final Point navigatePreloadControl1 = new Point(20.000, 75.000, Point.CARTESIAN);
    public static final Point navigatePreload = new Point(42, 75.000, Point.CARTESIAN);


    public static final Point align1Control1 = new Point(20.000, 68.000, Point.CARTESIAN);
    public static final Point align1Control2 = new Point(0.000, 17.000, Point.CARTESIAN);
    public static final Point align1Control3 = new Point(49.000, 40.000, Point.CARTESIAN);
    public static final Point align1Control4 = new Point(60.000, 40.000, Point.CARTESIAN);
    public static final Point align1 = new Point(57.000, 28.000, Point.CARTESIAN);

    public static final Point push1 = new Point(29.000, 28.000, Point.CARTESIAN);


    public static final Point align2Control1 = new Point(62.000, 30.000, Point.CARTESIAN);
    public static final Point align2 = new Point(57, 18.000, Point.CARTESIAN);

    public static final Point push2 = new Point(11.650, 18.000, Point.CARTESIAN);


    public static final Point pickup = new Point(11.950, 34.000, Point.CARTESIAN);


    public static final Point navigateCacheControl1 = new Point(12.100, 75.000, Point.CARTESIAN);
    public static final Point navigateCacheControl2 = new Point(39.00, 75.000, Point.CARTESIAN);
    public static final Point navigateCache = new Point(42.000, 75.000, Point.CARTESIAN);


    public static final Point navigate1Control1 = new Point(12.100, 75.000, Point.CARTESIAN);
    public static final Point navigate1Control2 = new Point(36.00, 75.000, Point.CARTESIAN);
    public static final Point navigate1 = new Point(42.000, 75.000, Point.CARTESIAN);


    public static final  Point navigate2Control1 = new Point(12.100, 75.000, Point.CARTESIAN);
    public static final Point navigate2Control2 = new Point(41.500, 75.000, Point.CARTESIAN);
    public static final Point navigate2 = new Point(42.000, 75.000, Point.CARTESIAN);
}
