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
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.autonomous.auto_PID;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.feedback.feedback;
import org.firstinspires.ftc.teamcode.lift.elevator_thread;
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
 <p>X (square) => update coordinates </p>
 <p>dpad left => go to left backdrop pos</p>
 <p>dpad up => go to center backdrop pos</p>
 <p>dpad right => go to right backdrop pos</p>
 <p>left bumper => go to nearest farm</p>
 <br>

 ***/
@TeleOp(name = "Tele")
public class Tele extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    vec2 JoyDir = new vec2(0);
    Pose2d curPos;
    Vector2d atPos;

    int flipperPos = 0; //1 up 0 low
    boolean endgame = false, intake_run_away_flag = false;
    int autoDrive = 0, old_pix = 0;
    /*
       0 - OFF
       1 - WING
       2 - BACKDROP LEFT
       3 - BACKDROP CENTER
       4 - BACKDROP RIGHT
       5 - FARM
     */
    double last_turn = 0;
    boolean WasRotating = false, outtake_flag = false;
    ElapsedTime outtake_timer = new ElapsedTime();
    ElapsedTime intake_timer = new ElapsedTime();
    ElapsedTime timer = new ElapsedTime();
    ElapsedTime intake_run_away = new ElapsedTime();

    ElapsedTime timer_flipper = new ElapsedTime();
    PID_system calculator = new PID_system();

    feedback gamepad1Feedback;
    feedback gamepad2Feedback;

    elevator_thread elevator = new elevator_thread();

    auto_PID auto_calculator = new auto_PID();
    @Override
    public void runOpMode() throws InterruptedException {
        if (gamepad1 != null) {
            gamepad1Feedback = new feedback(gamepad1);
            gamepad2Feedback = new feedback(gamepad1);
        }
        else {
            gamepad1Feedback = null;
            gamepad2Feedback = null;
        }
        if (gamepad2 != null) {
            gamepad2Feedback = new feedback(gamepad2);
            if (gamepad1Feedback == null) {
                gamepad1Feedback = new feedback(gamepad2);
            }
        }

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();
        Gamepad PreviousGamepad1 = new Gamepad();
        Gamepad PreviousGamepad2 = new Gamepad();

        Robot.init(hardwareMap, telemetry, this);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(op_container.location);

        telemetry.addData("", "init succesfully");
        telemetry.update();

        calculator.init(0);
        Robot.FN.drop();
        elevator.init(hardwareMap, telemetry);
        elevator.start();

        auto_calculator.reset(new Pose2d(0, 48, 0), new Pose2d(0, 48, 0));

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

            PreviousGamepad1.copy(currentGamepad1);
            PreviousGamepad2.copy(currentGamepad2);
            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);

            /*** WHEELBASE DRIVING SECTION ***/

            if (abs(gamepad1.left_stick_x) > 0.02 || abs(gamepad1.left_stick_y) > 0.02 || (Math.abs(gamepad1.left_trigger - gamepad1.right_trigger)) > 0.02) {
                autoDrive = 0;
                last_turn = (gamepad1.left_trigger - gamepad1.right_trigger);
                JoyDir.set(-gamepad1.left_stick_x, -gamepad1.left_stick_y);

                if (Math.abs(last_turn) < 0.02)
                    Robot.DD.applySpeed(JoyDir, /*calculator.calculate_speeds(dHeading)*/ 0, telemetry);
                else
                    Robot.DD.applySpeed(JoyDir, last_turn, telemetry);
            } else {
                if (autoDrive == 0)
                    Robot.DD.applySpeed(new vec2(0, 0), /*calculator.calculate_speeds(dHeading)*/0, telemetry);
                //Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);
                //dHeading = 0;
            }

            /*
            if (gamepad2.right_stick_button)
                Robot.FN.drop();
            if (gamepad2.left_stick_button)
                Robot.FN.prepare();
*/

            telemetry.addData("CALCULATE ", calculator.getRotation());
            telemetry.addData("real angle ", Robot.IM.getPositiveAngle());
            telemetry.addData("WasRotating", WasRotating);
            telemetry.addData("lastTurn", last_turn);
            telemetry.addData("Right ticks", Robot.DD.rightModule.upMotor.getCurrentPosition());
            telemetry.addData("Left ticks", Robot.DD.leftModule.upMotor.getCurrentPosition());
            telemetry.addData("X:", curPos.getX());
            telemetry.addData("Y:", curPos.getY());
            telemetry.addData("pixel", elevator.getNumPixels());


            /*** END OF WHEELBASE DRIVING SECTION ***/

            /*** AUTO RELOCATION SECTION ***/

            /* coordinates can be updated */

            atPos = Robot.AT.getRobotPos(curPos.getHeading());
            if (atPos != null && !gamepad1.x)
                gamepad1Feedback.setColor(1, 0.5, 0);
            else if (atPos != null && gamepad1.x) {
                drive.setPoseEstimate(new Pose2d(atPos, curPos.getHeading()));
                gamepad1Feedback.runEffectCoordsUpdated();
            } else
                gamepad1Feedback.setColor(0, 1, 0);

            /* WING PARKING */
            if (gamepad1.a)
                autoDrive = 1;

            /* BACKDROP LEFT PARKING */
            if (gamepad1.dpad_left)
                autoDrive = 2;

            /* BACKDROP CENTER PARKING */
            if (gamepad1.dpad_up) {
                autoDrive = 3;
            }

            /* BACKDROP RIGHT PARKING */
            if (gamepad1.dpad_right) {
                autoDrive = 4;
            }

            /* CLOSEST FARM PARKING */
            if (gamepad1.left_bumper) {
                autoDrive = 5;
            }

            switch (autoDrive) {
                case 0:
                    break;
                case 1:
                    if (op_container.blue) {
                        if (Robot.DD.straightGoToTeleop(BLUE_WING, new Pose2d(1, 1, 0.1), auto_calculator, drive, this))
                            autoDrive = 0;
                    } else {
                        if (Robot.DD.straightGoToTeleop(RED_WING, new Pose2d(1, 1, 0.1), auto_calculator, drive, this))
                            autoDrive = 0;
                    }
                    break;
                case 2:
                    if (op_container.blue) {
                        if (Robot.DD.straightGoToTeleop(BLUE_LEFT_DROP, new Pose2d(1, 1, 0.1), auto_calculator, drive, this))
                            autoDrive = 0;
                    } else {
                        if (Robot.DD.straightGoToTeleop(RED_LEFT_DROP, new Pose2d(1, 1, 0.1), auto_calculator, drive, this))
                            autoDrive = 0;
                    }
                    break;
                case 3:
                    if (op_container.blue) {
                        if (Robot.DD.straightGoToTeleop(BLUE_CENTER_DROP, new Pose2d(1, 1, 0.1), auto_calculator, drive, this))
                            autoDrive = 0;
                    } else {
                        if (Robot.DD.straightGoToTeleop(RED_CENTER_DROP, new Pose2d(1, 1, 0.1), auto_calculator, drive, this))
                            autoDrive = 0;
                    }
                    break;
                case 4:
                    if (op_container.blue) {
                        if (Robot.DD.straightGoToTeleop(BLUE_RIGHT_DROP, new Pose2d(1, 1, 0.1), auto_calculator, drive, this))
                            autoDrive = 0;
                    } else {
                        if (Robot.DD.straightGoToTeleop(RED_RIGHT_DROP, new Pose2d(1, 1, 0.1), auto_calculator, drive, this))
                            autoDrive = 0;
                    }
                    break;
                case 5:
                    if (Robot.DD.straightGoToTeleop(tele_auto.findClosestFarm(curPos), new Pose2d(1, 1, 0.1), auto_calculator, drive, this))
                        autoDrive = 0;
                    break;
                default:
                    break;
            }

            /*if (autoDrive != 0)
                gamepad1Feedback.setColor(1, 0, 0);
            else
                gamepad1Feedback.setColor(0, 1, 0);
            */


            /*** END OF AUTO RELOCATION SECTION ***/

            /*** INTAKE CONTROL ***/
            if (gamepad2.x/* || gamepad1.y || gamepad1.b*/ && elevator.target_pos == 0 && flipperPos == 0 && elevator.getNumPixels() <= 2)
                Robot.IN.intake_run();
            else if (gamepad2.right_trigger > 0.5) {
                Robot.IN.intake_run_away();
            } else if (elevator.getNumPixels() > 2 && old_pix <= 2) {
                Robot.IN.intake_run_away();
                intake_run_away.reset();
                intake_run_away_flag = true;
            }
            else if (intake_run_away_flag && intake_run_away.milliseconds() < 500){
                Robot.IN.intake_run_away();
            }
            else if(intake_run_away_flag && intake_run_away.milliseconds() > 500) intake_run_away_flag = false;
            else
                Robot.IN.stopIntakeMotors();
            old_pix = elevator.getNumPixels();
            /*** END OF INTAKE CONTROL ***/

            /*** PLANE CONTROL ***/

            if (gamepad2.right_bumper || gamepad1.right_bumper) {
                Robot.PL.launch();
            }
            else {
                if (gamepad2.right_trigger > 0.5)
                    Robot.PL.prepare();
                /*
                else if (gamepad2.right_trigger > 0.5 && !endgame)
                    gamepad2Feedback.runEffectDisabled();*/
            }

            /** END OF PLANE CONTROL ***/

            /** CHANGE OVER CONTROL ***/

            if (gamepad2.dpad_right) {
                Robot.CO.setPositionHigh();
                flipperPos = 1;
                timer_flipper.reset();
            }

            else if (gamepad2.dpad_left) {
                Robot.CO.setPositionLow();
                flipperPos = 0;
                Robot.CO.setBoxDefault();
            }

            else if (gamepad2.left_bumper) {
                Robot.CO.setHangerPos();
            }
            else if (gamepad2.left_stick_button) {
                Robot.CO.setBoxScoring();
                Robot.CO.setHangerPos();
            }

            if (timer_flipper.milliseconds() > 300 && flipperPos == 1)
                Robot.CO.setBoxScoring();
            /*
            if (gamepad2.left_bumper)
                Robot.CO.setBoxDefault();
            else if (gamepad2.right_bumper)
                Robot.CO.setBoxScoring();
           */

            /*** END OF CHANGE OVER CONTROL ***/

            /*** OUTTAKE CONTROL ***/

            if (gamepad2.x)
                Robot.OT.runLoading();
            else if (gamepad2.b && outtake_flag == false) {
                outtake_flag = true;
                outtake_timer.reset();
            }
            else
                Robot.OT.stop();
            //Robot.OT.checkOuttake();
            if (gamepad2.right_stick_button)
                Robot.CO.setPositionHi();

            if (outtake_flag){
                if (outtake_timer.milliseconds() < 200){
                    Robot.OT.runUnloading();
                    elevator.intake_sensor.num_pixels = 0;
                }
                else{
                    Robot.OT.stop();
                    outtake_flag = false;
                }
            }
            /*** END OF OUTTAKE CONTROL ***/

            /*** LIFT CONTROL ***/
            //lift.applyPower(gamepad2.left_stick_y, telemetry);

            if (currentGamepad2.dpad_up && !PreviousGamepad2.dpad_up)
                elevator.target_pos += 1;
            if (currentGamepad2.dpad_down && !PreviousGamepad2.dpad_down)
                elevator.target_pos -= 1;
            if (currentGamepad2.options && !PreviousGamepad2.options)
                elevator.LI.setZero();
            if (currentGamepad2.share && !PreviousGamepad2.share){
                elevator.target_pos = 0;
            }
            /*telemetry.addData("options", currentGamepad2.options);
            telemetry.addData("guide", currentGamepad2.guide);
            telemetry.addData("share", currentGamepad2.share);
           *//* if (currentGamepad2.left_bumper)
                elevator.target_pos = 0;*/

            if (elevator.target_pos < 0)
                elevator.target_pos = 0;
            if (elevator.target_pos > 3 && elevator.target_pos != 10)
                elevator.target_pos = 3;


            telemetry.addData("current position left:", elevator.LI.getPos());
            telemetry.addData("lift position:", elevator.target_pos);
            telemetry.update();

            /*** END OF LIFT CONTROL ***/

            /*** HANGER CONTROL ***/
