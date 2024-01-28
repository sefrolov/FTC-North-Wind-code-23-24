package org.firstinspires.ftc.teamcode.test_different_stuff;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.maths.vec2;

@TeleOp(name = "math_test")
@Disabled
public class math_test extends LinearOpMode {

    RobotNW Robot = new RobotNW();
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        telemetry.addData("","init succesfully");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            if (Math.abs(gamepad1.right_trigger - gamepad1.left_trigger) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1)
                Robot.DD.leftModule.applyVector(gamepad1.right_trigger - gamepad1.left_trigger, -gamepad1.left_stick_y);
            else
                Robot.DD.leftModule.applyVector(0, 0);
            if (Math.abs(gamepad1.right_stick_x) > 0.1 || Math.abs(gamepad1.right_stick_y) > 0.1)
                Robot.DD.rightModule.applyVector(gamepad1.right_stick_x, -gamepad1.right_stick_y);
            else
                Robot.DD.rightModule.applyVector(0, 0);

            telemetry.addData("left y:", -gamepad1.left_stick_y);
            telemetry.addData("right y:", -gamepad1.right_stick_y);
            telemetry.addData("trig :", gamepad1.right_trigger - gamepad1.left_trigger);
            telemetry.addData("leftUp power", Robot.DD.leftModule.upMotor.getPower());
            telemetry.addData("leftDown power", Robot.DD.leftModule.downMotor.getPower());
            telemetry.addData("RightUp power", Robot.DD.rightModule.upMotor.getPower());
            telemetry.addData("RightDown power", Robot.DD.rightModule.downMotor.getPower());

            telemetry.update();

        }
    }
}
