package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotNW;

public class CommonAutonomousActions {
    RobotNW Robot;
    ElapsedTime timer;
    LinearOpMode lin;

    public CommonAutonomousActions(RobotNW Robot1, ElapsedTime timer1, LinearOpMode lin1) {
        Robot = Robot1;
        timer = timer1;
        lin = lin1;
    }

    public void unloadPixel() {
        Robot.IN.unloadPixel();
        timer.reset();
        while (timer.milliseconds() < 100 && !lin.isStopRequested())
            ;
        Robot.IN.stopIntakeMotors();
    }
}
