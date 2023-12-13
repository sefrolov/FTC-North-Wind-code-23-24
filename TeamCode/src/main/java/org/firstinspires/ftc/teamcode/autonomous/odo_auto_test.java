package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.maths.vec2;

@Autonomous(name = "odo_auto_test")
public class odo_auto_test extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";

    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Robot.init(hardwareMap, telemetry, this, "Blue");
        telemetry.addData("", "Init complited");
        telemetry.update();
        CommonAutonomousActions comAct = new CommonAutonomousActions(Robot, timer, this, drive);

        drive.setPoseEstimate(new Pose2d(-60, 12, Math.toRadians(180)));

        Pose2d myPose;
        Pose2d targetPose;

        while (!isStarted()) {
            telemetry.addData("", "not started");
            prop_pos = Robot.BD.getPosition(telemetry);
            telemetry.update();
        }

        waitForStart();

        //Robot.servo.setPosition(0.16);

        while (opModeIsActive()) {
            targetPose = new Pose2d(-50, 12, Math.toRadians(180));
            myPose = drive.getPoseEstimate();

            while (Math.abs(targetPose.getX() - myPose.getX()) >= 10 || Math.abs(targetPose.getY() - myPose.getY()) >= 10 || Math.abs(targetPose.getHeading() - myPose.getHeading()) >= 0.3) {
                drive.update();
                comAct.goTo(targetPose.getX(), targetPose.getY(), targetPose.getHeading(), 0.05);
                myPose = drive.getPoseEstimate();
            }
            Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);
            
            while (timer.milliseconds() < 30000)
                ;

        }
    }
}