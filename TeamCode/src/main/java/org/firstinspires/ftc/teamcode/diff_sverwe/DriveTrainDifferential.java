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
import org.firstinspires.ftc.teamcode.tele_movement.op_container;

public class DriveTrainDifferential {
    public module leftModule = new module();
    public module rightModule = new module();
    vec2 rightWheelCoord = new vec2(5, 0);
    vec2 leftWheelCoord = new vec2(-5, 0);
    double MAX_ANG_SPEED = 1. / max(rightWheelCoord.len(), leftWheelCoord.len()) * 0.13;
    vec2 turnSpeedLeft, turnSpeedRight, leftSpeed, rightSpeed;

    Telemetry telemetry;

    boolean isParked = false;
    public void init(HardwareMap HM, Telemetry tele) {
        leftModule.init(HM, "motorLD", "motorLU", 8192, 0.6, op_container.TICS_LEFT);
        rightModule.init(HM, "motorRD", "motorRU", /*1440*/1024, 0.6, op_container.TICS_RIGHT);
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
            double koef = max(rightSpeed.len(), leftSpeed.len());
            rightSpeed.mul(1. / koef);
            leftSpeed.mul(1. / koef);
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
        rightModule.applyVectorPTeleHard(rightSpeed.mul(1.0), tele);
        leftModule.applyVectorPTeleHard(leftSpeed.mul(1.0), tele);
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
        //tele.update();
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
        ElapsedTime timer = new ElapsedTime();
        double err1 = 0;
        double err2 = 0;
        boolean block_suspected = false;

        timer.reset();

        while (!isParked && lop.opModeIsActive() && Math.abs(lop.gamepad1.left_stick_x) <= 0.02 && Math.abs(lop.gamepad1.left_stick_y) <= 0.02){
            drive.update();
            myPose = drive.getPoseEstimate();
            relocation = calculator.calculate_speeds(targetPose, myPose, 1);
            if (timer.milliseconds() > 5000 && !block_suspected) {
                block_suspected = true;
                err1 = new vec2(calculator.getErrorX(), calculator.getErrorY()).len();
                timer.reset();
            }
            if (timer.milliseconds() > 3000 && block_suspected){
                err2 = new vec2(calculator.getErrorX(), calculator.getErrorY()).len();
                if (Math.abs(err2 - err1) < 5)
                    isParked = true;
            }

            /*telemetry.addData("SpeedX:", calculator.getSpeedX());
            telemetry.addData("SpeedY:", calculator.getSpeedY());
            telemetry.addData("Rotation:", calculator.getRotation());
            telemetry.addData("IsParked:", isParked);
            telemetry.update();*/
            if (Math.abs(calculator.getErrorX()) <= errors.getX() && Math.abs(calculator.getErrorY()) <= errors.getY() && Math.abs(calculator.getErrorHeading()) <= errors.getHeading())
                isParked = true;
            applySpeedFieldCentric(new vec2(relocation.getX(), relocation.getY()), relocation.getHeading(), myPose.getHeading());
        }
        applySpeed(new vec2(0, 0), 0, telemetry);
    }

    public void straightGoToNoSlow(Pose2d targetPose, Pose2d errors, auto_PID calculator, SampleMecanumDrive drive, LinearOpMode lop){
        isParked = false;
        Pose2d myPose;
        Pose2d relocation;
        ElapsedTime timer = new ElapsedTime();
        double err1 = 0;
        double err2 = 0;
        boolean block_suspected = false;

        timer.reset();

        while (!isParked && lop.opModeIsActive() && Math.abs(lop.gamepad1.left_stick_x) <= 0.02 && Math.abs(lop.gamepad1.left_stick_y) <= 0.02){
            drive.update();
            myPose = drive.getPoseEstimate();
            relocation = calculator.calculate_speeds_noslow(targetPose, myPose, 1);
            if (timer.milliseconds() > 5000 && !block_suspected) {
                block_suspected = true;
                err1 = new vec2(calculator.getErrorX(), calculator.getErrorY()).len();
                timer.reset();
            }
            if (timer.milliseconds() > 3000 && block_suspected){
                err2 = new vec2(calculator.getErrorX(), calculator.getErrorY()).len();
                if (Math.abs(err2 - err1) < 5)
                    isParked = true;
            }

            if (calculator.getErrorX() * calculator.errXStart < 0 && calculator.getErrorY() * calculator.errYStart < 0)
                isParked = true;

            /*telemetry.addData("SpeedX:", calculator.getSpeedX());
            telemetry.addData("SpeedY:", calculator.getSpeedY());
            telemetry.addData("Rotation:", calculator.getRotation());
            telemetry.addData("IsParked:", isParked);
            telemetry.update();*/
            if (Math.abs(calculator.getErrorX()) <= errors.getX() && Math.abs(calculator.getErrorY()) <= errors.getY() && Math.abs(calculator.getErrorHeading()) <= errors.getHeading())
                isParked = true;
            applySpeedFieldCentric(new vec2(relocation.getX(), relocation.getY()), relocation.getHeading(), myPose.getHeading());
        }
        //TODO: APPLY IT IN AUTONOMOUS ONLY WHERE NEEDED
    }

    public boolean straightGoToTeleop(Pose2d targetPose, Pose2d errors, auto_PID calculator, SampleMecanumDrive drive, LinearOpMode lop){
        Pose2d myPose;
        Pose2d relocation;

        myPose = drive.getPoseEstimate();
        relocation = calculator.calculate_speeds(targetPose, myPose, 1);
        /*telemetry.addData("SpeedX:", calculator.getSpeedX());
        telemetry.addData("SpeedY:", calculator.getSpeedY());
        telemetry.addData("Rotation:", calculator.getRotation());
        telemetry.addData("IsParked:", isParked);
        telemetry.update();*/
        if (Math.abs(calculator.getErrorX()) <= errors.getX() && Math.abs(calculator.getErrorY()) <= errors.getY() && Math.abs(calculator.getErrorHeading()) <= errors.getHeading())
        {
            applySpeed(new vec2(0), 0, telemetry);
            return true;
        }
        applySpeedFieldCentric(new vec2(relocation.getX(), relocation.getY()), relocation.getHeading(), myPose.getHeading());
        return false;
    }
}
