package org.firstinspires.ftc.teamcode.test_different_stuff;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.TwoTrackingWheelLocalizer;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;
import org.firstinspires.ftc.teamcode.drive.TwoWheelTrackingLocalizer;

import java.util.List;

@TeleOp(name = "odo_test_roadrunner")
@Disabled
public class odo_test_roadrunner extends LinearOpMode {

    RobotNW Robot = new RobotNW();

    @Override
    public void runOpMode() {
        // Insert whatever initialization your own code does
        Robot.init(hardwareMap, telemetry, this);
        // This is assuming you're using StandardTrackingWheelLocalizer.java
        // Switch this class to something else (Like TwoWheeTrackingLocalizer.java) if your configuration is different
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        TwoWheelTrackingLocalizer myLocalizer = new TwoWheelTrackingLocalizer(hardwareMap, /*Robot.IM*/ drive);
        myLocalizer.setPoseEstimate(new Pose2d(0, 48, Math.toRadians(270)));

        waitForStart();

        while(opModeIsActive()) {
            // Make sure to call myLocalizer.update() on *every* loop
            // Increasing loop time by utilizing bulk reads and minimizing writes will increase your odometry accuracy
            //myLocalizer.update();
            drive.update();
            // Retrieve your pose
            Pose2d myPose = /*myLocalizer*/drive.getPoseEstimate();

            telemetry.addData("x", myPose.getX());
            telemetry.addData("y", myPose.getY());
            telemetry.addData("heading", myPose.getHeading());
            telemetry.update();

            // Insert whatever teleop code you're using
        }
    }
}
