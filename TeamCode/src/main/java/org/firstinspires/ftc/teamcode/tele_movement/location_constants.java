package org.firstinspires.ftc.teamcode.tele_movement;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public interface location_constants {
    /* BLUE */
    public static final Pose2d BLUE_LEFT_DROP = new Pose2d(-43, 50.5, Math.toRadians(270));
    public static final Pose2d BLUE_CENTER_DROP = new Pose2d(-36.8, 50.8, Math.toRadians(270));
    public static final Pose2d BLUE_RIGHT_DROP = new Pose2d(-30.5, 50.55, Math.toRadians(270));
    public static final Pose2d BLUE_WING = new Pose2d(48, -48, Math.toRadians(315));

    public static final Pose2d MPT_1 = new Pose2d(24, 36, Math.toRadians(0));

    public static final Pose2d MPT_2 = new Pose2d(-24, 36, Math.toRadians(180));

    /* RED */
    public static final Pose2d RED_LEFT_DROP = new Pose2d(30.5, 51.4, Math.toRadians(270));
    public static final Pose2d RED_CENTER_DROP = new Pose2d(36.5, 52, Math.toRadians(270));
    public static final Pose2d RED_RIGHT_DROP = new Pose2d(41, 52.5, Math.toRadians(270));
    public static final Pose2d RED_WING = new Pose2d(-48, -48, Math.toRadians(225));

    /* COMMON */

    public static final Pose2d FARM_RED_WALL_FRONT = new Pose2d(60, 12, Math.toRadians(270));
    public static final Pose2d FARM_RED_CENTER_FRONT = new Pose2d(36, 12, Math.toRadians(270));
    public static final Pose2d FARM_BLUE_CENTER_FRONT = new Pose2d(-36, 12, Math.toRadians(270));
    public static final Pose2d FARM_BLUE_WALL_FRONT = new Pose2d(-60, 12, Math.toRadians(270));

    public static final Pose2d FARM_RED_WALL_BACK = new Pose2d(60, -12, Math.toRadians(270));
    public static final Pose2d FARM_RED_CENTER_BACK = new Pose2d(36, -12, Math.toRadians(270));
    public static final Pose2d FARM_BLUE_CENTER_BACK = new Pose2d(-36, -12, Math.toRadians(270));
    public static final Pose2d FARM_BLUE_WALL_BACK = new Pose2d(-60, -12, Math.toRadians(270));

    public static final Pose2d[] farms_front = {FARM_BLUE_CENTER_FRONT, FARM_BLUE_WALL_FRONT, FARM_RED_CENTER_FRONT, FARM_RED_WALL_FRONT};
    public static final Pose2d[] farms_back = {FARM_BLUE_CENTER_BACK, FARM_BLUE_WALL_BACK, FARM_RED_CENTER_BACK, FARM_RED_WALL_BACK};
}
