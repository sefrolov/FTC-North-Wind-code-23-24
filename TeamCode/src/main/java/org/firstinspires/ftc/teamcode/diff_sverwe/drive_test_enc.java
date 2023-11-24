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

            /*
            if (abs(gamepad1.left_stick_x) > 0.05 || abs(gamepad1.left_stick_y) > 0.05)
            Robot.DD.rightModule.applyVectorP(new vec2(gamepad1.left_stick_x, -gamepad1.left_stick_y), telemetry);
            else
             Robot.DD.rightModule.applyVectorP(new vec2(0, 0), telemetry);
            if (abs(gamepad1.right_stick_x) > 0.05 || abs(gamepad1.right_stick_y) > 0.05)
            Robot.DD.rightModule.applyVectorP(new vec2(gamepad1.right_stick_x, -gamepad1.right_stick_y), telemetry);
            else
             Robot.DD.rightModule.applyVectorP(new vec2(0, 0), telemetry);
            //telemetry.addData("left encoder", Robot.DD.leftModule.getDirection());
            //telemetry.addData("right encoder", Robot.DD.rightModule.getDirection());
            telemetry.update();
*/
            /*
            if (gamepad1.a){
                Robot.DD.rightModule.applyVectorTele(0, -1, telemetry);
            }
            else if (gamepad1.b){
                Robot.DD.rightModule.applyVectorTele(1, 0, telemetry);
            }
            else if (gamepad1.x){
                Robot.DD.rightModule.applyVectorTele(0.5, 0.5, telemetry);
            }
            else if (gamepad1.y){
                Robot.DD.rightModule.applyVectorTele(-0.5, 0.3, telemetry);
            }
            else
                Robot.DD.rightModule.applyVectorTele(0, 0, telemetry);

            */
            if (!gamepad2.y){
                if (abs(gamepad2.left_stick_x) > 0.05 || abs(gamepad2.left_stick_y) > 0.05)
                    Robot.DD.leftModule.applyVectorPTele(new vec2(-gamepad2.left_stick_x, -gamepad2.left_stick_y), telemetry);
                else
                    Robot.DD.leftModule.applyVectorPTele(new vec2(0, 0), telemetry);
                if (abs(gamepad2.right_stick_x) > 0.05 || abs(gamepad2.right_stick_y) > 0.05)
                    Robot.DD.rightModule.applyVectorPTele(new vec2(-gamepad2.right_stick_x, gamepad2.right_stick_y), telemetry);
                else
                    Robot.DD.rightModule.applyVectorPTele(new vec2(0, 0), telemetry);
                telemetry.addData("left encoder", Robot.DD.leftModule.upMotor.getCurrentPosition());
                telemetry.addData("right encoder", Robot.DD.rightModule.upMotor.getCurrentPosition());
                telemetry.update();
            }
            else {
                if (abs(gamepad2.left_stick_x) > 0.05 || abs(gamepad2.left_stick_y) > 0.05 || (Math.abs(gamepad2.right_trigger - gamepad2.left_trigger)) > 0.05)
                    Robot.DD.applySpeed(new vec2(gamepad2.left_stick_x, gamepad2.left_stick_y), (gamepad2.right_trigger - gamepad2.left_trigger), telemetry);
                else
                    Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);
            }


            /*
            if (gamepad2.a) {
                Robot.DD.leftModule.applyVector(0, -1);
            }
            else if (gamepad2.b) {
                Robot.DD.leftModule.applyVector(1, 0);
            }
            else if (gamepad2.x) {
                Robot.DD.leftModule.applyVector(1, 0);
            }
            else if (gamepad2.y) {
                Robot.DD.leftModule.applyVector(-0.5, 0.3);
            }
            else
                Robot.DD.leftModule.applyVector(0, 0);
            */
        }
    }
}