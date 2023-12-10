package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class intake_folding {
    CRServo crservo_motor1;

    public void init(HardwareMap HM) {
        crservo_motor1 = HM.get(CRServo.class, "folding");
    }

    private void setPower(double power) {
        crservo_motor1.setPower(power);
    }

    public void spin(){
        setPower(-1);
    }

    public void spin_reverse(){
        setPower(1);
    }
    public void stop(){
        setPower(0);
    }
    public double getPower() {
        return crservo_motor1.getPower();
    }
}
