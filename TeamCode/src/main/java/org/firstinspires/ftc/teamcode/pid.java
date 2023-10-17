
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class pid  {
    // double tP = 0.01, tD = 0.0;
    static int err, errpr;

    static double d, p, pwr, tP = 0.0096, tD = 0.0072;

    static void liftGo(int gel_zn, DcMotor motor){
            err = gel_zn - motor.getCurrentPosition();
            p = err * tP;
            d = (err - errpr) * tD;
            pwr = p + d;
            motor.setPower(pwr);
            errpr = err;

   /*         err = gel_zn - motor.getCurrentPosition();
            errpr = err;
            if (errpr > 0){
                p = err * tP;
                d = (err - errpr) * tD;
                pwr = p + d;
                motor.setPower(0);
            }else{
                p = err * tP;
                d = (err - errpr) * tD;
                pwr = p + d;
                motor.setPower(pwr);
            }*/




            /*double Er = motor.getCurrentPosition() / 751.8 * 360 - ang, ErPrev;
            //751.8 : 360 - кол-во градусов за 1 тик
            //motor.motor.getCurrentPosition() - кол-во тиков
            //кол-во градусов общее =  кол-во градусов на 1 тик * кол-во тиков
            double p, d = 1, pwr = 1;
            ErPrev = Er;
            Er = motor.getCurrentPosition() / 751.8 * 360 - ang;
            p = Er * tP;
            d = (Er - ErPrev) * tD;
            pwr = p + d;
            motor.setPower(pwr);*/
        }
    }

