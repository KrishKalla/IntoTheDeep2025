package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utility.constants;

public class claw {
    private Servo claw;
    private Servo wrist;

    private String clawState;
    private String wristState;

    public claw() {
        clawState = "PRESET";}

    public void init(HardwareMap map) {
        claw = map.get(Servo.class, "hand");
        wrist = map.get(Servo.class, "wrist");

        reset();
    }

    public void preset(constants.CLAW preset) {
        switch (preset) {
            case STRAIGHT:
                wrist.setPosition(constants.STRAIGHT);
                wristState = "STRAIGHT";
                break;
            case ROTATED:
                wrist.setPosition(constants.ROTATED);
                wristState = "ROTATED";
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
        wristState = "CUSTOM";
        double step = 0;
        if (direction == -1) {
            step = constants.WRIST_STEP;
        }
        else if (direction == 1) {
            step = -constants.WRIST_STEP;
        }

        double target = wrist.getPosition() + step;

        wrist.setPosition(target);

        step = 0;
    }

    public double getClawPosition() {
        return claw.getPosition();
    }

    public String getClawState() {
        return clawState;
    }

    public double getRotatorPosition() {
        return wrist.getPosition();
    }

    public String getWristState() {
        return wristState;
    }

    @Override
    @NonNull
    public String toString() {
        return "Claw Pos: " + claw.getPosition() + ", Rotator Pos: " + wrist.getPosition();
    }

    private void reset() {
        clawState = "RESET";
        claw.setPosition(constants.OPEN);
        wrist.setPosition(constants.STRAIGHT);
        clawState = "OPEN";
        wristState = "STRAIGHT";
    }
}
