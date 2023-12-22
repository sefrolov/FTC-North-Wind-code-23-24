package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.maths.vec2;
import org.opencv.core.Mat;

@Autonomous(name = "test_min_values")
@Disabled
public class test_min_values extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";

    int i = 0;
    double turn_spd = 0;
    double trg_rot = 0;
    double koef_turn = 10;

    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Robot.init(hardwareMap, telemetry, this, "Blue");
        telemetry.addData("", "Init complited");
        telemetry.update();
        CommonAutonomousActions comAct = new CommonAutonomousActions(Robot, timer, this, drive);

        drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));

        Pose2d myPose;
        Pose2d targetPose;
        double speed;
        vec2 relocation = new vec2(0);

        while (!isStarted()) {
            telemetry.addData("", "not started");
            prop_pos = Robot.BD.getPosition(telemetry);
            telemetry.update();
        }

        waitForStart();

        //Robot.servo.setPosition(0.16);

        while (opModeIsActive()) {
            drive.update();
            timer.reset();
            while (timer.milliseconds() < 10000 && opModeIsActive()) {
                drive.update();
                Robot.DD.applySpeedFieldCentric(new vec2(0.05, 0), 0, drive.getPoseEstimate().getHeading());
            }
            Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);
            while(!isStopRequested());
        }
    }
}