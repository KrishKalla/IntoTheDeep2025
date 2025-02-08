package org.firstinspires.ftc.teamcode.OpModes.Auto.states;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.subsystems.upperClaw;
import org.firstinspires.ftc.teamcode.subsystems.intake;
import org.firstinspires.ftc.teamcode.subsystems.hslides;
import org.firstinspires.ftc.teamcode.subsystems.slides;

import org.firstinspires.ftc.teamcode.OpModes.Auto.states.leftAutoConstants.LEFT;
import org.firstinspires.ftc.teamcode.utility.constants;

import java.util.LinkedHashMap;
import java.util.Map;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "Autonomous Left", group = "A")
@Config
public class left extends OpMode{
    private static final Map<Integer, LEFT> map = new LinkedHashMap<>();
    static {
        map.put(0, LEFT.INIT);
        map.put(1, LEFT.START);

        map.put(2, LEFT.NAVIGATE_PRELOAD);
        map.put(3, LEFT.SCORE_PRELOAD);

        map.put(4, LEFT.PICKUP_1);
        map.put(5, LEFT.NAVIGATE_1);
        map.put(6, LEFT.SCORE_1);

        map.put(7, LEFT.PICKUP_2);
        map.put(8, LEFT.NAVIGATE_2);
        map.put(9, LEFT.SCORE_2);

        map.put(10, LEFT.PICKUP_3);
        map.put(11, LEFT.NAVIGATE_3);
        map.put(12, LEFT.SCORE_3);

        map.put(13, LEFT.PARK);

    }

    private LEFT currentState;
    private LEFT nextState;
    public static int state = 0;

    private PathChain
        navigatePreload,
        pickup1, navigate1,
        pickup2, navigate2,
        pickup3, navigate3,
        park;

    private Follower follower;
    private Timer pathTimer;
    private Timer actionTimer;
    private Timer opModeTimer;

    private slides slides;
    private hslides hslides;
    private upperClaw upperClaw;
    private intake intake;

    Runnable specimen_score = new Runnable() {
        @Override
        public void run() {
            upperClaw.preset(constants.UPPERCLAW.SPECIMEN_SCORE);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            upperClaw.preset(constants.UPPERCLAW.OPEN);
            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            upperClaw.preset(constants.UPPERCLAW.BACK);
            slides.preset(constants.VSLIDES.BASE);
        }
    };

    Runnable transfer = new Runnable() {
        @Override
        public void run() {
            hslides.preset(constants.HSLIDES.RETRACTED);
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
            upperClaw.preset(constants.UPPERCLAW.BACK);
            intake.preset(constants.INTAKE.PUSHOUT);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    Runnable pickup = new Runnable() {
        @Override
        public void run() {
            Timer t = new Timer();
            for (t.getElapsedTimeSeconds(); t.getElapsedTimeSeconds() < 3; t.getElapsedTimeSeconds()) {
                intake.preset(constants.INTAKE.TAKEIN);
                hslides.crudeSetCustomExtension(1);
            }
        }
    };

    Runnable scorePosition = new Runnable() {
        @Override
        public void run() {
            slides.preset(constants.VSLIDES.HIGH_BASKET);
            upperClaw.preset(constants.UPPERCLAW.SCORE);
        }
    };

    Runnable sampleScore = new Runnable() {
        @Override
        public void run() {
            upperClaw.preset(constants.UPPERCLAW.OPEN);
        }
    };

    Runnable ready = new Runnable() {
        @Override
        public void run() {
            slides.preset(constants.VSLIDES.BASE);
            upperClaw.preset(constants.UPPERCLAW.BACK);
        }
    };

    public void buildPaths() {
        navigatePreload = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        leftAutoConstants.startPoint,
                        leftAutoConstants.navigatePreload
                )))
                .setConstantHeadingInterpolation(Math.toRadians(leftAutoConstants.navigatePreloadHeading))
                .addParametricCallback(0.2, scorePosition)
                .build();

