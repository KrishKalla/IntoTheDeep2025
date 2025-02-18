package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utility.constants;

public class upperClaw {
    private Servo left;
    private Servo right;
    private Servo claw;

    private String clawState;
    private String pivotState;

    public upperClaw() {
        clawState = "PRESET";
        pivotState = "PRESET";
    }

    public void init(HardwareMap map) {
        claw = map.get(Servo.class, "upperClaw");
        left = map.get(Servo.class, "upperL");
        right = map.get(Servo.class, "upperR");

        reset();
    }

    public void init(HardwareMap map, boolean isAuto) {
        claw = map.get(Servo.class, "upperClaw");
        left = map.get(Servo.class, "upperL");
        right = map.get(Servo.class, "upperR");
        if (isAuto){
           preset(constants.UPPERCLAW.AUTO_INIT);
        }
    }

    public void preset(constants.UPPERCLAW preset) {
        switch (preset) {
            case OPEN:
                claw.setPosition(constants.OPEN);
                clawState = "OPEN";
                break;
            case CLOSED:
                claw.setPosition(constants.CLOSED);
                clawState = "CLOSED";
                break;
            case TRANSFER:
                left.setPosition(1-constants.transfer);
                right.setPosition(constants.transfer);
                pivotState = "TRANSFER";
                break;
            case BACK:
                left.setPosition(1-constants.back);
                right.setPosition(constants.back);
                pivotState = "BACK";
                break;
            case SCORE:
                left.setPosition(1-constants.score);
                right.setPosition(constants.score);
                pivotState = "SCORE";
                break;
            case SPECIMEN_ALIGN:
                left.setPosition(1-constants.specimen);
                right.setPosition(constants.specimen);
                pivotState = "SPECIMEN_ALIGN";
                break;
            case SPECIMEN_SCORE:
                left.setPosition(1-constants.specimen_score);
                right.setPosition(constants.specimen_score);
                pivotState = "SPECIMEN_SCORE";
                break;
            case AUTO_INIT:
                left.setPosition(1-constants.autoInit);
                right.setPosition(constants.autoInit);
                pivotState = "AUTO_INIT";
                break;
            case RESET:
                reset();
        }
    }

    public double getClawPosition() {return claw.getPosition();}

    public String getClawState() {return clawState;}

    public String getPivotState() {return pivotState;}

    public String getPivotPosition() {return "Left Pivot" + left.getPosition() + ", Right Pivot: " + right.getPosition();}

    @Override
    @NonNull
    public String toString() {
        return "Claw State: " + clawState + ", Pivot State: " + pivotState;
    }

    public void reset() {
        clawState = "RESET";
        claw.setPosition(constants.OPEN);
        left.setPosition(1-constants.back);
        right.setPosition(constants.back);
        clawState = "OPEN";
        pivotState = "BACK";
    }
}
