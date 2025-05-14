package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.TankDrive;

public final class SplineTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

            waitForStart();
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineTo(new Vector2d(48, 48), Math.PI / 2)
                            .splineTo(new Vector2d(0, 96), Math.PI)
                            .splineTo(new Vector2d(-48, 48), 3 * Math.PI / 2)
                            .splineTo(new Vector2d(0, 0), 0)
                            .build());
            FtcDashboard dashboard = FtcDashboard.getInstance();

            while (opModeIsActive()) {
                TelemetryPacket packet = new TelemetryPacket();
                Canvas fieldOverlay = packet.fieldOverlay();

                Pose2d pose = drive.localizer.getPose(); // or however you access current robot pose

                // Draw current pose
                fieldOverlay.setStroke("blue");
                fieldOverlay.strokeCircle(pose.position.x, pose.position.y, 3);
                fieldOverlay.strokeLine(
                        pose.position.x,
                        pose.position.y,
                        pose.position.x + 10 * Math.cos(pose.heading.real),
                        pose.position.y + 10 * Math.sin(pose.heading.real)
                );

                dashboard.sendTelemetryPacket(packet);
                sleep(20);
            }

        } else if (TuningOpModes.DRIVE_CLASS.equals(TankDrive.class)) {
            TankDrive drive = new TankDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineTo(new Vector2d(30, 30), Math.PI / 2)
                            .splineTo(new Vector2d(0, 60), Math.PI)
                            .build());
        } else {
            throw new RuntimeException();
        }
    }
}
