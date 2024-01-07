package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_CENTER_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_LEFT_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_RIGHT_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_WING;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_CENTER_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_LEFT_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_RIGHT_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_WING;
import static java.lang.Math.abs;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.auto_PID;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.feedback.feedback;
import org.firstinspires.ftc.teamcode.maths.vec2;
import org.firstinspires.ftc.teamcode.diff_sverwe.PID_system;
import org.firstinspires.ftc.teamcode.tele_movement.op_container;
import org.firstinspires.ftc.teamcode.tele_movement.tele_auto;


/*** FEEDBACK INFO ***/
/**
<b> GAMEPAD TABLE: </b>
<p><font color="green">GREEN => OPERATOR CONTROLLED</font></p>
 <p><font color="red">RED & vibrating => AUTO CONTROLLED</font></p>
 <p><font color="orange">ORANGE => CAN UPDATE COORDINATES</font></p>
 <br>
<b> AUTO CONTROLS: </b>
 <p>A (cross) => go to wing</p>
 <p>dpad left => go to left backdrop pos</p>
 <p>dpad up => go to center backdrop pos</p>
 <p>dpad right => go to right backdrop pos</p>
 <p>left bumper => go to nearest farm</p>
 <br>

 ***/
@TeleOp(name = "tele_main")
public class tele_main extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    vec2 JoyDir = new vec2(0);
    Pose2d curPos;
    Vector2d atPos;

    boolean endgame = false;
    double last_turn = 0;
    boolean WasRotating = false, outtake_flag = false;
    ElapsedTime outtake_timer = new ElapsedTime();
    ElapsedTime timer = new ElapsedTime();
    PID_system calculator = new PID_system();
    auto_PID auto_calculator = new auto_PID();

    feedback gamepad1Feedback = new feedback(gamepad1);
    feedback gamepad2Feedback = new feedback(gamepad2);
    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(op_container.location);

        telemetry.addData("", "init succesfully");
        telemetry.update();

        calculator.init(0);
        auto_calculator.init(new Pose2d(0, 48, 0), new Pose2d(0, 48, 0));

        gamepad1Feedback.setColor(0, 1, 0);
        gamepad2Feedback.setColor(0, 0, 1);
        waitForStart();
        timer.reset();
        gamepad2Feedback.setColor(0, 1, 0);

        while (opModeIsActive()) {
            /*** ODOMETRY SECTION ***/
            drive.update();
            // Retrieve your pose
            curPos = drive.getPoseEstimate();

            /*** WHEELBASE DRIVING SECTION ***/

            if (abs(gamepad1.left_stick_x) > 0.02 || abs(gamepad1.left_stick_y) > 0.02 || (Math.abs(gamepad1.left_trigger - gamepad1.right_trigger)) > 0.02) {
                last_turn = (gamepad1.left_trigger - gamepad1.right_trigger);
                JoyDir.set(-gamepad1.left_stick_x, -gamepad1.left_stick_y);

                if (Math.abs(last_turn) < 0.02)
                    Robot.DD.applySpeed(JoyDir, /*calculator.calculate_speeds(dHeading)*/ 0, telemetry);
                else
                    Robot.DD.applySpeed(JoyDir, last_turn, telemetry);
            }
            else
            {
                Robot.DD.applySpeed(new vec2(0, 0), /*calculator.calculate_speeds(dHeading)*/0, telemetry);
                //Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);
                //dHeading = 0;
            }

            telemetry.addData("CALCULATE ", calculator.getRotation());
            telemetry.addData("real angle ", Robot.IM.getPositiveAngle());
            telemetry.addData("WasRotating", WasRotating);
            telemetry.addData("lastTurn", last_turn);
            telemetry.addData("Right ticks", Robot.DD.rightModule.upMotor.getCurrentPosition());
            telemetry.addData("Left ticks", Robot.DD.leftModule.upMotor.getCurrentPosition());

            /*** END OF WHEELBASE DRIVING SECTION ***/

            /*** AUTO RELOCATION SECTION ***/

            /* coordinates can be updated */

            atPos = Robot.AT.getRobotPos(curPos.getHeading());
            if (atPos != null && !gamepad1.x)
                gamepad1Feedback.setColor(1, 0.5, 0);
            else if (atPos != null && gamepad1.x) {
                drive.setPoseEstimate(new Pose2d(atPos, curPos.getHeading()));
                gamepad1Feedback.runEffectCoordsUpdated();
            }
            else
                gamepad1Feedback.setColor(0, 1, 0);

            /* WING PARKING */
            if (gamepad1.a && op_container.blue == true) {
                gamepad1Feedback.setColor(1, 0, 0);
                tele_auto.goToZone(BLUE_WING, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                gamepad1Feedback.setColor(0, 1, 0);
            }
            else if (gamepad1.a && op_container.blue == false) {
                gamepad1Feedback.setColor(1, 0, 0);
                tele_auto.goToZone(RED_WING, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                gamepad1Feedback.setColor(0, 1, 0);
            }

            /* BACKDROP LEFT PARKING */
            if (gamepad1.dpad_left && op_container.blue == true) {
                gamepad1Feedback.setColor(1, 0, 0);
                tele_auto.goToZone(BLUE_LEFT_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                gamepad1Feedback.setColor(0, 1, 0);
            }
            else if (gamepad1.dpad_left && op_container.blue == false) {
                gamepad1Feedback.setColor(1, 0, 0);
                tele_auto.goToZone(RED_LEFT_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                gamepad1Feedback.setColor(0, 1, 0);
            }

            /* BACKDROP CENTER PARKING */
            if (gamepad1.dpad_up && op_container.blue == true){
                gamepad1Feedback.setColor(1, 0, 0);
                tele_auto.goToZone(BLUE_CENTER_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                gamepad1Feedback.setColor(0, 1, 0);
            }
            else if (gamepad1.dpad_up && op_container.blue == false){
                gamepad1Feedback.setColor(1, 0, 0);
                tele_auto.goToZone(RED_CENTER_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                gamepad1Feedback.setColor(0, 1, 0);
            }

            /* BACKDROP RIGHT PARKING */
            if (gamepad1.dpad_right && op_container.blue == true){
                gamepad1Feedback.setColor(1, 0, 0);
                tele_auto.goToZone(BLUE_RIGHT_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                gamepad1Feedback.setColor(0, 1, 0);
            }
            else if (gamepad1.dpad_right && op_container.blue == false){
                gamepad1Feedback.setColor(1, 0, 0);
                tele_auto.goToZone(RED_RIGHT_DROP, drive, auto_calculator, gamepad1, Robot, telemetry, this);
                gamepad1Feedback.setColor(0, 1, 0);
            }

            /* CLOSEST FARM PARKING */
            if (gamepad1.left_bumper){
                gamepad1Feedback.setColor(1, 0, 0);
                tele_auto.goToZone(tele_auto.findClosestFarm(drive.getPoseEstimate()), drive, auto_calculator, gamepad1, Robot, telemetry, this);
                gamepad1Feedback.setColor(0, 1, 0);
            }

            /*** INTAKE CONTROL ***/
            if (gamepad2.x)
                Robot.IN.intake_run();
            else if (gamepad2.right_trigger > 0.5)
                Robot.IN.intake_run_away();
            else
                Robot.IN.stopIntakeMotors();

            /*** END OF INTAKE CONTROL ***/

            /*** PLANE CONTROL ***/

            if (gamepad2.right_bumper) {
                Robot.PL.prepare();
            }
            else {
                if (gamepad2.left_trigger > 0.5 && endgame)
                    Robot.PL.launch();
                else if (gamepad2.left_trigger > 0.5 && !endgame)
                    gamepad2Feedback.runEffectDisabled();
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

            /*** OUTTAKE FLIPPER CONTROL ***/

            Robot.OF.setPos(0.8);

            /*** END OF OUTTAKE FLIPPER CONTROL ***/

            /*** UTILITY ***/
            if (timer.milliseconds() > 120000 && !endgame) /* endgame started */
            {
                gamepad1Feedback.runEffectEndgameStart();
                gamepad2Feedback.runEffectEndgameStart();
                endgame = true;
            }

            telemetry.update();
        }
    }
}