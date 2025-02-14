//Gamepad 1 -- Krish -- Drivetrain & Vertical Slides
//Gamepad 2 -- Bodie -- Horizontal Slides & Intake & Upper Claw




package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.util.Timer;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

import com.pedropathing.util.Timer;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.teamcode.OpModes.TeleOp.TeleConstants.TELE;
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

    private TELE currentState;
    private TELE nextState = TELE.IDLE;
    private Timer pathTimer;
    private Timer actionTimer;
    private Timer opModeTimer;
    private PathChain navigate, pickup;

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
        pathTimer = new Timer();
        actionTimer = new Timer();
        opModeTimer = new Timer();

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
        buildPaths();
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
        opModeTimer.resetTimer();

    }

    @Override
    public void loop() {
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -prioitizedStrafe(), -gamepad1.right_stick_x, true);
        follower.update();
        pathUpdater();

        if (gamepad1.left_stick_x > 0.5 || gamepad1.left_stick_y > 0.5) {
            setNextPath(TELE.IDLE);
        }
        //region Controller 1 Inputs
        //region Slide Inputs
        if (gamepad1.dpad_down) {
            slides.preset(constants.VSLIDES.BASE);
            upperClaw.preset(constants.UPPERCLAW.BACK);
        }

        if (gamepad1.dpad_left) {
            slides.preset(constants.VSLIDES.LOW_BASKET);
            upperClaw.preset(constants.UPPERCLAW.SCORE);
        }

        if (gamepad1.dpad_up) {
            slides.preset(constants.VSLIDES.HIGH_BASKET);
            upperClaw.preset(constants.UPPERCLAW.SCORE);
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
        if (gamepad1.right_stick_button) {
            buildPaths();
            setNextPath(TELE.NAVIGATE);
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
            Runnable specimen_align = new Runnable() {
                @Override
                public void run() {
                    upperClaw.preset(constants.UPPERCLAW.SPECIMEN_ALIGN);
                    slides.preset(constants.VSLIDES.LOW_BAR);
                }
            };
            Thread t = new Thread(specimen_align);
            t.start();
        }

        if (gamepad2.right_stick_button) {
            Runnable specimen_score = new Runnable() {
                @Override
                public void run() {
                    slides.preset(constants.VSLIDES.HIGH_BAR);
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    upperClaw.preset(constants.UPPERCLAW.SPECIMEN_SCORE);
                    try {
                        Thread.sleep(750);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    upperClaw.preset(constants.UPPERCLAW.OPEN);
//                    upperClaw.preset(constants.UPPERCLAW.BACK);
//                    slides.preset(constants.VSLIDES.BASE);
                }
            };
//            Runnable specimen_score = new Runnable() {
//                @Override
//                public void run() {
//                    slides.setHeight(420);
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    upperClaw.preset(constants.UPPERCLAW.SPECIMEN_SCORE);
//                    try {
//                        Thread.sleep(350);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    slides.preset(constants.VSLIDES.HIGH_BAR);
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    upperClaw.preset(constants.UPPERCLAW.OPEN);
//                    try {
//                        Thread.sleep(350);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    upperClaw.preset(constants.UPPERCLAW.BACK);
//                    slides.preset(constants.VSLIDES.BASE);
//                }
//            };
            Thread t = new Thread(specimen_score);
            t.start();
        }

        if (gamepad2.dpad_right) {
            Runnable transfer = new Runnable() {
                @Override
                public void run() {
                    hslides.preset(constants.HSLIDES.TRANSFER);
                    upperClaw.preset(constants.UPPERCLAW.OPEN);
                    upperClaw.preset(constants.UPPERCLAW.TRANSFER);
                    intake.preset(constants.INTAKE.TRANSFER);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    upperClaw.preset(constants.UPPERCLAW.CLOSED);
                    try {
                        Thread.sleep(350);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Timer timer = new Timer();
                    upperClaw.preset(constants.UPPERCLAW.SCORE);
                    while (timer.getElapsedTimeSeconds() < 0.75){
                        intake.preset(constants.INTAKE.PUSHOUT);
                    }
                    hslides.preset(constants.HSLIDES.RETRACTED);
                    intake.preset(constants.INTAKE.RETRACTED);
                    intake.preset(constants.INTAKE.OFF);
                    slides.preset(constants.VSLIDES.HIGH_BASKET);
                }
            };
            Thread t = new Thread(transfer);
            t.start();
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

        if (opModeTimer.getElapsedTimeSeconds() == 80){
            gamepad1.rumble(200);
            gamepad2.rumble(200);
        }

        if (opModeTimer.getElapsedTimeSeconds() == 110) {
            gamepad1.rumble(500);
            gamepad2.rumble(500);
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
        telemetry.addLine(upperClaw.toString());
        telemetry.addLine(intake.toString());
        telemetry.addLine();
        telemetry.addData("Current State: ", currentState);
        telemetry.addData("Next State: ", nextState);
        telemetry.addData("isRightPressed", gamepad1.right_stick_button);
        telemetry.update();
    }

    public double prioitizedStrafe() {
        if (Math.abs(gamepad1.left_stick_x) > 0.05){
            return gamepad1.left_stick_x;
        }
        else if (Math.abs(gamepad2.right_stick_x) > 0.05) {
            return gamepad2.right_stick_x;
        }
        else {
            return gamepad1.left_stick_x;
        }
    }

    public void pathUpdater() {
        Runnable specimen_align = new Runnable() {
            @Override
            public void run() {
                upperClaw.preset(constants.UPPERCLAW.SPECIMEN_ALIGN);
                slides.preset(constants.VSLIDES.LOW_BAR);
            }
        };

        Runnable specimen_score = new Runnable() {
                @Override
                public void run() {
                    slides.setHeight(420);
                    try {
                        Thread.sleep(350);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    upperClaw.preset(constants.UPPERCLAW.SPECIMEN_SCORE);
                    try {
                        Thread.sleep(350);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    slides.preset(constants.VSLIDES.HIGH_BAR);
                    try {
                        Thread.sleep(350);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    upperClaw.preset(constants.UPPERCLAW.OPEN);
                    upperClaw.preset(constants.UPPERCLAW.BACK);
                }
            };
        switch (nextState){
            case NAVIGATE:
                follower.setPose(new Pose(TeleConstants.pickup.getX(), TeleConstants.pickup.getY(), 0));
                currentState = TELE.NAVIGATE;
                Thread t = new Thread(specimen_align);
                t.start();
                follower.followPath(navigate);
                setNextPath(TELE.SCORE);
                break;

            case SCORE:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 2.8) {
                    currentState = TELE.SCORE;
                    Thread score = new Thread(specimen_score);
                    score.start();
                    setNextPath(TELE.PICKUP);
                }
                break;

            case PICKUP:
                if (pathTimer.getElapsedTimeSeconds() > 1.55) {
                    currentState = TELE.PICKUP;
                    follower.followPath(pickup);
                    setNextPath(TELE.RESET);
                }
                break;
            case RESET:
                if (pathTimer.getElapsedTimeSeconds() > 2.65) {
                    currentState = TELE.RESET;
                    follower.startTeleopDrive();
                    setNextPath(TELE.IDLE);
                }
            case IDLE:
                currentState = TELE.IDLE;
        }
    }

    public void buildPaths() {
        Runnable specimen_align = new Runnable() {
            @Override
            public void run() {
                upperClaw.preset(constants.UPPERCLAW.SPECIMEN_ALIGN);
                slides.preset(constants.VSLIDES.LOW_BAR);
            }
        };

        Runnable ready = new Runnable() {
            @Override
            public void run() {
                slides.preset(constants.VSLIDES.BASE);
                upperClaw.preset(constants.UPPERCLAW.BACK);
            }
        };

        navigate = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        TeleConstants.pickup,
                        TeleConstants.navigate
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(4)
                .setPathEndVelocityConstraint(35)
                .build();

        pickup = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        TeleConstants.navigate,
                        TeleConstants.pickup
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addParametricCallback(0.2, ready)
                .setZeroPowerAccelerationMultiplier(3)
                .build();
    }

    public void setNextPath(TELE nextPath) {
        nextState = nextPath;
        pathTimer.resetTimer();
        actionTimer.resetTimer();
    }

}
