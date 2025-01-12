package org.firstinspires.ftc.teamcode.subsystems;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.constants;

public class arm {
    private Servo leftPivot;
    private Servo rightPivot;
    private Servo centerPivot;

    private String state;

    public arm() {state = "PRESET";}

    public void init(HardwareMap map) {
        state = "INIT";

        leftPivot = map.get(Servo.class, "leftpivot");
        rightPivot = map.get(Servo.class, "rightpivot");
        centerPivot = map.get(Servo.class, "centerpivot");

        reset();
    }

    public void preset(constants.ARM preset) {
        switch (preset) {
            case GROUND:
                state = "GROUND";
                leftPivot.setPosition(constants.GROUND);
                rightPivot.setPosition(constants.GROUND);
                break;
            case VERTICAL:
                state = "VERTICAL";
                leftPivot.setPosition(constants.VERTICAL);
                rightPivot.setPosition(constants.VERTICAL);
                break;
            case RESET:
                reset();
                break;
        }
    }

    public void setCustomRotation(int direction) {
        state = "CUSTOM ROTATION";
        double step = 0;
        if (direction == 1) {
            step = 2;
        }
        else if (direction == -1) {
            step = -2;
        }

        double target = centerPivot.getPosition() + step;
        centerPivot.setPosition(target);
    }

    @NonNull
    @Override
    public String toString() {
        return "L Pivot: " + leftPivot.getPosition() + ", R Pivot: " + rightPivot.getPosition() + ", C Pivot: " + centerPivot.getPosition();
    }

    public double getLeftPosition() {
        return leftPivot.getPosition();
    }

    public double getRightPosition() {
        return rightPivot.getPosition();
    }

    public double getCenterPosition() {
        return centerPivot.getPosition();
    }

    private void reset() {
        state = "RESET";
        leftPivot.setPosition(constants.GROUND);
        rightPivot.setPosition(constants.GROUND);
        centerPivot.setPosition(constants.CENTER);
        state = "READY";
    }
}
