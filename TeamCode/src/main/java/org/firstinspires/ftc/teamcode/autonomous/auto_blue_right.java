package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.intake.sensors_thread;
import org.firstinspires.ftc.teamcode.lift.elevator_thread;
import org.firstinspires.ftc.teamcode.maths.vec2;
import org.firstinspires.ftc.teamcode.tele_movement.op_container;

@Autonomous(name = "auto_blue_right")
public class auto_blue_right extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";

    elevator_thread elevator = new elevator_thread();
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Robot.init(hardwareMap, telemetry, this, "Blue");
        telemetry.addData("", "Init completed");
        telemetry.update();

        drive.setPoseEstimate(auto_constants.BLUE_RIGHT_START);

        Pose2d myPose = drive.getPoseEstimate();
        Pose2d targetPose;
        Pose2d errors;
        auto_PID calculator = new auto_PID();

        timer.reset();
        while (!isStarted()) {
            telemetry.addData("", "not started");
                prop_pos = Robot.BD.getPosition(telemetry);
                timer.reset();
                telemetry.addData("pos:", prop_pos);
            telemetry.update();
        }

        op_container.blue = true;

        elevator.init(hardwareMap, telemetry);
        elevator.start();

        waitForStart();

        //Robot.servo.setPosition(0.16);

        Robot.BD.StopStreaming();
        if (prop_pos.equals("Center"))
            targetPose = auto_constants.BLUE_RIGHT_CENTER_SPIKE;
        else if (prop_pos.equals("Left"))
        {
            targetPose = auto_constants.BLUE_RIGHT_LEFT_SPIKE_WAYPOINT;
            calculator.reset(targetPose, myPose);
            errors = new Pose2d(5, 5, 1);
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
            targetPose = auto_constants.BLUE_RIGHT_LEFT_SPIKE;
        }
        else
            targetPose = auto_constants.BLUE_RIGHT_RIGHT_SPIKE;

        calculator.reset(targetPose, myPose);

        errors = new Pose2d(1, 1, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);

        Robot.FN.drop();

        targetPose = auto_constants.BLUE_DOBOR_ADDITIONAL;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(2, 1, 0.2);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        targetPose = auto_constants.BLUE_DOBOR_FIRST;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(1, 1, 0.2);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        Robot.IN.intake_run();
        Robot.OT.runLoading();

        double i = 0;
        while(elevator.getNumPixels() < 1 && i < 3 && opModeIsActive())
        {
            targetPose = auto_constants.BLUE_DOBOR_BACK;
            calculator.reset(targetPose, myPose);
            errors = new Pose2d(1, 1, 0.1);
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

            if (elevator.getNumPixels() < 1) {
                targetPose = auto_constants.BLUE_DOBOR;
                calculator.reset(targetPose, myPose);
                errors = new Pose2d(2, 2, 0.1);
                Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
                i++;
            }
            Robot.DD.stopDrivetrain();
            sleep(300);
        }
        sleep(600);
        Robot.OT.stop();
        Robot.IN.intake_run_away_auto();

        targetPose = auto_constants.UNDER_SCENE_BACK_BLUE;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(1, 3, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        targetPose = auto_constants.UNDER_SCENE_MID_BLUE;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(1, 3, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        targetPose = auto_constants.UNDER_SCENE_FRONT_BLUE;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(1, 3, 0.1);
        Robot.DD.straightGoToNoSlow(targetPose, errors, calculator, drive, this, true);

        /*
        Pose2d curPos = drive.getPoseEstimate();
        if (Math.abs(curPos.getHeading() - targetPose.getHeading()) > errors.getHeading()) { //crashed in farm
            targetPose = new Pose2d(-9, curPos.getY(), Math.toRadians(270));
            calculator.reset(targetPose, myPose);
            errors = new Pose2d(2, 2, 0.3);
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        }
        */

        targetPose = auto_constants.BLUE_BEFORE_DROPS;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(1, 0.5, 0.05);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);

        /*
        Pose2d curPos = drive.getPoseEstimate();
        Vector2d vec;
        if ((vec = Robot.AT.getRobotPos(drive.getPoseEstimate().getHeading())) != null){
            vec.plus(new Vector2d(curPos.getX(), curPos.getY()));
            Pose2d pose = new Pose2d(vec.div(2), drive.getPoseEstimate().getHeading());

            drive.setPoseEstimate(pose);
        }
        */

        Robot.IN.stopIntakeMotors();

        elevator.target_pos = 4;
        Robot.CO.setPositionHigh();
        Robot.CO.setBoxScoring();

        if (prop_pos.equals("Center"))
            targetPose = auto_constants.BLUE_RIGHT_DROP;
        else if (prop_pos.equals("Right"))
            targetPose = auto_constants.BLUE_CENTER_DROP;
        else
            targetPose = auto_constants.BLUE_CENTER_DROP;

        calculator.reset(targetPose, myPose);
        errors = new Pose2d(1, 1, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);

        Robot.OT.runUnloading();
        sleep(120);
        Robot.OT.stop();

        targetPose = auto_constants.BLUE_BEFORE_DROPS;

        calculator.reset(targetPose, myPose);
        errors = new Pose2d(1, 1, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        if (prop_pos.equals("Center"))
            targetPose = auto_constants.BLUE_CENTER_DROP;
        else if (prop_pos.equals("Right"))
            targetPose = auto_constants.BLUE_RIGHT_DROP;
        else
            targetPose = auto_constants.BLUE_LEFT_DROP;

        calculator.reset(targetPose, myPose);
        errors = new Pose2d(1, 1, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);

        Robot.OT.runUnloading();
        sleep(600);
        Robot.OT.stop();

        Robot.CO.setPositionLow();
        Robot.CO.setBoxDefault();


        targetPose = auto_constants.BLUE_FINAL_TMP;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(2, 2, 0.3);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);

        elevator.target_pos = 0;
        Robot.DD.setWheelsDefault();
        Robot.CO.setPositionLow();
        Robot.DD.stopDrivetrain();
        drive.update();
        op_container.transferData(drive.getPoseEstimate(), Robot.DD.leftModule.upMotor.getCurrentPosition(), Robot.DD.rightModule.upMotor.getCurrentPosition(), elevator.LI.getPos());
        while (opModeIsActive()){
            drive.update();
            op_container.transferData(drive.getPoseEstimate(), Robot.DD.leftModule.upMotor.getCurrentPosition(), Robot.DD.rightModule.upMotor.getCurrentPosition(), elevator.LI.getPos());
        }
    }
}
