package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.pedropathing.pathgen.Point;

public class TeleConstants {
    public enum TELE {
        PICKUP,
        NAVIGATE,
        SCORE,
        RESET,
        IDLE
    }


    public static final Point pickup = new Point(0.000, 0.000, Point.CARTESIAN);
    public static final Point navigate = new Point(30.000, 39.500, Point.CARTESIAN);
}
