package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;
import com.pedropathing.localization.localizers.ThreeWheelIMULocalizer;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;



public class LConstants {
    static {
        ThreeWheelIMUConstants.forwardTicksToInches = -0.0005428;
        ThreeWheelIMUConstants.strafeTicksToInches = 0.000528;
        ThreeWheelIMUConstants.turnTicksToInches = -.00055305;
        ThreeWheelIMUConstants.leftY = 7.95;
        ThreeWheelIMUConstants.rightY = -7.95;
        ThreeWheelIMUConstants.strafeX = 3.85;
        ThreeWheelIMUConstants.leftEncoder_HardwareMapName = "backRight";
        ThreeWheelIMUConstants.rightEncoder_HardwareMapName = "frontLeft";
        ThreeWheelIMUConstants.strafeEncoder_HardwareMapName = "frontRight";
        ThreeWheelIMUConstants.leftEncoderDirection = Encoder.FORWARD;
        ThreeWheelIMUConstants.rightEncoderDirection = Encoder.FORWARD;
        ThreeWheelIMUConstants.strafeEncoderDirection = Encoder.FORWARD;
        ThreeWheelIMUConstants.IMU_HardwareMapName = "imu";
        ThreeWheelIMUConstants.IMU_Orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.FORWARD, RevHubOrientationOnRobot.UsbFacingDirection.RIGHT);
        ThreeWheelIMULocalizer.useIMU = true;
    }
}



