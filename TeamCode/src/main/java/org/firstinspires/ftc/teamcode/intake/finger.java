package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class finger {
    Servo finger;

    public void init(HardwareMap HM) {
        // init of motor1
        finger = HM.get(Servo.class, "finger");
        prepare();
    }

    void setPos(double deg) {
        finger.setPosition(deg);
    }

    public void drop() {
        setPos(0.2);
    }

    public void prepare() {
        setPos(0.8);
    }

    public double getPos(){
        return finger.getPosition();
    }
}
