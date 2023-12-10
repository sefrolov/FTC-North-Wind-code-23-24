package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

public class intake {

    public DcMotor motor1 = null;
    public DcMotor motor2 = null;
    double Power = 0;
    double acceleration_koef;
    double PowerPartFromMaxValue = 0.02;
    ElapsedTime acceleration_time = new ElapsedTime();

    ElapsedTime time = new ElapsedTime();

    public void init(HardwareMap HM) {
        // init of motor1
        motor1 = HM.get(DcMotor.class, "intake_motor1");
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // init of motor2
        motor2 = HM.get(DcMotor.class, "intake_motor2");
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void unloadPixel() {
        motor1.setPower(0.001);
        motor2.setPower(-0.001);
    }

    public void stopIntakeMotors() {
        motor1.setPower(0);
        motor2.setPower(0);
        time.reset();
    }

    public void intake_run() {
        if (time.milliseconds() > 500)
            acceleration_koef = 0.009;
        else
            acceleration_koef = (0.5 - 0.0008 * time.milliseconds());

        Power = acceleration_koef;

        motor1.setPower(Power);
        motor2.setPower(Power);
    }

    public void intake_run_away() {
        if (time.milliseconds() > 500)
            acceleration_koef = 0.009;
        else
            acceleration_koef = (0.5 - 0.0008 * time.milliseconds());

        Power = acceleration_koef;

        motor1.setPower(-Power);
        motor2.setPower(-Power);
    }
    public void work_intake(double input){
        if (acceleration_time.milliseconds() > 1000)
            acceleration_koef = 0.1;
        else
            acceleration_koef = (10 - 0.001 * acceleration_time.milliseconds());

        if (Math.abs(input) > 0.1) {
            if (-input < 0)
                Power = 4 * 1.7 * input * PowerPartFromMaxValue /* /Math.abs(gamepad1.right_stick_y)*/ * acceleration_koef;
            else
                Power = 4 * 1.5 * input * PowerPartFromMaxValue /* /Math.abs(gamepad1.right_stick_y)*/ * acceleration_koef;
        }
        else {
                Power = 0;
                acceleration_time.reset();
             }
        System.out.println("here" + Power);
        motor1.setPower(-Power);
        motor2.setPower(-Power);
    }
}
