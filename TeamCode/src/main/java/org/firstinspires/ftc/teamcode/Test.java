package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(group="TeleOp", name="Test")
public class Test extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        waitForStart();
        Localizer localizer = new PinpointLocalizer(hardwareMap, 1, new Pose2d(0, 0, 0));
        while (opModeIsActive()) {
            localizer.update();
            Log.d("Localizer Pose", "" + localizer.getPose());
            telemetry.addData("Pose X", localizer.getPose().position.x);
            telemetry.addData("Pose Y", localizer.getPose().position.y);
            telemetry.addData("Pose H", localizer.getPose().heading.real);
            telemetry.update();
        }
    }
}
