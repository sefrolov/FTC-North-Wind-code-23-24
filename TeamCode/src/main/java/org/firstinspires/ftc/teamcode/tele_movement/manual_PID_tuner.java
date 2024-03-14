package org.firstinspires.ftc.teamcode.tele_movement;

import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.BLUE_WING;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_WING;
import static java.lang.Math.PI;
import static java.lang.Math.abs;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.autonomous.auto_PID;
import org.firstinspires.ftc.teamcode.diff_sverwe.PID_system;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.maths.vec2;

import java.lang.annotation.Target;

@TeleOp(name = "manual_PID_tuner")
@Disabled
public class manual_PID_tuner extends LinearOpMode {
    RobotNW Robot = new RobotNW();

    auto_PID calculator = new auto_PID();
    Pose2d targetPose = location_constants.MPT_2;
    Pose2d curPose = location_constants.MPT_1;
    Pose2d errors = new Pose2d(0.4, 0.4, 0.1);
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(curPose);

        telemetry.addData("", "init succesfully");
        telemetry.update();

        calculator.reset(targetPose, curPose);

        waitForStart();

        while (opModeIsActive()) {
            /*** AUTO RELOCATION SECTION ***/

            targetPose = location_constants.MPT_2;
            gamepad1.setLedColor(1, 0, 0, 300000);
            calculator.reset(targetPose, drive.getPoseEstimate());
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

            timer.reset();
            while (timer.milliseconds() < 2000);

            targetPose = location_constants.MPT_1;
            gamepad1.setLedColor(0, 0, 1, 300000);
            calculator.reset(targetPose, drive.getPoseEstimate());
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

            timer.reset();
            while (timer.milliseconds() < 2000);

            if (gamepad1.a && !gamepad1.left_bumper)
                calculator.incrkPtrans();
            if (gamepad1.b && !gamepad1.left_bumper)
                calculator.incrkItrans();
            if (gamepad1.x && !gamepad1.left_bumper)
                calculator.incrkDtrans();
            if (gamepad1.dpad_down && !gamepad1.left_bumper)
                calculator.incrkPheading();
            if (gamepad1.dpad_right && !gamepad1.left_bumper)
                calculator.incrkIheading();
            if (gamepad1.dpad_left && !gamepad1.left_bumper)
                calculator.incrkDheading();

            if (gamepad1.a && gamepad1.left_bumper)
                calculator.decrkPtrans();
            if (gamepad1.b && gamepad1.left_bumper)
                calculator.decrkItrans();
            if (gamepad1.x && gamepad1.left_bumper)
                calculator.decrkDtrans();
            if (gamepad1.dpad_down && gamepad1.left_bumper)
                calculator.decrkPheading();
            if (gamepad1.dpad_right && gamepad1.left_bumper)
                calculator.decrkIheading();
            if (gamepad1.dpad_left && gamepad1.left_bumper)
                calculator.decrkDheading();

            telemetry.addData("kPheading", calculator.kPHeading);
            telemetry.addData("kDheading", calculator.kDHeading);
            telemetry.addData("kIheading", calculator.kIHeading);
            telemetry.addData("kPtrans", calculator.kPtrans);
            telemetry.addData("kDtrans", calculator.kDtrans);
            telemetry.addData("kItrans", calculator.kItrans);
            telemetry.update();
        }
    }
}
