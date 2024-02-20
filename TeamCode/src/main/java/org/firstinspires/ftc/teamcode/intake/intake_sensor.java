package org.firstinspires.ftc.teamcode.intake;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class intake_sensor {
    AnalogInput sensor;
    boolean old_sensor = false;
    public int num_pixels = 0;

    public void init(HardwareMap HM){
        sensor = HM.get(AnalogInput.class, "sensor");
    }

    public void checkSensor() {
        if (!old_sensor && sensor.getVoltage() < 3)
        {
            num_pixels++;
            old_sensor = true;
        }
        else if (sensor.getVoltage() > 3)
            old_sensor = false;
    }

    public int getNumPixels(){
        return num_pixels;
    }
}
