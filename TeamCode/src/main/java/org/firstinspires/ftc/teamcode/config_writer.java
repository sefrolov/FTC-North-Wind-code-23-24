package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.autonomous.auto_constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@TeleOp(name = "config_writer")
public class config_writer extends LinearOpMode {
    BufferedReader reader;

    String parking_zone = "wall";
    float delay = 0;
    int selection = 1;



    {
        try {
            FileReader fr = new FileReader("configs/config.txt");
            reader = new BufferedReader(fr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void readConfig(){
        try {
            parking_zone = reader.readLine();
            delay = Float.parseFloat(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void writeConfig(){
        FileWriter fw;
        try {
            fw = new FileWriter("configs/config.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fw.write(parking_zone);
            fw.write(String.valueOf(delay));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Gamepad currentGamepad1 = new Gamepad();
    Gamepad PreviousGamepad1 = new Gamepad();
    @Override
    public void runOpMode() throws InterruptedException {
        readConfig();
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (opModeIsActive()) {
            PreviousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);

            if (selection == 1) {
                if (currentGamepad1.dpad_right && !PreviousGamepad1.dpad_right) {
                    if (parking_zone.equals("wall"))
                        parking_zone = "center";
                    else if (parking_zone.equals("center"))
                        parking_zone = "wall";
                } else if (currentGamepad1.dpad_down && !PreviousGamepad1.dpad_down)
                    selection = 2;
            }
            if (selection == 2) {
                if (gamepad1.dpad_right)
                    delay++;
                else if (gamepad1.dpad_left)
                    delay--;
                if (delay < 0)
                    delay = 0;
                else if (delay > 23000)
                    delay = 23000;
                else if (currentGamepad1.dpad_up && !PreviousGamepad1.dpad_up)
                    selection = 1;
            }
            if (selection == 1)
                telemetry.addData("(*) zone:", parking_zone);
            else
                telemetry.addData("zone:", parking_zone);
            if (selection == 2)
                telemetry.addData("(*) delay:", delay);
            else
                telemetry.addData("delay:", delay);
            telemetry.update();
        }
        writeConfig();
    }
}
