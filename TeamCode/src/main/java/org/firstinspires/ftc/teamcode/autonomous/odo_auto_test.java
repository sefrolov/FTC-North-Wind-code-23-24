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

@Autonomous(name = "odo_auto_test")
@Disabled
public class odo_auto_test extends LinearOpMode {
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

        drive.setPoseEstimate(new Pose2d(-63.38, 15.14, Math.toRadians(180)));

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
            /*
            targetPose = new Pose2d(0, 0, Math.toRadians(90));
            myPose = drive.getPoseEstimate();

            while (Math.abs(targetPose.getX() - myPose.getX()) >= 10 || Math.abs(targetPose.getY() - myPose.getY()) >= 10 || Math.abs(targetPose.getHeading() - myPose.getHeading()) >= 0.3) {
                drive.update();
                myPose = drive.getPoseEstimate();
                comAct.goTo(targetPose.getX(), targetPose.getY(), targetPose.getHeading(), 0.05);
                System.out.println("here");
            }
            Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);
            */

            /*
            while (Math.abs(drive.getPoseEstimate().getX() - 48) >= 5)
            {
                drive.update();
                comAct.goTo(48, 0, 0,0.3);
            }
            */


            drive.update();
            targetPose = new Pose2d(-30.93, 35.2, Math.toRadians(270));
            myPose = drive.getPoseEstimate();
            speed = 0.5;
            while ((Math.abs(myPose.getX() - targetPose.getX()) >= 1.6 || Math.abs(myPose.getY() - targetPose.getY()) >= 1.6 || Math.abs(myPose.getHeading() - targetPose.getHeading()) >= 0.1) && opModeIsActive())
            {
                drive.update();
                myPose = drive.getPoseEstimate();
                relocation.set(targetPose.getX() - myPose.getX(), targetPose.getY() - myPose.getY());
                i++;
                comAct.goTo(targetPose.getX(), targetPose.getY(), targetPose.getHeading(), relocation.len() * relocation.len() / 30);
                telemetry.addData("x:", myPose.getX());
                telemetry.addData("y:", myPose.getY());
                telemetry.addData("heading:", myPose.getHeading());
                telemetry.update();
            }

            timer.reset();
            while (timer.milliseconds() < 2000)
                Robot.DD.applySpeed(new vec2(0), 0, telemetry);

            telemetry.addData("Here", 0);
            telemetry.update();

            drive.update();
            targetPose = new Pose2d(-46, 49.5, Math.toRadians(270));
            myPose = drive.getPoseEstimate();

            while ((Math.abs(myPose.getX() - targetPose.getX()) >= 1.6 || Math.abs(myPose.getY() - targetPose.getY()) >= 1.6 || Math.abs(myPose.getHeading() - targetPose.getHeading()) >= 0.1) && opModeIsActive())
            {
                drive.update();
                myPose = drive.getPoseEstimate();
                relocation.set(targetPose.getX() - myPose.getX(), targetPose.getY() - myPose.getY());
                i++;
                comAct.goTo(targetPose.getX(), targetPose.getY(), targetPose.getHeading(), relocation.len() / 14);
                telemetry.addData("x:", myPose.getX());
                telemetry.addData("y:", myPose.getY());
                telemetry.addData("heading:", myPose.getHeading());
                telemetry.update();
            }

            timer.reset();
            while (timer.milliseconds() < 500)
                Robot.DD.applySpeed(new vec2(0), 0, telemetry);

            requestOpModeStop();
            /*
            while (timer.milliseconds() < 3000)
            {
                drive.update();
                i++;

                trg_rot = Math.toRadians(270) - drive.getPoseEstimate().getHeading();
                if (Math.abs(trg_rot) > Math.PI + 1)
                    turn_spd = -trg_rot;
                else
                    turn_spd = trg_rot;

                koef_turn = 1 / (30 - drive.getPoseEstimate().getX()) * 100;

                Robot.DD.applySpeedFieldCentric(new vec2((30 - drive.getPoseEstimate().getX()) / 130, (20 - drive.getPoseEstimate().getY()) / 130), trg_rot / koef_turn, drive.getPoseEstimate().getHeading());
            }
            */
        }
    }
}