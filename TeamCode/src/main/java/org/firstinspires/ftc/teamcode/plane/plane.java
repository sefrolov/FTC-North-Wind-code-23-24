package org.firstinspires.ftc.teamcode.plane;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

public class plane {

    Servo serv_zap;

    public void init(HardwareMap HM) {
        // init of motor1
        serv_zap = HM.get(Servo.class, "SvZpsk");
    }

    public void setPos(double deg) {
        serv_zap.setPosition(deg);
    }

    public double getPos(){
        return serv_zap.getPosition();
    }
}
