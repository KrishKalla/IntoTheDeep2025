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


    public static final Pose startPose = new Pose(8.270, 80.071, 90);
    public static final Point startPoint = new Point(startPose);
    public static final double startHeading = 90;

    public static final Point navigatePreload = new Point(14.000, 130.000, Point.CARTESIAN);
    public static final double navigatePreloadHeading = -45;

    public static final Point pickup1 = new Point(32.000, 121.000, Point.CARTESIAN);
    public static final double pickup1Heading = 0;

    public static final Point navigate1 = new Point(14.000, 130.000, Point.CARTESIAN);
    public static double navigate1Heading = -45;


    public static final Point pickup2 = new Point(32.000, 131.500, Point.CARTESIAN);
    public static double pickup2Heading = 0;

    public static final Point navigate2 = new Point(17.000, 135.500, Point.CARTESIAN);
    public static double navigate2Heading = 0;


    public static final Point pickup3Control1 = new Point(21.000, 120.000, Point.CARTESIAN);
    public static final Point pickup3 = new Point(38.500, 131.000, Point.CARTESIAN);
    public static double pickup3Heading = 60;

    public static final Point navigate3 = new Point(18.700, 134.000, Point.CARTESIAN);
    public static double navigate3Heading = -20;


    public static final Point park = new Point(58.5, 92, Point.CARTESIAN);
    public static double parkHeading = -90;
}
