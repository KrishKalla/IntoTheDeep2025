package org.firstinspires.ftc.teamcode.OpModes.Auto.testing;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "IntoTheDeep2025Auto")
public class Base {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;


    private int pathState;
    private final Pose startPose = new Pose(134.20408163265307, 80.13061224489796, Math.toRadians(180));
    private final Pose barPose = new Pose(96.58775510204082, 79.93469387755101, Math.toRadians(180));
    private final Pose barControl1 = new Pose(141.0612244897959, 81.89387755102041, Math.toRadians(168));
    private final Pose barControl2 = new Pose(142.62857142857143, 101.68163265306123, Math.toRadians(168));
    private final Pose pickup1_start = new Pose(58.38367346938775, 120.2938775510204, Math.toRadians(168));
    private final Pose pickup1_end = new Pose(138.12244897959184, 120.68571428571428, Math.toRadians(180));
    private final Pose controlmove1 = new Pose(88.55510204081632, 115.39591836734694, Math.toRadians(180));
    private final Pose pickup2_start = new Pose(53.093877551020405, 132.04897959183674, Math.toRadians(180));
    private final Pose pickup2_end = new Pose(136.9469387755102, 131.26530612244898, Math.toRadians(180));

    private Path specimen; // specimen is the first part. the second part is getting the
    // the others and pushing them into the alliance zone, where we also park.
    private PathChain move, pickup1, move2, pickup2;

    public void buildPaths() {
        specimen = new Path(new BezierLine(new Point(startPose), new Point(barPose)));
        specimen.setTangentialHeadingInterpolation();

        move = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(barPose), new Point(barControl1), new Point(barControl2), new Point(pickup1_start)))
                .setConstantInterpolationHeading(Math.toRadians(168))
                .build();
        pickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup1_start), new Point(pickup1_end)))
                .setReversed(true)
                .build();
        move2 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(pickup1_end), new Point(controlmove1), new Point(pickup2_start));
                .setLinearInterpolationHeading(Math.toRadians(180), Math.toRadians(180))
                .build();
        pickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup2_start), new Point(pickup2_end))))
                .setTangentialInterpolationHeading()
                .build();
    }
}
