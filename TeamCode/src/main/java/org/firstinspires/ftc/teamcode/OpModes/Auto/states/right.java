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


import org.firstinspires.ftc.teamcode.OpModes.Auto.AConstants;
import org.firstinspires.ftc.teamcode.subsystems.arm;
import org.firstinspires.ftc.teamcode.subsystems.claw;
import org.firstinspires.ftc.teamcode.subsystems.upperClaw;
import org.firstinspires.ftc.teamcode.subsystems.intake;
import org.firstinspires.ftc.teamcode.subsystems.hslides;
import org.firstinspires.ftc.teamcode.subsystems.slides;

import org.firstinspires.ftc.teamcode.OpModes.Auto.states.rightAutoConstants;
import org.firstinspires.ftc.teamcode.OpModes.Auto.states.rightAutoConstants.RIGHT;
import org.firstinspires.ftc.teamcode.utility.constants;

import java.util.LinkedHashMap;
import java.util.Map;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "Autonomous Right", group = "A")
@Config
public class right extends OpMode{
    private static final Map<Integer, RIGHT> map = new LinkedHashMap<>();
    static {
        map.put(0, RIGHT.INIT);
        map.put(1, RIGHT.START);

        map.put(2, RIGHT.NAVIGATE_PRELOAD);
        map.put(3, RIGHT.SCORE_PRELOAD);

        map.put(4, RIGHT.ALIGN_1);
        map.put(5, RIGHT.PUSH_1);
        map.put(6, RIGHT.ALIGN_2);
        map.put(7, RIGHT.PUSH_2);

        map.put(8, RIGHT.PICKUP_CACHE);
        map.put(9, RIGHT.NAVIGATE_CACHE);
        map.put(10, RIGHT.SCORE_CACHE);

        map.put(11, RIGHT.PICKUP_1);
        map.put(12, RIGHT.NAVIGATE_1);
        map.put(13, RIGHT.SCORE_1);

        map.put(14, RIGHT.PICKUP_2);
        map.put(15, RIGHT.NAVIGATE_2);
        map.put(16, RIGHT.SCORE_2);

        map.put(17, RIGHT.PARK);
    }

    private RIGHT currentState;
    private RIGHT nextState;
    public static int state = 0;

    private PathChain
            navigatePreload,
            align1, push1, align2, push2, push,
            pickupCache, navigateCache,
            pickup1, navigate1,
            pickup2, navigate2,
            park;

    private Follower follower;
    private Timer pathTimer;
    private Timer actionTimer;
    private Timer opModeTimer;

