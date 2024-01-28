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
    public static int HANGER_TICS = 0;
    public static int elevator_right = 0;

    public static void transferData(Pose2d loc, double tics_left, double tics_right, int elevator_l, int elevator_r, int hanger_tics){
        location = loc;
        TICS_LEFT = tics_left;
        TICS_RIGHT = tics_right;
        elevator_left = elevator_l;
        elevator_right = elevator_r;
        HANGER_TICS = hanger_tics;
    }
}

