package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class sensors_thread extends Thread{
    intake_sensor intake_sensor = new intake_sensor();

    public void init(HardwareMap HM){
        intake_sensor.init(HM);
    }

    public void run(){
        while (!isInterrupted()) {
            intake_sensor.checkSensor();
        }
    }

    public int getNumPixels(){
        return intake_sensor.getNumPixels();
    }
}
