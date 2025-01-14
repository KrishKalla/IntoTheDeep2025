package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;


public class LConstants {
    static {
        ThreeWheelIMUConstants.forwardTicksToInches = -0.0005428;
        ThreeWheelIMUConstants.strafeTicksToInches = 0.000539;
        ThreeWheelIMUConstants.turnTicksToInches = -.00083;
        ThreeWheelIMUConstants.leftY = 7.5;
        ThreeWheelIMUConstants.rightY = -7.5;
        ThreeWheelIMUConstants.strafeX = 3.5;
        ThreeWheelIMUConstants.leftEncoder_HardwareMapName = "backRight";
        ThreeWheelIMUConstants.rightEncoder_HardwareMapName = "frontLeft";
        ThreeWheelIMUConstants.strafeEncoder_HardwareMapName = "frontRight";
        ThreeWheelIMUConstants.leftEncoderDirection = Encoder.REVERSE;
        ThreeWheelIMUConstants.rightEncoderDirection = Encoder.FORWARD;
        ThreeWheelIMUConstants.strafeEncoderDirection = Encoder.FORWARD;
        ThreeWheelIMUConstants.IMU_HardwareMapName = "imu";
        ThreeWheelIMUConstants.IMU_Orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.FORWARD, RevHubOrientationOnRobot.UsbFacingDirection.RIGHT);
    }
}




