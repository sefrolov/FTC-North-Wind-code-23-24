package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotNW;

public class CommonAutonomousTrajectory {
    RobotNW Robot;
    ElapsedTime timer;
    LinearOpMode lin;

    public CommonAutonomousTrajectory(RobotNW Robot1, ElapsedTime timer1, LinearOpMode lin1) {
        Robot = Robot1;
        timer = timer1;
        lin = lin1;
    }

    public void goFromStartToBackstage(){
        /* go forward */
        Robot.DD.goForward(-1);
        timer.reset();
        while (timer.milliseconds() < 1300 * 4 && !lin.isStopRequested())
            ;

        /* stop */
        Robot.DD.stopDrivetrain();
        timer.reset();
        while (timer.milliseconds() < 100 && !lin.isStopRequested())
            ;

        /* unload pixel */
        Robot.IN.unloadPixel();
        timer.reset();
        while (timer.milliseconds() < 100 && !lin.isStopRequested())
            ;
        Robot.IN.stopIntakeMotors();
    }

    public void stopDrivetrain(){
        Robot.DD.stopDrivetrain();
        timer.reset();
        while (timer.milliseconds() < 100 && !lin.isStopRequested())
            ;
    }

    public void goForward(double speed, int time) {
        Robot.DD.goForward(speed);
        timer.reset();
        while (timer.milliseconds() < time && !lin.isStopRequested())
            ;
    }

    public void rotateModules(double speed, int time) {
        Robot.DD.rotateModules(1);
        timer.reset();
        while (timer.milliseconds() < 450 && !lin.isStopRequested())
            ;
    }
}
