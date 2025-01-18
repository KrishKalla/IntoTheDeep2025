package org.firstinspires.ftc.teamcode.subsystems;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.constants;


public class hslides {
    private String state = "INIT";
    private final double cutoff = constants.cutoff;
    private double x = constants.i;

    private Servo leftServo;
    private Servo rightServo;

    public hslides() {
    }

    public void init(HardwareMap map) {
        leftServo = map.get(Servo.class, "extendL");
        rightServo = map.get(Servo.class, "extendR");

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
        return (Math.PI/2 - Math.acos(((x+constants.i)/2)/constants.length)) * 1/(Math.PI);
    }

    public void setCustomExtension(int direction) {
        double step = 0;
        if (direction == 1) {
            step = 0.25;
            setExtension(x + step);
        }
        else if (direction == -1) {
            step = -0.25;
            setExtension(x + step);
        }

    }

    public void setExtension(double x) {
        if (x > cutoff) {
            state = "INVALID";
        }
        else {
            this.x = x;
            double theta = calculate(this.x);
            leftServo.setPosition(theta);
            rightServo.setPosition(1 - theta);
            state = "CUSTOM";
        }
    }

    public void crudeSetCustomExtension(int direction) {
        double step = 0;
        if (direction == 1) {
            step = constants.CRUDE_STEP;

        }
        if (direction == -1) {
            step = -constants.CRUDE_STEP;
        }

        if (leftServo.getPosition() + step < constants.pivot_left_retracted || leftServo.getPosition() + step > constants.pivot_left_extended || rightServo.getPosition() - step > constants.pivot_right_retracted || rightServo.getPosition() - step < constants.pivot_right_extended) {
            state = "INVALID";
        }
        else {
            leftServo.setPosition(leftServo.getPosition() + step);
            rightServo.setPosition(rightServo.getPosition() - step);
        }
    }

    public void unlatch(){
        leftServo.setPosition(constants.pivot_left_retracted + 4*constants.CRUDE_STEP);
        rightServo.setPosition(constants.pivot_right_retracted - 4*constants.CRUDE_STEP);
    }

    public String getState() {
        return state;
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
