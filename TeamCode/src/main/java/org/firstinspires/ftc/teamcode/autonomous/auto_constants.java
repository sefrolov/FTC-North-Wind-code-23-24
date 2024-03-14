package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public interface auto_constants {
    /* BLUE */
    public static final Pose2d BLUE_BEFORE_DROPS = new Pose2d(-36.5, 44, Math.toRadians(270));
    public static final Pose2d BLUE_LEFT_DROP = new Pose2d(-44, 51.85, Math.toRadians(270));
    public static final Pose2d BLUE_CENTER_DROP = new Pose2d(-33.9, 52, Math.toRadians(270));
    public static final Pose2d BLUE_RIGHT_DROP = new Pose2d(-23.2, 51, Math.toRadians(270));
    public static final Pose2d BLUE_FINAL_ZONE = new Pose2d(-12, 48, Math.toRadians(270));
    public static final Pose2d BLUE_FINAL_ZONE_WALL = new Pose2d(-58, 48, Math.toRadians(270));
    public static final Pose2d BLUE_FINAL_TMP = new Pose2d(-36, 43, Math.toRadians(270));


    public static final Pose2d BLUE_LEFT_START = new Pose2d(-61.88, 15.14, Math.toRadians(180));
    public static final Pose2d BLUE_LEFT_LEFT_SPIKE = new Pose2d(-30.93, 31.2, Math.toRadians(270));
    public static final Pose2d BLUE_LEFT_CENTER_SPIKE = new Pose2d(-22, 20, Math.toRadians(270));
    public static final Pose2d BLUE_LEFT_RIGHT_SPIKE = new Pose2d(-35.3, 9.35, Math.toRadians(270));
    public static final Pose2d BLUE_LEFT_CENTER_ADDITIONAL = new Pose2d(-24, 30, Math.toRadians(270));
    public static final Pose2d BLUE_LEFT_RIGHT_SPIKE_WAYPOINT = new Pose2d(-30.93, 24, Math.toRadians(270));


    public static final Pose2d BLUE_RIGHT_START = new Pose2d(-61.88, -39.14, Math.toRadians(180));
    public static final Pose2d BLUE_RIGHT_LEFT_SPIKE = new Pose2d(-38, -37.5, Math.toRadians(90));
    public static final Pose2d BLUE_RIGHT_CENTER_SPIKE = new Pose2d(-13, -40, Math.toRadians(180));
    public static final Pose2d BLUE_RIGHT_RIGHT_SPIKE = new Pose2d(-22, -52.5, Math.toRadians(180));
    public static final Pose2d BLUE_RIGHT_LEFT_SPIKE_WAYPOINT = new Pose2d(-30.93, -38, Math.toRadians(90));


    public static final Pose2d BLUE_DOBOR_FIRST = new Pose2d(-13, -62.2, Math.toRadians(270));
    public static final Pose2d BLUE_DOBOR = new Pose2d(-13, -61, Math.toRadians(270));
    public static final Pose2d BLUE_DOBOR_BACK = new Pose2d(-13, -57, Math.toRadians(270));
    public static final Pose2d BLUE_DOBOR_ADDITIONAL = new Pose2d(-13, -48, Math.toRadians(270));
    public static final Pose2d UNDER_SCENE_BACK_BLUE = new Pose2d(-9, -40, Math.toRadians(270));
    public static final Pose2d UNDER_SCENE_FRONT_BLUE = new Pose2d(-10, 12, Math.toRadians(270));

    public static final Pose2d UNDER_SCENE_MID_BLUE = new Pose2d(-9, -12, Math.toRadians(270));


    /* RED */
    public static final Pose2d RED_BEFORE_DROPS = new Pose2d(36.5, 45, Math.toRadians(270));
    public static final Pose2d RED_LEFT_DROP = new Pose2d(30.5, 54, Math.toRadians(270));
    public static final Pose2d RED_CENTER_DROP = new Pose2d(36.5, 54, Math.toRadians(270));
    public static final Pose2d RED_RIGHT_DROP = new Pose2d(40.7, 54, Math.toRadians(270));
    public static final Pose2d RED_FINAL_ZONE = new Pose2d(14, 48, Math.toRadians(270));


    public static final Pose2d RED_LEFT_START = new Pose2d(61.88, -39.14, Math.toRadians(0));
    public static final Pose2d RED_LEFT_LEFT_SPIKE = new Pose2d(30.93, -57.9, Math.toRadians(90));
    public static final Pose2d RED_LEFT_CENTER_SPIKE = new Pose2d(36, -36, Math.toRadians(180));
    public static final Pose2d RED_LEFT_RIGHT_SPIKE = new Pose2d(30.93, -33.9, Math.toRadians(90));
    public static final Pose2d RED_LEFT_RIGHT_SPIKE_WAYPOINT = new Pose2d(-30.93, -38, Math.toRadians(90));


    public static final Pose2d RED_RIGHT_START = new Pose2d(61.88, 15.14, Math.toRadians(0));
    public static final Pose2d RED_RIGHT_LEFT_SPIKE = new Pose2d(35.3, 9.35, Math.toRadians(270));
    public static final Pose2d RED_RIGHT_CENTER_SPIKE = new Pose2d(25.5, 20, Math.toRadians(270));
    public static final Pose2d RED_RIGHT_RIGHT_SPIKE = new Pose2d(30.93, 31.2, Math.toRadians(270));
    public static final Pose2d RED_RIGHT_LEFT_SPIKE_WAYPOINT = new Pose2d(30.93, 24, Math.toRadians(270));
    public static final Pose2d RED_RIGHT_CENTER_ADDITIONAL = new Pose2d(25.5, 30, Math.toRadians(270));


}
