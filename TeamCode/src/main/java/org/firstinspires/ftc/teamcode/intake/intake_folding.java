package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class intake_folding {
    CRServo crservo_motor1;

    public void init(HardwareMap HM) {
        crservo_motor1 = HM.get(CRServo.class, "crservo_motor1");
    }

    public void setPower(double power) {
        crservo_motor1.setPower(power);
    }

    public double getPower() {
        return crservo_motor1.getPower();
    }
}
