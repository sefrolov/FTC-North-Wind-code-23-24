package org.firstinspires.ftc.teamcode.diff_sverwe;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class DriveTrainDifferential {
    module leftModule = new module();
    module rightModule = new module();

    public void init(HardwareMap HM) {
        leftModule.init(HM, "motorLD", "motorLU");
        rightModule.init(HM, "motorRD", "motorRU");
    }
}

    /*
    # Name
    0 Left Front (to be checked)
    1 Left Back (to be checked)
    2 Right Back
    3 Right Front
     */

