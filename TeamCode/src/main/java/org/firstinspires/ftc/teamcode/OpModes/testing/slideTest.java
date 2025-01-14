package org.firstinspires.ftc.teamcode.OpModes.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.slides;

import org.firstinspires.ftc.teamcode.utility.constants;

@TeleOp(name = "TeleOp", group = "testing")
public class slideTest extends OpMode {

    private slides slides;

    @Override
    public void init(){
        slides = new slides();
        slides.init(hardwareMap, true);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {;

    }

    @Override
    public void loop() {
        telemetry.addData("Left Slide Height: ", slides.getLeftHeight());
        telemetry.addData("Right Slide Height: ", slides.getRightHeight());
        telemetry.update();
    }

    @Override
    public void stop() {

    }



}
