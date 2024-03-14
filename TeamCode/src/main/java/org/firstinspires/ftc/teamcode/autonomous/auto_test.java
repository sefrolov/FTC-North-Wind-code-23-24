package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.lift.elevator_thread;
import org.firstinspires.ftc.teamcode.maths.vec2;
import org.firstinspires.ftc.teamcode.tele_movement.op_container;

@Autonomous
public class auto_test extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";

    elevator_thread elevator = new elevator_thread();
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Robot.init(hardwareMap, telemetry, this, "Blue", "false");
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

        op_container.blue = true;

        elevator.init(hardwareMap, telemetry);
        elevator.start();

        waitForStart();

        //Robot.servo.setPosition(0.16);

        targetPose = new Pose2d(-42, 15, Math.toRadians(180));

        calculator.init(targetPose, myPose);
        elevator.target_pos = 2;
        Robot.CO.setPositionHigh();
        Robot.CO.setBoxScoring();

        errors = new Pose2d(1, 1, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);

        Robot.OT.runUnloading();
        timer.reset();
        while(timer.milliseconds() < 3800 && opModeIsActive()) {
            telemetry.addData("adaaaa", "+");
            telemetry.update();
        }
        Robot.OT.stop();

        Robot.CO.setPositionLow();
        Robot.CO.setBoxDefault();
        elevator.target_pos = 0;
/*
        targetPose = new Pose2d(-40, 20, Math.toRadians(180));
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(1, 1, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);

*/

        /*

        targetPose = auto_constants.BLUE_BEFORE_DROPS;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(2, 2, 0.3);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        targetPose = auto_constants.BLUE_FINAL_ZONE_WALL;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(2, 2, 0.3);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        */
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);
        Robot.DD.setWheelsDefault();
        //Robot.CO.setPositionLow();
        Robot.DD.stopDrivetrain();
        //elevator.target_pos = 0;
        timer.reset();
        while (!isStopRequested() && timer.milliseconds() < 3000) drive.update();
        elevator.interrupt();
        /* update once if autonomous ended by timer */
        drive.update();
        op_container.transferData(drive.getPoseEstimate(), Robot.DD.leftModule.upMotor.getCurrentPosition(), Robot.DD.rightModule.upMotor.getCurrentPosition(), elevator.LI.getPos());
        while (opModeIsActive()){
            drive.update();
            op_container.transferData(drive.getPoseEstimate(), Robot.DD.leftModule.upMotor.getCurrentPosition(), Robot.DD.rightModule.upMotor.getCurrentPosition(), elevator.LI.getPos());
        }
    }
}
