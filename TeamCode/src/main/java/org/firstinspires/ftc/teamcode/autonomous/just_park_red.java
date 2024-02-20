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

@Autonomous(name = "just_park_red")
public class just_park_red extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";
    elevator_thread elevator = new elevator_thread();

    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Robot.init(hardwareMap, telemetry, this, "Red");
        telemetry.addData("", "Init completed");
        telemetry.update();

        drive.setPoseEstimate(auto_constants.RED_RIGHT_START);

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

        op_container.blue = false;
        elevator.init(hardwareMap, telemetry);
        elevator.start();

        waitForStart();

        //Robot.servo.setPosition(0.16);

        sleep(24000);
        targetPose = new Pose2d(60, 55, Math.toRadians(0));
        calculator.init(targetPose, myPose);
        errors = new Pose2d(1, 1, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);
        Robot.DD.setWheelsDefault();
        Robot.DD.stopDrivetrain();
        /* update once if autonomous ended by timer */
        drive.update();
        op_container.transferData(drive.getPoseEstimate(), Robot.DD.leftModule.upMotor.getCurrentPosition(), Robot.DD.rightModule.upMotor.getCurrentPosition(), elevator.LI.getPos());
        while (opModeIsActive()){
            drive.update();
            op_container.transferData(drive.getPoseEstimate(), Robot.DD.leftModule.upMotor.getCurrentPosition(), Robot.DD.rightModule.upMotor.getCurrentPosition(), elevator.LI.getPos());
        }
    }
}
