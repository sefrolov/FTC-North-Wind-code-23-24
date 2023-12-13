package org.firstinspires.ftc.teamcode.diff_sverwe;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.signum;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;

import org.checkerframework.checker.units.qual.Speed;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.maths.vec2;

public class module {
    public DcMotor downMotor = null;
    public DcMotor upMotor = null;
    double TICS_PER_REV, p_coef_turn, cur_speed = 0, target_speed = 0;
    public vec2 cur_dir = new vec2(0, 1), target_dir = new vec2(0);
    public void init(HardwareMap HM, String DownMotorName, String UpMotorName, double TPR, double coef) {
        // init of downMotor
        downMotor = HM.get(DcMotor.class, DownMotorName);
        downMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        downMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        downMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        downMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // init of upMotor
        upMotor = HM.get(DcMotor.class, UpMotorName);
        upMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        upMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        upMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        TICS_PER_REV = TPR;
        p_coef_turn = coef;
    }

    /* function to apply power to both motors. first argument is <double> downMotorPower; second argument is <double> upMotorPower */
    void applyMotorsPowers(double downMotorPower, double upMotorPower){
        downMotor.setPower(downMotorPower);
        upMotor.setPower(upMotorPower);
    }

    /* function to apply power to only one motor. first argument is <String> motor location (down, Down, 0, up, Up, 1); second argument is <double> power */
    void applyMotorPower(String motor, double power){
        if (motor.equals("up") || motor.equals("Up") || motor.equals("1"))
            upMotor.setPower(power);
        else if (motor.equals("down") || motor.equals("Down") || motor.equals("0"))
            downMotor.setPower(power);
    }

    public void applyVector(double speed, double rotation){
        vec2 vector = new vec2(speed, rotation);
        vec2 UpMotorVector = new vec2(sin(Math.toRadians(45)), sin(Math.toRadians(45)));
        vec2 DownMotorVector = new vec2(-sin(Math.toRadians(45)), sin(Math.toRadians(45)));
        //vec2 RotLin = new vec2(0, 0);
        //double cosA;

        /*
        vector.turn(vector.DegToRad(45));
        */

        /* RotLin.set(vector.scalMul(DownMotorVector) / 1, vector.scalMul(UpMotorVector) / 1); */

        vec2 tmpvec = vector;
        vec2 tmpvec2 = vector;
        System.out.println("cur speed:" + speed);
        System.out.println("before new vector set:" + vector.getX());
        vector.set(tmpvec.scalMul(DownMotorVector) / 1, tmpvec2.scalMul(UpMotorVector) / 1);
        System.out.println("after new vector set:" + vector.getX());

        vector.mul(4 / Math.sin(toRadians(45)));
        vector = vector.normalize();

        downMotor.setPower(vector.getX());
        upMotor.setPower(vector.getY());
    }

    public void applyVectorTele(double speed, double rotation, Telemetry tele){
        vec2 vector = new vec2(speed, rotation);
        vec2 UpMotorVector = new vec2(sin(Math.toRadians(45)), sin(Math.toRadians(45)));
        vec2 DownMotorVector = new vec2(-sin(Math.toRadians(45)), sin(Math.toRadians(45)));
        //vec2 RotLin = new vec2(0, 0);
        //double cosA;

        /*
        vector.turn(vector.DegToRad(45));
        */

        /* RotLin.set(vector.scalMul(DownMotorVector) / 1, vector.scalMul(UpMotorVector) / 1); */

        vec2 tmpvec = vector;
        vec2 tmpvec2 = vector;
        System.out.println("cur speed:" + speed);
        System.out.println("before new vector set:" + vector.getX());
        vector.set(tmpvec.scalMul(DownMotorVector) / 1, tmpvec2.scalMul(UpMotorVector) / 1);
        System.out.println("after new vector set:" + vector.getX());

        vector.mul(4 / Math.sin(toRadians(45)));
        vector = vector.normalize();

        downMotor.setPower(vector.getX());
        upMotor.setPower(vector.getY());

        /*
        tele.addData("upMotorHuy", upMotor.getCurrentPosition());
        tele.addData("TPR", TICS_PER_REV);
        tele.update();
        */


        /*
        downMotor.setPower(RotLin.getX());
        upMotor.setPower(RotLin.getY());
        */
        //motor2.power =
    }

    public double getDirection(){
        return (upMotor.getCurrentPosition() / TICS_PER_REV * 2 * PI) % (2 * PI);
    }

    public double getDifference(){
        cur_dir = new vec2(cos(getDirection() + PI * 0.5), sin(getDirection() + PI * 0.5));
        return acos(target_dir.scalMul(cur_dir) / cur_dir.len() / target_dir.len());
    }

