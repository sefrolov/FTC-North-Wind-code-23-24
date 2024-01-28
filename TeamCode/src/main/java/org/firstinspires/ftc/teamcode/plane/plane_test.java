package org.firstinspires.ftc.teamcode.plane;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotNW;

@TeleOp(name = "plane_test")
@Disabled
public class plane_test extends LinearOpMode {

    RobotNW Robot = new RobotNW();;
    double  degr = 1;

    ElapsedTime wait_time = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {

        Robot.init(hardwareMap, telemetry, this);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad2.a) {
                Robot.PL.prepare();
            } else if (gamepad2.b) {
                Robot.PL.launch();
            }

          /*  wait_time.reset();
            while (degr != 1) {
                if (wait_time.milliseconds() == 1000){
                    degr = 1;
            }
*/
           /*

           if (degr == 0) {
                while(g != 1000000000){
                    g++;
                }
            degr = 1
                */


        }
    }
}
