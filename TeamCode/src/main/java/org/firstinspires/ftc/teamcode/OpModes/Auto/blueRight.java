package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.acmerobotics.dashboard.FtcDashboard;
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

import org.firstinspires.ftc.teamcode.OpModes.Auto.AConstants.BlueRight;
import org.firstinspires.ftc.teamcode.OpModes.Auto.AConstants.BLUE_RIGHT;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;



@Autonomous(name = "BlueRight", group = "Qual-1")
public class blueRight extends OpMode{

    //Pedro Pathing Business
    private Follower follower;
    private Timer pathTimer, actionTimer, opModeTimer;

    private BLUE_RIGHT state;

    private PathChain navigateToPreload, scorePreload, pushSample1, pushSample2, navigateToPickup, navigateToScore, score, park;

    //All Subsystems
    private slides slides;
    private claw claw;
    private hslides hslides;
    private arm arm;

    private String alliance = "BLUE";

    public void buildPaths() {
        navigateToPreload = follower.pathBuilder()
            .addPath(new Path(new BezierCurve(BlueRight.startPoint, BlueRight.navigateToPreloadControl1, BlueRight.navigateToPreload)))
            .setConstantHeadingInterpolation(Math.toRadians(180))
            .build();

        scorePreload = follower.pathBuilder()
                .addPath(new Path(new BezierLine(BlueRight.navigateToPreload, BlueRight.scorePreload)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(new Path(new BezierLine(BlueRight.scorePreload, BlueRight.releasePreload)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(new Path(new BezierLine(BlueRight.releasePreload, BlueRight.retreatFromPreload)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        pushSample1 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(BlueRight.retreatFromPreload, BlueRight.navigateToSample1Control1, BlueRight.NavigateToSample1Control2,BlueRight.navigateToSample1)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(new Path(new BezierLine(BlueRight.navigateToPreload, BlueRight.pushSample1)))
                .setConstantHeadingInterpolation(180)
                .build();

        pushSample2 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(BlueRight.pushSample1, BlueRight.navigateToSample2Control1, BlueRight.navigateToSample2)))
                .setConstantHeadingInterpolation(180)
                .addPath(new Path(new BezierLine(BlueRight.navigateToSample2, BlueRight.pushSample2)))
                .setConstantHeadingInterpolation(180)
                .build();

        navigateToPickup = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(BlueRight.pushSample2, BlueRight.navigateToPickupControl1, BlueRight.navigateToPickup)))
                .setConstantHeadingInterpolation(180)
                .build();

        navigateToScore = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(BlueRight.navigateToPickup, BlueRight.navigateToScoreControl1, BlueRight.navigateToScore)))
                .setConstantHeadingInterpolation(180)
                .build();

        score = follower.pathBuilder()
                .addPath(new Path(new BezierLine(BlueRight.navigateToScore, BlueRight.score)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(new Path(new BezierLine(BlueRight.score, BlueRight.release)))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        park = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(BlueRight.release, BlueRight.parkControl1, BlueRight.parkControl2, BlueRight.park)))
                .setConstantHeadingInterpolation(180)
                .build();
    }


    public void pathUpdater() {
        switch (state) {
            case INIT:
                pathTimer = new Timer();

                Constants.setConstants(FConstants.class, LConstants.class);
                follower = new Follower(hardwareMap);
                follower.setStartingPose(BlueRight.startPose);

                break;

            case START:
                state = BLUE_RIGHT.PRELOAD;
                break;

            case PRELOAD:
                if (!follower.isBusy()) {
                    follower.followPath(navigateToPreload, true);
                    setNextPath(BLUE_RIGHT.PRELOAD);
                }
                break;

            case SCORE_PRELOAD:
                if (!follower.isBusy()) {
                    follower.followPath(scorePreload, true);
                    setNextPath(BLUE_RIGHT.PUSH_1);
                }
                break;

            case PUSH_1:
                if (!follower.isBusy()) {
                    follower.followPath(pushSample1, true);
                    setNextPath(BLUE_RIGHT.PUSH_2);
                }
                break;

            case PUSH_2:
                if (!follower.isBusy()) {
                    follower.followPath(pushSample2, true);
                    setNextPath(BLUE_RIGHT.PICK_1);
                }
                break;

            case PICK_1:
                if (!follower.isBusy()) {
                    follower.followPath(navigateToPickup, true);
                    setNextPath(BLUE_RIGHT.READY);
                }
                break;

            case READY:
                if (!follower.isBusy()) {
                    follower.followPath(navigateToScore, true);
                    setNextPath(BLUE_RIGHT.SCORE);
                }
                break;

            case SCORE:
                if (!follower.isBusy()) {
                    follower.followPath(score, true);
                    setNextPath(BLUE_RIGHT.PARK);
                }
                break;

            case PARK:
                if (!follower.isBusy()) {
                    follower.followPath(park, true);
                    setNextPath(BLUE_RIGHT.IDLE);
                }
                break;
        }
    }

    public void setNextPath(BLUE_RIGHT next) {
        state = next;
        pathTimer.resetTimer();
    }

    @Override
    public void init() {
        setNextPath(BLUE_RIGHT.INIT);

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
    }

    @Override
    public void init_loop() {
        telemetry.update();
    }

    @Override
    public void start() {
        opModeTimer.resetTimer();
        setNextPath(BLUE_RIGHT.START);
    }

    @Override
    public void loop() {
        follower.update();
        pathUpdater();
        infoUpdater();
    }

    @Override
    public void stop() {};

    private void infoUpdater() {
        telemetry.addData("Path State: ", state);
        telemetry.addData("Position: ", follower.getPose().toString());
        telemetry.update();
    }

}
