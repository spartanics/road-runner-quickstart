package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Elbow;
import org.firstinspires.ftc.teamcode.hardware.Mouth;
import org.firstinspires.ftc.teamcode.hardware.Neck;
import org.firstinspires.ftc.teamcode.hardware.Viper;
import org.firstinspires.ftc.teamcode.hardware.Wrist;

// I AM DOCTOR IVO ROBOTNIK!
@TeleOp(name = "scrimTeleOp", group = "OpModes")
public class scrimTeleOp extends OpMode {

    double speed;
    Elbow elbow;
    Mouth mouth;
    Neck neck;
    Viper viper;
    Wrist wrist;
    // Assuming you're using StandardTrackingWheelLocalizer.java
    // Switch this class to something else (Like TwoWheeTrackingLocalizer.java) if your configuration is different
    MecanumDrive drive;

    // Read pose
    Pose2d poseEstimate;
    Vector2d input;

    // Create a vector from the gamepad x/y inputs
// Then, rotate that vector by the inverse of that heading
    private void gamepadToMovement() {
        double xDir = -gamepad1.left_stick_x * speed;
        double yDir = -gamepad1.left_stick_y * speed;
        double heading = drive.pose.heading.toDouble();
        double vectorLength = Math.sqrt(Math.pow(xDir, 2) + Math.pow(yDir, 2));

        //rotate the vector
        double transXDir = vectorLength * Math.cos(heading);
        double transYDir = vectorLength * Math.sin(heading);;

        input = new Vector2d(
                transYDir,
                transXDir
        );
    }

// Pass in the rotated input + right stick value for rotation
// Rotation is not part of the rotated input thus must be passed in separately

    private void checkForSpeedChange() {
        if (gamepad1.dpad_right) {
            speed = 1;
        }
        if (gamepad1.dpad_up) {
            speed = 0.75;
        }
        if (gamepad1.dpad_left) {
            speed = 0.5;
        }
        if (gamepad1.dpad_down) {
            speed = 0.25;
        }

    }


    @Override
    public void init() {

        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        elbow = new Elbow(this);
        mouth = new Mouth(this);
        neck = new Neck(this);
        viper = new Viper(this);
        wrist = new Wrist(this);

        elbow.init();
        mouth.init();
        neck.init();
        viper.init();
        wrist.init();

        speed = 1;
    }

    @Override
    public void loop() {
        drive.updatePoseEstimate();
        poseEstimate = drive.pose;



        checkForSpeedChange();
        gamepadToMovement();
        drive.setDrivePowers(
                new PoseVelocity2d(
                        input, -gamepad1.right_stick_x
                )
        );

        elbow.listen();
        mouth.listen();
        neck.listen();
        viper.listen();
        wrist.listen();

        elbow.sendTelemetry();
        mouth.sendTelemetry();
        neck.sendTelemetry();
        viper.sendTelemetry();
        wrist.sendTelemetry();
        updateTelemetry(telemetry);

    }

    @Override
    public void init_loop(){}
    @Override
    public void start(){}
    @Override
    public void stop(){}

}
