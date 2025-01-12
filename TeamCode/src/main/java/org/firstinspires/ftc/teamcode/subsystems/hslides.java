package org.firstinspires.ftc.teamcode.subsystems;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.constants;


public class hslides {
    private String state = "INIT";
    private final double cutoff = constants.cutoff;
    private double x;

    private Servo leftServo;
    private Servo rightServo;

    public hslides() {
    }

    public void init(HardwareMap map) {
        leftServo = map.get(Servo.class, "left");
        rightServo = map.get(Servo.class, "right");

        reset();
        state = "INIT";
    }

    public void preset(constants.HSLIDES preset) {
        // replace below constants with constants in constants.java once we get them
        switch (preset) {
            case EXTENDED:
                leftServo.setPosition(constants.pivot_left_extended);
                rightServo.setPosition(constants.pivot_right_extended);
                state = "EXTENDED";
                break;
            case RETRACTED:
                leftServo.setPosition(constants.pivot_left_retracted);
                rightServo.setPosition(constants.pivot_right_retracted);
                state = "RETRACTED";
                break;
            case RESET:
                reset();
                break;
        }
    }

    //Theta calculation based on x
    private double calculate(double x){
        return 90 - Math.acos(((x+constants.i)/2)/constants.length);
    }

    public void setCustomExtension(int direction) {
        double step = 0;
        if (direction == 1) {
            step = 2;
            setExtension(x + step);
        }
        else if (direction == -1) {
            step = -2;
            setExtension(x + step);
        }

    }

    public void setExtension(double x) {
        if (x > cutoff) {
            state = "INVALID";
        }
        else {
            double theta = calculate(x);
            leftServo.setPosition(theta);
            rightServo.setPosition(180 - theta);
            state = "CUSTOM";
        }
    }


    @NonNull
    @Override
    public String toString() {
        return ("LEFT: " + leftServo.getPosition() + ", RIGHT: " + rightServo.getPosition());
    }

    private void reset() {
        state = "INIT";
        leftServo.setPosition(constants.pivot_left_retracted);
        rightServo.setPosition(constants.pivot_right_retracted);
        state = "READY";
    }
}
