package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotNW;

@TeleOp(name = "intake_test")
public class intake_test extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        telemetry.addData("","init succesfully");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
//
            //
            //

            if ((Math.abs(gamepad1.right_stick_y) >= 0.1) && opModeIsActive()) {

                if (Robot.IN.acceleration_time.milliseconds() > 500)
                    Robot.IN.acceleration_koef = 0.1;
                else
                    Robot.IN.acceleration_koef = (10 - 0.001 * Robot.IN.acceleration_time.milliseconds());

                if (-gamepad1.right_stick_y < 0)
                    Robot.IN.Power = 1.7 * gamepad1.right_stick_y * Robot.IN.PowerPartFromMaxValue /* /Math.abs(gamepad1.right_stick_y)*/ * Robot.IN.acceleration_koef;
                else
                    Robot.IN.Power = 1.5 * gamepad1.right_stick_y * Robot.IN.PowerPartFromMaxValue /* /Math.abs(gamepad1.right_stick_y)*/ * Robot.IN.acceleration_koef;
                //Power = gamepad1.right_stick_y * PowerPartFromMaxValue / Math.abs(gamepad1.right_stick_y);

                telemetry.addData("Intake motors power:", Robot.IN.Power);
                telemetry.addData("Intake acceleration koef:", Robot.IN.acceleration_koef);
                telemetry.update();
                // Show the elapsed game time and wheel power.
                //  telemetry.addData("Status", "Run Time: " + runtime.toString());
                //  telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
                //  telemetry.update();
            }
            else if (gamepad1.y)
            {

                if (Robot.IN.acceleration_time.milliseconds() > 500)
                    Robot.IN.acceleration_koef = 0.1;
                else
                    Robot.IN.acceleration_koef = (10 - 0.001 * Robot.IN.acceleration_time.milliseconds());

                Robot.IN.Power = -1;
                //Power = gamepad1.right_stick_y * PowerPartFromMaxValue / Math.abs(gamepad1.right_stick_y);

            }
            else {
                Robot.IN.Power = 0;
                Robot.IN.acceleration_time.reset();
            }
            Robot.IN.motor1.setPower(Robot.IN.Power);
            Robot.IN.motor2.setPower(Robot.IN.Power);
        }
    }
}