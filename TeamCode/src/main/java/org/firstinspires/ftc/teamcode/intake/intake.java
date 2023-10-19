package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "intake_test")
public class intake extends LinearOpMode {
    DcMotor motor1 = null;
    DcMotor motor2 = null;
    BNO055IMU imu;
    double Power = 0;

    double acceleration_koef;

    double PowerPartFromMaxValue = 0.001;

    ElapsedTime acceleration_time = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        // init of motor1
        motor1 = hardwareMap.get(DcMotor.class, "motor1");
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // init of motor2
         motor2 = hardwareMap.get(DcMotor.class, "motor2");
         motor2.setDirection(DcMotorSimple.Direction.REVERSE);
         motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
         motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
//
            //
            //

            if ((Math.abs(gamepad1.right_stick_y) >= 0.1) && opModeIsActive()) {
                if (acceleration_time.milliseconds() > 1000)
                    acceleration_koef = 1;
                else
                    acceleration_koef = 100 - 0.019 * acceleration_time.milliseconds();

                Power = gamepad1.right_stick_y * PowerPartFromMaxValue / Math.abs(gamepad1.right_stick_y) * acceleration_koef;
                telemetry.addData("Intake motors power:", Power);
                telemetry.addData("Intake acceleration koef:", acceleration_koef);
                telemetry.update();
                // Show the elapsed game time and wheel power.
                //  telemetry.addData("Status", "Run Time: " + runtime.toString());
                //  telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
                //  telemetry.update();
            }
            else {
                Power = 0;
                acceleration_time.reset();
            }
            motor1.setPower(Power);
            motor2.setPower(Power);


        }
    }
}