package org.firstinspires.ftc.teamcode.diff_sverwe;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotNW;

@TeleOp(name = "drive_test")
@Disabled
public class drive_test extends LinearOpMode {
    RobotNW Robot = new RobotNW();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        telemetry.addData("","init succesfully");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (!gamepad1.y) {
                if ((Math.abs(gamepad1.right_stick_y) >= 0.1) && opModeIsActive()) {
                    Robot.DD.leftModule.applyMotorPower("down", gamepad1.right_stick_y);
                } else
                    Robot.DD.leftModule.applyMotorPower("down", 0);

                if ((Math.abs(gamepad1.left_stick_y) >= 0.1) && opModeIsActive()) {
                    Robot.DD.leftModule.applyMotorPower("up", gamepad1.left_stick_y);
                } else
                    Robot.DD.leftModule.applyMotorPower("up", 0);

                if ((Math.abs(gamepad2.right_stick_y) >= 0.1) && opModeIsActive()) {
                    Robot.DD.rightModule.applyMotorPower("down", gamepad2.left_stick_y);
                } else
                    Robot.DD.rightModule.applyMotorPower("down", 0);

                if ((Math.abs(gamepad2.left_stick_y) >= 0.1) && opModeIsActive()) {
                    Robot.DD.rightModule.applyMotorPower("up", gamepad2.left_stick_y);
                } else
                    Robot.DD.rightModule.applyMotorPower("up", 0);

                if (gamepad1.a) {
                    //go forward
                    Robot.DD.leftModule.applyMotorsPowers(0.5, -0.5);
                    Robot.DD.rightModule.applyMotorsPowers(0.5, -0.5);
                }
                if (gamepad1.b) {
                    //go rightup
                    Robot.DD.leftModule.applyMotorsPowers(0.494975, 0.919239);
                    Robot.DD.rightModule.applyMotorsPowers(0.494975, 0.919239);
                }
            }
            else {
                if (Math.abs(gamepad1.left_stick_x) > 0.1 || Math.abs(gamepad1.left_stick_y) > 0.1)
                    Robot.DD.leftModule.applyVector(gamepad1.left_stick_x, -gamepad1.left_stick_y);
                else
                    Robot.DD.leftModule.applyVector(0, 0);
                if (Math.abs(gamepad1.right_stick_x) > 0.1 || Math.abs(gamepad2.right_stick_y) > 0.1)
                    Robot.DD.rightModule.applyVector(gamepad1.right_stick_x, -gamepad1.right_stick_y);
                else
                    Robot.DD.rightModule.applyVector(0, 0);
            }
        }
    }
}
