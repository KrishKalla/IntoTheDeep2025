package org.firstinspires.ftc.teamcode.OpModes.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.manipulator;
import org.firstinspires.ftc.teamcode.utility.constants;


@TeleOp
public class claw_test extends LinearOpMode {

    manipulator claw;
    String clawState = null;
  
    private static ElapsedTime timer;

    @Override
    public void runOpMode() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        claw = new manipulator();
        timer = new ElapsedTime();

        claw.init(hardwareMap);

        while (opModeInInit()) {
            telemetry.addLine("Ready to Start... Initialization Mode");
            clawState = claw.toString();
            getTelemetry();
        }

        waitForStart();
        timer.reset();

        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad1.right_trigger >= 0.3) {
                claw.activate(constants.ROTOR_STATE.ON);
            }
            if (gamepad1.left_trigger >= 0.3) {
                claw.activate(constants.ROTOR_STATE.REVERSE);
            }
            if (gamepad1.right_trigger < 0.3 && gamepad1.left_trigger < 0.3) {
                claw.activate(constants.ROTOR_STATE.OFF);
            }

            getTelemetry();
        }
    }

    public void getTelemetry() {
        telemetry.addData("Claw State: ", claw.toString());
        telemetry.addData("Timer: ", timer);
        telemetry.update();
    }

    public void updateStates() {
        clawState = claw.toString();
    }
}
