package org.firstinspires.ftc.teamcode.subsystems;
import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utility.constants;

import java.util.Objects;
public class intake {
    private Servo right;
    private Servo left;
    private CRServo cr_right;
    private CRServo cr_left;

    private String state;
    private String crState;

    public intake() {state = "PRESET";}

    public void init(HardwareMap map) {
        left = map.get(Servo.class, "shoulderL");
        right = map.get(Servo.class, "shoulderR");
        cr_left = map.get(CRServo.class, "intakeL");
        cr_right = map.get(CRServo.class, "intakeR");

        reset();
    }

    public void preset(constants.INTAKE preset) {
        switch (preset) {
            case GROUND:
                state = "GROUND";
                left.setPosition(1 - constants.GROUND_PIVOT);
                right.setPosition(constants.GROUND_PIVOT);
                break;
            case PRE_PICKUP:
                state = "PRE_PICKUP";
                left.setPosition(1 - constants.PRE_PICKUP);
                right.setPosition(constants.PRE_PICKUP);
                break;
            case TRANSFER:
                state = "TRANSFER";
                left.setPosition(1-constants.TRANSFER);
                right.setPosition(constants.TRANSFER);
                break;
            case RETRACTED:
                state = "RETRACTED";
                left.setPosition(1-constants.RETRACTED);
                right.setPosition(constants.RETRACTED);
                break;
            case PUSHOUT:
                cr_right.setDirection(DcMotorSimple.Direction.REVERSE);
                cr_right.setPower(1.0);
                cr_left.setDirection(DcMotorSimple.Direction.FORWARD);
                cr_left.setPower(1.0);
                crState = "PUSHOUT";
                break;
            case TAKEIN:
                cr_right.setDirection(DcMotorSimple.Direction.FORWARD);
                cr_right.setPower(1.0);
                cr_left.setDirection(DcMotorSimple.Direction.REVERSE);
                cr_left.setPower(1.0);
                crState = "TAKEIN";
                break;
            case OFF:
                crState = "OFF";
                cr_left.setPower(0.0);
                cr_right.setPower(0.0);
                break;
            case RESET:
                reset();
                break;
        }
    }

    @Override
    @NonNull
    public String toString() {
        return "LPIVOT pos: " + left.getPosition() + "RPIVOT pos: " + right.getPosition() + "CRLEFT DIR: " + cr_left.getDirection() + "CRLEFT POWER: " + cr_left.getPower() + "CRRIGHT DIR: "  +cr_right.getDirection() + "CRRIGHT POWER: " + cr_right.getPower();
    }

    private void customPivot(double angle_piv) {
        state = "RESET";
        left.setPosition(1-angle_piv);
        right.setPosition(angle_piv);
        state = "GROUND";
    }

    private void reset() {
        state = "RESET";
        cr_right.setPower(0);
        cr_left.setPower(0);
        left.setPosition(1-constants.RETRACTED);
        right.setPosition(constants.RETRACTED);
        state = "RETRACTED";
        crState = "OFF";
    }
}
