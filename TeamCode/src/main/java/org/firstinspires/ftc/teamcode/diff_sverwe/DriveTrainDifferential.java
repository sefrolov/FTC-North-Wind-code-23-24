package org.firstinspires.ftc.teamcode.diff_sverwe;

import static java.lang.Double.max;
import static java.lang.Math.PI;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.maths.vec2;

public class DriveTrainDifferential {
    public module leftModule = new module();
    public module rightModule = new module();

    vec2 rightWheelCoord = new vec2(5, 0);
    vec2 leftWheelCoord = new vec2(-5, 0);
    double MAX_ANG_SPEED = 1. / max(rightWheelCoord.len(), leftWheelCoord.len());
    vec2 turnSpeedLeft, turnSpeedRight, leftSpeed, rightSpeed;
    public void init(HardwareMap HM) {
        leftModule.init(HM, "motorLD", "motorLU", 8192, 0.3);
        rightModule.init(HM, "motorRD", "motorRU", /*1440*/1310, -0.3);
    }

    public void applySpeed(vec2 trans, double turnSpeed, Telemetry tele) {
        turnSpeedRight = rightWheelCoord.turn(0.5 * PI).mul(MAX_ANG_SPEED * turnSpeed);
        turnSpeedLeft = leftWheelCoord.turn(0.5 * PI).mul(MAX_ANG_SPEED * turnSpeed);

        rightSpeed = trans.plus(turnSpeedRight);
        leftSpeed = trans.plus(turnSpeedLeft);
        if (max(rightSpeed.len(), leftSpeed.len()) > 1) {
            rightSpeed.mul(1. / max(rightSpeed.len(), leftSpeed.len()));
            leftSpeed.mul(1. / max(rightSpeed.len(), leftSpeed.len()));
        }

        rightSpeed.set(rightSpeed.getX(), -rightSpeed.getY());
        rightModule.applyVectorPTele(rightSpeed, tele);
        leftModule.applyVectorPTele(leftSpeed, tele);
        tele.addData("right speed X:", rightSpeed.getX());
        tele.addData("right speed Y:", rightSpeed.getY());
        tele.addData("left speed X:", leftSpeed.getX());
        tele.addData("left speed Y:", leftSpeed.getY());
    }

    public void goForward( double speed ) {
        rightModule.applyVector(speed, 0);
        leftModule.applyVector(speed, 0);
    }

    public void rotateModules( double speed ) {
        rightModule.applyVector(0, speed);
        leftModule.applyVector(0, speed);
    }

    public void stopDrivetrain() {
        rightModule.applyVector(0, 0);
        leftModule.applyVector(0, 0);
    }
}
