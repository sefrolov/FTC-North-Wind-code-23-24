package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class lift extends LinearOpMode {

    Servo serv_lift_r;
    Servo serv_lift_l;
    double pos1 = 0, pos2 = 0.5, g, dgr;


    @Override
    public void runOpMode() throws InterruptedException {
        serv_lift_r = hardwareMap.get(Servo.class, "SvLft");

        waitForStart();

        while (opModeIsActive()){
            g = serv_lift_r.getPosition();
            if ((Math.abs(gamepad1.left_stick_y) >= 0.1) && opModeIsActive()) {
                dgr = gamepad1.left_stick_y;

                if (g >= 6 && dgr > 0 && opModeIsActive()){
                    serv_lift_r.setPosition(5.8);
                    serv_lift_l.setPosition(5.8);
                }
                else if(g < 6 && dgr > 0 && opModeIsActive()){
                    serv_lift_r.setPosition(dgr * 0.8);
                    serv_lift_l.setPosition(dgr * 0.8);
                }
                else if(g > 0 && dgr < 0 && opModeIsActive())   {
                    serv_lift_r.setPosition(dgr * 0.8);
                    serv_lift_l.setPosition(dgr * 0.8);
                }
                else if(g <= 0 && dgr < 0 && opModeIsActive()){
                    serv_lift_r.setPosition(0.2);
                    serv_lift_l.setPosition(0.2);
                }
                g = serv_lift_r.getPosition();
            }
            else{
                if (gamepad1.a){
                    while(!gamepad1.b && Math.abs(gamepad1.left_stick_y) < 0.1){
                        serv_lift_r.setPosition(pos1);
                    }
                }
                if (gamepad1.b){
                    while(!gamepad1.a && Math.abs(gamepad1.left_stick_y) < 0.1) {
                        serv_lift_r.setPosition(pos2);
                    }
                }
            }
         /*   if (gamepad1.a){
                serv_lift_r.setPosition(pos1);
                sleep(150);
            }
            if (gamepad1.b){
                serv_lift_r.setPosition(pos2);
                sleep(150);
            }*/
        }
    }
}
