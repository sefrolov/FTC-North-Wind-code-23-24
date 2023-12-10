package org.firstinspires.ftc.teamcode.diff_sverwe;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotNW;

import org.firstinspires.ftc.teamcode.maths.vec2;

public class odometry {
    public double x1, y1;
    double C = 15.6 / 1024.0 * 2.0 * Math.PI;
    static double x0, y0, tetta_0, n1_0, n2_0;
    double delta_tetta, delta_x, delta_y, delta_n1, delta_n2;
    static final double L = 10, B = 10; /// парал = (вперед) = X: 8.8, Y: 4.6 /// перпендекуляр X: -8.4б Y: 10.5
    imu_sensor tetta;
    public DcMotor encoder_x, encoder_y;

    public void init(HardwareMap HM, Telemetry tele, imu_sensor koef) {
        // init of downMotor
        encoder_x = HM.get(DcMotor.class, "intake_motor2c ");
        encoder_y = HM.get(DcMotor.class, "encoder_y");
        tele.addData("","init succesfully");
        tele.update();
        tetta_0 = 0;
        n1_0 = 0;
        n2_0 = 0;
        x0 = 0;
        y0 = 0;
        tetta = koef;
    }

    void Update_pos() {
        delta_n1 = encoder_x.getCurrentPosition() - n1_0;
        delta_n2 = encoder_y.getCurrentPosition() - n2_0;
        delta_x = C * delta_n1 + L * delta_tetta;
        delta_y = C * delta_n2 - B * delta_tetta;
        x1 = x0 + delta_x * cos(tetta_0) - delta_y * sin(tetta_0);
        y1 = y0 + delta_x * sin(tetta_0) + delta_y * cos(tetta_0);

        tetta_0 = tetta.getAngle();
        x0 = x1;
        y0 = y1;
        n1_0 = encoder_x.getCurrentPosition();
        n2_0 = encoder_y.getCurrentPosition();
    }

    public double getPosition_x() {
        Update_pos();
        return x1;
    }

    public double getPosition_y () {
        Update_pos();
        return y1;
    }

    public vec2 getPosition () {
        return new vec2(x1, y1);
    }
}
