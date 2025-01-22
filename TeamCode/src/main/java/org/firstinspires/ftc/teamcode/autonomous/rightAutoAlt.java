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
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Claw;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Elbow;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Intake;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Shoulder;
import org.firstinspires.ftc.teamcode.hardware.tidev2.Viper;


@Config
@Autonomous(name = "4 clip Robotnik Style", group = "Autonomous")
public class rightAutoAlt extends LinearOpMode {
    final double POSX_FARTHEST = 35;
    final double POSY_WALL = -60;
    final double POSX_COLLECT = 45;
    final double POSX_BAR = 5;
    final double POSY_BAR = -35;
    final int POS_SHOULDER_HANG = 475;
    final double TS_WAIT_TO_GRAB_SAMPLE = 0.1;

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
                //put arm up while strafing
                //stop and place the sample on the bar
                .afterTime(0, shoulder.autonSetShoulderTarget(POS_SHOULDER_HANG))
                .afterTime(0.1, viper.autonHangSpecimen())
                .strafeTo(new Vector2d(POSX_BAR, POSY_BAR))

                // STEP: post-hang
                .afterTime(0.1, claw.autonOpenClaw())
//                .afterTime(0, shoulder.autonSetShoulderTarget(420))
                .afterTime(0.1, viper.autonSlightOut())
                .waitSeconds(.3)

                .afterTime(0.5, shoulder.autonDown())

                // STEP: go collect one sample to observation zone
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(20,POSY_BAR-10),
                        Math.toRadians(-90)), 0)

                .splineTo(new Vector2d(POSX_COLLECT, -13), 0)
                .strafeTo(new Vector2d(POSX_COLLECT,POSY_WALL+10))
                //one in observation zone

                // STEP: go collect another sample to observation zone
                .strafeTo(new Vector2d(45,-13))
                .strafeTo(new Vector2d(55,-13))
                //.strafeTo(new Vector2d(43,-59))
                //undo ^ if something goes wrong.
                .strafeTo(new Vector2d(44, POSY_WALL))

                //TODO: to continue tuning here

                // STEP: collect specimen and hang
                .afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.25)

                .afterTime(0.1, shoulder.autonSetShoulderTarget(POS_SHOULDER_HANG))
                //grab sample, routing towards chamber.
                //raise arm to clip
                .afterTime(0.5, viper.autonHangSpecimen())
                .setReversed(true)

                // TODO: to experiment here on approaching the bar in a more direct way
                .splineToSplineHeading(new Pose2d(new Vector2d(POSX_BAR+2, POSY_BAR),
                        Math.toRadians(90)), Math.toRadians(90))

                // STEP: going back to pick up sample to hang
                //clip, routing to push final sample and grab specimen

                // STEP: post-hang
                .afterTime(0.1, claw.autonOpenClaw())
//                .afterTime(0, shoulder.autonSetShoulderTarget(420))
                .afterTime(0.1, viper.autonSlightOut())
                .waitSeconds(.3)


                // STEP: pick another sample to hang
                .afterTime(0.5, shoulder.autonDown())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(POSX_FARTHEST, POSY_WALL),
                        Math.toRadians(-80)), Math.toRadians(0))

                .afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.25)

                // STEP: go to the bar to hang sample
                .afterTime(0.1, shoulder.autonSetShoulderTarget(POS_SHOULDER_HANG))
                .afterTime(0.5, viper.autonHangSpecimen())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(POSX_BAR-2, POSY_BAR),
                        Math.toRadians(90)), Math.toRadians(90))

                // STEP: post-hang
                .afterTime(0.1, claw.autonOpenClaw())
//                .afterTime(0, shoulder.autonSetShoulderTarget(420))
                .afterTime(0.1, viper.autonSlightOut())
                .waitSeconds(.3)

                // STEP: pick another sample to hang
                .afterTime(0.5, shoulder.autonDown())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(POSX_FARTHEST, POSY_WALL),
                        Math.toRadians(-80)), Math.toRadians(0))

                .afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.25)

                // STEP: go to the bar to hang sample
                .afterTime(0.1, shoulder.autonSetShoulderTarget(POS_SHOULDER_HANG))
                .afterTime(0.5, viper.autonHangSpecimen())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(POSX_BAR+1, POSY_BAR),
                        Math.toRadians(90)), Math.toRadians(90))

                // STEP: post-hang
                .afterTime(0.1, claw.autonOpenClaw())
//                .afterTime(0, shoulder.autonSetShoulderTarget(420))
                .afterTime(0.1, viper.autonSlightOut())
                .waitSeconds(.3)

                // STEP: go back to park
                .setReversed(true)
                .splineTo(new Vector2d(POSX_FARTHEST,POSY_WALL), Math.toRadians(-90))
                .afterTime(0, shoulder.autonDown())

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

            if (timer.seconds() < 4) {
                viper.manualSetPower(-0.6);
            }else if (inited == true) {
                viper.listen();
            } else {
                viper.init();
                shoulder.setTarget(70);
                viper.setTarget(40);
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