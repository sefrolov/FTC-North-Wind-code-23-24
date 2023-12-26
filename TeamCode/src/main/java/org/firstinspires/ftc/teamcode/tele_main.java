package org.firstinspires.ftc.teamcode;


import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Device;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.TwoWheelTrackingLocalizer;
import org.firstinspires.ftc.teamcode.maths.vec2;

@TeleOp(name = "tele_main")
public class tele_main extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    vec2 JoyDir = new vec2(0);
    double last_turn = 0;
    vec2 last_trans = new vec2(0);
    double angle = Math.toRadians(180);
    boolean WasRotating = false, outtake_flag = false;
    ElapsedTime outtake_timer = new ElapsedTime();

    double dHeading;
    double last_angle = 0;
    double rotation;
    double Irotation = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(new Pose2d(-63.38, 15.14, angle));

        telemetry.addData("", "init succesfully");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            /*** ODOMETRY SECTION ***/
            drive.update();
            // Retrieve your pose
            Pose2d myPose = /*myLocalizer*/drive.getPoseEstimate();
            /*
            telemetry.addData("x", myPose.getX());
            telemetry.addData("y", myPose.getY());
            telemetry.addData("heading", myPose.getHeading());
            telemetry.update();
            */
            /*** WHEELBASE DRIVING SECTION ***/

            /* normal condition */
            /*if (Math.abs(gamepad1.left_stick_x) > 0.05 || Math.abs(gamepad1.left_stick_y) > 0.05 || (gamepad1.right_trigger - gamepad1.left_trigger) >= 0.05)
                Robot.DD.applySpeed(new vec2(gamepad1.left_stick_x, -gamepad1.left_stick_y), gamepad1.right_trigger - gamepad1.left_trigger, telemetry);
            else
                Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);*/

            /*** FOR DRIVER STARTS HERE ***/

/*
            JoyDir.set(gamepad1.left_stick_x, gamepad1.left_stick_y);

            if (abs(gamepad1.left_stick_x) > 0.02 || abs(gamepad1.left_stick_y) > 0.02 || (Math.abs(gamepad1.right_trigger - gamepad1.left_trigger) / 2.) > 0.02) {
                last_trans = new vec2(gamepad1.left_stick_x, gamepad1.left_stick_y);
                last_turn = (gamepad1.right_trigger - gamepad1.left_trigger) / 2.;

                if (last_turn == 0 && WasRotating) {
                    angle = Robot.IM.getAngle();
                    WasRotating = false;
                }
*/
                /* angle control */
/*
                if (last_turn == 0 && !WasRotating) {
                    Robot.DD.applySpeed(JoyDir.turn(Robot.IM.getPositiveAngle() + 2 * PI), (Robot.IM.getAngle() - angle) / 1., telemetry);
                }
                if (last_turn != 0) {
                    WasRotating = true;
                    Robot.DD.applySpeed(JoyDir.turn(Robot.IM.getPositiveAngle() + 2 * PI), last_turn, telemetry);
                }
 */
                /* end of angle control */
/*
            }
            else
                Robot.DD.applySpeed(/*new vec2(0.01, 0.01)*/
/*              last_trans.mul(0.1), last_turn / 10., telemetry);
*/

            /*** FOR DRIVER ENDS HERE ***/

            if (abs(gamepad1.left_stick_x) > 0.02 || abs(gamepad1.left_stick_y) > 0.02 || (Math.abs(gamepad1.left_trigger - gamepad1.right_trigger)) > 0.02) {
                last_turn = (gamepad1.left_trigger - gamepad1.right_trigger);
                JoyDir.set(-gamepad1.left_stick_x, -gamepad1.left_stick_y);

                if (last_turn == 0 && !WasRotating) {
                    if (dHeading * (Robot.IM.getPositiveAngle() - angle) < 0)
                        Irotation = 0;
                    dHeading = Robot.IM.getPositiveAngle() - angle;
                    Irotation += dHeading;
                }
                if (last_turn == 0 && WasRotating) {
                    angle = Robot.IM.getPositiveAngle();
                    WasRotating = false;
                    Irotation = 0;
                }
                if (last_turn != 0) {
                    WasRotating = true;
                    Irotation = 0;
                }
                telemetry.addData("angle ", angle);

                telemetry.addData("WasRotating", WasRotating);
                telemetry.addData("lastTurn", last_turn);
                telemetry.addData("turnSpd", rotation / 10 + last_turn);
                telemetry.update();
                Robot.DD.applySpeed(JoyDir, /*Irotation * 0.01 +*/ last_turn, telemetry);
                last_angle = Robot.IM.getPositiveAngle();
            }
            else {
                Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);
                dHeading = 0;
            }

            /*
            if (abs(gamepad1.left_stick_x) > 0.02 || abs(gamepad1.left_stick_y) > 0.02 || (Math.abs(gamepad1.right_trigger - gamepad1.left_trigger)) > 0.02)

                Robot.DD.applySpeed(JoyDir.turn(Robot.IM.getPositiveAngle() + 2 * PI), (gamepad1.right_trigger - gamepad1.left_trigger), telemetry);
            else
                Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);
            */

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

            /*if (gamepad1.b) {
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
            }*/

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


            if (gamepad2.x)
                Robot.IN.intake_run();
            else if (gamepad2.right_trigger > 0.5)
                Robot.IN.intake_run_away();
            else
                Robot.IN.stopIntakeMotors();


            /*** END OF INTAKE CONTROL ***/
            /* ODOMETRY TELEMETRY

            telemetry.addData("Pos y:", Robot.OD.encoder_y.getCurrentPosition());
            telemetry.addData("Pos x:", Robot.OD.encoder_x.getCurrentPosition());
            */

            /*** PLANE CONTROL ***/

            if (gamepad2.right_bumper) {
                Robot.PL.prepare();
            } else if (gamepad2.left_trigger > 0.5) {
                Robot.PL.launch();
            }

            /*** END OF PLANE CONTROL ***/

            /*** INTAKE FOLDING CONTROL ***/


            if (gamepad2.x || gamepad2.y)
                Robot.IF.spin();
            else if (gamepad2.dpad_left)
                Robot.IF.spin_reverse();
            else
                Robot.IF.stop();


            /*** END OF INTAKE FOLDING CONTROL ***/

            /** CHANGE OVER CONTROL ***/

            if (gamepad2.dpad_up)
                Robot.CO.setPositionHigh();
            else if (gamepad2.dpad_down)
                Robot.CO.setPositionLow();
            else if (gamepad2.dpad_right)
              Robot.CO.setPositionDef();

            /*** END OF CHANGE OVER CONTROL ***/

            /*** OUTTAKE CONTROL ***/

            if (gamepad2.x)
                Robot.OT.runLoading();
            else if ((gamepad2.b || gamepad1.b) && outtake_flag == false) {
                outtake_flag = true;
                outtake_timer.reset();
            }
            else
                Robot.OT.stop();
            //Robot.OT.checkOuttake();

            if (outtake_flag){
                if (outtake_timer.milliseconds() < 200){
                    Robot.OT.runUnloading();
                }
                else{
                    Robot.OT.stop();
                    outtake_flag = false;
                }
            }
            /*** END OF OUTTAKE CONTROL ***/

            telemetry.update();
        }
    }
}