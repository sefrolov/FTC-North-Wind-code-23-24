package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;

@TeleOp(name = "intake_folding_test")
public class intake_folding_test extends LinearOpMode {

    // intake_folding motor = new intake_folding();
    RobotNW Robot = new RobotNW();;
    double  power = 0;

    ElapsedTime time = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
         // motor.init(hardwareMap);
        Robot.init(hardwareMap, telemetry, this);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.b) {
                power = 0.5;
            }
            else {
                power = 0;
            }
            // motor.setPower(power);
            Robot.IF.setPower(power);

        }
    }

}
