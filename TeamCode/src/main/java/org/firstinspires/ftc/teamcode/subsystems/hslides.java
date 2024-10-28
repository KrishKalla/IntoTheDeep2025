package org.firstinspires.ftc.teamcode.subsystems;

public class hslides {
    private state = "INIT";
    private cutoff = null;
    private Servo servothing;

    public void init(HardwareMap map) {
        leftservo = map.get(Servo.class, "left")
        rightservo = map.get(Servo.class, "right")
        state = "INIT"
    }

    public void setState() {

    }

    public void activate(constants.ROTOR_STATE state) {

    }
}
