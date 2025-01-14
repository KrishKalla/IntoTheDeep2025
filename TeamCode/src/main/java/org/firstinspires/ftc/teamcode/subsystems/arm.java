package org.firstinspires.ftc.teamcode.subsystems;


import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.constants;

import java.util.Objects;

public class arm {
    private Servo leftPivot;
    private Servo rightPivot;
    private Servo centerPivot;

    private String state;

    public arm() {state = "PRESET";}

    public void init(HardwareMap map) {
        state = "INIT";

        leftPivot = map.get(Servo.class, "shoulderL");
        rightPivot = map.get(Servo.class, "shoulderR");
        centerPivot = map.get(Servo.class, "elbow");

        reset(constants.INIT);
    }

    public void preset(constants.ARM preset) {
        switch (preset) {
            case GROUND:
                state = "GROUND";
                leftPivot.setPosition(1-constants.GROUND);
                rightPivot.setPosition(constants.GROUND);
                break;
            case VERTICAL:
                state = "VERTICAL";
                leftPivot.setPosition(1-constants.VERTICAL);
                rightPivot.setPosition(constants.VERTICAL);
                break;
            case PRE_PICKUP:
                state = "PRE_PICKUP";
                leftPivot.setPosition(1-constants.PRE_PICKUP);
                rightPivot.setPosition(constants.PRE_PICKUP);
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
            step = constants.ARM_STEP;
        }
        else if (direction == -1) {
            step = -constants.ARM_STEP;
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
        centerPivot.setPosition(constants.CENTER);
        leftPivot.setPosition(1-constants.GROUND);
        rightPivot.setPosition(constants.GROUND);
        state = "READY";

    }

    private void reset(double angle) {
        state = "RESET";
        centerPivot.setPosition(constants.CENTER);
        leftPivot.setPosition(1-angle);
        rightPivot.setPosition(angle);
        state = "READY";

    }
}
