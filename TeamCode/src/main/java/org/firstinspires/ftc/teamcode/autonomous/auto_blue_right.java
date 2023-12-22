package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.maths.vec2;

@Autonomous(name = "auto_blue_right")
public class auto_blue_right extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";

    boolean isParked = false;

    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Robot.init(hardwareMap, telemetry, this, "Blue");
        telemetry.addData("", "Init completed");
        telemetry.update();

        drive.setPoseEstimate(auto_constants.BLUE_RIGHT_START);

        Pose2d myPose = drive.getPoseEstimate();
        Pose2d targetPose;
        Pose2d relocation;
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
                targetPose = auto_constants.BLUE_RIGHT_CENTER_SPIKE;
            else if (prop_pos.equals("Right"))
                targetPose = auto_constants.BLUE_RIGHT_RIGHT_SPIKE;
            else
                targetPose = auto_constants.BLUE_RIGHT_LEFT_SPIKE;

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
            while (timer.milliseconds() <= 1500 && opModeIsActive()); /* drop purple pixel here */

            while(opModeIsActive())
                Robot.DD.applySpeed(new vec2(0), 0, telemetry);

        }
    }
}
