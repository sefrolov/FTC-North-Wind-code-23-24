package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.intake.intake_sensor;

public class elevator_thread extends Thread {
    public elevator LI = new elevator();
    intake_sensor intake_sensor = new intake_sensor();

    public hanger HG = new hanger();
    public int target_pos = 0;


    public void run() {
        //EL.initElevator(HM);
        while (!isInterrupted()) {
            intake_sensor.checkSensor();
            // HG.autoSetPos(LI.getPos(LI.motor_left));
            switch (target_pos){
                case 0:
                    LI.setPosDown();
                    HG.setPosDown();
                    break;
                case 1:
                    LI.setPosLow();
                    HG.setPosLow();
                    break;
                case 2:
                    LI.setPosMid();
                    HG.setPosMid();
                    break;
                case 3:
                    LI.setPosHigh();
                    HG.setPosHigh();
                    break;
                case 4:
                    LI.setPosAutoYellow();
                    HG.setPosAutoYellow();
                    break;
                case 10:
                    LI.setPosDown();
                    HG.setPosMax();
                    break;
                default:
                    LI.setPosDown();
                    HG.setPosDown();
                    break;
            }
        }
    }

    public void init(HardwareMap HM, Telemetry tele){
        HG.init(HM, tele);
        LI.init(HM, tele);
        intake_sensor.init(HM);
    }
    public int getNumPixels(){
        return intake_sensor.getNumPixels();
    }
}
