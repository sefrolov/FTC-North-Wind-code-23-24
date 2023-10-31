package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;

@Autonomous(name = "Auto_red_right")
public class Auto_red_right extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";

    CommonAutonomousTrajectory comTraj = new CommonAutonomousTrajectory(Robot, timer, this);

    CommonAutonomousActions comAct = new CommonAutonomousActions(Robot, timer, this);

    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        telemetry.addData("", "Init complited");
        telemetry.update();

        while (!isStarted()) {
            telemetry.addData("", "not started");
            prop_pos = Robot.BD.getPosition(telemetry);
            telemetry.update();
        }

        waitForStart();

        //Robot.servo.setPosition(0.16);

        while (opModeIsActive()) {
            Robot.BD.StopStreaming();
            if (prop_pos.equals("Center"))
                center();
            else if (prop_pos.equals("Right"))
                right();
            else if (prop_pos.equals("Left"))
                left();
            else
                ;/* most stable */
        }
    }

    private void right() {
        comTraj.rightSpike();

        /* go right to one tile before backstage */

        /* rotate modules */
        comTraj.rotateModules(1, 250);

        /* stop */
        comTraj.stopDrivetrain();

        comTraj.goForward(1, 1300);

        comTraj.stopDrivetrain();

        comAct.unloadPixel();
    }

    private void center() {
        comTraj.centerSpike();

        /* go right to one tile before backstage */

        /* rotate modules */
        comTraj.rotateModules(1, 350);

        /* stop */
        comTraj.stopDrivetrain();

        comTraj.goForward(1, 1300);

        comTraj.stopDrivetrain();

        comAct.unloadPixel();
    }

    private void left() {
        comTraj.leftSpike();

        /* go right to one tile before backstage */

        /* rotate modules */
        comTraj.rotateModules(1, 450);

        /* stop */
        comTraj.stopDrivetrain();

        comTraj.goForward(1, 1300);

        comTraj.stopDrivetrain();

        comAct.unloadPixel();
    }
}