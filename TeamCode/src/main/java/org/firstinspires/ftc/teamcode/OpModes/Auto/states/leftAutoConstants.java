package org.firstinspires.ftc.teamcode.OpModes.Auto.states;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Point;

@Config
public class leftAutoConstants {

    public static double ROBOT_LENGTH = 16.1417;
    public static double ROBOT_WIDTH = 16.54;

    public enum LEFT {
        INIT,
        START,

        NAVIGATE_PRELOAD,
        SCORE_PRELOAD,

        PICKUP_1,
        NAVIGATE_1,
        SCORE_1,

        PICKUP_2,
        NAVIGATE_2,
        SCORE_2,

        PICKUP_3,
        NAVIGATE_3,
        SCORE_3,

        PARK,

        IDLE
    }


    public static final Pose startPose = new Pose(8.270, 110, 0);
    public static final Point startPoint = new Point(startPose);
    public static final double startHeading = 0;

    public static final Point navigatePreload = new Point(15.000, 124.000, Point.CARTESIAN);
    public static final double navigatePreloadHeading = 315;

    public static final Point pickup1 = new Point(24.000, 121.500, Point.CARTESIAN);
    public static final double pickup1Heading = 0;

    public static final Point navigate1 = new Point(17.000, 125.000, Point.CARTESIAN);
    public static double navigate1Heading = -45;


    public static final Point pickup2 = new Point(24.000, 130.000, Point.CARTESIAN);
    public static double pickup2Heading = 0;

    public static final Point navigate2 = new Point(16.750, 126.750, Point.CARTESIAN);
    public static double navigate2Heading = -45;


    public static final Point pickup3Control1 = new Point(23.000, 120.000, Point.CARTESIAN);
    public static final Point pickup3 = new Point(28.750, 126.000, Point.CARTESIAN);
    public static double pickup3Heading = 41;

    public static final Point navigate3 = new Point(17.250, 127.250, Point.CARTESIAN);
    public static double navigate3Heading = -40;


    public static final Point park = new Point(72.5, 92, Point.CARTESIAN);
    public static double parkHeading = -90;
}
