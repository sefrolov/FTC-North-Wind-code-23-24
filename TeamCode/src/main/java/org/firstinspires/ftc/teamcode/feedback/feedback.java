package org.firstinspires.ftc.teamcode.feedback;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.R;

public class feedback {
    Gamepad gamepad;

    public feedback(Gamepad gp){
        gamepad = gp;
    }

    public void runEffectEndgameStart(){
        Gamepad.LedEffect Leffect = new Gamepad.LedEffect.Builder()
                .addStep(1, 0, 0, 300)
                .addStep(0, 0, 0, 300)
                .addStep(0, 1, 0, 300)
                .addStep(0, 0, 0, 300)
                .addStep(0, 0, 1, 300)
                .addStep(0, 0, 0, 300)
                .addStep(1, 1, 0, 300)
                .addStep(0, 0, 0, 300)
                .addStep(1, 0, 1, 300)
                .addStep(0, 0, 0, 300)
                .build();

        Gamepad.RumbleEffect Reffect = new Gamepad.RumbleEffect.Builder()
                .addStep(1, 1, 100)
                .addStep(0, 0, 300)
                .addStep(1, 1, 100)
                .addStep(0, 0, 300)
                .build();

        gamepad.runLedEffect(Leffect);
        gamepad.runRumbleEffect(Reffect);
    }

    public void runEffectDisabled(){
        gamepad.rumble(500);
        gamepad.setLedColor(1, 0, 0, 500);
    }

    public void runEffectCoordsUpdated(){
        gamepad.rumble(200);
        gamepad.setLedColor(0, 0, 1, 200);
    }

    public void setColor(double r, double g, double b){
        gamepad.setLedColor(r, g, b, 300000);
    }

    public void rumble(){
        gamepad.rumble(300000);
    }

    public void stopRumble(){
        gamepad.stopRumble();
    }
}
