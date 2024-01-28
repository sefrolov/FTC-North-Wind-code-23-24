package org.firstinspires.ftc.teamcode.lift;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;


@TeleOp(name = "change_over_test")
@Disabled
public class change_over_test extends LinearOpMode {
    RobotNW Robot = new RobotNW();;
    double  degr = 1;

    ElapsedTime wait_time = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {

        Robot.init(hardwareMap, telemetry, this);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                degr = -0.5;
            } else if (gamepad1.y) {
                degr = 0.5;
            } else{
                degr = 0;
            }
            /*
            Robot.CO.setPos(degr);
            */


        }
    }
}
