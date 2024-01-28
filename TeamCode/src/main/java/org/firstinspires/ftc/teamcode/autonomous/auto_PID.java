package org.firstinspires.ftc.teamcode.autonomous;

import static java.lang.Math.PI;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.maths.vec2;

public class auto_PID {
    double Ix, Dx, Px, Iy, Dy, Py, IHeading, DHeading, PHeading;

    public double kPtrans = 0.0205;
    public double kDtrans = 0.117;
    public double kItrans = 0.0021;

    public double kPHeading = 0.135;

    public double kDHeading = 0.133;
    public double kIHeading = 0.033;

    double errorX;
    double errorOldX;

    double errorY;
    double errorOldY;

    double errorHeading;
    double errorOldHeading;
    double targetX;
    double targetY;
    double targetHeading;

    double currentX;
    double currentY;
    double currentHeading;

    double speedX;
    double speedY;
    double rotation;

    public double errXStart;
    public double errYStart;

    vec2 relocation = new vec2(0);

    public void init(Pose2d targetPos, Pose2d curPos){
        errorX = 0;
        errorOldX = targetPos.getX() - curPos.getX();

        errorY = 0;
        errorOldY = targetPos.getY() - curPos.getY();

        errorHeading = 0;
        errorOldHeading = targetPos.getHeading() - curPos.getHeading();

        targetX = 0;
        targetY = 0;
        targetHeading = 0;

        currentX = 0;
        currentY = 0;
        currentHeading = 0;

        speedX = 0;
        speedY = 0;
        rotation = 0;

        errXStart = errorOldX;
        errYStart = errorOldY;

        Ix = Dx = Px = Iy = Dy = Py = IHeading = DHeading = PHeading = 0;
    }
    public void reset(Pose2d targetPos, Pose2d curPos){
        init(targetPos, curPos);
    }

    public Pose2d calculate_speeds(Pose2d target, Pose2d current, double speed){
        targetX = target.getX();
        targetY = target.getY();
        targetHeading = target.getHeading();
        currentX = current.getX();
        currentY = current.getY();
        currentHeading = current.getHeading();

        errorX = targetX - currentX;
        errorY = targetY - currentY;
        errorHeading = targetHeading - currentHeading;

        if (Math.abs(errorHeading) > PI && errorHeading > 0)
            errorHeading = errorHeading - 2 * PI;
        else if (Math.abs(errorHeading) > PI && errorHeading < 0)
            errorHeading = 2 * PI + errorHeading;

        relocation.set(errorX, errorY);

        Px = errorX;
        Py = errorY;
        PHeading = errorHeading;

        if (Math.abs(errorX) <= 6)
            Ix += errorX;
        if (Math.abs(errorY) <= 6)
            Iy += errorY;
        if (Math.abs(errorHeading) <= 0.2 && relocation.len() <= 4)
            IHeading += errorHeading;

        Dx = errorX - errorOldX;
        Dy = errorY - errorOldY;
        DHeading = errorHeading - errorOldHeading;

        if (errorX * errorOldX < 0)
            Ix = 0;
        if (errorY * errorOldY < 0)
            Iy = 0;
        if (errorHeading * errorOldHeading < 0)
            IHeading = 0;

        speedX = Px * kPtrans + Dx * kDtrans + Ix * kItrans;
        speedY = Py * kPtrans + Dy * kDtrans + Iy * kItrans;
        rotation = PHeading * kPHeading + DHeading * kDHeading + IHeading * kIHeading;

        if (Math.abs(speedX) > 1 || Math.abs(speedY) > 1 || Math.abs(rotation) > 1){
            double koef = 1 / Math.max(Math.max(speedX, speedY), rotation);

            speedX *= koef;
            speedY *= koef;
            rotation *= koef;
        }
        speedX *= speed;
        speedY *= speed;
        rotation *= speed;

        errorOldX = errorX;
        errorOldY = errorY;
        errorOldHeading = errorHeading;
        return new Pose2d(speedX, speedY, rotation);
    }

