package org.firstinspires.ftc.teamcode.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.utility.constants;

public class slides {
    private String state = null;

    private DcMotorEx left;
    private DcMotorEx right;

    private double speed;



    public void init(HardwareMap map) {
        state = "INIT";

        left = map.get(DcMotorEx.class, "slideL");
        right = map.get(DcMotorEx.class, "slideR");

        left.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        left.setDirection(DcMotorEx.Direction.REVERSE);
        right.setDirection(DcMotorEx.Direction.FORWARD);

        reset();
    }



    public void preset(constants.VSLIDES preset) {
        switch (preset) {
            case BASE:
                setHeight(constants.BASE);
                state = "BASE";
                break;
            case LOW_BAR:
                setHeight(constants.LOW_BAR);
                state = "LOW_BAR";
                break;
            case HIGH_BAR:
                setHeight(constants.HIGH_BAR);
                state = "HIGH_BAR";
                break;
            case LOW_BASKET:
                setHeight(constants.LOW_BASKET);
                state = "LOW_BASKET";
                break;
            case HIGH_BASKET:
                setHeight(constants.HIGH_BASKET);
                state = "HIGH_BASKET";
                break;
            case LOW_RUNG:
                setHeight(constants.LOW_RUNG);
                state = "LOW_RUNG";
                break;
            case HIGH_RUNG:
                setHeight(constants.HIGH_RUNG);
                state = "HIGH_RUNG";
                break;
            case RESET:
                reset();
                break;
        }
    }


    private double getVariableSpeed(int target) {
        double newSpeed;

        int current = getHeight();

        if (target - current > 100) {
            newSpeed = 0.6;
        }
        else if (target - current > 50) {
            newSpeed = 0.4;
        }
        else {
            newSpeed = 0.2;
        }
        return newSpeed;
    }

    public void setHeight(int target) {
        while (getHeight() != target){
            speed = getVariableSpeed(target);

            left.setTargetPosition(target);
            right.setTargetPosition(target);

            left.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            right.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

            left.setPower(speed);
            right.setPower(speed);
        }
    }


    public void setCustomHeight(int direction) {
        state = "CUSTOM";
        int step = 0;
        if (direction == 1) {
            step = 5;
        }
        if (direction == -1) {
            step = -5;
        }
        int target = getHeight() + step;

        while (getHeight() != target){
            speed = getVariableSpeed(target);

            left.setTargetPosition(target);
            right.setTargetPosition(target);

            left.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            right.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

            left.setPower(speed);
            right.setPower(speed);
        }
        step = 0;
    }

    public int getHeight() {
        return ((left.getCurrentPosition() + right.getCurrentPosition()) / 2);
    }

    @Override
    @NonNull
    public String toString() {
        return state;
    }

    private void reset() {
        state = "RESET";
        left.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        state = "BASE";
    }
}
