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

    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this);
        telemetry.addData("", "Init complited");
        telemetry.update();

        while (!opModeIsActive())
            prop_pos = Robot.BD.getPosition();

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
        /* rotate modules */
        Robot.DD.rightModule.applyVector(1, 0);
        Robot.DD.leftModule.applyVector(1, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;

        /* stop rotating modules */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;



        /* go to spike mark */
        Robot.DD.rightModule.applyVector(0, 1);
        Robot.DD.leftModule.applyVector(0, 1);
        timer.reset();
        while (timer.milliseconds() < 1300 && !isStopRequested())
            ;
        /* stop  */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;

        /* unload pixel */
        Robot.IN.motor1.setPower(0.001);
        Robot.IN.motor2.setPower(-0.001);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;
        Robot.IN.motor1.setPower(0);
        Robot.IN.motor2.setPower(0);



        /* go back to starting location */

        Robot.DD.rightModule.applyVector(0, -1);
        Robot.DD.leftModule.applyVector(0, -1);
        timer.reset();
        while (timer.milliseconds() < 1300 && !isStopRequested())
            ;
        /* stop  */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;



        /* go right to one tile before backstage */

        /* rotate modules */
        Robot.DD.rightModule.applyVector(1, 0);
        Robot.DD.leftModule.applyVector(1, 0);
        timer.reset();
        while (timer.milliseconds() < 250 && !isStopRequested())
            ;

        /* stop */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;

        /* go forward */
        Robot.DD.rightModule.applyVector(0, 1);
        Robot.DD.leftModule.applyVector(0, 1);
        timer.reset();
        while (timer.milliseconds() < 1300 * 2 && !isStopRequested())
            ;

        /* stop */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;

        /* unload pixel */
        Robot.IN.motor1.setPower(0.001);
        Robot.IN.motor2.setPower(-0.001);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;
        Robot.IN.motor1.setPower(0);
        Robot.IN.motor2.setPower(0);
    }

    private void center() {
        /* go to spike mark */
        Robot.DD.rightModule.applyVector(0, 1);
        Robot.DD.leftModule.applyVector(0, 1);
        timer.reset();
        while (timer.milliseconds() < 1300 && !isStopRequested())
            ;
        /* stop  */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;

        /* unload pixel */
        Robot.IN.motor1.setPower(0.001);
        Robot.IN.motor2.setPower(-0.001);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;
        Robot.IN.motor1.setPower(0);
        Robot.IN.motor2.setPower(0);



        /* go back to starting location */

        Robot.DD.rightModule.applyVector(0, -1);
        Robot.DD.leftModule.applyVector(0, -1);
        timer.reset();
        while (timer.milliseconds() < 1300 && !isStopRequested())
            ;
        /* stop  */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;



        /* go right to one tile before backstage */

        /* rotate modules */
        Robot.DD.rightModule.applyVector(1, 0);
        Robot.DD.leftModule.applyVector(1, 0);
        timer.reset();
        while (timer.milliseconds() < 350 && !isStopRequested())
            ;

        /* stop */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;

        /* go forward */
        Robot.DD.rightModule.applyVector(0, 1);
        Robot.DD.leftModule.applyVector(0, 1);
        timer.reset();
        while (timer.milliseconds() < 1300 * 2 && !isStopRequested())
            ;

        /* stop */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;

        /* unload pixel */
        Robot.IN.motor1.setPower(0.001);
        Robot.IN.motor2.setPower(-0.001);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;
        Robot.IN.motor1.setPower(0);
        Robot.IN.motor2.setPower(0);
    }

    private void left() {
        /* rotate modules */
        Robot.DD.rightModule.applyVector(-1, 0);
        Robot.DD.leftModule.applyVector(-1, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;

        /* stop rotating modules */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;



        /* go to spike mark */
        Robot.DD.rightModule.applyVector(0, 1);
        Robot.DD.leftModule.applyVector(0, 1);
        timer.reset();
        while (timer.milliseconds() < 1300 && !isStopRequested())
            ;
        /* stop  */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;

        /* unload pixel */
        Robot.IN.motor1.setPower(0.001);
        Robot.IN.motor2.setPower(-0.001);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;
        Robot.IN.motor1.setPower(0);
        Robot.IN.motor2.setPower(0);



        /* go back to starting location */

        Robot.DD.rightModule.applyVector(0, -1);
        Robot.DD.leftModule.applyVector(0, -1);
        timer.reset();
        while (timer.milliseconds() < 1300 && !isStopRequested())
            ;
        /* stop  */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;



        /* go right to one tile before backstage */

        /* rotate modules */
        Robot.DD.rightModule.applyVector(1, 0);
        Robot.DD.leftModule.applyVector(1, 0);
        timer.reset();
        while (timer.milliseconds() < 450 && !isStopRequested())
            ;

        /* stop */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;

        /* go forward */
        Robot.DD.rightModule.applyVector(0, 1);
        Robot.DD.leftModule.applyVector(0, 1);
        timer.reset();
        while (timer.milliseconds() < 1300 * 2 && !isStopRequested())
            ;

        /* stop */
        Robot.DD.rightModule.applyVector(0, 0);
        Robot.DD.leftModule.applyVector(0, 0);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;

        /* unload pixel */
        Robot.IN.motor1.setPower(0.001);
        Robot.IN.motor2.setPower(-0.001);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested())
            ;
        Robot.IN.motor1.setPower(0);
        Robot.IN.motor2.setPower(0);
    }
}