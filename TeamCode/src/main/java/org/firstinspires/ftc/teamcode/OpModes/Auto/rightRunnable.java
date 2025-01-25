package org.firstinspires.ftc.teamcode.OpModes.Auto;

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

import org.firstinspires.ftc.teamcode.subsystems.arm;
import org.firstinspires.ftc.teamcode.subsystems.claw;
import org.firstinspires.ftc.teamcode.subsystems.hslides;
import org.firstinspires.ftc.teamcode.subsystems.slides;

import org.firstinspires.ftc.teamcode.OpModes.Auto.AConstants.RightRunnable;
import org.firstinspires.ftc.teamcode.OpModes.Auto.AConstants.RIGHT_RUNNABLE;
import org.firstinspires.ftc.teamcode.utility.constants;

import java.util.HashMap;
import java.util.Map;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous
@Config
public class rightRunnable extends OpMode {

    private RIGHT_RUNNABLE currentState;
    private RIGHT_RUNNABLE nextState;
    public static int configState = 0;
    private int count = 0;

    private final Map<RIGHT_RUNNABLE, Integer> stateMap = new HashMap<>();
    private final Map<Integer, RIGHT_RUNNABLE> map = new HashMap<>();

    private PathChain navigateToPreload, scorePreload, unloadPreload, pushSample1, pushSample2, navigateToPickup, navigateToScore, score, scoreToPickup, unlatch, park;

    private Follower follower;
    private Timer pathTimer;
    private Timer actionTimer;
    private Timer opModeTimer;

    private slides slides;
    private claw claw;
    private hslides hslides;
    private arm arm;

    private int cycleCount = 0;

