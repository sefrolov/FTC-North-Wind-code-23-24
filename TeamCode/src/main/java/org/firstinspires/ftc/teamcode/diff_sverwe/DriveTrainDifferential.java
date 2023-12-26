package org.firstinspires.ftc.teamcode.diff_sverwe;

import static java.lang.Double.max;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.autonomous.auto_PID;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.maths.vec2;

public class DriveTrainDifferential {
    public module leftModule = new module();
    public module rightModule = new module();
    vec2 rightWheelCoord = new vec2(5, 0);
    vec2 leftWheelCoord = new vec2(-5, 0);
    double MAX_ANG_SPEED = 1. / max(rightWheelCoord.len(), leftWheelCoord.len());
    vec2 turnSpeedLeft, turnSpeedRight, leftSpeed, rightSpeed;

    Telemetry telemetry;

    boolean isParked = false;
    public void init(HardwareMap HM, Telemetry tele) {
        leftModule.init(HM, "motorLD", "motorLU", 8192, 0.3);
        rightModule.init(HM, "motorRD", "motorRU", /*1440*/1024, 0.3);
        telemetry = tele;
    }

    public void applySpeedFieldCentric(vec2 trans, double turnSpeed, double heading) {

        /* transfer robot vector to filed system */
        trans.set(trans.getY(), trans.getX());

        /* turn vector to heading of robot */
        trans.turn(heading);

        turnSpeedRight = rightWheelCoord.turned(0.5 * PI).mul(MAX_ANG_SPEED * turnSpeed);
        turnSpeedLeft = leftWheelCoord.turned(0.5 * PI).mul(MAX_ANG_SPEED * turnSpeed);

        rightSpeed = trans.plus(turnSpeedRight);
        leftSpeed = trans.plus(turnSpeedLeft);
        rightSpeed.mul(-1);
        if (max(rightSpeed.len(), leftSpeed.len()) > 1) {
            rightSpeed.mul(1. / max(rightSpeed.len(), leftSpeed.len()));
            leftSpeed.mul(1. / max(rightSpeed.len(), leftSpeed.len()));
        }

        //rightSpeed.set(rightSpeed.getX(), -rightSpeed.getY());
        rightModule.applyVectorPHard(rightSpeed);
        leftModule.applyVectorPHard(leftSpeed);
    }
    public void applySpeed(vec2 trans, double turnSpeed, Telemetry tele) {
        turnSpeedRight = rightWheelCoord.turned(0.5 * PI).mul(MAX_ANG_SPEED * turnSpeed);
        turnSpeedLeft = leftWheelCoord.turned(0.5 * PI).mul(MAX_ANG_SPEED * turnSpeed);

        rightSpeed = trans.plus(turnSpeedRight);
        leftSpeed = trans.plus(turnSpeedLeft);
        rightSpeed.mul(-1);
        if (max(rightSpeed.len(), leftSpeed.len()) > 1) {
            rightSpeed.mul(1. / max(rightSpeed.len(), leftSpeed.len()));
            leftSpeed.mul(1. / max(rightSpeed.len(), leftSpeed.len()));
        }

        //rightSpeed.set(rightSpeed.getX(), -rightSpeed.getY());
        rightModule.applyVectorPTeleHard(rightSpeed, tele);
        leftModule.applyVectorPTeleHard(leftSpeed, tele);
        /*tele.addData("right speed X:", rightSpeed.getX());
        tele.addData("right speed Y:", rightSpeed.getY());
        tele.addData("left speed X:", leftSpeed.getX());
        tele.addData("left speed Y:", leftSpeed.getY());
        tele.addData("turn right speed X:", turnSpeedRight.getX());
        tele.addData("turn right speed Y:", turnSpeedRight.getY());
        tele.addData("turn left speed X:", turnSpeedLeft.getX());
        tele.addData("turn left speed Y:", turnSpeedLeft.getY());
        tele.addData("turn left speed X:", rightWheelCoord/*.turn(0.5 * PI).getX());*/
        //tele.addData("turn left speed Y:", rightWheelCoord/*.turn(0.5 * PI)*/.getY());
        //tele.addData("turn speed:", turnSpeed);
        tele.update();
    }

    public void goForward( double speed ) {
        rightModule.applyVector(speed, 0);
        leftModule.applyVector(speed, 0);
    }

    public void setWheelsDefault() {
        rightModule.setDirection(0);
        leftModule.setDirection(0);
    }

    public void rotateModules( double speed ) {
        rightModule.applyVector(0, speed);
        leftModule.applyVector(0, speed);
    }

    public void stopDrivetrain() {
        rightModule.applyVector(0, 0);
        leftModule.applyVector(0, 0);
    }

    public void straightGoTo(Pose2d targetPose, Pose2d errors, auto_PID calculator, SampleMecanumDrive drive, LinearOpMode lop){
        isParked = false;
        Pose2d myPose;
        Pose2d relocation;
        while (!isParked && lop.opModeIsActive()){
            drive.update();
            myPose = drive.getPoseEstimate();
            relocation = calculator.calculate_speeds(targetPose, myPose, 1);
            telemetry.addData("SpeedX:", calculator.getSpeedX());
            telemetry.addData("SpeedY:", calculator.getSpeedY());
            telemetry.addData("Rotation:", calculator.getRotation());
            telemetry.addData("IsParked:", isParked);
            telemetry.update();
            if (Math.abs(calculator.getErrorX()) <= errors.getX() && Math.abs(calculator.getErrorY()) <= errors.getY() && Math.abs(calculator.getErrorHeading()) <= errors.getHeading())
                isParked = true;
            applySpeedFieldCentric(new vec2(relocation.getX(), relocation.getY()), relocation.getHeading(), myPose.getHeading());
        }
        applySpeed(new vec2(0), 0, telemetry);
    }
}