//            lift.applyPower(gamepad2.left_stick_y, telemetry);
            if (currentGamepad2.left_stick_button && elevator.target_pos != 10 && !PreviousGamepad2.left_stick_button) {
                elevator.target_pos = 10;
                Robot.CO.setHangerPos();
            }
            if (gamepad2.left_stick_button && elevator.target_pos == 10 && !PreviousGamepad2.left_stick_button)
                elevator.target_pos = 1;

            /*** END OF HANGER CONTROL ***/

            /*** FINGER CONTROL ***/
            if (gamepad2.a)
                Robot.FN.prepare();

            else if (gamepad2.y)
                Robot.FN.drop();

            /*** UTILITY ***/
            if (timer.milliseconds() > 120000 && !endgame) /* endgame started */
            {
                gamepad1Feedback.runEffectEndgameStart();
                gamepad2Feedback.runEffectEndgameStart();
                endgame = true;
            }
            telemetry.addData("encoder_odo_new", Robot.IN.motor1.getCurrentPosition());
            telemetry.addData("encoder_odo_perp_old", Robot.DD.leftModule.downMotor.getCurrentPosition());
            telemetry.addData("encoder_odo_paral", Robot.DD.rightModule.downMotor.getCurrentPosition());

            //telemetry.update();
        }
        elevator.interrupt();
    }
}