package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class lift {

    Servo servo_lift;

    public void init(HardwareMap HM) {
        // init of motor1
        servo_lift = HM.get(Servo.class, "liftServo");
    }

    public void setPos(double deg) {
        servo_lift.setPosition(deg);
    }

    public double getPos(){
        return servo_lift.getPosition();
    }
}
