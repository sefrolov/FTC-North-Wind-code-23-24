package org.firstinspires.ftc.teamcode.diff_sverwe;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.maths.vec2;

@TeleOp(name = "drive_test_enc")
public class drive_test_enc extends LinearOpMode {
    RobotNW Robot = new RobotNW();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        telemetry.addData("","init succesfully");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (abs(gamepad1.left_stick_x) > 0.05 || abs(gamepad1.left_stick_y) > 0.05)
            Robot.DD.leftModule.applyVectorP(new vec2(gamepad1.left_stick_x, -gamepad1.left_stick_y), telemetry);
            else Robot.DD.leftModule.applyVectorP(new vec2(0, 0), telemetry);
            if (abs(gamepad1.right_stick_x) > 0.05 || abs(gamepad1.right_stick_y) > 0.05)
            Robot.DD.rightModule.applyVectorP(new vec2(gamepad1.right_stick_x, -gamepad1.right_stick_y), telemetry);
            else Robot.DD.rightModule.applyVectorP(new vec2(0, 0), telemetry);
            //telemetry.addData("left encoder", Robot.DD.leftModule.getDirection());
            //telemetry.addData("right encoder", Robot.DD.rightModule.getDirection());
            telemetry.update();
        }
    }
}