package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class change_over {

    Servo motor1;
    Servo motor2;

    public void init(HardwareMap HM) {

        motor1 = HM.get(Servo.class, "change_over_1");
        motor2 = HM.get(Servo.class, "change_over_2");
        setPos(-0.5);
    }

    private void setPos(double deg) {
        motor1.setPosition(0.5 + deg);
        motor2.setPosition(0.5 - deg);
    }

    public void setPositionLow() {
        setPos(-0.5);
    }

    public void setPositionDef() {
        setPos(0);
    }
    public void setPositionHigh() {
        setPos(0.28);
    }

    public double getPos_motor1() {
        return motor1.getPosition();
    }

    public double getPos_motor2 (){
        return motor2.getPosition();
    }
}
