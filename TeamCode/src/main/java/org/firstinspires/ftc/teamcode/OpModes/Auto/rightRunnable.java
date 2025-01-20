package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
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

    private RIGHT_RUNNABLE state;
    public static int configState = 0;

    private final Map<RIGHT_RUNNABLE, Integer> stateMap = new HashMap<>();
    private final Map<Integer, RIGHT_RUNNABLE> map = new HashMap<>();

    private PathChain navigateToPreload, scorePreload, pushSample1, pushSample2, navigateToPickup, navigateToScore, score, park;

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
                .setConstantHeadingInterpolation(180)
                .addParametricCallback(0, new Runnable() {
                    @Override
                    public void run() {
                        slides.setHeight(1300);
                    }
                })
                .addParametricCallback(0.4, new Runnable() {
                    @Override
                    public void run() {
                        arm.preset(constants.ARM.BACK);
                    }
                })
                .build();

        scorePreload = follower.pathBuilder()
                .addPath(new Path(new BezierLine(RightRunnable.navigateToPreload, RightRunnable.scorePreload)))
                .setConstantHeadingInterpolation(180)
                .addTemporalCallback(0.5, new Runnable() {
                    @Override
                    public void run() {
                        slides.setHeight(1780);
                    }
                })
                .addTemporalCallback(1, new Runnable() {
                    @Override
                    public void run() {
                        hslides.preset(constants.HSLIDES.HALF);
                    }
                })
                .setPathEndTimeoutConstraint(1.5)
                .build();

        pushSample1 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(RightRunnable.scorePreload, RightRunnable.navigateToPushSample1Control1, RightRunnable.navigateToPushSample1Control2, RightRunnable.navigateToPushSample1)))
                .setConstantHeadingInterpolation(180)
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
                        slides.setHeight(1300);
                    }
                })
                .addParametricCallback(0.4, new Runnable() {
                    @Override
                    public void run() {
                        arm.preset(constants.ARM.PRE_PICKUP);
                    }
                })
                .addPath(new Path(new BezierCurve(RightRunnable.navigateToPushSample1,RightRunnable.lineUpToPushSample1Control1, RightRunnable.lineUpToPushSample1)))
                .setConstantHeadingInterpolation(180)
                .addPath(new Path(new BezierLine(RightRunnable.lineUpToPushSample1, RightRunnable.PushSample1)))
                .setConstantHeadingInterpolation(180)
                .build();

        pushSample2 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(RightRunnable.PushSample1, RightRunnable.navigateToPushSample2Control1, RightRunnable.navigateToPushSample2)))
                .setConstantHeadingInterpolation(180)
                .addPath(new Path(new BezierLine(RightRunnable.navigateToPushSample2, RightRunnable.PushSample2)))
                .setConstantHeadingInterpolation(180)
                .build();

        navigateToPickup = follower.pathBuilder()
                .addPath(new Path(new BezierLine(RightRunnable.PushSample2, RightRunnable.pickup)))
                .setConstantHeadingInterpolation(180)
                .addTemporalCallback(3.5, new Runnable() {
                    @Override
                    public void run() {
                        slides.setHeight(1180);
                    }
                })
                .addTemporalCallback(4, new Runnable() {
                    @Override
                    public void run() {
                        claw.preset(constants.CLAW.CLOSED);
                    }
                })
                .addTemporalCallback(4.25, new Runnable() {
                    @Override
                    public void run() {
                        hslides.unlatch();
                    }
                })
                .build();

        navigateToScore = follower.pathBuilder()
            .addPath(new Path(new BezierCurve(RightRunnable.pickup, RightRunnable.navigateToScoreControl1, RightRunnable.navigateToScore)))
            .addParametricCallback(0, new Runnable() {
                @Override
                public void run() {
                    slides.setHeight(1300);
                }
            })
            .addParametricCallback(0.25, new Runnable() {
                @Override
                public void run() {
                    arm.preset(constants.ARM.BACK);
                }
            })
            .build();

        score = follower.pathBuilder()
                .addPath(new Path(new BezierLine(RightRunnable.navigateToScore, RightRunnable.score)))
                .addTemporalCallback(0.5, new Runnable() {
                    @Override
                    public void run() {
                        slides.setHeight(1780);
                    }
                })
                .addTemporalCallback(1, new Runnable() {
                    @Override
                    public void run() {
                        hslides.preset(constants.HSLIDES.HALF);
                    }
                })
                .setPathEndTimeoutConstraint(1.5)
                .build();

        park = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(RightRunnable.score, RightRunnable.navigateToParkControl1, RightRunnable.navigateToPark)))
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
        switch (state){
            case INIT:
                claw.preset(constants.CLAW.CLOSED);
                break;

            case START:
                state = RIGHT_RUNNABLE.NAVIGATE_TO_PRELOAD;
                break;

            case NAVIGATE_TO_PRELOAD:
                if (!follower.isBusy()) {
                    follower.followPath(navigateToPreload);
                    setNextPath(RIGHT_RUNNABLE.SCORE_PRELOAD);
                }

            case SCORE_PRELOAD:
                if (!follower.isBusy()) {
                    follower.followPath(scorePreload, true);
                    setNextPath(RIGHT_RUNNABLE.PUSH_1);
                }
                break;

            case PUSH_1:
                if (!follower.isBusy()) {
                    follower.followPath(pushSample1);
                    setNextPath(RIGHT_RUNNABLE.PUSH_2);
                }
                break;

            case PUSH_2:
                if (!follower.isBusy()) {
                    follower.followPath(pushSample2);
                    setNextPath(RIGHT_RUNNABLE.PICKUP);
                }
                break;

            case PICKUP:
                if (!follower.isBusy()) {
                    follower.followPath(navigateToPickup, true);
                    setNextPath(RIGHT_RUNNABLE.NAVIGATE_TO_SCORE);
                }
                break;

            case NAVIGATE_TO_SCORE:
                if (!follower.isBusy()) {
                    follower.followPath(navigateToScore);
                    setNextPath(RIGHT_RUNNABLE.SCORE);
                }
                break;

            case SCORE:
                if (!follower.isBusy()) {
                    follower.followPath(score, true);
                    setNextPath(RIGHT_RUNNABLE.PARK);
                }
                break;

            case PARK:
                if (!follower.isBusy()) {
                    follower.followPath(park, true);
                    setNextPath(RIGHT_RUNNABLE.IDLE);
                }
                break;

            case IDLE:
                if (!follower.isBusy()) {
                    stop();
                }

        }
    }

    public void setNextPath(AConstants.RIGHT_RUNNABLE next) {
        state = next;
        pathTimer.resetTimer();
        actionTimer.resetTimer();
    }


    @Override
    public void init() {
        pathTimer = new Timer();
        opModeTimer = new Timer();
        actionTimer = new Timer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(AConstants.BlueRight.startPose);

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
        telemetry.addData("blueRight State: ", state);
        telemetry.update();




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
        telemetry.addData("Path State: ", state);
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
