package org.firstinspires.ftc.teamcode;


import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Device;

import org.firstinspires.ftc.teamcode.maths.vec2;

@TeleOp(name = "tele_main")
public class tele_main extends LinearOpMode {
    RobotNW Robot = new RobotNW();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        telemetry.addData("", "init succesfully");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            /*** WHEELBASE DRIVING SECTION ***/
            /*if (Math.abs(gamepad1.left_stick_x) > 0.05 || Math.abs(gamepad1.left_stick_y) > 0.05 || (gamepad1.right_trigger - gamepad1.left_trigger) >= 0.05)
                Robot.DD.applySpeed(new vec2(gamepad1.left_stick_x, -gamepad1.left_stick_y), gamepad1.right_trigger - gamepad1.left_trigger, telemetry);
            else
                Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);*/
            if (abs(gamepad1.left_stick_x) > 0.05 || abs(gamepad1.left_stick_y) > 0.05)
                Robot.DD.leftModule.applyVectorPTele(new vec2(gamepad1.left_stick_x, -gamepad1.left_stick_y), telemetry);
            else
                Robot.DD.leftModule.applyVectorPTele(new vec2(0, 0), telemetry);
            if (Math.abs(gamepad1.right_stick_x) > 0.05 || Math.abs(gamepad2.right_stick_y) > 0.05)
                Robot.DD.rightModule.applyVector(gamepad1.right_stick_x, -gamepad1.right_stick_y);
            else
                Robot.DD.rightModule.applyVector(0, 0);
            /*** END OF WHEELBASE DRIVING SECTION ***/

            /*** INTAKE CONTROL ***/
            Robot.IN.work_intake(gamepad2.left_stick_y);
            /*** END OF INTAKE CONTROL ***/
        }
    }
}
