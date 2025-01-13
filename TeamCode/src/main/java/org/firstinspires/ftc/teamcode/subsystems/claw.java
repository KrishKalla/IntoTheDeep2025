package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utility.constants;

public class claw {
    private Servo claw;
    private Servo rotator;

    private String clawState;
    private String rotatorState;

    public claw() {
        clawState = "PRESET";}

    public void init(HardwareMap map) {
        claw = map.get(Servo.class, "claw");
        rotator = map.get(Servo.class, "rotator");

        reset();
    }

    public void preset(constants.CLAW preset) {
        switch (preset) {
            case STRAIGHT:
                rotator.setPosition(constants.STRAIGHT);
                rotatorState = "STRAIGHT";
                break;
            case ROTATED:
                rotator.setPosition(constants.ROTATED);
                rotatorState = "ROTATED";
                break;
            case OPEN:
                claw.setPosition(constants.OPEN);
                clawState = "OPEN";
                break;
            case CLOSED:
                claw.setPosition(constants.CLOSED);
                clawState = "CLOSED";
                break;
            case RESET:
                reset();
        }
    }

    public void setCustomRotation(int direction) {
        rotatorState = "CUSTOM";
        double step = 0;
        if (direction == -1) {
            step = -1;
        }
        else if (direction == 1) {
            step = 1;
        }

        double target = rotator.getPosition() + step;

        rotator.setPosition(target);

        step = 0;
    }

    public double getClawPosition() {
        return claw.getPosition();
    }

    public String getClawState() {
        return clawState;
    }

    public double getRotatorPosition() {
        return rotator.getPosition();
    }

    public String getRotatorState() {
        return rotatorState;
    }

    @Override
    @NonNull
    public String toString() {
        return "Claw Pos: " + claw.getPosition() + ", Rotator Pos: " + rotator.getPosition();
    }

    private void reset() {
        clawState = "RESET";
        claw.setPosition(constants.OPEN);
        rotator.setPosition(constants.STRAIGHT);
        clawState = "OPEN";
        rotatorState = "STRAIGHT";
    }
}
