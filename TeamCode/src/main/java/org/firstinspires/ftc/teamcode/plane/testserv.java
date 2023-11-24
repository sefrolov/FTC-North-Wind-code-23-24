package org.firstinspires.ftc.teamcode.plane;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "testserv")
public class testserv extends LinearOpMode {

    Servo servo;

    double g;

    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(Servo.class, "SvZpsk");
        waitForStart();
        while(opModeIsActive()){
            g = servo.getPosition();
            servo.setPosition(0.4);
        }

    }
}
