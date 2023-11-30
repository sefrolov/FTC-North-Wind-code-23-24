package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;

@TeleOp(name = "lift_test")
public class lift_test extends LinearOpMode {

    RobotNW Robot = new RobotNW();;
    double degr = 1;

    ElapsedTime wait_time = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {

        Robot.init(hardwareMap, telemetry, this);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                degr = 0;
                Robot.LI.setPos(degr);
            } else if (gamepad1.b) {
                degr = 1;
                Robot.LI.setPos(degr);
            }
        }
    }
}
