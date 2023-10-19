package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class lift extends LinearOpMode {

    Servo serv_lift;
    double pos1 = 0, pos2 = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        serv_lift = hardwareMap.get(Servo.class, "SvLft");

        waitForStart();

        while (opModeIsActive()){
            if (gamepad1.a){
                serv_lift.setPosition(pos1);
                sleep(150);
            }
            if (gamepad1.b){
                serv_lift.setPosition(pos2);
                sleep(150);
            }
        }
    }
}
