package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.maths.vec2;

@Autonomous(name = "Auto_blue_left_drop_1012")
public class auto_blue_left_drop_1012 extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";
    CommonAutonomousTrajectory comTraj = new CommonAutonomousTrajectory(Robot, timer, this);

    CommonAutonomousActions comAct = new CommonAutonomousActions(Robot, timer, this);

    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        telemetry.addData("", "Init completed");
        telemetry.update();

        while (!isStarted()) {
            telemetry.addData("", "not started");
            /*
            prop_pos = Robot.BD.getPosition(telemetry);
            */
            telemetry.update();
        }

        waitForStart();

        //Robot.servo.setPosition(0.16);

        while (opModeIsActive()) {
            /*
            Robot.BD.StopStreaming();
            if (prop_pos.equals("Center"))
                center();
            else if (prop_pos.equals("Right"))
                right();
            else if (prop_pos.equals("Left"))
                left();
            else

             */

            Robot.CO.setPositionHigh();

            timer.reset();
            while(timer.milliseconds() < 900)
                Robot.DD.applySpeed(new vec2(-0.15, 0.15), 0, telemetry);

            timer.reset();
            while(timer.milliseconds() < 550)
                Robot.DD.applySpeed(new vec2(0, 0.07), 0, telemetry);

            Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);

            Robot.OT.runUnloading();
            timer.reset();
            while (timer.milliseconds() < 2000)
                ;

            Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);
            timer.reset();
            while(timer.milliseconds() < 200);

            timer.reset();
            while(timer.milliseconds() < 600)
                Robot.DD.applySpeed(new vec2(0, -0.07), 0, telemetry);

            Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);

            Robot.OT.stop();
            /*
            Robot.CO.setPositionHigh();
            Robot.OT.runUnloading();
            timer.reset();
            while (timer.milliseconds() < 7000)
                ;
            Robot.OT.stop();
            Robot.CO.setPositionLow();

            timer.reset();

            while(timer.milliseconds() < 500)
                Robot.DD.applySpeed(new vec2(0, -0.1), 0, telemetry);
            timer.reset();

            */
            Robot.DD.applySpeed(new vec2(0, 0), 0, telemetry);
            while(!isStopRequested());
        }
    }
}