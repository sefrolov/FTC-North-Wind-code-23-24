package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.maths.vec2;

import org.firstinspires.ftc.teamcode.RobotNW;
import org.firstinspires.ftc.teamcode.autonomous.CommonAutonomousTrajectory;

@Autonomous(name = "Auto_0111")
public class Auto_0111 extends LinearOpMode {
    RobotNW Robot = new RobotNW();
    ElapsedTime timer = new ElapsedTime();
    String prop_pos = "";

    CommonAutonomousTrajectory comTraj = new CommonAutonomousTrajectory(Robot, timer, this);

    CommonAutonomousActions comAct = new CommonAutonomousActions(Robot, timer, this);

    public void runOpMode() throws InterruptedException {
        Robot.init(hardwareMap, telemetry, this, "Blue");
        telemetry.addData("", "Init complited");
        telemetry.update();

        waitForStart();

        //Robot.servo.setPosition(0.16);

        while (opModeIsActive()) {
            center(telemetry);
        }
    }

    private void center(Telemetry telemetry) {
        timer.reset();
        Robot.DD.rightModule.applyVector(1, -0.08);
        telemetry.addData("", Robot.DD.rightModule.cur_dir);
        telemetry.update();
        while (timer.milliseconds() < 3000 && !isStopRequested()){
            telemetry.addData("X:", Robot.DD.rightModule.cur_dir.getX());
            telemetry.addData("Y:", Robot.DD.rightModule.cur_dir.getY());
            telemetry.update();
        }
        Robot.DD.rightModule.applyVectorPTele(new vec2(0, -0.6), telemetry);
        timer.reset();
        while (timer.milliseconds() < 500 && !isStopRequested());
        Robot.DD.rightModule.applyVectorPTele(new vec2(0, 0), telemetry);
        timer.reset();
        while (timer.milliseconds() < 100 && !isStopRequested());
        while (!isStopRequested());
    }
}