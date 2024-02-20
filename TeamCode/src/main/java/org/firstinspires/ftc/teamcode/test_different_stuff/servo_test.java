package org.firstinspires.ftc.teamcode.test_different_stuff;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@TeleOp
public class servo_test extends LinearOpMode {
    /*ServoImplEx servo;
    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(ServoImplEx.class, "servo");
        servo.setPwmRange(new PwmControl.PwmRange(500, 2500));
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) servo.setPosition(0);
            else if (gamepad1.b) servo.setPosition(1);
            else if (gamepad1.y) {
                servo.setPosition(-gamepad1.left_stick_y);
            }
        }
    }*/
    Servo servo;

    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(ServoImplEx.class, "change_over_2");
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) servo.setPosition(0);
            else if (gamepad1.b) servo.setPosition(1);
            else if (gamepad1.y) {
                servo.setPosition(-gamepad1.left_stick_y);
            }
        }
    }
}
