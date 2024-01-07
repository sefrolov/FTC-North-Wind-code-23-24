package org.firstinspires.ftc.teamcode.test_different_stuff;

import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_CENTER_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_RIGHT_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_WING;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_CENTER_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_RIGHT_DROP;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_WING;
import static java.lang.Math.PI;
import static java.lang.Math.abs;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CVision.AT_Detector;
import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.autonomous.auto_PID;
import org.firstinspires.ftc.teamcode.diff_sverwe.PID_system;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.maths.vec2;
import org.firstinspires.ftc.teamcode.tele_movement.op_container;
import org.firstinspires.ftc.teamcode.tele_movement.tele_auto;

@TeleOp(name = "at_localization_test", group = "Test")
public class at_localization_test extends LinearOpMode {
    RobotNW Robot = new RobotNW();

    Pose2d curPos;
    double heading;
    Vector2d atPos;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(new Pose2d(-36, -36, Math.toRadians(90)));

        telemetry.addData("", "init succesfully");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            /*** ODOMETRY SECTION ***/
            drive.update();
            // Retrieve your pose
            curPos = drive.getPoseEstimate();
            telemetry.addData("drive pos X:", curPos.getX());
            telemetry.addData("drive pos Y:", curPos.getY());
            heading = curPos.getHeading();
            atPos = Robot.AT.getRobotPos(heading);
            if (atPos != null) {
                telemetry.addData("AT pos X:", atPos.getX());
                telemetry.addData("AT pos Y:", atPos.getY());
            }
            telemetry.update();
        }
    }
}