    private slides slides;
    private hslides hslides;
    private upperClaw upperClaw;
    private intake intake;

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
                Thread.sleep(500);
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
            upperClaw.preset(constants.UPPERCLAW.BACK);
            intake.preset(constants.INTAKE.PUSHOUT);
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hslides.preset(constants.HSLIDES.RETRACTED);
            intake.preset(constants.INTAKE.RETRACTED);
            intake.preset(constants.INTAKE.OFF);
        }
    };



    public void buildPaths() {
        navigatePreload = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        rightAutoConstants.startPoint,
                        rightAutoConstants.navigatePreloadControl1,
                        rightAutoConstants.navigatePreload
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addParametricCallback(0, specimen_align)
                .setZeroPowerAccelerationMultiplier(3)
//                .setPathEndVelocityConstraint(35)
                .build();

        align1 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        rightAutoConstants.navigatePreload,
                        rightAutoConstants.align1Control1,
                        rightAutoConstants.align1Control2,
                        rightAutoConstants.align1Control3,
                        rightAutoConstants.align1Control4,
                        rightAutoConstants.align1
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
//                .setZeroPowerAccelerationMultiplier(1.5)
                .build();

        push = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        rightAutoConstants.navigatePreload,
                        rightAutoConstants.align1Control1,
                        rightAutoConstants.align1Control2,
                        rightAutoConstants.align1Control3,
                        rightAutoConstants.align1Control4,
                        rightAutoConstants.align1
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(new Path(new BezierLine(
                        rightAutoConstants.align1,
                        rightAutoConstants.push1
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(new Path(new BezierCurve(
                        rightAutoConstants.push1,
                        rightAutoConstants.align2Control1,
                        rightAutoConstants.align2
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(new Path(new BezierLine(
                        rightAutoConstants.align2,
                        rightAutoConstants.push2
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        push1 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        rightAutoConstants.navigatePreload,
                        rightAutoConstants.align1Control1,
                        rightAutoConstants.align1Control2,
                        rightAutoConstants.align1Control3,
                        rightAutoConstants.align1Control4,
                        rightAutoConstants.align1
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(new Path(new BezierLine(
                        rightAutoConstants.align1,
                        rightAutoConstants.push1
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        align2 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        rightAutoConstants.push1,
                        rightAutoConstants.align2Control1,
                        rightAutoConstants.align2
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        push2 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        rightAutoConstants.push1,
                        rightAutoConstants.align2Control1,
                        rightAutoConstants.align2
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(new Path(new BezierLine(
                        rightAutoConstants.align2,
                        rightAutoConstants.push2
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        pickupCache = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        rightAutoConstants.push2,
                        rightAutoConstants.pickup
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(1)
                .build();

        navigateCache = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        rightAutoConstants.push2,
//                        rightAutoConstants.navigateCacheControl1,
                        rightAutoConstants.navigateCache
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(4)
                .setPathEndVelocityConstraint(35)
//                .setZeroPowerAccelerationMultiplier(0.1)
//                .setPathEndVelocityConstraint(25)
//                .addParametricCallback(0, specimen_align)
//                .addPath(new Path(new BezierLine(
//                        rightAutoConstants.navigateCacheControl2,
//                        rightAutoConstants.navigateCache
//                )))
//                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        pickup1 = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        rightAutoConstants.navigateCache,
//                        rightAutoConstants.navigateCacheControl1,
                        rightAutoConstants.pickup
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(3)
                .build();

        navigate1 = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        rightAutoConstants.pickup,
//                        rightAutoConstants.navigate1Control1,
                        rightAutoConstants.navigate1
                )))
                .addParametricCallback(0, specimen_align)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(4)
                .setPathEndVelocityConstraint(35)
//                .addPath(new Path(new BezierLine(
//                        rightAutoConstants.navigate1Control2,
//                        rightAutoConstants.navigate1
//                )))
//                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(3)
                .build();

        pickup2 = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        rightAutoConstants.navigate1,
//                        rightAutoConstants.navigate1Control1,
                        rightAutoConstants.pickup
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(3)
                .build();

        navigate2 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(
                        rightAutoConstants.pickup,
//                        rightAutoConstants.navigate2Control1,
                        rightAutoConstants.navigate2
                )))
                .addParametricCallback(0, specimen_align)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .setZeroPowerAccelerationMultiplier(4)
                .setPathEndVelocityConstraint(35)
                .build();

        park = follower.pathBuilder()
                .addPath(new Path(new BezierLine(
                        rightAutoConstants.navigate2,
//                        rightAutoConstants.navigate2Control1,
                        rightAutoConstants.pickup
                )))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
    }


    public void pathUpdater() {
        switch(nextState) {
            case INIT:
                currentState = RIGHT.INIT;
                upperClaw.preset(constants.UPPERCLAW.CLOSED);
                break;

            case START:
                currentState = RIGHT.START;
                nextState = RIGHT.NAVIGATE_PRELOAD;
                break;

            case NAVIGATE_PRELOAD:
                if (!follower.isBusy()) {
                    currentState = RIGHT.NAVIGATE_PRELOAD;
                    follower.followPath(navigatePreload);
                    setNextPath(RIGHT.SCORE_PRELOAD);
                }
                break;

            case SCORE_PRELOAD:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 1.8) {
                    currentState = RIGHT.SCORE_PRELOAD;
                    Thread t = new Thread(specimen_score);
                    t.start();
                    setNextPath(RIGHT.PUSH);
                }
                break;

            case PUSH:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 1.65) {
                    currentState = RIGHT.PUSH;
                    follower.followPath(push);
                    setNextPath(RIGHT.NAVIGATE_CACHE);
                }

//            case PUSH_1:
//                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 2) {
//                    currentState = RIGHT.PUSH_1;
//                    follower.followPath(push1);
//                    setNextPath(RIGHT.PUSH_2);
//                }
//                break;
//
//            case PUSH_2:
//                if (!follower.isBusy()) {
//                    currentState = RIGHT.PUSH_2;
//                    follower.followPath(push2);
//                    setNextPath(RIGHT.NAVIGATE_CACHE);
//                }
//                break;

            case PICKUP_CACHE:
                if (!follower.isBusy()) {
                    currentState = RIGHT.PICKUP_CACHE;
                    follower.followPath(pickupCache);
                    setNextPath(RIGHT.NAVIGATE_CACHE);
                }
                break;

            case NAVIGATE_CACHE:
                if (pathTimer.getElapsedTimeSeconds() > 8) {
                    upperClaw.preset(constants.UPPERCLAW.CLOSED);
                }
                if (pathTimer.getElapsedTimeSeconds() > 8.3) {
                    Thread t = new Thread(specimen_align);
                    t.start();
                    currentState = RIGHT.NAVIGATE_CACHE;
                    follower.followPath(navigateCache, true);
                    setNextPath(RIGHT.SCORE_CACHE);
                }
                break;

            case SCORE_CACHE:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 2.8) {
                    currentState = RIGHT.SCORE_CACHE;
                    Thread t = new Thread(specimen_score);
                    t.start();
                    setNextPath(RIGHT.PICKUP_1);
                }
                break;

            case PICKUP_1:
                if (pathTimer.getElapsedTimeSeconds() > 1.55) {
                    currentState = RIGHT.PICKUP_1;
                    follower.followPath(pickup1);
                    setNextPath(RIGHT.NAVIGATE_1);
                }
                break;

            case NAVIGATE_1:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 2.65) {
                    upperClaw.preset(constants.UPPERCLAW.CLOSED);
                }
                if (pathTimer.getElapsedTimeSeconds() > 2.85) {
                    currentState = RIGHT.NAVIGATE_1;
                    follower.followPath(navigate1);
                    setNextPath(RIGHT.SCORE_1);
                }
                break;

            case SCORE_1:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 2.8) {
                    currentState = RIGHT.SCORE_1;
                    Thread t = new Thread(specimen_score);
                    t.start();
                    setNextPath(RIGHT.PICKUP_2);
                }
                break;

            case PICKUP_2:
                if (pathTimer.getElapsedTimeSeconds() > 1.55) {
                    currentState = RIGHT.PICKUP_2;
                    follower.followPath(pickup2);
                    setNextPath(RIGHT.NAVIGATE_2);
                }
                break;

            case NAVIGATE_2:
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 2.65) {
                    upperClaw.preset(constants.UPPERCLAW.CLOSED);
                }
                if (pathTimer.getElapsedTimeSeconds() > 2.85) {
                    currentState = RIGHT.NAVIGATE_2;
                    follower.followPath(navigate2);
                    setNextPath(RIGHT.SCORE_2);
                }
                break;

            case SCORE_2:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 3.2) {
                    currentState = RIGHT.SCORE_2;
                    Thread t = new Thread(specimen_score);
                    t.start();
                    setNextPath(RIGHT.PARK);
                }
                break;

            case PARK:
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 3) {
                    currentState = RIGHT.PARK;
                    follower.followPath(park);
                    setNextPath(RIGHT.PICKUP_CACHE);
                }
                break;

            case IDLE:
                currentState = RIGHT.IDLE;
                stop();
                break;
        }
    }

    public void setNextPath(RIGHT nextPath) {
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
        follower.update();

        telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        intake = new intake();
        upperClaw = new upperClaw();
        slides = new slides();
        hslides = new hslides();

        slides.init(hardwareMap);
        upperClaw.init(hardwareMap, true);
        hslides.init(hardwareMap);
        intake.init(hardwareMap);

        telemetry.addLine("Right Side Autonomous Initiated... Waiting For Start...");
        telemetry.addData("Current State: ", currentState);
        telemetry.update();

        buildPaths();
        setNextPath(RIGHT.INIT);
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
        setNextPath(RIGHT.START);
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
