package org.firstinspires.ftc.teamcode.subsystems;
import org.firstinspires.ftc.teamcode.utility.constants;
public class hslides {
    private state = "INIT";
    private cutoff;

    private Servo leftservo;
    private Servo rightservo;

    public hslides() {
    }

    public void init(HardwareMap map) {
        leftservo = map.get(Servo.class, "left")
        rightservo = map.get(Servo.class, "right")
        state = "INIT"
    }

    public void setState(constants.HSLIDES curr_state) {
        // replace below constnats with constants in constants.java once we get them
        double pivot_left_extended;
        double pivot_right_extended;

        double pivot_left_retracted;
        double pivot_right_retracted;

        double i;
        double x;
        double length;

        switch (curr_state) {
            case EXTENDED:
                leftservo.setPosition(pivot_left_extended);
                rightservo.setPosition(pivot_right_extended)
                state = "EXTENDED";
                break;
            case RETRACTED:
                leftservo.setPosition(pivot_left_retracted);
                rightservo.setPosiiton(pivot_right_retracted);
                state = "RETRACTED";
                break
            case CUSTOM:
                if (x > cutoff) {
                    state = "INVALID";
                    break;
                }
                double ninetytheta = Math.acos(((x+i)/2)/length);
                double theta = 90-ninetytheta;
                leftservo.setPosition(theta);
                rightservo.setPosition(180-theta);
                state = "CUSTOM";
                break;
        }
    }

    public String toString() {
        return "LEFT: " + leftservo + " RIGHT: " + rightservo
    }
}
