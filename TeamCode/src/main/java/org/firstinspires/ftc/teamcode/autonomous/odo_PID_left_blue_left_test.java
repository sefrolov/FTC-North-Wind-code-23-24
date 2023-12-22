package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.maths.vec2;
import org.opencv.core.Mat;

@Autonomous(name = "PID_LBL")
public class odo_PID_left_blue_left_test extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";

    int i = 0;
    boolean isParked = false;

    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Robot.init(hardwareMap, telemetry, this, "Blue");
        telemetry.addData("", "Init complited");
        telemetry.update();
        CommonAutonomousActions comAct = new CommonAutonomousActions(Robot, timer, this, drive);

        drive.setPoseEstimate(new Pose2d(-63.38, 15.14, Math.toRadians(180)));

        Pose2d myPose = drive.getPoseEstimate();
        Pose2d targetPose = auto_constants.BLUE_LEFT_LEFT_SPIKE;
        Pose2d relocation = new Pose2d(0, 0, 0);
        auto_PID calculator = new auto_PID();

        while (!isStarted()) {
            telemetry.addData("", "not started");
            prop_pos = Robot.BD.getPosition(telemetry);
            if (prop_pos.equals("Center"))
                Robot.BD.restart_streaming(hardwareMap);
            telemetry.addData("pos:", prop_pos);
            telemetry.update();
        }

        waitForStart();

        //Robot.servo.setPosition(0.16);

        while (opModeIsActive()) {
            Robot.BD.StopStreaming();
            if (prop_pos.equals("Center"))
                targetPose = auto_constants.BLUE_LEFT_CENTER_SPIKE;
            else if (prop_pos.equals("Right"))
                targetPose = auto_constants.BLUE_LEFT_RIGHT_SPIKE;
            else
                targetPose = auto_constants.BLUE_LEFT_LEFT_SPIKE;

            calculator.init(targetPose.getX() - myPose.getX(), targetPose.getY() - myPose.getY(), targetPose.getHeading() - myPose.getHeading());

            while (!isParked && opModeIsActive()){
                drive.update();
                myPose = drive.getPoseEstimate();
                relocation = calculator.calculate_speeds(targetPose, myPose, 1);
                telemetry.addData("SpeedX:", calculator.speedX);
                telemetry.addData("SpeedY:", calculator.speedY);
                telemetry.addData("Rotation:", calculator.rotation);
                telemetry.addData("IsParked:", isParked);
                telemetry.update();
                if (Math.abs(calculator.errorX) <= 1 && Math.abs(calculator.errorY) <= 1 && Math.abs(calculator.errorHeading) <= 0.1)
                    isParked = true;
                Robot.DD.applySpeedFieldCentric(new vec2(relocation.getX(), relocation.getY()), relocation.getHeading(), myPose.getHeading());
            }
            Robot.DD.applySpeed(new vec2(0), 0, telemetry);
            telemetry.addData("IsParked:", isParked);
            telemetry.update();

            timer.reset();
            while (timer.milliseconds() <= 1500 && opModeIsActive());

            if (prop_pos.equals("Center"))
                targetPose = auto_constants.BLUE_LEFT_CENTER_DROP;
            else if (prop_pos.equals("Right"))
                targetPose = auto_constants.BLUE_LEFT_RIGHT_DROP;
            else
                targetPose = auto_constants.BLUE_LEFT_LEFT_DROP;

            calculator.reset(targetPose.getX() - myPose.getX(), targetPose.getY() - myPose.getY(), targetPose.getHeading() - myPose.getHeading());
            isParked = false;

            while (!isParked && opModeIsActive()){
                drive.update();
                myPose = drive.getPoseEstimate();
                relocation = calculator.calculate_speeds(targetPose, myPose, 1);
                telemetry.addData("SpeedX:", calculator.speedX);
                telemetry.addData("SpeedY:", calculator.speedY);
                telemetry.addData("Rotation:", calculator.rotation);
                telemetry.addData("IsParked:", isParked);
                telemetry.update();
                if (Math.abs(calculator.errorX) <= 1 && Math.abs(calculator.errorY) <= 1 && Math.abs(calculator.errorHeading) <= 0.1)
                    isParked = true;
                Robot.DD.applySpeedFieldCentric(new vec2(relocation.getX(), relocation.getY()), relocation.getHeading(), myPose.getHeading());
            }
            Robot.DD.applySpeed(new vec2(0), 0, telemetry);
            telemetry.addData("IsParked:", isParked);
            telemetry.update();

            timer.reset();
            while (timer.milliseconds() <= 1500 && opModeIsActive());

            targetPose = auto_constants.BLUE_LEFT_FINAL_ZONE;
            calculator.reset(targetPose.getX() - myPose.getX(), targetPose.getY() - myPose.getY(), targetPose.getHeading() - myPose.getHeading());
            isParked = false;

            while (!isParked && opModeIsActive()){
                drive.update();
                myPose = drive.getPoseEstimate();
                relocation = calculator.calculate_speeds(targetPose, myPose, 1);
                telemetry.addData("SpeedX:", calculator.speedX);
                telemetry.addData("SpeedY:", calculator.speedY);
                telemetry.addData("Rotation:", calculator.rotation);
                telemetry.addData("IsParked:", isParked);
                telemetry.update();
                if (Math.abs(calculator.errorX) <= 1 && Math.abs(calculator.errorY) <= 1 && Math.abs(calculator.errorHeading) <= 0.1)
                    isParked = true;
                Robot.DD.applySpeedFieldCentric(new vec2(relocation.getX(), relocation.getY()), relocation.getHeading(), myPose.getHeading());
            }
            Robot.DD.applySpeed(new vec2(0), 0, telemetry);
            telemetry.addData("IsParked:", isParked);
            telemetry.update();
            while(opModeIsActive())
                Robot.DD.applySpeed(new vec2(0), 0, telemetry);

        }
    }
}
