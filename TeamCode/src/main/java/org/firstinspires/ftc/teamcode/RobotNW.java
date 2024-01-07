package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.CVision.AT_Detector;
import org.firstinspires.ftc.teamcode.diff_sverwe.DriveTrainDifferential;
import org.firstinspires.ftc.teamcode.diff_sverwe.imu_sensor;
import org.firstinspires.ftc.teamcode.intake.intake;
import org.firstinspires.ftc.teamcode.CVision.Detector;
import org.firstinspires.ftc.teamcode.outtake.outtake_flipper;
import org.firstinspires.ftc.teamcode.plane.plane;
import org.firstinspires.ftc.teamcode.lift.lift;
import org.firstinspires.ftc.teamcode.intake.intake_folding;
import org.firstinspires.ftc.teamcode.diff_sverwe.odometry;
import org.firstinspires.ftc.teamcode.outtake.outtake;
import org.firstinspires.ftc.teamcode.lift.change_over;

public class RobotNW {
    //TensorFlow camera = null;
    public DriveTrainDifferential DD = new DriveTrainDifferential();
    public intake IN = new intake();
    public Detector BD = new Detector();

    public lift LI = new lift();

    public imu_sensor IM = new imu_sensor();
    public plane PL = new plane();

    public intake_folding IF = new intake_folding();

    public odometry OD = new odometry();
    public change_over CO = new change_over();

    public outtake OT = new outtake();
    public AT_Detector AT = new AT_Detector();

    public outtake_flipper OF = new outtake_flipper();
    ElapsedTime timer = new ElapsedTime();

    public void init(HardwareMap HM, Telemetry tele, LinearOpMode lop, String... object_detection_color) {
        String ODcolor = "";
        if (object_detection_color.length > 0) {
            ODcolor = object_detection_color[0];
            BD.init(HM, tele, ODcolor); /* tf2 detector */
        }
        else
            AT.init(HM);
        DD.init(HM, tele); /* Differential drive */
        IN.init(HM); /* intake */
        PL.init(HM); /* plane */
        IM.init(HM); /* imu sensor */
        LI.init(HM); /* lift */
        IF.init(HM); /* intake folding */
        CO.init(HM); /* change over */
        /* OD.init(HM, tele, IM); */
        OT.init(HM); /* outtake */
        OF.init(HM); /* outtake flipper */


        //camera = new TensorFlow(HM,  lop,  tele, "model_unquant.tflite", "labels.txt");
    }

}