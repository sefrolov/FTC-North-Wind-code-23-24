package org.firstinspires.ftc.teamcode.diff_sverwe;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.maths.vec2;

public class PID_system {
    double IHeading, DHeading, PHeading;

    double kPHeading = /*0.135*/ 0.5;
    double kDHeading = 0.235;
    double kIHeading = 0.03;

    double errorHeading;
    double errorOldHeading;

    double rotation;

    vec2 relocation = new vec2(0);

    public void init(double erh){
        errorHeading = 0;
        errorOldHeading = erh;
        rotation = 0;
    }

    public void reset(double erh){
        errorHeading = 0;
        errorOldHeading = erh;
        rotation = 0;

        IHeading = DHeading = PHeading = 0;
    }

    public double calculate_speeds(double angle){
        errorHeading = angle;

        PHeading = errorHeading;

        if (Math.abs(errorHeading) <= 0.2 && relocation.len() <= 4)
            IHeading += errorHeading * kIHeading;

        DHeading = errorHeading - errorOldHeading;

        if (errorHeading * errorOldHeading < 0)
            IHeading = 0;

        rotation = PHeading * kPHeading + DHeading * kDHeading + IHeading;

        if (Math.abs(rotation) > 1) {
            rotation = 1 * Math.signum(rotation);
        }
        //rotation *= angle;

        //if (angle <= 0.01)
         //   return 0;
        errorOldHeading = errorHeading;
        return rotation;
    }
    public double getRotation(){
        return this.rotation;
    }


    public double getErrorHeading(){
        return this.errorHeading;
    }

}
