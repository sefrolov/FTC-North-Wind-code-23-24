package org.firstinspires.ftc.teamcode.diff_sverwe;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class module {
    DcMotor downMotor = null;
    DcMotor upMotor = null;
    public void init(HardwareMap HM, String DownMotorName, String UpMotorName) {
        // init of downMotor
        downMotor = HM.get(DcMotor.class, DownMotorName);
        downMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        downMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        downMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        downMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // init of upMotor
        upMotor = HM.get(DcMotor.class, UpMotorName);
        upMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        upMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        upMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /* function to apply power to both motors. first argument is <double> downMotorPower; second argument is <double> upMotorPower */
    void applyMotorsPowers(double downMotorPower, double upMotorPower){
        downMotor.setPower(downMotorPower);
        upMotor.setPower(upMotorPower);
    }

    /* function to apply power to only one motor. first argument is <String> motor location (down, Down, 0, up, Up, 1); second argument is <double> power */
    void applyMotorPower(String motor, double power){
        if (motor.equals("up") || motor.equals("Up") || motor.equals("1"))
            upMotor.setPower(power);
        else if (motor.equals("down") || motor.equals("Down") || motor.equals("0"))
            downMotor.setPower(power);
    }
}
