package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public interface auto_constants {
    /* BLUE */
    public static final Pose2d BLUE_LEFT_START = new Pose2d(-63.38, 15.14, Math.toRadians(180));
    public static final Pose2d BLUE_RIGHT_START = new Pose2d(-63.38, -39.14, Math.toRadians(180));
    public static final Pose2d BLUE_LEFT_LEFT_SPIKE = new Pose2d(-30.93, 31.9, Math.toRadians(270));
    public static final Pose2d BLUE_LEFT_CENTER_SPIKE = new Pose2d(-32.5, 20, Math.toRadians(315));

    public static final Pose2d BLUE_LEFT_RIGHT_SPIKE_WAYPOINT = new Pose2d(-30.93, 24, Math.toRadians(270));
    public static final Pose2d BLUE_LEFT_RIGHT_SPIKE = new Pose2d(-30.93, 11, Math.toRadians(270));
    public static final Pose2d BLUE_RIGHT_LEFT_SPIKE = new Pose2d(-30.93, -33.9, Math.toRadians(90));
    public static final Pose2d BLUE_RIGHT_CENTER_SPIKE = new Pose2d(-36, -36, Math.toRadians(0));
    public static final Pose2d BLUE_RIGHT_RIGHT_SPIKE = new Pose2d(-30.93, -57.9, Math.toRadians(90));
    public static final Pose2d BLUE_LEFT_DROP = new Pose2d(-43, 50.5, Math.toRadians(270));
    public static final Pose2d BLUE_CENTER_DROP = new Pose2d(-36.8, 50.8, Math.toRadians(270));
    public static final Pose2d BLUE_RIGHT_DROP = new Pose2d(-30.5, 50.55, Math.toRadians(270));
    public static final Pose2d BLUE_FINAL_ZONE = new Pose2d(-12, 48, Math.toRadians(270));

    /* RED */
    public static final Pose2d RED_LEFT_START = new Pose2d(63.38, -39.14, Math.toRadians(0));
    public static final Pose2d RED_RIGHT_START = new Pose2d(63.38, 15.14, Math.toRadians(0));
    public static final Pose2d RED_LEFT_LEFT_SPIKE = new Pose2d(30.93, -57.9, Math.toRadians(90));
    public static final Pose2d RED_LEFT_CENTER_SPIKE = new Pose2d(36, -36, Math.toRadians(180));
    public static final Pose2d RED_LEFT_RIGHT_SPIKE = new Pose2d(30.93, -33.9, Math.toRadians(90));
    public static final Pose2d RED_RIGHT_LEFT_SPIKE = new Pose2d(30.93, 11.6, Math.toRadians(270));
    public static final Pose2d RED_RIGHT_CENTER_SPIKE = new Pose2d(25.5, 20, Math.toRadians(270));

    public static final Pose2d RED_RIGHT_LEFT_SPIKE_WAYPOINT = new Pose2d(30.93, 24, Math.toRadians(270));
    public static final Pose2d RED_RIGHT_RIGHT_SPIKE = new Pose2d(30.93, 31.9, Math.toRadians(270));
    public static final Pose2d RED_LEFT_DROP = new Pose2d(30.5, 51.4, Math.toRadians(270));
    public static final Pose2d RED_CENTER_DROP = new Pose2d(36.5, 52, Math.toRadians(270));
    public static final Pose2d RED_RIGHT_DROP = new Pose2d(41, 52.5, Math.toRadians(270));
    public static final Pose2d RED_FINAL_ZONE = new Pose2d(11, 48, Math.toRadians(270));
}
