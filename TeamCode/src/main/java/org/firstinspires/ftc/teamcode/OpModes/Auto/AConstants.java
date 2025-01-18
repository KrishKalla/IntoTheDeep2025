package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Point;

@Config
public class AConstants {
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
        SCORE,
        PARK,
        IDLE
    }

    public static final class BlueRight {
        public static final Pose startPose = new Pose(8.70, 57, Math.toRadians(180));
        public static final Point startPoint = new Point(8.5, 57);

        public static final Point navigateToPreload = new Point(36.75, 68, Point.CARTESIAN);
        public static final Point navigateToPreloadControl1 = new Point(16, 55, Point.CARTESIAN);

        public static final Point scorePreload = new Point(34.5, 68, Point.CARTESIAN);
        public static final Point releasePreload = new Point(37, 68, Point.CARTESIAN);
        public static final Point retreatFromPreload = new Point(30, 68, Point.CARTESIAN);

        public static final Point navigateToSample1 = new Point(66, 23, Point.CARTESIAN);
        public static final Point navigateToSample1Control1 = new Point(18, 17, Point.CARTESIAN);
        public static final Point NavigateToSample1Control2 = new Point(62, 30, Point.CARTESIAN);

        public static final Point pushSample1 = new Point(16, 23, Point.CARTESIAN);

        public static final Point navigateToSample2 = new Point(66, 12, Point.CARTESIAN);
        public static final Point navigateToSample2Control1 = new Point(66, 33, Point.CARTESIAN);

        public static final Point pushSample2 = new Point(16, 12, Point.CARTESIAN);

        public static final Point navigateToPickup = new Point(21.10, 27.75, Point.CARTESIAN);
        public static final Point navigateToPickupControl1 = new Point(17, 66, Point.CARTESIAN);

        public static final Point navigateToScore = new Point(38.5, 76, Point.CARTESIAN);
        public static final Point navigateToScoreControl1 = new Point(7, 66, Point.CARTESIAN);

        public static final Point score = new Point(36.2, 76, Point.CARTESIAN);
        public static final Point release = new Point(41, 68, Point.CARTESIAN);

            public static final Point park = new Point(68, 48, Point.CARTESIAN);
        public static final Point parkControl1 = new Point(12, 0, Point.CARTESIAN);
        public static final Point parkControl2 = new Point(68, 0, Point.CARTESIAN);
    }

}
