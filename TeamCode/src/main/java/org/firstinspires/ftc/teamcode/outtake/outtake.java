package org.firstinspires.ftc.teamcode.outtake;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class outtake {
    CRServo crservo_motor1;

    ElapsedTime timer = new ElapsedTime();

    boolean drop = false;
    public void init(HardwareMap HM) {
        crservo_motor1 = HM.get(CRServo.class, "outtake");
    }

    private void setPower(double power) {
        crservo_motor1.setPower(power);
    }

    public void runLoading(){
        setPower(-1);
    }

    public void runUnloading(){
        setPower(1);
    }

    public void dropOne() {
        if (timer.milliseconds() < 75)
            drop = true;
        else
            drop = false;
    }

    public void checkOuttake() {
        if (drop) setPower(1);
        else setPower(0);
    }

    public void stop(){
        setPower(0);
        drop = false;
        timer.reset();
    }

    public double getPower() {
        return crservo_motor1.getPower();
    }
}

