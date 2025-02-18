package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Point;

@Config
public class AConstants {

    public static double ROBOT_LENGTH = 16.1417;
    public static double ROBOT_WIDTH = 16.5354;


    public enum BLUE_RIGHT {
        INIT,
        START,
        PRELOAD,
        PRE_SCORE,
        SCORE_PRELOAD,
        OPEN_CLAW,
        PUSH_1,
        PUSH_2,
        PICK_1,
        RESET_TIMER,
        UNLATCH,
        READY,
        PRE,
        SCORE,
        PARK,
        IDLE
    }

    public static final class BlueRight {
        public static final Pose startPose = new Pose(ROBOT_LENGTH / 2, 57, Math.toRadians(180));
        public static final Point startPoint = new Point(8.5, 57);

        public static final Point navigateToPreload = new Point(36.75, 68, Point.CARTESIAN);
        public static final Point navigateToPreloadControl1 = new Point(16, 55, Point.CARTESIAN);

        public static final Point scorePreload = new Point(34.55, 68, Point.CARTESIAN);
        public static final Point releasePreload = new Point(37, 68, Point.CARTESIAN);
        public static final Point retreatFromPreload = new Point(30, 68, Point.CARTESIAN);

        public static final Point navigateToSample1 = new Point(66, 23, Point.CARTESIAN);
        public static final Point navigateToSample1Control1 = new Point(18, 17, Point.CARTESIAN);
        public static final Point NavigateToSample1Control2 = new Point(62, 30, Point.CARTESIAN);

        public static final Point pushSample1 = new Point(16, 23, Point.CARTESIAN);

        public static final Point navigateToSample2 = new Point(66, 12, Point.CARTESIAN);
        public static final Point navigateToSample2Control1 = new Point(66, 33, Point.CARTESIAN);

        public static final Point pushSample2 = new Point(12.5, 12, Point.CARTESIAN);

        public static final Point navigateToPickup = new Point(18.5, 23.5, Point.CARTESIAN);
        public static final Point navigateToPickupControl1 = new Point(17, 66, Point.CARTESIAN);

        public static final Point navigateToScore = new Point(39, 76, Point.CARTESIAN);
        public static final Point navigateToScoreControl1 = new Point(7, 66, Point.CARTESIAN);

        public static final Point score = new Point(31, 76, Point.CARTESIAN);
        public static final Point release = new Point(41, 68, Point.CARTESIAN);

        public static final Point park = new Point(10.2, 17, Point.CARTESIAN);
        public static final Point parkControl1 = new Point(17, 66, Point.CARTESIAN);
        public static final Point parkControl2 = new Point(68, 0, Point.CARTESIAN);
    }


    public enum RIGHT_RUNNABLE {
        INIT,
        START,
        NAVIGATE_TO_PRELOAD,
        SCORE_PRELOAD,
        UNLOAD_PRELOAD,
        PUSH_1,
        PUSH_2,
        PICKUP,
        NAVIGATE_TO_SCORE,
        SCORE,
        SCORE_TO_PICKUP,
        UNLATCH,
        PARK,
        IDLE
    }

    public static final class RightRunnable {
        public static final Pose startPose = new Pose(ROBOT_LENGTH / 2, 56, Math.toRadians(180));
        public static final Point startPoint = new Point(ROBOT_LENGTH / 2, 56);


        public static final Point navigateToPreload = new Point(32, 69);
        public static final Point preloadControl1 = new Point(24, 69);


        public static final Point scorePreload = new Point(40, 69);


        public static final Point navigateToPushSample1 = new Point(48, 35);
        public static final Point navigateToPushSample1Control1 = new Point(-10, 58.5);
        public static final Point navigateToPushSample1Control2 = new Point(23, 36);

        public static final Point lineUpToPushSample1 = new Point(60, 28.25);
        public static final Point lineUpToPushSample1Control1 = new Point(60, 35.5);

        public static final Point PushSample1 = new Point(13, 28.25);


        public static final Point navigateToPushSample2 = new Point(60, 16.5);
        public static final Point navigateToPushSample2Control1 = new Point(55.5, 28.4);

        public static final Point PushSample2 = new Point(13, 16.5);


        public static final Point pickup = new Point(12.75, 26);


        public static final Point navigateToScore = new Point(30, 72);
        public static final Point navigateToScoreControl1 = new Point(21.75, 67.5);


        public static final Point score = new Point(36, 72);

        public static final Point navigateToPark = new Point(11, 24);
        public static final Point navigateToParkControl1 = new Point(16, 72);
    }

}
