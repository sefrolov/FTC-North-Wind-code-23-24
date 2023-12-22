package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.maths.vec2;
import org.opencv.core.Mat;

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
        drive.update();
        Pose2d curPos = drive.getPoseEstimate();
        double curX = curPos.getX();
        double curY = curPos.getY();
        double heading = curPos.getHeading();

        double rotation = 0;
        double speedX = 0;
        double speedY = 0;

        double dX = targetX - curX;
        double dY = targetY - curY;
        double dHeading = targetHeading - heading;

        if (dHeading < 0 && Math.abs(dHeading) > Math.PI)
            rotation = 2 * Math.PI + dHeading;
        else if (dHeading < 0 && Math.abs(dHeading) <= Math.PI)
            rotation = dHeading;
        else if (dHeading >= 0 && Math.abs(dHeading) <= Math.PI)
            rotation = dHeading;
        else if (dHeading >= 0 && Math.abs(dHeading) > Math.PI)
            rotation = -(2 * Math.PI - dHeading);

        if (new vec2(dX, dY).len() >= 10)
            rotation *= new vec2(dX, dY).len() / 20;
        else {
            if (rotation > 0)
                rotation = 0.074;
            else
                rotation = -0.074;
        }

        if (Math.abs(dX) >= 1 && Math.abs(dY) >= 1) {
            speedY = dY / Math.abs(dX);
            speedX = dX / Math.abs(dY);
        }
        else if (Math.abs(dX) <= 1) {
            speedX = 0;
            speedY = dY;
        }
        else if (Math.abs(dY) <= 1) {
            speedX = dX;
            speedY = 0;
        }
        else {
            speedX = dX;
            speedY = dY;
        }

        if (Math.abs(dX) <= 12 && Math.abs(dX) > 1)
            speedX = 0.09 * Math.signum(dX);

        if (Math.abs(dY) <= 12  && Math.abs(dY) > 1)
            speedY = 0.09 * Math.signum(dY);

        Robot.DD.applySpeedFieldCentric(new vec2(speedX, speedY).normalize().mul(speed), rotation, heading);
    }
}
