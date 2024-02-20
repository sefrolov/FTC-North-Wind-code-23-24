package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.tele_movement.op_container;

public class hanger {

    double Pl;
    double Dl;

    double Pr;
    double Dr;

    final double kP = 0.01;
    final double kD = 0.01;


    final static double KOEF_LIFT = -8.32;
    int errLeft;
    int errOld;
    int err;
    public DcMotor motor;

    int START_TICKS;

    Telemetry telemetry;

    public void init(HardwareMap HM, Telemetry tele) {
        // init of motor1
        motor = HM.get(DcMotor.class, "hang_motor");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry = tele;
    }

    public void setPosHigh(){ calculate_and_apply_power( -430); }
    public void setPosMid(){
        calculate_and_apply_power(-260);
    }

    public void setPosLow(){calculate_and_apply_power(-202); }
    public void setPosDown(){
        calculate_and_apply_power(5);
    }
    public void setPosMax(){
        motor.setPower(-1);
    }
    public void setPosAutoYellow(){
        calculate_and_apply_power(-65);
    }

    public void autoSetPos(int pos) {
        calculate_and_apply_power(pos);
    }
    public void stop(){motor.setPower(0);}

    private void calculate_and_apply_power(int target_pos){
        /*target_pos *= KOEF_LIFT;*/
        if (target_pos == -202)
            target_pos = 1700;
        else if (target_pos == -260)
            target_pos = 2310;
        else if (target_pos == -430)
            target_pos = 3770;
        else if (target_pos == -65)
            target_pos = 565;
        else if (target_pos == 5)
            target_pos = 0;
        else
            target_pos = 1497;

        /*else
            target_pos *= KOEF_LIFT;*/

        int curPosMotor = getPos();
        int koef = 3;

        if (curPosMotor < -40){
            motor.setPower(0.2);
        }

        err = target_pos - curPosMotor;

        Pr = err;

        Dr = err - errOld;

        if (err <= 70)
            koef = 1;
        if (Math.abs(err) < 40)
            motor.setPower(0);
        else
            motor.setPower( (Pr * kP + Dr * kD) * koef);

        errOld = err;
    }
    public int getPos(){
        return motor.getCurrentPosition() - START_TICKS;
    }

    public void applyPower(double power, Telemetry telemetry){
        motor.setPower(power);
    }
}