    public Pose2d calculate_speeds_noslow(Pose2d target, Pose2d current, double speed){
        targetX = target.getX();
        targetY = target.getY();
        targetHeading = target.getHeading();
        currentX = current.getX();
        currentY = current.getY();
        currentHeading = current.getHeading();

        errorX = targetX - currentX;
        errorY = targetY - currentY;
        errorHeading = targetHeading - currentHeading;

        if (Math.abs(errorHeading) > PI && errorHeading > 0)
            errorHeading = errorHeading - 2 * PI;
        else if (Math.abs(errorHeading) > PI && errorHeading < 0)
            errorHeading = 2 * PI + errorHeading;

        relocation.set(errorX, errorY);

        Px = errorX;
        Py = errorY;
        PHeading = errorHeading;

        if (Math.abs(errorX) <= 6)
            Ix += errorX;
        if (Math.abs(errorY) <= 6)
            Iy += errorY;
        if (Math.abs(errorHeading) <= 0.2 && relocation.len() <= 4)
            IHeading += errorHeading;

        Dx = errorX - errorOldX;
        Dy = errorY - errorOldY;
        DHeading = errorHeading - errorOldHeading;

        if (errorX * errorOldX < 0)
            Ix = 0;
        if (errorY * errorOldY < 0)
            Iy = 0;
        if (errorHeading * errorOldHeading < 0)
            IHeading = 0;

        speedX = Px * kPtrans + Dx * kDtrans + Ix * kItrans;
        speedY = Py * kPtrans + Dy * kDtrans + Iy * kItrans;
        rotation = PHeading * kPHeading + DHeading * kDHeading + IHeading * kIHeading;

        if (Math.abs(speedX) < 0.6 || Math.abs(speedY) < 0.6 || Math.abs(rotation) < 0.6)
        {
            double koef = 0.8 / Math.abs((Math.min(Math.min(Math.abs(rotation), Math.abs(speedX)), Math.abs(speedY))));

            speedX *= koef;
            speedY *= koef;
            rotation *= koef;
        }

        if (Math.abs(speedX) > 1 || Math.abs(speedY) > 1 || Math.abs(rotation) > 1){
            double koef = 1 / Math.max(Math.max(speedX, speedY), rotation);

            speedX *= koef;
            speedY *= koef;
            rotation *= koef;
        }

        speedX *= speed;
        speedY *= speed;
        rotation *= speed;

        errorOldX = errorX;
        errorOldY = errorY;
        errorOldHeading = errorHeading;
        return new Pose2d(speedX, speedY, rotation);
    }

    public void incrkPtrans(){
        kPtrans += 0.0005;
    }

    public void incrkDtrans(){
        kDtrans += 0.002;
    }

    public void incrkItrans(){
        kItrans += 0.00005;
    }

    public void incrkPheading(){
        kPHeading += 0.0005;
    }

    public void incrkDheading(){
        kDHeading += 0.0005;
    }

    public void incrkIheading(){
        kIHeading += 0.004;
    }

    public void decrkPtrans(){
        kPtrans -= 0.0005;
    }

    public void decrkDtrans(){
        kDtrans -= 0.002;
    }

    public void decrkItrans(){
        kItrans -= 0.00005;
    }

    public void decrkPheading(){
        kPHeading -= 0.0005;
    }

    public void decrkDheading(){
        kDHeading -= 0.0005;
    }

    public void decrkIheading(){
        kIHeading -= 0.004;
    }

    public double getSpeedX(){
        return this.speedX;
    }

    public double getSpeedY(){
        return this.speedY;
    }

    public double getRotation(){
        return this.rotation;
    }

    public double getErrorX(){
        return this.errorX;
    }

    public double getErrorY(){
        return this.errorY;
    }

    public double getErrorHeading(){
        return this.errorHeading;
    }
}
