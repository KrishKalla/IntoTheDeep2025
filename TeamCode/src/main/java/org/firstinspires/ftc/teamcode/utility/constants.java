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
        RETRACTED, //trigger left
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

    public enum ARM {
        GROUND, //dpad_down
        VERTICAL, //dpad_up
        RESET //dpad_left
    }

    public enum CLAW {
        STRAIGHT, // cross
        ROTATED, //triangle
        OPEN, //square
        CLOSED, //circle
        RESET
    }

    //set servo positions for max extension and retraction
    public static double pivot_left_extended = 0.25;
    public static double pivot_right_extended = 0.57;

    public static double pivot_left_retracted = 0.05;
    public static double pivot_right_retracted = 0.79;

    //initial distance of pivot
    public static double i = 11.75;
    //length of bars
    public static double length = 7;
    //max length of hslides
    public static double cutoff = 18.75;

    //HSLIDE CONSTANTS
    public static int BASE = 0;
    public static int LOW_BAR = 50;
    public static int HIGH_BAR = 155;
    public static int LOW_BASKET = 175;
    public static int HIGH_BASKET = 250;
    public static int LOW_RUNG = 150;
    public static int HIGH_RUNG = 225;

    //arm constants
    public static double GROUND = 0.05;
    public static double VERTICAL = 0.6;
    public static double CENTER = 0.46;

    //claw constants
    //claw positions
    public static double OPEN = 45;
    public static double CLOSED = 0;
    //claw rotations
    public static double STRAIGHT = 0;
    public static double ROTATED = 90;


}
