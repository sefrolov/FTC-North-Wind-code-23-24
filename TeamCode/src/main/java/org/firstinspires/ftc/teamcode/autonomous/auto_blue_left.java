package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.maths.vec2;

@Autonomous(name = "auto_blue_left")
public class auto_blue_left extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";

    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Robot.init(hardwareMap, telemetry, this, "Blue");
        telemetry.addData("", "Init completed");
        telemetry.update();

        drive.setPoseEstimate(auto_constants.BLUE_LEFT_START);

        Pose2d myPose = drive.getPoseEstimate();
        Pose2d targetPose;
        Pose2d errors;
        auto_PID calculator = new auto_PID();

        while (!isStarted()) {
            telemetry.addData("", "not started");
            prop_pos = Robot.BD.getPosition(telemetry);
            telemetry.addData("pos:", prop_pos);
            telemetry.update();
        }

        waitForStart();

        //Robot.servo.setPosition(0.16);

        Robot.BD.StopStreaming();
        if (prop_pos.equals("Center"))
            targetPose = auto_constants.BLUE_LEFT_CENTER_SPIKE;
        else if (prop_pos.equals("Right"))
        {
            targetPose = auto_constants.BLUE_LEFT_RIGHT_SPIKE_WAYPOINT;
            calculator.init(targetPose.getX() - myPose.getX(), targetPose.getY() - myPose.getY(), targetPose.getHeading() - myPose.getHeading());
            errors = new Pose2d(5, 5, 1);
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
            targetPose = auto_constants.BLUE_LEFT_RIGHT_SPIKE;
        }
        else
            targetPose = auto_constants.BLUE_LEFT_LEFT_SPIKE;

        calculator.init(targetPose.getX() - myPose.getX(), targetPose.getY() - myPose.getY(), targetPose.getHeading() - myPose.getHeading());

        errors = new Pose2d(0.4, 0.4, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        timer.reset();
        while(timer.milliseconds() < 325 && opModeIsActive()) {
            Robot.IN.unloadPixel();
            telemetry.addData("adaaaa", "+");
            telemetry.update();
        }
        Robot.IN.stopIntakeMotors();
        Robot.CO.setPositionHigh();

        if (prop_pos.equals("Center"))
            targetPose = auto_constants.BLUE_CENTER_DROP;
        else if (prop_pos.equals("Right"))
            targetPose = auto_constants.BLUE_RIGHT_DROP;
        else
            targetPose = auto_constants.BLUE_LEFT_DROP;

        calculator.reset(targetPose.getX() - myPose.getX(), targetPose.getY() - myPose.getY(), targetPose.getHeading() - myPose.getHeading());
        errors = new Pose2d(0.8, 0.1, 0.05);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        Robot.OT.runUnloading();
        timer.reset();
        while(timer.milliseconds() < 800 && opModeIsActive()) {
            telemetry.addData("adaaaa", "+");
            telemetry.update();
        }
        Robot.OT.stop();

        targetPose = auto_constants.BLUE_FINAL_ZONE;
        calculator.reset(targetPose.getX() - myPose.getX(), targetPose.getY() - myPose.getY(), targetPose.getHeading() - myPose.getHeading());
        errors = new Pose2d(0.2, 1, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.setWheelsDefault();
        Robot.CO.setPositionLow();
    }
}
