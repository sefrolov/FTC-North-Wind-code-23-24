package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_CENTER_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_LEFT_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_RIGHT_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_WING;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_CENTER_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_LEFT_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_RIGHT_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_WING;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Device;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.auto_PID;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.TwoWheelTrackingLocalizer;
import org.firstinspires.ftc.teamcode.maths.vec2;
import org.firstinspires.ftc.teamcode.diff_sverwe.PID_system;
import org.firstinspires.ftc.teamcode.tele_movement.location_constants;
import org.firstinspires.ftc.teamcode.tele_movement.op_container;
import org.firstinspires.ftc.teamcode.tele_movement.tele_auto;

@TeleOp(name = "tele_main")
public class tele_main extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    vec2 JoyDir = new vec2(0);
    double last_turn = 0;
    vec2 last_trans = new vec2(0);
    double targetAngle = Math.toRadians(0);
    double curAngle = Math.toRadians(0);
    boolean WasRotating = false, outtake_flag = false, IsAngle = true;
    boolean see = false;
    ElapsedTime outtake_timer = new ElapsedTime();

    Pose2d errors = new Pose2d(1, 1, 0.05);
    double dHeading;
    double last_delta_angle = 0;
    double rotation;
    double Irotation = 0;

    Pose2d errs = new Pose2d(1000, 1000, 1000);
    PID_system calculator = new PID_system();
    auto_PID auto_calculator = new auto_PID();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(op_container.location);

        telemetry.addData("", "init succesfully");
        telemetry.update();

        calculator.init(0);
        auto_calculator.init(new Pose2d(0, 48, 0), new Pose2d(0, 48, 0));

        waitForStart();

        while (opModeIsActive()) {
            /*** ODOMETRY SECTION ***/
            drive.update();
            // Retrieve your pose
            Pose2d curPos = drive.getPoseEstimate();

            /*** WHEELBASE DRIVING SECTION ***/

            curAngle = Robot.IM.getPositiveAngle();

            if ((Math.abs(gamepad1.left_trigger - gamepad1.right_trigger)) < 0.02 && WasRotating) {
                targetAngle = Robot.IM.getPositiveAngle();
                WasRotating = false;
            }

            dHeading = targetAngle - curAngle;

            if (Math.abs(dHeading) > PI && dHeading > 0)
                dHeading = dHeading - 2 * PI;
            else if (Math.abs(dHeading) > PI && dHeading < 0)
                dHeading = 2 * PI + dHeading;

            if (abs(gamepad1.left_stick_x) > 0.02 || abs(gamepad1.left_stick_y) > 0.02 || (Math.abs(gamepad1.left_trigger - gamepad1.right_trigger)) > 0.02) {
                last_turn = (gamepad1.left_trigger - gamepad1.right_trigger);
                JoyDir.set(-gamepad1.left_stick_x, -gamepad1.left_stick_y);

                if (Math.abs(last_turn) < 0.02) {
                    Robot.DD.applySpeed(JoyDir, /*calculator.calculate_speeds(dHeading)*/ 0, telemetry);
                    WasRotating = false;
                }
                else {
                    Robot.DD.applySpeed(JoyDir, last_turn, telemetry);
                    WasRotating = true;
                }
            }
            else
            {
                Robot.DD.applySpeed(new vec2(0, 0), /*calculator.calculate_speeds(dHeading)*/0, telemetry);
                //Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);
                //dHeading = 0;
            }

            telemetry.addData("dHeading", dHeading);
            telemetry.addData("CALCULATE ", calculator.getRotation());
            telemetry.addData("angle ", targetAngle);
            telemetry.addData("real angle ", Robot.IM.getPositiveAngle());
            telemetry.addData("WasRotating", WasRotating);
            telemetry.addData("lastTurn", last_turn);
            telemetry.addData("turnSpd", rotation / 10 + last_turn);
            telemetry.update();

            /*** END OF WHEELBASE DRIVING SECTION ***/

            /*** AUTO RELOCATION SECTION ***/

            /* WING PARKING */
            if (gamepad1.a && op_container.blue == true) {
                tele_auto.goToZone(BLUE_WING, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                targetAngle = BLUE_WING.getHeading();
            }
            else if (gamepad1.a && op_container.blue == false) {
                tele_auto.goToZone(RED_WING, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                targetAngle = RED_WING.getHeading();
            }

            /* BACKDROP LEFT PARKING */

            /* align to get correct errs & set target position */

            if (gamepad1.dpad_left) {
                if (op_container.blue == true)
                    errs = Robot.AT.getErrors(1);
                else
                    errs = Robot.AT.getErrors(4);
                if (errs.getY() != 1000) {
                    see = true;
                    gamepad1.setLedColor(1, 0, 1, 300000);
                }
                else {
                    see = false;
                    gamepad1.setLedColor(0, 1, 0, 300000);
                    gamepad1.rumbleBlips(2);
                }
                drive.update();
                curPos = drive.getPoseEstimate();

                if (see) {
                    double X = curPos.getX() + errs.getX() + 1;
                    double Y = curPos.getY() + errs.getY() - 6;
                    Pose2d targetPose = new Pose2d(X, Y, Math.toRadians(270));
                    Robot.DD.straightGoTo(targetPose, errors, auto_calculator, drive, this);
                    drive.update();
                    if (op_container.blue == true)
                        drive.setPoseEstimate(new Pose2d(-42, 50, drive.getPoseEstimate().getHeading()));
                    else
                        drive.setPoseEstimate(new Pose2d(30, 50, drive.getPoseEstimate().getHeading()));
                }
            }
/*
            if (gamepad1.dpad_left && op_container.blue == true) {
                tele_auto.goToZone(BLUE_LEFT_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                targetAngle = BLUE_LEFT_DROP.getHeading();
            }
            else if (gamepad1.dpad_left && op_container.blue == false) {
                tele_auto.goToZone(RED_LEFT_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                targetAngle = RED_LEFT_DROP.getHeading();
            }
*/
            /* BACKDROP CENTER PARKING */
            if (gamepad1.dpad_up && op_container.blue == true) {
                tele_auto.goToZone(BLUE_CENTER_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                targetAngle = BLUE_CENTER_DROP.getHeading();
            }
            else if (gamepad1.dpad_up && op_container.blue == false) {
                tele_auto.goToZone(RED_CENTER_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                targetAngle = RED_CENTER_DROP.getHeading();
            }

            /* BACKDROP RIGHT PARKING */
            if (gamepad1.dpad_right && op_container.blue == true) {
                tele_auto.goToZone(BLUE_RIGHT_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                targetAngle = BLUE_RIGHT_DROP.getHeading();
            }
            else if (gamepad1.dpad_right && op_container.blue == false) {
                tele_auto.goToZone(RED_RIGHT_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                targetAngle = RED_RIGHT_DROP.getHeading();
            }

            /* CLOSEST FARM PARKING */
            if (gamepad1.left_bumper){
                drive.update();
                tele_auto.goToZone(tele_auto.findClosestFarm(drive.getPoseEstimate()), drive, auto_calculator, gamepad1, Robot, telemetry, this);
            }



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