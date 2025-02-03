package org.firstinspires.ftc.teamcode.utility;

import com.acmerobotics.dashboard.config.Config;

@Config
public class constants {

    // Manipulator Constants
    public enum ROTOR_STATE{
        ON,
        OFF,
        REVERSE,
        RESET
    }

    public enum HSLIDES {
        EXTENDED, //trigger right
        RETRACTED,
        HALF,//trigger left
        RESET
    }

    public enum VSLIDES {
        BASE, // dpad down
        LOW_BAR, // cross
        HIGH_BAR, // triangle
        LOW_BASKET, // dpad_left
        HIGH_BASKET, // dpad up
        LOW_RUNG, // square
        HIGH_RUNG, // circle
        RESET //
    }

    public enum INTAKE {
        // CR SERVOS:
        PUSHOUT, // push sample out
        TAKEIN, // take sample in
        OFF,
        // PIVOT SERVOS
        GROUND,
        PRE_PICKUP,
        TRANSFER,
        RETRACTED,
        // BOTH
        RESET
    }

    public enum ARM {
        GROUND, //dpad_down
        PRE_PICKUP,
        VERTICAL,
        BACK,
        CENTER,//dpad_up
        RESET //dpad_left
    }

    public enum CLAW {
        STRAIGHT, // cross
        ROTATED, //triangle
        OPEN, //square
        CLOSED, //circle
        RESET
    }

    public enum UPPERCLAW {
        OPEN,
        CLOSED,
        TRANSFER,
        BACK,
        SPECIMEN,
        RESET
    }

    //set servo positions for max extension and retraction
    public static double pivot_left_extended = 0.3;
    public static double pivot_right_extended = 0.54;

    public static double pivot_left_retracted = 0.0;
    public static double pivot_right_retracted = 0.84;
    public static double CRUDE_STEP = 0.007;

    //initial distance of pivot
    public static double i = 11.75;
    //length of bars
    public static double length = 7;
    //max length of hslides
    public static double cutoff = 18.75;

    //HSLIDE CONSTANTS
    public static int BASE = 0;
    public static int LOW_BAR = 50;
    public static int HIGH_BAR = 970;
    public static int LOW_BASKET = 1250;
    public static int HIGH_BASKET = 2900;
    public static int LOW_RUNG = 1200;
    public static int HIGH_RUNG = 2500;
    public static double MAX_SLIDE_SPEED = 0.8;

    //arm constants
    public static double GROUND = 0.01;
    public static double PRE_PICKUP = 0.07;
    public static double VERTICAL = 0.575;
    public static double BACK = 0.99;

    public static double INIT = 0.765;
    public static double CENTER = 0.525;
    public static double ARM_STEP = 0.0065;

    //claw constants
    //claw positions
    public static double OPEN = 0.15;
    public static double CLOSED = 0.5;
    //claw rotations
    public static double STRAIGHT = 0.5;
    public static double ROTATED = 0;
    public static double WRIST_STEP = 0.025;

    // pivot intake constants (update these)
    public static double GROUND_PIVOT = 0;
    public static double INIT_PIVOT = 0;
    public static double TRANSFER = 0;
    public static double RETRACTED = 0;

    //Upper Claw constants (open & close from claw positions)
    public static double transfer = 0;
    public static double back = 0;
    public static double specimen = 0;

}
