package org.firstinspires.ftc.teamcode.tele_movement;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.autonomous.auto_constants;
import org.opencv.core.Mat;

public class op_container {
    public static Pose2d location = new Pose2d(-60, 12, Math.toRadians(180));
    public static double TICS_LEFT = 0;
    public static double TICS_RIGHT = 0;
    public static boolean blue = true;

    public static int elevator_left = 0;
    public static void transferData(Pose2d loc, double tics_left, double tics_right, int elevator_l){
        location = loc;
        TICS_LEFT = tics_left;
        TICS_RIGHT = tics_right;
        elevator_left = elevator_l;

    }
}

