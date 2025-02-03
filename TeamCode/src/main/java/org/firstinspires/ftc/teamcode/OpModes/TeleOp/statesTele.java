//Gamepad 1 -- Krish -- Drivetrain & Vertical Slides
//Gamepad 2 -- Bodie -- Horizontal Slides & Intake & Upper Claw




package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.hslides;
import org.firstinspires.ftc.teamcode.subsystems.intake;
import org.firstinspires.ftc.teamcode.subsystems.slides;
import org.firstinspires.ftc.teamcode.subsystems.upperClaw;

import org.firstinspires.ftc.teamcode.utility.constants;

@TeleOp(name = "TeleOp")
public class statesTele extends OpMode{
    //Pedro Pathing TeleOp Follower Business
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);

    //All Subsystems
    private slides slides;
    private hslides hslides;
    private intake intake;
    private upperClaw upperClaw;

    private String alliance = "NOT SET";

    @Override
    public void init(){
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        intake = new intake();
        upperClaw = new upperClaw();
        slides = new slides();
        hslides = new hslides();

        intake.init(hardwareMap);
        upperClaw.init(hardwareMap);
        slides.init(hardwareMap);
        hslides.init(hardwareMap);

        telemetry.addLine("TeleOp Initiated... Waiting For Start...");
        telemetry.update();
    }

    @Override
    public void init_loop() {
        if (gamepad1.circle) {
            alliance = "RED";
        }
        else if (gamepad1.cross) {
            alliance = "BLUE";
        }
    }

    @Override
    public void start() {

        follower.startTeleopDrive();

    }

    @Override
    public void loop() {
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();

        //region Controller 1 Inputs
        //region Slide Inputs
        if (gamepad1.dpad_down) {
            slides.preset(constants.VSLIDES.BASE);
        }

        if (gamepad1.dpad_left) {
            slides.preset(constants.VSLIDES.LOW_BASKET);
        }

        if (gamepad1.dpad_up) {
            slides.preset(constants.VSLIDES.HIGH_BASKET);
        }

        if (gamepad1.dpad_right) {
            slides.preset(constants.VSLIDES.HIGH_BAR);
        }

        if (gamepad1.square) {
            slides.preset(constants.VSLIDES.LOW_BAR);
        }

        if (gamepad1.triangle) {
            slides.preset(constants.VSLIDES.HIGH_RUNG);
        }
        if (gamepad1.cross) {
            slides.preset(constants.VSLIDES.LOW_RUNG);
        }


        if (gamepad1.right_trigger > 0.3) {
            slides.setCustomHeight(1);
        }

        if (gamepad1.left_trigger > 0.3) {
            slides.setCustomHeight(-1);
        }

        if (gamepad1.left_bumper) {
            slides.left.setTargetPosition(slides.left.getCurrentPosition() - 30);
            slides.left.setPower(constants.MAX_SLIDE_SPEED);
        }

        if (gamepad1.right_bumper && gamepad1.left_bumper) {
            slides.preset(constants.VSLIDES.RESET);
        }
        //endregion
        //endregion

        //region Controller 2 Inputs
        //region UpperClaw Inputs
        if (gamepad2.circle){
            upperClaw.preset(constants.UPPERCLAW.OPEN);
        }

        if (gamepad2.cross){
            upperClaw.preset(constants.UPPERCLAW.BACK);
        }

        if (gamepad2.square) {
            upperClaw.preset(constants.UPPERCLAW.CLOSED);
        }

        if (gamepad2.triangle) {
            upperClaw.preset(constants.UPPERCLAW.SPECIMEN);
        }

        if (gamepad2.dpad_right) {
            upperClaw.preset(constants.UPPERCLAW.TRANSFER);
        }





        //endregion
        //region Intake Inputs
        if (gamepad2.dpad_down) {
            intake.preset(constants.INTAKE.GROUND);
        }

        if (gamepad2.dpad_left) {
            intake.preset(constants.INTAKE.PRE_PICKUP);
        }

        if (gamepad2.dpad_up) {
            intake.preset(constants.INTAKE.RETRACTED);
        }

        if (gamepad2.left_trigger > 0.3) {
            intake.preset(constants.INTAKE.PUSHOUT);
        }

        if (gamepad2.right_trigger > 0.3) {
            intake.preset(constants.INTAKE.TAKEIN);
        }
        if (gamepad2.left_trigger < 0.3 && gamepad2.right_trigger < 0.3) {
            intake.preset(constants.INTAKE.OFF);
        }


        //endregion
        //region HSlides Inputs
        if (gamepad2.right_bumper) {
            hslides.preset(constants.HSLIDES.EXTENDED);
        }

        if (gamepad2.left_bumper) {
            hslides.preset(constants.HSLIDES.RETRACTED);
        }

        if (gamepad2.left_stick_y > 0.3) {
            hslides.crudeSetCustomExtension(-1);
        }

        if (gamepad2.left_stick_y < -0.3) {
            hslides.crudeSetCustomExtension(1);
        }

        if (gamepad2.options) {
            hslides.preset(constants.HSLIDES.RESET);
        }

        //endregion
        //endregion


        updateTelemetry();
    }

    @Override
    public void stop() {

    }


    public void updateTelemetry() {
        telemetry.addLine("Alliance: " + alliance);
        telemetry.addData("X: ", follower.getPose().getX());
        telemetry.addData("Y: ", follower.getPose().getY());
        telemetry.addData("Heading: ", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.addLine("---------SLIDES--------");
        telemetry.addLine(slides.toString());
        telemetry.addData("Slide Speed: ", slides.getSpeed());
        telemetry.addData("Right Slide: ", slides.getRightHeight());
        telemetry.addData("Left Slide: ", slides.getLeftHeight());
        telemetry.addLine(hslides.toString());
        telemetry.addData("hslide state: ", hslides.getState());
        telemetry.addLine("----------CLAW---------");
        telemetry.addLine(claw.toString());
        telemetry.addLine(arm.toString());

        telemetry.update();
    }

}
