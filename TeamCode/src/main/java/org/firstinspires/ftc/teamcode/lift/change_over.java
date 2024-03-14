package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class change_over {

    public Servo motor1;
    public Servo motor2;
    Servo box_flipper;

    public void init(HardwareMap HM) {

        motor1 = HM.get(Servo.class, "change_over_1"); //left
        motor2 = HM.get(Servo.class, "change_over_2"); //right
        box_flipper = HM.get(Servo.class, "box_flipper");
        setPositionLow();
        setBoxDefault();
    }
    public void setPos(double deg) {
        motor1.setPosition(0.5 + deg);
        motor2.setPosition(0.5 - deg);
    }

    void setBoxServoPosition(double pos){
        box_flipper.setPosition(pos);
    }

    public void setBoxScoring(){
        setBoxServoPosition(0.15);
    }

    public void setBoxDefault(){
        setBoxServoPosition(0.56);
    }
    public void setPositionLow() {
        setMotor1Pos(0.03);
        setMotor2Pos(0.98);
    }
    public void setPositionHi() {
        setMotor1Pos(0.54);
        setMotor2Pos(0.34);
    }
    public void setPositionDef() {
        setPos(0);
    }
    public void setPositionHigh() {
        setMotor1Pos(1.00);
        setMotor2Pos(0.033);
    }

    public void setHangerPos() {
        motor1.getController().pwmDisable();
        motor2.getController().pwmDisable();
        setBoxScoring();
    }

    public void setMotor2_0(){
        motor2.setPosition(0.);
        motor1.setPosition(0.);
    }
    public void setMotor2_1() {
        motor2.setPosition(1.);
        motor1.setPosition(1.);
    }
    public void setMotor1Pos(double pos){
        motor1.setPosition(pos);
    }
    public void setMotor2Pos(double pos){
        motor2.setPosition(pos);
    }
}
