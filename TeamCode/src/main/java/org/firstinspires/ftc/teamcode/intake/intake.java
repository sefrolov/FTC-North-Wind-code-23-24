package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "intake_test")
public class intake extends LinearOpMode {
    DcMotor motor = null;
    BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotor.class, "motor");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        while (opModeIsActive()) {

            if ((Math.abs(gamepad1.right_stick_y) >= 0.1) && opModeIsActive()) {
                double leftPower = 0;

                leftPower = gamepad1.right_stick_y * 0.25;
                motor.setPower(leftPower);

                // Show the elapsed game time and wheel power.
                //  telemetry.addData("Status", "Run Time: " + runtime.toString());
                //  telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
                //  telemetry.update();
            }
        }
    }
}