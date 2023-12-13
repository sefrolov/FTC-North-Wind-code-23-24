package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.maths.vec2;

public class CommonAutonomousActions {
    RobotNW Robot;
    ElapsedTime timer;
    LinearOpMode lin;

    SampleMecanumDrive drive;
    public CommonAutonomousActions(RobotNW Robot1, ElapsedTime timer1, LinearOpMode lin1, SampleMecanumDrive driv) {
        Robot = Robot1;
        timer = timer1;
        lin = lin1;
        drive = driv;
    }

    public void unloadPixel() {
        Robot.IN.unloadPixel();
        timer.reset();
        while (timer.milliseconds() < 100 && !lin.isStopRequested())
            ;
        Robot.IN.stopIntakeMotors();
    }

    public void goTo(double targetX, double targetY, double targetHeading, double speed){
        Pose2d curPos = drive.getPoseEstimate();
        double curX = curPos.getX();
        double curY = curPos.getY();
        double heading = curPos.getHeading();
        Pose2d targetPos = new Pose2d(targetX, targetY, targetHeading);
        double dX = targetX - curX;
        double dY = targetY - curY;
        double dHeading = targetHeading - heading;

        if (dX != 0 && dY != 0)
            Robot.DD.applySpeedFieldCentric(new vec2(dX/dY * speed, dY/dX * speed), dHeading, heading);
        else if (dX == 0)
            Robot.DD.applySpeedFieldCentric(new vec2(0, dY * speed), dHeading, heading);
        else
            Robot.DD.applySpeedFieldCentric(new vec2(dX * speed, 0), dHeading, heading);
    }
}
