package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.diff_sverwe.DriveTrainDifferential;
import org.firstinspires.ftc.teamcode.intake.Intake;
import org.firstinspires.ftc.teamcode.CVision.Detector;

public class RobotNW {
    //TensorFlow camera = null;
    public DriveTrainDifferential DD = new DriveTrainDifferential();
    public Intake IN = new Intake();
    public Detector BD = new Detector();
    ElapsedTime timer = new ElapsedTime();

    public void init(HardwareMap HM, Telemetry tele, LinearOpMode lop, String... object_detection_color){
        String ODcolor = "";
        if (object_detection_color.length > 0)
            ODcolor = object_detection_color[0];
        DD.init(HM);
        //IN.init(HM);
        BD.init(HM, tele, ODcolor);

        //camera = new TensorFlow(HM,  lop,  tele, "model_unquant.tflite", "labels.txt");
    }

    public void waiter(long time){
        timer.reset();
        while(timer.milliseconds() < time)
            ;
    }
}