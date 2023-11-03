package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class plane extends LinearOpMode {

    Servo serv_zap;

    double g, degr;

    @Override
    public void runOpMode() throws InterruptedException {

        serv_zap = hardwareMap.get(Servo.class, "SvZpsk");

        waitForStart();

        while (opModeIsActive()){
            g = serv_zap.getPosition();
            if (gamepad1.a){
                degr = 0;
            }else if (gamepad1.b){
                degr  = 90;
            }
            serv_zap.setPosition(degr);
        }
    }
}
