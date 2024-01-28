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

    final double kP = 0.012;
    final double kD = 0.01;

    final static double KOEF_LIFT = -9.18125431;
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
    }

    public void setPosHigh(){ calculate_and_apply_power( 400); }
    public void setPosMid(){
        calculate_and_apply_power(250);
    }

    public void setPosLow(){calculate_and_apply_power(180); }
    public void setPosDown(){
        calculate_and_apply_power(0);
    }
    public void setPosAutoYellow(){
        calculate_and_apply_power(70);
    }

    public void autoSetPos(int pos) {
        calculate_and_apply_power(pos);
    }

    private void calculate_and_apply_power(int target_pos){
        target_pos *= KOEF_LIFT;
        int curPosMotor = getPos();

        if (curPosMotor < 0){
            motor.setPower(0.2);
        }

        err = target_pos - curPosMotor;

        Pr = err;

        Dr = err - errOld;

        if (Math.abs(err) < 80)
            motor.setPower(0);
        else
            motor.setPower(Pr * kP + Dr * kD);

        errOld = err;
    }
    public int getPos(){
        return motor.getCurrentPosition() - START_TICKS + op_container.HANGER_TICS;
    }

    public void applyPower(double power, Telemetry telemetry){
        motor.setPower(power);
    }
}

