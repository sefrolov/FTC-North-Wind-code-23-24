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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Autonomous(name = "auto_blue_left")
public class auto_blue_left extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";
    Pose2d parking_zone = auto_constants.BLUE_FINAL_ZONE_WALL;
    float delay = 0;
    elevator_thread elevator = new elevator_thread();
    BufferedReader reader;

    {
        try {
            FileReader fr = new FileReader("configs/config.txt");
            reader = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void readConfig(){
        try {
            String zone = reader.readLine();
            if (zone.equals("wall"))
                parking_zone = auto_constants.BLUE_FINAL_ZONE_WALL;
            if (zone.equals("center"))
                parking_zone = auto_constants.BLUE_FINAL_ZONE_CENTER;
            delay = Float.parseFloat(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

        readConfig();

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
        Robot.BD.StopStreaming();

        timer.reset();
        while(timer.milliseconds() < delay)
            ;

        if (prop_pos.equals("Center"))
            targetPose = auto_constants.BLUE_LEFT_CENTER_SPIKE;
        else if (prop_pos.equals("Right"))
        {
            targetPose = auto_constants.BLUE_LEFT_RIGHT_SPIKE_WAYPOINT;
            calculator.reset(targetPose, myPose);
            errors = new Pose2d(5, 5, 1);
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
            Robot.DD.applySpeed(new vec2(0), 0, telemetry);
            targetPose = auto_constants.BLUE_LEFT_RIGHT_SPIKE;
        }
        else
            targetPose = auto_constants.BLUE_LEFT_LEFT_SPIKE;

        calculator.reset(targetPose, myPose);

        errors = new Pose2d(1, 1, 0.1);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);

        Robot.FN.drop();

        if (prop_pos.equals("Right")) {
            targetPose = auto_constants.BLUE_LEFT_RIGHT_SPIKE_DOP;
            calculator.reset(targetPose, myPose);
            errors = new Pose2d(1, 1, 1);
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        }
        else if (prop_pos.equals("Center")) {
            targetPose = auto_constants.BLUE_LEFT_CENTER_SPIKE_DOP;
            calculator.reset(targetPose, myPose);
            errors = new Pose2d(1, 1, 1);
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        }
        elevator.target_pos = 4;
        Robot.CO.setPositionHigh();
        Robot.CO.setBoxScoring();

        if (prop_pos.equals("Center"))
        {
            /*targetPose = auto_constants.BLUE_LEFT_CENTER_ADDITIONAL;
            calculator.reset(targetPose, myPose);
            errors = new Pose2d(5, 5, 1);
            Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);*/
            targetPose = auto_constants.BLUE_CENTER_DROP;
        }
        else if (prop_pos.equals("Right"))
            targetPose = auto_constants.BLUE_RIGHT_DROP;
        else
            targetPose = auto_constants.BLUE_LEFT_DROP;

        calculator.reset(targetPose, myPose);
        errors = new Pose2d(1, 1, 0.05);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);
        Robot.DD.applySpeed(new vec2(0), 0, telemetry);

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



        targetPose = auto_constants.BLUE_BEFORE_DROPS;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(2, 2, 0.3);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        targetPose = parking_zone;
        calculator.reset(targetPose, myPose);
        errors = new Pose2d(2, 2, 0.3);
        Robot.DD.straightGoTo(targetPose, errors, calculator, drive, this);

        Robot.DD.applySpeed(new vec2(0), 0, telemetry);
        Robot.DD.setWheelsDefault();
        //Robot.CO.setPositionLow();
        Robot.DD.stopDrivetrain();
        //elevator.target_pos = 0;
        timer.reset();
        while (!isStopRequested() && timer.milliseconds() < 1000) drive.update();
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
