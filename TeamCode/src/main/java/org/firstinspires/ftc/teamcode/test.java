package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class test extends LinearOpMode {
    DcMotor motor, motor2;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotor.class, "motor");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.a) {
                motor.setPower(1);
                motor2.setPower(-1);
            }
            else if (gamepad1.b) {
                motor.setPower(1);
                motor2.setPower(1);
            }
            else {
                motor.setPower(0);
                motor2.setPower(0);
            }
            telemetry.addData("enc", motor.getCurrentPosition());
            telemetry.update();
        }
    }
}
