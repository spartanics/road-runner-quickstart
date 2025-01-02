package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Claw;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Elbow;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Intake;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Shoulder;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Viper;


@Config
@Autonomous(name = "4 clip Robotnik Style", group = "Autonomous")
public class rightAutoAlt extends LinearOpMode {
    Pose2d startPose;
    MecanumDrive drive;

    Shoulder shoulder = new Shoulder(this);
    Elbow elbow = new Elbow(this);
    Intake intake = new Intake(this);
    Viper viper = new Viper(this);
    Claw claw = new Claw(this);
    
    





    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(14, -61, Math.toRadians(90));
        drive = new MecanumDrive(hardwareMap, startPose);
        TrajectoryActionBuilder build = drive.actionBuilder(startPose)
                .afterTime(0, shoulder.autonHC())
                .afterTime(0.7, viper.autonHangSpecimen())
                .strafeTo(new Vector2d(5, -30))

                //put arm up while strafing
                //stop and place the sample on the bar
                .afterTime(0, claw.autonOpenClaw())
                .afterTime(0, viper.autonSlightOut())



                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(26,-43), Math.toRadians(-90)), 0)
                .afterTime(0, shoulder.autonDown())

                .splineTo(new Vector2d(45, -13), 0)

                .strafeTo(new Vector2d(45,-53))
                //one in observation zone
                .strafeTo(new Vector2d(45,-13))
                .strafeTo(new Vector2d(55,-13))
                .strafeTo(new Vector2d(43,-59))
                .waitSeconds(0.1)
                .strafeTo(new Vector2d(46,-60))

                .afterTime(0, viper.autonSlightOut())

                .afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.3)
                .afterTime(0, shoulder.autonHC())
                //grab sample, routing towards chamber.
                //raise arm to clip
                .afterTime(1.4, viper.autonHangSpecimen())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(7, -30), Math.toRadians(90)), Math.toRadians(90))


                //clip, routing to push final sample and grab specimen

                .afterTime(0, claw.autonOpenClaw())
                .afterTime(0.1, viper.autonSlightOut())



                .afterTime(1, shoulder.autonDown())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(36, -58.5), Math.toRadians(-90)), Math.toRadians(0))

                .afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.3)
                .afterTime(0, shoulder.autonHC())
                //grab sample, routing towards chamber.
                //raise arm to clip
                .afterTime(1.5, viper.autonHangSpecimen())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(4, -30), Math.toRadians(90)), Math.toRadians(90))

                .afterTime(0, claw.autonOpenClaw())
                .afterTime(0, viper.autonSlightOut())


                .afterTime(1, shoulder.autonDown())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(36, -58.5), Math.toRadians(-90)), Math.toRadians(0))

                .afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.3)
                .afterTime(0, shoulder.autonHC())
                //grab sample, routing towards chamber.
                //raise arm to clip
                .afterTime(1.5, viper.autonHangSpecimen())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(6, -30), Math.toRadians(90)), Math.toRadians(90))

                .afterTime(0, claw.autonOpenClaw())
                .afterTime(0, viper.autonSlightOut())
                .waitSeconds(0.5)

                .setReversed(true)
                .splineTo(new Vector2d(50,-60), Math.toRadians(-90))
                .afterTime(0, shoulder.autonDown())

                ;

        shoulder.init();
        elbow.init();
        intake.init();
        viper.init();
        claw.init();

        while (!isStopRequested() && !opModeIsActive()) {
            Actions.runBlocking(new SequentialAction(
                    new InstantAction(() -> shoulder.autonListen()),
                    new InstantAction(() -> viper.autonListen()),
                    new InstantAction(() -> elbow.autonListen()),
                    new InstantAction(() -> intake.autonListen()),
                    new InstantAction(() -> claw.autonListen())
            ));

        }





        waitForStart();

        Actions.runBlocking(new ParallelAction(
                shoulder.autonListen(),
                viper.autonListen(),
                elbow.autonListen(),
                intake.autonListen(),
                claw.autonListen(),
                build.build()
        ));
        PoseStorage.storedPose = drive.pose;
    }

}