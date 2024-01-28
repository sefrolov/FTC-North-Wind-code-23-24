package org.firstinspires.ftc.teamcode.diff_sverwe;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotNW;

@TeleOp(name = "odometry_test")
@Disabled
public class odometry_test extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    odometry od;
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        telemetry.addData("","init succesfully");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Pos y:", od.getPosition_y());
            telemetry.addData("Pos x:", od.getPosition_x());
            telemetry.update();

        }
    }
}