    public void buildPaths() {
        navigateToPreload = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(RightRunnable.startPoint, RightRunnable.preloadControl1, RightRunnable.navigateToPreload)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setZeroPowerAccelerationMultiplier(0.85)
                .setPathEndVelocityConstraint(15)
                .addParametricCallback(0, new Runnable() {
                    @Override
                    public void run() {
                        slides.setHeight(1300);
                    }
                })
                .addParametricCallback(0.2, new Runnable() {
                    @Override
                    public void run() {
                        arm.preset(constants.ARM.BACK);
                    }
                })
                .build();

        scorePreload = follower.pathBuilder()
                .addPath(new Path(new BezierLine(RightRunnable.navigateToPreload, RightRunnable.scorePreload)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setPathEndTimeoutConstraint(1000)
                .addTemporalCallback(0.25, new Runnable() {
                    @Override
                    public void run() {
                        slides.setHeight(1938);
                    }
                })
                .build();

        unloadPreload = follower.pathBuilder()
                .addPath(new Path(new BezierLine(new Point(follower.getPose()), new Point(32, 66))))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setPathEndTimeoutConstraint(1000)
                .build();

        pushSample1 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(RightRunnable.navigateToPreload, RightRunnable.navigateToPushSample1Control1, RightRunnable.navigateToPushSample1Control2, RightRunnable.navigateToPushSample1)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addParametricCallback(0, new Runnable() {
                    @Override
                    public void run() {
                        claw.preset(constants.CLAW.OPEN);
                    }
                })
                .addParametricCallback(0.2, new Runnable() {
                    @Override
                    public void run() {
                        slides.setHeight(1300);
                    }
                })
                .addParametricCallback(0.4, new Runnable() {
                    @Override
                    public void run() {
                        arm.preset(constants.ARM.VERTICAL);
                    }
                })
                .addParametricCallback(0.7, new Runnable() {
                    @Override
                    public void run() {
                        arm.preset(constants.ARM.PRE_PICKUP);
                    }
                })
                .addPath(new Path(new BezierCurve(RightRunnable.navigateToPushSample1,RightRunnable.lineUpToPushSample1Control1, RightRunnable.lineUpToPushSample1)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(new Path(new BezierLine(RightRunnable.lineUpToPushSample1, RightRunnable.PushSample1)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        pushSample2 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(RightRunnable.PushSample1, RightRunnable.navigateToPushSample2Control1, RightRunnable.navigateToPushSample2)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(new Path(new BezierLine(RightRunnable.navigateToPushSample2, RightRunnable.PushSample2)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        navigateToPickup = follower.pathBuilder()
                .addPath(new Path(new BezierLine(RightRunnable.PushSample1, RightRunnable.pickup)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addTemporalCallback(0, new Runnable() {
                    @Override
                    public void run() {
                        slides.setHeight(1200);
                        arm.preset(constants.ARM.PRE_PICKUP);
                    }
                })
                .build();

        navigateToScore = follower.pathBuilder()
            .addPath(new Path(new BezierCurve(RightRunnable.pickup, RightRunnable.navigateToScoreControl1, RightRunnable.navigateToScore)))
            .setConstantHeadingInterpolation(Math.toRadians(180))
                .setZeroPowerAccelerationMultiplier(0.85)
                .setPathEndVelocityConstraint(15)
                .addParametricCallback(0, new Runnable() {
                    @Override
                    public void run() {
                        slides.setHeight(1300);
                        arm.preset(constants.ARM.BACK);
                    }
                })
                .addParametricCallback(0.2, new Runnable() {
                    @Override
                    public void run() {
                        hslides.preset(constants.HSLIDES.RETRACTED);
                    }
                })
                .build();

        score = follower.pathBuilder()
                .addPath(new Path(new BezierLine(RightRunnable.navigateToScore, new Point(38.5, 66+10*(count), Point.CARTESIAN))))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setPathEndTimeoutConstraint(1500)
                .addTemporalCallback(0.25, new Runnable() {
                    @Override
                    public void run() {
                        slides.setHeight(1850);
                    }
                })
                .build();

        unlatch = follower.pathBuilder()
                .addPath(new Path(new BezierLine(new Point(follower.getPose()), new Point(32, 66 + 10*count, Point.CARTESIAN))))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setPathEndTimeoutConstraint(1000)
                .build();

        scoreToPickup = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(new Point(32, 66+10*count, Point.CARTESIAN), RightRunnable.navigateToScoreControl1, RightRunnable.pickup)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addParametricCallback(0, new Runnable() {
                    @Override
                    public void run() {
                        claw.preset(constants.CLAW.OPEN);
                    }
                })
                .addParametricCallback(0.4, new Runnable() {
                    @Override
                    public void run() {
                        slides.setHeight(1180);
                        arm.preset(constants.ARM.PRE_PICKUP);
                    }
                })
                .build();


        park = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(new Point(follower.getPose()), RightRunnable.navigateToParkControl1, RightRunnable.navigateToPark)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addParametricCallback(0, new Runnable() {
                    @Override
                    public void run() {
                        claw.preset(constants.CLAW.OPEN);
                        hslides.preset(constants.HSLIDES.RETRACTED);
                    }
                })
                .addParametricCallback(0.2, new Runnable() {
                    @Override
                    public void run() {
                        slides.preset(constants.VSLIDES.BASE);
                    }
                })
                .addParametricCallback(0.2, new Runnable() {
                    @Override
                    public void run() {
                        arm.preset(constants.ARM.VERTICAL);
                    }
                })
                .build();

    }



    public void pathUpdater() {
        switch (nextState){
            case INIT:
                currentState = RIGHT_RUNNABLE.INIT;
                claw.preset(constants.CLAW.CLOSED);
                break;

            case START:
                currentState = RIGHT_RUNNABLE.START;
                nextState = RIGHT_RUNNABLE.NAVIGATE_TO_PRELOAD;
                break;

            case NAVIGATE_TO_PRELOAD:
                if (!follower.isBusy()) {
                    currentState = RIGHT_RUNNABLE.NAVIGATE_TO_SCORE;
                    follower.followPath(navigateToPreload);
                    setNextPath(RIGHT_RUNNABLE.SCORE_PRELOAD);
                }
                break;

            case SCORE_PRELOAD:
                if (!follower.isBusy()) {
                    currentState = RIGHT_RUNNABLE.SCORE_PRELOAD;
                    follower.followPath(scorePreload, true);
                    setNextPath(RIGHT_RUNNABLE.UNLOAD_PRELOAD);
                }
                break;

            case UNLOAD_PRELOAD:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 1) {
                    currentState = RIGHT_RUNNABLE.UNLOAD_PRELOAD;
                    follower.followPath(unloadPreload);
                    setNextPath(RIGHT_RUNNABLE.PUSH_1);
                }
                break;

            case PUSH_1:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 1) {
                    claw.preset(constants.CLAW.OPEN);
                    currentState = RIGHT_RUNNABLE.PUSH_1;
                    follower.followPath(pushSample1, true);
                    setNextPath(RIGHT_RUNNABLE.PICKUP);
                }
                break;

            case PICKUP:
                if (!follower.isBusy()) {
                    currentState = RIGHT_RUNNABLE.PICKUP;
                    follower.followPath(navigateToPickup, true);
                    setNextPath(RIGHT_RUNNABLE.NAVIGATE_TO_SCORE);
                }
                break;

            case NAVIGATE_TO_SCORE:
                if (pathTimer.getElapsedTimeSeconds() > 2.25 + 2.5*count){
                    arm.preset(constants.ARM.GROUND);
                }
                if (pathTimer.getElapsedTimeSeconds() > 2.5 + 2.5*count) {
                    claw.preset(constants.CLAW.CLOSED);
                    hslides.preset(constants.HSLIDES.HALF);
                }
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 3 + 2.5*count) {
                    count++;
                    currentState = RIGHT_RUNNABLE.NAVIGATE_TO_SCORE;
                    follower.followPath(navigateToScore);
                    setNextPath(RIGHT_RUNNABLE.SCORE);
                }
                break;

            case SCORE:
                if (!follower.isBusy()) {
                    currentState = RIGHT_RUNNABLE.SCORE;
                    follower.followPath(score, true);
                    setNextPath(RIGHT_RUNNABLE.UNLATCH);
                }
                break;

            case SCORE_TO_PICKUP:
                if (!follower.isBusy()) {
                    currentState = RIGHT_RUNNABLE.SCORE_TO_PICKUP;
                    follower.followPath(scoreToPickup);
                    setNextPath(RIGHT_RUNNABLE.NAVIGATE_TO_SCORE);
                }
                break;

            case UNLATCH:
                if (!follower.isBusy() || pathTimer.getElapsedTimeSeconds() > 1.25) {
                    currentState = RIGHT_RUNNABLE.UNLATCH;
                    follower.followPath(unlatch, true);
                    if (count < 2){
                        setNextPath(RIGHT_RUNNABLE.SCORE_TO_PICKUP);
                    }
                    else {
                        setNextPath(RIGHT_RUNNABLE.PARK);
                    }
                }

            case PARK:
                if (!follower.isBusy()) {
                    currentState = RIGHT_RUNNABLE.PARK;
                    follower.followPath(park, true);
                    setNextPath(RIGHT_RUNNABLE.IDLE);
                }
                break;

            case IDLE:
                if (!follower.isBusy()) {
                    currentState = RIGHT_RUNNABLE.IDLE;
                    stop();
                }

        }
    }

    public void setNextPath(AConstants.RIGHT_RUNNABLE next) {
        nextState = next;
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
        follower.setStartingPose(RightRunnable.startPose);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        arm = new arm();
        claw = new claw();
        slides = new slides();
        hslides = new hslides();

        slides.init(hardwareMap);
        claw.init(hardwareMap);
        hslides.init(hardwareMap);
        arm.init(hardwareMap);

        telemetry.addLine("blueRight Autonomous Initiated... Waiting For Start...");
        telemetry.addData("Right State: ", currentState);
        telemetry.update();

        buildPaths();


        stateMap.put(RIGHT_RUNNABLE.INIT, 0);
        stateMap.put(RIGHT_RUNNABLE.START, 1);
        stateMap.put(RIGHT_RUNNABLE.NAVIGATE_TO_PRELOAD, 2);
        stateMap.put(RIGHT_RUNNABLE.PUSH_1, 3);
        stateMap.put(RIGHT_RUNNABLE.PUSH_2, 4);
        stateMap.put(RIGHT_RUNNABLE.PICKUP, 5);
        stateMap.put(RIGHT_RUNNABLE.SCORE, 6);
        stateMap.put(RIGHT_RUNNABLE.PARK, 7);

        map.put(0,RIGHT_RUNNABLE.INIT);
        map.put(1,RIGHT_RUNNABLE.START);
        map.put(2,RIGHT_RUNNABLE.NAVIGATE_TO_PRELOAD);
        map.put(3,RIGHT_RUNNABLE.PUSH_1);
        map.put(4,RIGHT_RUNNABLE.PUSH_2);
        map.put(5,RIGHT_RUNNABLE.PICKUP);
        map.put(6,RIGHT_RUNNABLE.SCORE);
        map.put(7,RIGHT_RUNNABLE.PARK);

        setNextPath(RIGHT_RUNNABLE.INIT);
        pathUpdater();

    }

    @Override
    public void init_loop() {
        telemetry.update();
    }


    @Override
    public void start() {
        opModeTimer.resetTimer();
        setNextPath(RIGHT_RUNNABLE.START);
    }

    @Override
    public void loop() {
        follower.update();
        pathUpdater();
        infoUpdater();
    }

    @Override
    public void stop() {}

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
        telemetry.addData("Arm Rotation: ", arm.getCenterPosition());
        telemetry.addData("Arm Pivot: ", arm.getRightPosition());
        telemetry.addLine();
        telemetry.addData("Claw Position: ", claw.getClawPosition());
        telemetry.addData("Wrist Position: ", claw.getWristPosition());
        telemetry.addLine();
        telemetry.update();
    }



}
