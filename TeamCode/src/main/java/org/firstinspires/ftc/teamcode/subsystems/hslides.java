package org.firstinspires.ftc.teamcode.subsystems;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.constants;


public class hslides {
    private String state = "INIT";
    private double cutoff;
    private double x;

    private Servo leftservo;
    private Servo rightservo;

    public hslides() {
    }

    public void init(HardwareMap map) {
        leftservo = map.get(Servo.class, "left");
        rightservo = map.get(Servo.class, "right");
        state = "INIT";
    }

    public void setState(constants.HSLIDES curr_state) {
        // replace below constants with constants in constants.java once we get them
        switch (curr_state) {
            case EXTENDED:
                leftservo.setPosition(constants.pivot_left_extended);
                rightservo.setPosition(constants.pivot_right_extended);
                state = "EXTENDED";
                break;
            case RETRACTED:
                leftservo.setPosition(constants.pivot_left_retracted);
                rightservo.setPosition(constants.pivot_right_retracted);
                state = "RETRACTED";
                break;
            case CUSTOM:
                if (x > cutoff) {
                    state = "INVALID";
                    break;
                }
                double theta = calculate(x);
                leftservo.setPosition(theta);
                rightservo.setPosition(180-theta);
                state = "CUSTOM";
                break;
        }
    }

    //Theta calculation based on x
    public double calculate(double x){
        return 90 - Math.acos(((x+constants.i)/2)/constants.length);
    }

    public void extend(){
        x += constants.h_step;
    }

    public void retract(){
        x -= constants.h_step;
    }

    public void setX(double x){
        this.x = x;
    }

    @NonNull
    @Override
    public String toString() {
        return ("LEFT: " + leftservo + " RIGHT: " + rightservo);
    }
}
