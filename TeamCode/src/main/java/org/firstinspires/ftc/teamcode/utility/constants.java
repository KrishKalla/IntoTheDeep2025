package org.firstinspires.ftc.teamcode.utility;


public class constants {

    // Manipulator Constants
    public enum ROTOR_STATE{
        ON,
        OFF,
        REVERSE,
        RESET
    }

    public enum HSLIDES {
        EXTENDED,
        RETRACTED,
        CUSTOM
    }

    //set servo positions for max extension and retraction
    public static double pivot_left_extended;
    public static double pivot_right_extended;

    public static double pivot_left_retracted;
    public static double pivot_right_retracted;

    //initial distance of pivot
    public static double i;
    //length of bars
    public static double length;
    //step size of horizontal slides
    public static double h_step;
}
