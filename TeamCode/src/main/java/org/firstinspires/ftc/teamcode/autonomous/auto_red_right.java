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

@Autonomous(name = "auto_red_right")
public class auto_red_right extends LinearOpMode {
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

        Robot.BD.StopStreaming();
        if (prop_pos.equals("Center"))
            targetPose = auto_constants.RED_RIGHT_CENTER_SPIKE;
        else if (prop_pos.equals("Right"))
            targetPose = auto_constants.RED_RIGHT_RIGHT_SPIKE;
        else
        {
            targetPose = auto_constants.RED_RIGHT_LEFT_SPIKE_WAYPOINT;
            calculator.init(targetPose, myPose);
            errors = new Pose2d(5, 5, 1);
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
            Robot.DD.applySpeed(new vec2(0), 0, telemetry);
            targetPose = auto_constants.RED_RIGHT_LEFT_SPIKE;
        }

        calculator.init(targetPose, myPose);

        errors = new Pose2d(1, 1, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);

        Robot.FN.drop();

        elevator.target_pos = 4;
        Robot.CO.setPositionHigh();
        Robot.CO.setBoxScoring();

        targetPose = auto_constants.RED_BEFORE_DROPS;
        calculator.init(targetPose, myPose);
        errors = new Pose2d(2, 2, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        if (prop_pos.equals("Center"))
        {
            /*targetPose = auto_constants.RED_RIGHT_CENTER_ADDITIONAL;
            calculator.init(targetPose, myPose);
            errors = new Pose2d(5, 5, 1);
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);*/
            targetPose = auto_constants.RED_CENTER_DROP;
        }
        else if (prop_pos.equals("Right"))
            targetPose = auto_constants.RED_RIGHT_DROP;
        else
            targetPose = auto_constants.RED_LEFT_DROP;

        calculator.reset(targetPose, myPose);
        errors = new Pose2d(1, 1, 0.05);
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);

        sleep(600);
        Robot.OT.runUnloading();
        timer.reset();
        while(timer.milliseconds() < 800 && opModeIsActive()) {
            telemetry.addData("adaaaa", "+");
            telemetry.update();
        }
        Robot.OT.stop();

        Robot.CO.setPositionLow();
        Robot.CO.setBoxDefault();
        elevator.target_pos = 0;

        targetPose = auto_constants.RED_BEFORE_DROPS;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(2, 2, 0.3);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        targetPose = auto_constants.RED_FINAL_ZONE;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(2, 2, 0.3);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        elevator.target_pos = 0;
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);
        Robot.DD.setWheelsDefault();
        //Robot.CO.setPositionLow();
        Robot.DD.stopDrivetrain();
        //elevator.target_pos = 0;

        timer.reset();
        while (!isStopRequested() && timer.milliseconds() < 2000) drive.update();
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
