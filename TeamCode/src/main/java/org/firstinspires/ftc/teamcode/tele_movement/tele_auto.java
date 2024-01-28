package org.firstinspires.ftc.teamcode.tele_movement;

import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.RED_WING;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.farms_back;
import static org.firstinspires.ftc.teamcode.tele_movement.location_constants.farms_front;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.autonomous.auto_PID;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.maths.vec2;

public class tele_auto {
    public static double getPathLength(Pose2d targetPos, Pose2d curPos) {
        vec2 pathVector = new vec2(targetPos.getX() - curPos.getX(), targetPos.getY() - curPos.getY());
        return pathVector.len();
    }
    public static Pose2d findClosestFarm(Pose2d curPos) {
        double min = 200;
        Pose2d closest = curPos;
        if (curPos.getY() > 0) {
            for (Pose2d pos : farms_front) {
                if (getPathLength(pos, curPos) < min) {
                    closest = pos;
                    min = getPathLength(pos, curPos);
                }
            }
            return closest;
        }
        else {
            for (Pose2d pos : farms_back) {
                if (getPathLength(pos, curPos) < min) {
                    closest = pos;
                    min = getPathLength(pos, curPos);
                }
            }
            return closest;
        }
    }
}
