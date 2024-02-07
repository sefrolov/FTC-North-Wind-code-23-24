package org.firstinspires.ftc.teamcode.CVision;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

public class Detector {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private String TFOD_MODEL_ASSET = "model_meta.tflite";
    private static final String[] LABELS = {
            "prop",
    };

    private TfodProcessor tfod;
    private VisionPortal visionPortal;

    boolean default_left = false;
    WebcamName camera;

    public void init(HardwareMap HM, Telemetry tele, String color, boolean def_left){
        if (color.equals("Red"))
            TFOD_MODEL_ASSET = "red_model_meta.tflite";
        else if (color.equals("Blue")) {
            TFOD_MODEL_ASSET = "model_meta.tflite";
            tele.addData("", "Blue");
            tele.update();
        }
        else {
            tele.addData("TFOD", "unknown color");
            tele.update();
        }
        initTfod(HM);

        default_left = def_left;
    }

    private void initTfod(HardwareMap HM) {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                //   Use setModelAssetName() if the custom TF Model is built in as an asset (AS only).
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                .setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to
                // set parameters for custom models.
                .setModelLabels(LABELS)
                .setIsModelTensorFlow2(true)
                .setIsModelQuantized(true)
                .setModelInputSize(640)
                .setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            camera = HM.get(WebcamName.class, "Webcam 1");
            builder.setCamera(camera);
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
        builder.setAutoStopLiveView(true);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        tfod.setMinResultConfidence(0.7f);

        // Disable or re-enable the TFOD processor at any time.
        visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    public void restart_streaming(HardwareMap hm){
        visionPortal.setProcessorEnabled(tfod, false);
        visionPortal.setProcessorEnabled(tfod, true);
    }
    public String getPosition(Telemetry tele){
        List<Recognition> currentRecognitions = tfod.getRecognitions();

        tele.addData("", "here1");
        tele.addData("model", TFOD_MODEL_ASSET);
        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2 ;

            if (x <= 670) {
                tele.addData("", "Left");
                tele.update();
                return "Left";
            }
            else if (x <= 1320)
                return "Center";
            else
                return "Right";
        }
        if (!default_left)
            return "Right"; //left
        return "Right";
    }

    public void StopStreaming(){
        visionPortal.stopStreaming();
    }
}
