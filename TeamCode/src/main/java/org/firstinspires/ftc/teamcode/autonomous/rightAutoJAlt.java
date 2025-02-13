package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Claw;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Elbow;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Intake;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Shoulder;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Viper;

import java.util.Arrays;


@Config
@Autonomous(name = "JoJo's Bizzare 4 clip", group = "Autonomous")
public class rightAutoJAlt extends LinearOpMode {
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
                .afterTime(0.5, viper.autonHangSpecimen())

                .strafeTo(new Vector2d(6, -37), drive.halfVelConstraint)

                //put arm up while strafing
                //stop and place the sample on the bar
                .afterTime(0, claw.autonOpenClaw())
                .afterTime(0, shoulder.autonDownHC())
                .afterTime(0, viper.autonSlightOut())


                .afterTime(1, shoulder.autonDown())



                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(31,-43), Math.toRadians(-90)), 0)
                .afterTime(0, shoulder.autonDown())

                .splineToConstantHeading(new Vector2d(45, -13), 0)

                .splineToConstantHeading(new Vector2d(45,-33), -90)
                .splineToConstantHeading(new Vector2d(45,-48), -90)
                //one in observation zone
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(45,-13), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(60,-20), Math.toRadians(-90))

                //.strafeTo(new Vector2d(43,-59))
                //undo ^ if something goes wrong.
                .splineToSplineHeading(new Pose2d(new Vector2d(55,-60), Math.toRadians(-80)), Math.toRadians(-90), drive.slightlSlowerVelConstraint) //slightvel here
                //CRASH!!! INTO THE WALL!!!

                .afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.3)

                .afterTime(0.3, shoulder.autonHC())
                //grab sample, routing towards chamber.
                //raise arm to clip
                .afterTime(1.5, viper.autonHangSpecimen())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(8, -39), Math.toRadians(90)), Math.toRadians(90))

                .afterTime(0, claw.autonOpenClaw())
                .strafeTo(new Vector2d(6, -39))
                .afterTime(0, shoulder.autonDownHC())
                .afterTime(0, viper.autonSlightOut())


                .afterTime(1, shoulder.autonDown())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(35, -61), Math.toRadians(-80)), Math.toRadians(0))

                .afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.3)

                .afterTime(0.3, shoulder.autonHC())
                //grab sample, routing towards chamber.
                //raise arm to clip
                .afterTime(1.5, viper.autonHangSpecimen())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(8, -38), Math.toRadians(90)), Math.toRadians(90))

                .afterTime(0, claw.autonOpenClaw())
                .strafeTo(new Vector2d(6, -38))
                .afterTime(0, shoulder.autonDownHC())
                .afterTime(0, viper.autonSlightOut())


                .afterTime(1, shoulder.autonDown())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(35, -61), Math.toRadians(-80)), Math.toRadians(0))

                .afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.3)

                .afterTime(0.3, shoulder.autonHC())
                //grab sample, routing towards chamber.
                //raise arm to clip
                .afterTime(1.5, viper.autonHangSpecimen())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(8, -38), Math.toRadians(90)), Math.toRadians(90))

                .afterTime(0, claw.autonOpenClaw())
                .afterTime(0, shoulder.autonDownHC())
                .afterTime(0, viper.autonSlightOut())


                .afterTime(1, shoulder.autonDown())

                //.strafeToSplineHeading(new Vector2d(13, -40), Math.toRadians(180-42))
                .setReversed(true)
                .splineTo(new Vector2d(40, -60), -45)

                ;

        shoulder.init();
        elbow.init();
        intake.init();
        viper.init();
        claw.init();

        ElapsedTime timer = new ElapsedTime();
        boolean inited = false;

        while (!isStopRequested() && !opModeIsActive()) {
            Actions.runBlocking(new SequentialAction(
                    new InstantAction(() -> shoulder.autonListen()),
                    new InstantAction(() -> viper.autonListen()),
                    new InstantAction(() -> elbow.autonListen()),
                    new InstantAction(() -> intake.autonListen()),
                    new InstantAction(() -> claw.autonListen())
            ));

            if (timer.seconds() < 10 && !inited && !viper.isRetracted()) {
                viper.manualSetPower(-0.2);
            } else if (inited) {
                viper.listen();
                shoulder.listen();
            } else {
                viper.init();
                shoulder.init();

                shoulder.setTarget(0);
                viper.setTarget(0);
                inited = true;
            }

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