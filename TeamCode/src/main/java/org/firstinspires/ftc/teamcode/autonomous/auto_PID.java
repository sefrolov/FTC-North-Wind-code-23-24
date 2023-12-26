package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.maths.vec2;

public class auto_PID {
    double Ix, Dx, Px, Iy, Dy, Py, IHeading, DHeading, PHeading;

    double kPx = 0.0195;
    double kDx = 0.12;
    double kIx = 0.0018;

    double kPy = 0.0195;
    double kDy = 0.12;
    double kIy = 0.0018;

    double kPHeading = 0.135;
    double kDHeading = 0.135;
    double kIHeading = 0.03;

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

    vec2 relocation = new vec2(0);

    void init(double erx, double ery, double erh){
        errorX = 0;
        errorOldX = erx;

        errorY = 0;
        errorOldY = ery;

        errorHeading = 0;
        errorOldHeading = erh;

        targetX = 0;
        targetY = 0;
        targetHeading = 0;

        currentX = 0;
        currentY = 0;
        currentHeading = 0;

        speedX = 0;
        speedY = 0;
        rotation = 0;
    }

    void reset(double erx, double ery, double erh){
        errorX = 0;
        errorOldX = erx;

        errorY = 0;
        errorOldY = ery;

        errorHeading = 0;
        errorOldHeading = erh;

        targetX = 0;
        targetY = 0;
        targetHeading = 0;

        currentX = 0;
        currentY = 0;
        currentHeading = 0;

        speedX = 0;
        speedY = 0;
        rotation = 0;

        Ix = Dx = Px = Iy = Dy = Py = IHeading = DHeading = PHeading = 0;
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

        speedX = Px * kPx + Dx * kDx + Ix * kIx;
        speedY = Py * kPy + Dy * kDy + Iy * kIy;
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
