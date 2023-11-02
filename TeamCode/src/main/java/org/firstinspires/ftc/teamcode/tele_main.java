package org.firstinspires.ftc.teamcode;


import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

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

            /* normal condition */
            /*if (Math.abs(gamepad1.left_stick_x) > 0.05 || Math.abs(gamepad1.left_stick_y) > 0.05 || (gamepad1.right_trigger - gamepad1.left_trigger) >= 0.05)
                Robot.DD.applySpeed(new vec2(gamepad1.left_stick_x, -gamepad1.left_stick_y), gamepad1.right_trigger - gamepad1.left_trigger, telemetry);
            else
                Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);*/

            /* 1 encoder */
            /*
            if (abs(gamepad1.left_stick_x) > 0.05 || abs(gamepad1.left_stick_y) > 0.05)
                Robot.DD.leftModule.applyVectorPTele(new vec2(gamepad1.left_stick_x, -gamepad1.left_stick_y), telemetry);
            else
                Robot.DD.leftModule.applyVectorPTele(new vec2(0, 0), telemetry);
            if (Math.abs(gamepad1.right_stick_x) > 0.05 || Math.abs(gamepad1.right_stick_y) > 0.05)
                Robot.DD.rightModule.applyVector(gamepad1.right_stick_x, -gamepad1.right_stick_y);
            else
                Robot.DD.rightModule.applyVector(0, 0);
            */

            /* 6 wheel system no encoders */
            /*
            if (!gamepad1.y) {
                if (abs(gamepad1.left_stick_x) > 0.05 || abs(gamepad1.left_stick_y) > 0.05) {
                    Robot.DD.rightModule.applyVectorTele(-gamepad1.left_stick_y, gamepad1.left_stick_x, telemetry);
                    Robot.DD.leftModule.applyVectorTele(-gamepad1.left_stick_y, gamepad1.left_stick_x, telemetry);
                }
                else {
                    Robot.DD.rightModule.applyVector(0, 0);
                    Robot.DD.leftModule.applyVector(0, 0);
                }
            }
            else {
                if (abs(gamepad1.left_stick_x) > 0.05 || abs(gamepad1.left_stick_y) > 0.05)
                    Robot.DD.leftModule.applyVectorTele(-gamepad1.left_stick_y, gamepad1.left_stick_x, telemetry);
                else
                    Robot.DD.leftModule.applyVectorTele(0,0, telemetry);
                if (abs(gamepad1.right_stick_x) > 0.05 || abs(gamepad1.right_stick_y) > 0.05)
                    Robot.DD.rightModule.applyVectorTele(-gamepad1.right_stick_y, gamepad1.right_stick_x, telemetry);
                else
                    Robot.DD.rightModule.applyVectorTele(0,0, telemetry);
                }
            }
            */

            /* 6 wheel system 1 encoder */
            /*
            if (gamepad1.b) {
                Robot.DD.leftModule.cur_dir = new vec2(0, 1);
                Robot.DD.rightModule.cur_dir = Robot.DD.leftModule.cur_dir;
            }

            if (!gamepad1.y) {
                Robot.DD.leftModule.cur_dir = new vec2(cos(Robot.DD.rightModule.getDirection() + PI * 0.5), sin(Robot.DD.rightModule.getDirection() + PI * 0.5));
                if (abs(gamepad1.left_stick_x) > 0.05 || abs(gamepad1.left_stick_y) > 0.05) {
                    Robot.DD.rightModule.applyVectorPTele(new vec2(gamepad1.left_stick_y, gamepad1.left_stick_x), telemetry);
                    Robot.DD.leftModule.applyVectorPTeleNoEncoder(new vec2(gamepad1.left_stick_y, gamepad1.left_stick_x), telemetry);
                } else {
                    Robot.DD.rightModule.applyVectorPTele(new vec2(0, 0), telemetry);
                    Robot.DD.leftModule.applyVectorPTeleNoEncoder(new vec2(0, 0), telemetry);
                }
            } else {
                if (abs(gamepad1.left_stick_x) > 0.05 || abs(gamepad1.left_stick_y) > 0.05)
                    Robot.DD.leftModule.applyVectorTele(-gamepad1.left_stick_y, gamepad1.left_stick_x, telemetry);
                else
                    Robot.DD.leftModule.applyVectorTele(0, 0, telemetry);
                if (abs(gamepad1.right_stick_x) > 0.05 || abs(gamepad1.right_stick_y) > 0.05)
                    Robot.DD.rightModule.applyVectorTele(-gamepad1.right_stick_y, gamepad1.right_stick_x, telemetry);
                else
                    Robot.DD.rightModule.applyVectorTele(0, 0, telemetry);
            }
            */

            /* left module manual handling, right module encoder handling */

            if (gamepad1.b) {
                Robot.DD.rightModule.cur_dir = new vec2(0, 1);
                telemetry.addData("", "Reinit succesfull");
                telemetry.update();
            }
            if (gamepad1.y) {
                if (abs(gamepad1.right_stick_x) > 0.05 || abs(gamepad1.right_stick_y) > 0.05)
                    Robot.DD.rightModule.applyVectorTele(-gamepad1.right_stick_y / 6.23, -gamepad1.right_stick_x / 6.23, telemetry);
                else
                    Robot.DD.rightModule.applyVectorTele(0, 0, telemetry);
                if (abs(gamepad1.left_stick_x) > 0.05 || abs(gamepad1.left_stick_y) > 0.05)
                    Robot.DD.leftModule.applyVectorTele(-gamepad1.left_stick_y / 6.23, -gamepad1.left_stick_x / 6.23, telemetry);
                else
                    Robot.DD.leftModule.applyVectorTele(0, 0, telemetry);
            } else {
                if (abs(gamepad1.left_stick_x) > 0.05 || abs(gamepad1.left_stick_y) > 0.05)
                    Robot.DD.leftModule.applyVectorTele(-gamepad1.left_stick_y, gamepad1.left_stick_x, telemetry);
                else
                    Robot.DD.leftModule.applyVectorTele(0, 0, telemetry);
                if (abs(gamepad1.right_stick_x) > 0.05 || abs(gamepad1.right_stick_y) > 0.05)
                    Robot.DD.rightModule.applyVectorTele(-gamepad1.right_stick_y, gamepad1.right_stick_x, telemetry);
                else
                    Robot.DD.rightModule.applyVectorTele(0, 0, telemetry);
            }

            /* both modules manual handling */
            /* if y is pressed divide power by 3 */
            /*
            if (abs(gamepad1.left_stick_x) > 0.05 || abs(gamepad1.left_stick_y) > 0.05)
                Robot.DD.leftModule.applyVectorTele(-gamepad1.left_stick_y / ((gamepad1.y ? 1 : 0) * 5.23 + 1), gamepad1.left_stick_x / ((gamepad1.y ? 1 : 0) * 3.23 + 1), telemetry);
            else
                Robot.DD.leftModule.applyVectorTele(0, 0, telemetry);
            if (abs(gamepad1.right_stick_x) > 0.05 || abs(gamepad1.right_stick_y) > 0.05)
                Robot.DD.rightModule.applyVectorTele(-gamepad1.right_stick_y / ((gamepad1.y ? 1 : 0) * 5.23 + 1), gamepad1.right_stick_x / ((gamepad1.y ? 1 : 0) * 3.23 + 1), telemetry);
            else
                Robot.DD.rightModule.applyVectorTele(0, 0, telemetry);
            */

            /*** END OF WHEELBASE DRIVING SECTION ***/

            /*** INTAKE CONTROL ***/
            if (gamepad2.y) {
                Robot.IN.motor1.setPower(-1);
                Robot.IN.motor1.setPower(1);
            } else
                Robot.IN.work_intake(gamepad2.left_stick_y);
            /*** END OF INTAKE CONTROL ***/
        }
    }
}