    public void applyVectorP(vec2 dir){
        dir.normalize();

        cur_dir = new vec2(cos(getDirection() + PI * 0.5), sin(getDirection() + PI * 0.5));

        if (dir.scalMul(cur_dir) < 0){
            dir.invert();
            applyVector(-dir.len(), dir.vecMul(cur_dir) / dir.len() * p_coef_turn);
        }
        else applyVector(dir.len(), dir.vecMul(cur_dir) / dir.len() * p_coef_turn);
    }

    public void applyVectorPTele(vec2 dir, Telemetry tele){
        //dir.normalize();

        cur_dir = new vec2(cos(getDirection() + PI * 0.5), sin(getDirection() + PI * 0.5));
        target_dir = dir;
        target_speed = dir.len();
        if (dir.scalMul(cur_dir) < 0){
            dir.invert();
            target_speed *= -1;
            if (cur_speed - target_speed >= 0.02 && cur_speed >= -0.5) cur_speed -= 0.02;
            else if (cur_speed - target_speed <= -0.02 && cur_speed <= 0.5) cur_speed += 0.02;
            //else cur_speed = target_speed;
            if (getDifference() < PI / 6.) applyVectorTele(cur_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
            else applyVectorTele(cur_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
            tele.addData("negative", true);
            tele.addData("X:", cur_dir.getX());
            tele.addData("Y:", cur_dir.getY());
            tele.addData("upMotor", upMotor.getCurrentPosition());
            //tele.update();
        }
        else {
            if (cur_speed - target_speed >= 0.02 && cur_speed >= -0.5) cur_speed -= 0.02;
            else if (cur_speed - target_speed <= -0.02 && cur_speed <= 0.5) cur_speed += 0.02;
            //else cur_speed = target_speed;
            if (getDifference() < PI / 6.) applyVectorTele(cur_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
            else applyVectorTele(cur_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
        }
        tele.addData("cur_speed", cur_speed);
        tele.addData("target_speed", target_speed);
        tele.update();
        //tele.addData("upMotor", upMotor.getCurrentPosition());
    }

    public void applyVectorPTeleHard(vec2 dir, Telemetry tele){
        //dir.normalize();

        cur_dir = new vec2(cos(getDirection() + PI * 0.5), sin(getDirection() + PI * 0.5));
        target_dir = dir;
        target_speed = dir.len();
        if (dir.scalMul(cur_dir) < 0){
            dir.invert();
            target_speed *= -1;
            if (getDifference() < PI / 6.) applyVectorTele(target_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
            else applyVectorTele(target_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
            tele.addData("negative", true);
            tele.addData("X:", cur_dir.getX());
            tele.addData("Y:", cur_dir.getY());
            tele.addData("upMotor", upMotor.getCurrentPosition());
            //tele.update();
        }
        else {
            if (getDifference() < PI / 6.) applyVectorTele(target_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
            else applyVectorTele(target_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
        }
        tele.addData("target_speed", target_speed);
        tele.update();
        //tele.addData("upMotor", upMotor.getCurrentPosition());
    }

    public void applyVectorPHard(vec2 dir){
        //dir.normalize();

        cur_dir = new vec2(cos(getDirection() + PI * 0.5), sin(getDirection() + PI * 0.5));
        target_dir = dir;
        target_speed = dir.len();
        if (dir.scalMul(cur_dir) < 0){
            dir.invert();
            target_speed *= -1;
            if (getDifference() < PI / 6.) applyVector(target_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn);
            else applyVector(target_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn);
        }
        else {
            if (getDifference() < PI / 6.) applyVector(target_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn);
            else applyVector(target_speed, dir.vecMul(cur_dir) / dir.len() * p_coef_turn);
        }
    }

    /*
    public void applyVectorPTele(vec2 dir, Telemetry tele){
        //dir.normalize();

        cur_dir = new vec2(cos(getDirection() + PI * 0.5), sin(getDirection() + PI * 0.5));
        if (dir.scalMul(cur_dir) < 0){
            dir.invert();
            if (getDifference() < PI / 6.) applyVectorTele(-dir.len(), dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
            else applyVectorTele(0, dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
            tele.addData("negative", true);
            tele.addData("X:", cur_dir.getX());
            tele.addData("Y:", cur_dir.getY());
            tele.addData("upMotor", upMotor.getCurrentPosition());
            //tele.update();
        }
        else if (getDifference() < PI / 6.) applyVectorTele(dir.len(), dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
        else applyVectorTele(0, dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
        tele.addData("negative", true);
        tele.addData("cur dir", getDirection());
        tele.addData("upMotor", upMotor.getCurrentPosition());
    }*/

    public void applyVectorPTeleNoEncoder(vec2 dir, Telemetry tele){
        dir.normalize();

        if (dir.scalMul(cur_dir) < 0){
            dir.invert();
            applyVectorTele(-dir.len(), dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
            tele.addData("negative", true);
        }
        else applyVectorTele(dir.len(), dir.vecMul(cur_dir) / dir.len() * p_coef_turn, tele);
    }
}
