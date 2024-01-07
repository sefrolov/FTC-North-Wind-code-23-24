package org.firstinspires.ftc.teamcode.outtake;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class outtake_flipper {
    Servo motor;

    ElapsedTime timer = new ElapsedTime();

    boolean drop = false;

    public void init(HardwareMap HM) {
        motor = HM.get(Servo.class, "outtake_roller");
        setPos(0.5);
    }

    public void setPos(double deg) {
        motor.setPosition(deg);
    }
}
