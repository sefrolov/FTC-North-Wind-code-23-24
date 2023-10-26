
package org.firstinspires.ftc.teamcode;

import static java.lang.Math.ceil;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TELE")
public class four extends LinearOpMode {
    DcMotor motor;
    double pwr;
    int g, f;
////
    boolean flag;

    BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotor.class, "motor");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();

        while (opModeIsActive()) {

            if ((Math.abs(gamepad1.left_stick_y) >= 0.1) && opModeIsActive()) {
                //насколько я поняла, мотор установлен так, что значение позичии увеличится с поворотом против часовой стрелки
                //если стик отклонен на положительное число, то поднять вверх



                if (g < -50) {
                    pwr = gamepad1.left_stick_y * 0.5;
                    motor.setPower(pwr);
                    flag = true;
                } else {
                    pid.liftGo(-80, motor);
                    telemetry.addData("g", g);
                    telemetry.update();
                }

                g = motor.getCurrentPosition();

            } else {
                motor.setPower(0);
                if (flag && opModeIsActive()) {
                    flag = false;

                }
                if (gamepad1.a && opModeIsActive()) {
                    while (!gamepad1.x & !gamepad1.y & !gamepad1.b & Math.abs(gamepad1.left_stick_y) < 0.1 & opModeIsActive()) {
                        pid.liftGo(-1000, motor);
                        telemetry.addData("g", g);
                        telemetry.update();
                    }
                } else if (gamepad1.b && opModeIsActive()) {
                    while (!gamepad1.x & !gamepad1.y & !gamepad1.a & Math.abs(gamepad1.left_stick_y) < 0.1 &&opModeIsActive())  {
                        pid.liftGo(-1500, motor);
                        telemetry.addData("g", g);
                        telemetry.update();
                    }
                } else if (gamepad1.y && opModeIsActive()) {
                    while (!gamepad1.x & !gamepad1.b & !gamepad1.a & Math.abs(gamepad1.left_stick_y) < 0.1 && opModeIsActive()) {
                        pid.liftGo(-1750, motor);
                        telemetry.addData("g", g);
                        telemetry.update();
                    }
                } else if (gamepad1.x && opModeIsActive()) {
                    f = (int) Math.abs(Math.ceil(g));
                    while (Math.abs(gamepad1.left_stick_y) < 0.1 && opModeIsActive()) {
                        pid.liftGo(-f, motor);
                        telemetry.addData("g", g);
                        telemetry.update();
                    }
                } else {
                    f = (int) Math.abs(Math.ceil(g));
                    while (Math.abs(gamepad1.left_stick_y) < 0.1 && opModeIsActive() && !gamepad1.a && !gamepad1.b && !gamepad1.y) {
                        pid.liftGo(-f, motor);
                        telemetry.addData("g", g);
                        telemetry.update();
                    }
                }



            }
        }
    }
}

