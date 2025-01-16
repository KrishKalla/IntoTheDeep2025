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
        SCORE_PRELOAD,
        PUSH_1,
        PUSH_2,
        PICK_1,
        READY,
        SCORE,
        PARK,
        IDLE
    }

    public static final class BlueRight {
        public static final Pose startPose = new Pose(8.195, 55.727, Math.toRadians(180));
        public static final Point startPoint = new Point(8.195, 55.727);

        public static final Point navigateToPreload = new Point(41, 66, Point.CARTESIAN);
        public static final Point navigateToPreloadControl1 = new Point(33, 55, Point.CARTESIAN);

        public static final Point scorePreload = new Point(39, 66, Point.CARTESIAN);
        public static final Point releasePreload = new Point(41, 66, Point.CARTESIAN);
        public static final Point retreatFromPreload = new Point(30, 66, Point.CARTESIAN);

        public static final Point navigateToSample1 = new Point(66, 23, Point.CARTESIAN);
        public static final Point navigateToSample1Control1 = new Point(28, 34, Point.CARTESIAN);
        public static final Point NavigateToSample1Control2 = new Point(62, 48, Point.CARTESIAN);

        public static final Point pushSample1 = new Point(16, 23, Point.CARTESIAN);

        public static final Point navigateToSample2 = new Point(66, 12, Point.CARTESIAN);
        public static final Point navigateToSample2Control1 = new Point(66, 33, Point.CARTESIAN);

        public static final Point pushSample2 = new Point(16, 12, Point.CARTESIAN);

        public static final Point navigateToPickup = new Point(10, 24, Point.CARTESIAN);
        public static final Point navigateToPickupControl1 = new Point(7, 66, Point.CARTESIAN);

        public static final Point navigateToScore = new Point(41, 66, Point.CARTESIAN);
        public static final Point navigateToScoreControl1 = new Point(7, 66, Point.CARTESIAN);

        public static final Point score = new Point(39, 66, Point.CARTESIAN);
        public static final Point release = new Point(41, 66, Point.CARTESIAN);

        public static final Point park = new Point(24, 96, Point.CARTESIAN);
        public static final Point parkControl1 = new Point(48, 112, Point.CARTESIAN);
        public static final Point parkControl2 = new Point(72, 96, Point.CARTESIAN);
    }

}
