package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.tele_movement.op_container;

public class elevator {

    double Pl;
    double Dl;

    double Pr;
    double Dr;
    double Ir, Il;
    final double kP = 0.002;
    final double kD = 0; //0.013

    final double kI = 0; //0.0009

    int errOldLeft;
    int errLeft;

    int errOldRight;
    int errRight;
    public DcMotor motor_left;
    public DcMotor motor_right;
    final double COEF = 5;
    int START_TICKS_LEFT;
    int START_TICKS_RIGHT;

    Telemetry telemetry;

    public void init(HardwareMap HM, Telemetry tele) {
        // init of motor1
        motor_left = HM.get(DcMotor.class, "lift_motor_left");
        motor_left.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_right = HM.get(DcMotor.class, "lift_motor_right");
        motor_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        errOldRight = 0;
        errOldLeft = 0;
        START_TICKS_LEFT = motor_left.getCurrentPosition();

        telemetry = tele;
        telemetry.addData("START_TICKS_LEFT:", START_TICKS_LEFT);
        telemetry.update();
    }

    public void setPosHigh(){
        calculate_and_apply_power(2245);
    }

    public void setPosMid(){
        calculate_and_apply_power(1650);
    }

    public void setPosLow(){
        calculate_and_apply_power(900);
    }

    public void setPosDown(){
        calculate_and_apply_power(0);
    }

    public void setPosHang(){
        calculate_and_apply_power(1);
    }

    public void setPosAutoYellow(){
        calculate_and_apply_power(600);
    }

    private void calculate_and_apply_power(int target_pos){
        int curPosLeftMotor = getPos();

        if (target_pos == 1)
        {
            motor_right.setPower(-1);
            motor_left.setPower(-1);
            return;
        }
        

        if (curPosLeftMotor < 0){
            motor_right.setPower(0.2);
            motor_left.setPower(0.2);
        }

        errLeft = target_pos - curPosLeftMotor; /* -100*/

        Pl = errLeft;

        Dl = errLeft - errOldLeft;

        if (Math.abs(errLeft) <= 60)
            Il += errLeft;


       /* if (target_pos == 0 && curPosLeftMotor < 100) {
            motor_left.setPower(0);
            motor_right.setPower(0);
            return;
        }*/

        if (Math.abs(errLeft) < 50) {
            Il = 0;
            motor_right.setPower(0);
            motor_left.setPower(0);
        }

        if (target_pos > curPosLeftMotor && errLeft >= 50) {
            motor_right.setPower(Math.max((Pl * kP + Dl * kD + Il * kI), 0));
            motor_left.setPower(Math.max((Pl * kP + Dl * kD + Il * kI), 0));
        }
        else
        {
            if (errLeft < -50) {

                motor_right.setPower((Pl * kP + Dl * kD + Il * kI));
                motor_left.setPower((Pl * kP + Dl * kD + Il * kI));
            }
        }

        /*if (target_pos < curPosLeftMotor && errLeft >= 50) {

        }*/

        errOldLeft = errLeft;
    }
    public int getPos()
    {
        return motor_left.getCurrentPosition() - START_TICKS_LEFT/* + op_container.elevator_left*/;
    }

    public void setZero()
    {
        START_TICKS_LEFT = motor_left.getCurrentPosition();
    }


    public void applyPower(double power, Telemetry telemetry){
        motor_right.setPower(power);
        motor_left.setPower(power);
        //telemetry.addData("left pos:", getPos(motor_left));
        //telemetry.addData("right pos:", getPos(motor_right));
        //telemetry.update();
    }
}