        pickup1 = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        leftAutoConstants.navigatePreload,
                        leftAutoConstants.pickup1
                )))
                .setConstantHeadingInterpolation(Math.toRadians(leftAutoConstants.pickup1Heading))
                .addParametricCallback(0.3, ready)
                .build();

        navigate1 = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        leftAutoConstants.pickup1,
                        leftAutoConstants.navigate1
                )))
                .setConstantHeadingInterpolation(Math.toRadians(leftAutoConstants.navigate1Heading))
                .addParametricCallback(0.1, scorePosition)
                .build();

        pickup2 = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        leftAutoConstants.navigate1,
                        leftAutoConstants.pickup2
                )))
                .setConstantHeadingInterpolation(Math.toRadians(leftAutoConstants.pickup2Heading))
                .addParametricCallback(0.3, ready)
                .build();

        navigate2 = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        leftAutoConstants.pickup2,
                        leftAutoConstants.navigate2
                )))
                .setConstantHeadingInterpolation(Math.toRadians(leftAutoConstants.navigate2Heading))
                .addParametricCallback(0.1, scorePosition)
                .build();

        pickup3 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        leftAutoConstants.navigate2,
                        leftAutoConstants.pickup3Control1,
                        leftAutoConstants.pickup3
                )))
                .setConstantHeadingInterpolation(Math.toRadians(leftAutoConstants.pickup3Heading))
                .addParametricCallback(0.3, ready)
                .build();

        navigate3 = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        leftAutoConstants.pickup3,
                        leftAutoConstants.navigate3
                )))
                .setConstantHeadingInterpolation(Math.toRadians(leftAutoConstants.navigate3Heading))
                .addParametricCallback(0.1, scorePosition)
                .build();

        park = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        leftAutoConstants.navigate3,
                        leftAutoConstants.park
                )))
                .setConstantHeadingInterpolation(Math.toRadians(leftAutoConstants.parkHeading))
                .addParametricCallback(0.3, ready)
                .build();
    }

    public void pathUpdater() {
        switch(nextState) {
            case INIT:
                currentState = LEFT.INIT;
                upperClaw.preset(constants.UPPERCLAW.CLOSED);
                break;

            case START:
                currentState = LEFT.START;
                setNextPath(LEFT.NAVIGATE_PRELOAD);
                break;

            case NAVIGATE_PRELOAD:
                if (!follower.isBusy()) {
                    currentState = LEFT.NAVIGATE_PRELOAD;
                    follower.followPath(navigatePreload);
                    setNextPath(LEFT.SCORE_PRELOAD);
                }
                break;

            case SCORE_PRELOAD:
                if (!follower.isBusy()){
                    currentState = LEFT.SCORE_PRELOAD;
                    Thread t = new Thread(sampleScore);
                    t.start();
                    setNextPath(LEFT.PICKUP_1);
                }
                break;

            case PICKUP_1:
                if (!follower.isBusy() && actionTimer.getElapsedTimeSeconds() > 3) {
                    currentState = LEFT.PICKUP_1;
                    follower.followPath(pickup1);
                    setNextPath(LEFT.NAVIGATE_1);
                }
                break;

            case NAVIGATE_1:
                if (pathTimer.getElapsedTimeSeconds() > 2.5) {
                    Thread t = new Thread(pickup);
                    t.start();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 5) {
                    currentState = LEFT.NAVIGATE_1;
                    follower.followPath(navigate1);
                    setNextPath(LEFT.SCORE_1);
                }
                break;

            case SCORE_1:
                if (!follower.isBusy()) {
                    currentState = LEFT.SCORE_1;
                    Thread t = new Thread(sampleScore);
                    t.start();
                    setNextPath(LEFT.PICKUP_2);
                }
                break;

            case PICKUP_2:
                if (!follower.isBusy() && actionTimer.getElapsedTimeSeconds() > 3) {
                    currentState = LEFT.PICKUP_2;
                    follower.followPath(pickup2);
                    setNextPath(LEFT.NAVIGATE_2);
                }
                break;

            case NAVIGATE_2:
                if (pathTimer.getElapsedTimeSeconds() > 2.5) {
                    Thread t = new Thread(pickup);
                    t.start();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 5) {
                    currentState = LEFT.NAVIGATE_2;
                    follower.followPath(navigate2);
                    setNextPath(LEFT.SCORE_2);
                }
                break;

            case SCORE_2:
                if (!follower.isBusy()) {
                    currentState = LEFT.SCORE_2;
                    Thread t = new Thread(sampleScore);
                    t.start();
                    setNextPath(LEFT.PICKUP_3);
                }
                break;

            case PICKUP_3:
                if (!follower.isBusy() && actionTimer.getElapsedTimeSeconds() > 3) {
                    currentState = LEFT.PICKUP_3;
                    follower.followPath(pickup3);
                    setNextPath(LEFT.NAVIGATE_3);
                }
                break;

            case NAVIGATE_3:
                if (pathTimer.getElapsedTimeSeconds() > 2.5) {
                    Thread t = new Thread(pickup);
                    t.start();
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 5) {
                    currentState = LEFT.NAVIGATE_3;
                    follower.followPath(navigate3);
                    setNextPath(LEFT.SCORE_3);
                }
                break;

            case SCORE_3:
                if (!follower.isBusy()) {
                    currentState = LEFT.SCORE_3;
                    Thread t = new Thread(sampleScore);
                    t.start();
                    setNextPath(LEFT.PARK);
                }
                break;

            case PARK:
                if (!follower.isBusy()) {
                    currentState = LEFT.PARK;
                    follower.followPath(park);
                    setNextPath(LEFT.IDLE);
                }
                break;
        }

    }

    public void setNextPath(LEFT nextPath) {
        nextState = nextPath;
        pathTimer.resetTimer();
        actionTimer.resetTimer();
    }

    @Override
    public void init() {
        Follower.drawOnDashboard = true;

        pathTimer = new Timer();
        opModeTimer = new Timer();
        actionTimer = new Timer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(rightAutoConstants.startPose);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        intake = new intake();
        upperClaw = new upperClaw();
        slides = new slides();
        hslides = new hslides();

        slides.init(hardwareMap);
        upperClaw.init(hardwareMap);
        hslides.init(hardwareMap);
        intake.init(hardwareMap);

        telemetry.addLine("Right Side Autonomous Initiated... Waiting For Start...");
        telemetry.addData("Current State: ", currentState);
        telemetry.update();

        buildPaths();
        setNextPath(LEFT.INIT);
        pathUpdater();
    }

    @Override
    public void init_loop() {
        telemetry.addData("Current State: ", currentState);
        telemetry.update();
    }

    @Override
    public void start() {
        opModeTimer.resetTimer();
        setNextPath(LEFT.START);
    }

    @Override
    public void loop() {
        follower.update();
        pathUpdater();
        infoUpdater();
    }


    //Needs to be updated
    private void infoUpdater() {
        telemetry.addData("OpMode Timer: ", opModeTimer.getElapsedTimeSeconds());
        telemetry.addData("Path State: ", currentState);
        telemetry.addData("Is Follower Busy? ", follower.isBusy() ?"yes" : "no");
        telemetry.addData("Path Timer: ", pathTimer.getElapsedTimeSeconds());
        telemetry.addData("Field Position: ", follower.getPose().toString());
        telemetry.addLine();
        telemetry.addData("Action Timer: ", actionTimer.getElapsedTimeSeconds());
        telemetry.addLine();
        telemetry.addData("Left Slide Height: ", slides.getLeftHeight());
        telemetry.addData("Right Slide Height: ", slides.getRightHeight());
        telemetry.addLine();
        telemetry.addData("Slide Extension: ", hslides.getState());
        telemetry.addLine();
        telemetry.update();
    }


}
