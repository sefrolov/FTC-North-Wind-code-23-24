package org.firstinspires.ftc.teamcode.lift;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.intake.intake_sensor;

public class elevator_thread extends Thread {
    public elevator LI = new elevator();
    public intake_sensor intake_sensor = new intake_sensor();

    public int target_pos = 0;


    public void run() {
        //EL.initElevator(HM);
        while (!isInterrupted()) {
            intake_sensor.checkSensor();
            switch (target_pos){
                case 0:
                    LI.setPosDown();
                    break;
                case 1:
                    LI.setPosLow();
                    break;
                case 2:
                    LI.setPosMid();
                    break;
                case 3:
                    LI.setPosHigh();
                    break;
                case 4:
                    LI.setPosAutoYellow();
                    break;
                case 10:

                    LI.setPosDown();
                    break;
                default:
                    LI.setPosDown();
                    break;
            }
        }
    }

    public void init(HardwareMap HM, Telemetry tele){
        LI.init(HM, tele);
        intake_sensor.init(HM);
    }
    public int getNumPixels(){
        return intake_sensor.getNumPixels();
    }
}
