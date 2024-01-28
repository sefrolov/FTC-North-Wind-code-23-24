package org.firstinspires.ftc.teamcode.CVision;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.helpers.Helpers;
import org.firstinspires.ftc.teamcode.maths.vec2;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.opencv.core.Mat;

public class AT_Detector {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    private static final double offset_x = 0;
    private static final double offset_y = -8.66;

    static Vector2d camera1Offset = new Vector2d(
            0,
            -8.66);


    public void init(HardwareMap HM) {

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                .setDrawAxes(false)
                .setDrawCubeProjection(false)
                .setDrawTagOutline(false)
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.RADIANS)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
                // ... these parameters are fx, fy, cx, cy.

                .build();

        // Adjust Image Decimation to trade-off detection-range for detection-rate.
        // eg: Some typical detection data using a Logitech C920 WebCam
        // Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
        // Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
        // Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second (default)
        // Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second (default)
        // Note: Decimation can be changed on-the-fly to adapt during a match.
        aprilTag.setDecimation(3);

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(HM.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        builder.setCameraResolution(new Size(1920, 1080));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        builder.enableLiveView(false);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        visionPortal.setProcessorEnabled(aprilTag, true);
    }   // end method initAprilTag()

    public Pose2d getErrors(int id) {
        ArrayList<AprilTagDetection> currentDetections = aprilTag.getDetections();

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == id) {
                if (detection.metadata != null) {
                /*
                pitch - крен - самолет делает бочку
                roll - тангаж - самолет взлетает
                yaw - рыскание - самое главное - самолет поворачивает без наклона
                center.x center.y координаты центра
                bearing - горизонатльный угол между лучом из камеры вперед и лучом из камеры в центр тега
                elevation - вертикальный угол между лучом из камеры в центр тега
                range - длина вектора из центра камеры в центр тега
                 */
                    /*telemetry.addData("tf is that", detection.metadata.fieldPosition);
                    telemetry.update();*/
                    return new Pose2d(detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.bearing);
                }
            }
        }
        return new Pose2d(1000, 1000, 1000);
    }

    public Vector2d getFCPosition(AprilTagDetection detection, double botheading) {
        Vector2d cameraOffset;
        cameraOffset = camera1Offset;
        // get coordinates of the robot in RC coordinates
        // ensure offsets are RC
        double x = detection.ftcPose.x-offset_x;
        double y = detection.ftcPose.y-offset_y;

        // invert heading to correct properly
        botheading = -botheading;


        // rotate RC coordinates to be field-centric
        double x2 = x*Math.cos(botheading)+y*Math.sin(botheading);
        double y2 = x*-Math.sin(botheading)+y*Math.cos(botheading);
        double absX;
        double absY;
        // add FC coordinates to apriltag position
        // tags is just the CS apriltag library
        vec2 tagpose = Helpers.metaPosToRRcentric(detection.metadata.fieldPosition);
        if (detection.metadata.id <= 6) {
            absX = tagpose.getX() + y2;
            absY = tagpose.getY() - x2;

        } else {
            absX = tagpose.getX() - y2;
            absY = tagpose.getY() + x2; // prev -

        }
        return new Vector2d(absX, absY);
    }

    public Vector2d getRobotPos(double botHeading) {
        ArrayList<AprilTagDetection> Detections;
        Detections = aprilTag.getDetections();
        int realDetections = 0;
        Vector2d averagePos = new Vector2d(0, 0); // starting pose to add the rest to
        if (Detections.isEmpty())
            return null; // if we don't see any tags, give up (USES NEED TO HANDLE NULL)
        Vector2d RobotPos;

        // Step through the list of detections and calculate the robot position from each one.
        for (AprilTagDetection detection : Detections) {
            if (detection.metadata != null) { // && !detection.metadata.name.contains("Small")) { // TODO: Change if we want to use wall tags?
                Vector2d tagPos = Helpers.toVector2d(detection.metadata.fieldPosition); // SDK builtin tag position

                RobotPos = getRobotLocationFromTag(botHeading, detection); // calculate the robot position from the tag position
                //RobotPos = getFCPosition(detection, botHeading);

                // we're going to get the average here by adding them all up and dividingA the number of detections
                // we do this because the backdrop has 3 tags, so we get 3 positions
                // hopefully by averaging them we can get a more accurate position
                averagePos = averagePos.plus(RobotPos);
                realDetections++;
            }
        }   // end for() loop
        return averagePos.div(realDetections);
    }
    Vector2d getRobotLocationFromTag(double imuHeading, AprilTagDetection detection){
        // TODO: I don't actually know trig, this is probably terrible
        double xPos;
        double yPos;
        //if (Math.abs(Math.toDegrees((imuHeading - PARAMS.cameraYawOffset) - tagHeading)) - 5 > 0) { // if the robot isn't within half a degree of straight up
        double tagHeading = Helpers.quarternionToHeading(detection.metadata.fieldOrientation); // SDK builtin tag heading
        //double tagRelHeading = imuHeading - Math.toRadians(180) + detection.ftcPose.bearing - tagHeading;
        /*
        Vector2d camGlobalOffset = new Vector2d(
                offset_x * Math.cos(-imuHeading) - offset_y * Math.sin(-imuHeading),
                offset_x * Math.sin(-imuHeading) + offset_y * Math.cos(-imuHeading));
        xPos = tagPos.getX() - (Math.cos(tagRelHeading) * detection.ftcPose.y) - offset_x;
        yPos = tagPos.getY() - (Math.sin(tagRelHeading) * detection.ftcPose.y) - offset_y;
        */
        double tagRelHeading = Math.toRadians(90) - imuHeading - detection.ftcPose.bearing;
        vec2 tagPos = Helpers.metaPosToRRcentric(detection.metadata.fieldPosition);

        yPos = tagPos.getY() + detection.ftcPose.range * Math.cos(tagRelHeading) - offset_y * Math.cos(Math.toRadians(90) - imuHeading) + 3 * -(Math.signum(detection.id - 6.5)); /* if backdrop tags then +3; else -3 */
        xPos = tagPos.getX() + detection.ftcPose.range * Math.sin(tagRelHeading) - offset_y * Math.sin(Math.toRadians(90) - imuHeading);
        /*} else {
            xPos = (tagPos.x - detection.ftcPose.y) - PARAMS.cameraOffset.x; // TODO; this will ONLY work for the backdrop tags
            yPos = (tagPos.y - detection.ftcPose.x) - PARAMS.cameraOffset.y;
        }*/

        return new Vector2d(xPos, yPos);
    }
}

