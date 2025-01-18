package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.InstantFunction;
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
@Autonomous(name = "Left Auto", group = "Autonomous")
public class leftAuto extends LinearOpMode {
    Pose2d startPose;
    MecanumDrive drive;

    Shoulder shoulder = new Shoulder(this);
    Elbow elbow = new Elbow(this);
    Intake intake = new Intake(this);
    Viper viper = new Viper(this);
    Claw claw = new Claw(this);





    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(-38, -61, Math.toRadians(180));
        drive = new MecanumDrive(hardwareMap, startPose);
        TrajectoryActionBuilder build = drive.actionBuilder(startPose)
                .afterTime(0, intake.autoIntake())
                .splineToLinearHeading(new Pose2d(new Vector2d(-54.5, -54.5), Math.toRadians(225)), Math.toRadians(225))
                .afterTime(0, shoulder.autonUpHB())
                .waitSeconds(1)
                .afterTime(0, viper.autonHB())
                .afterTime(1.1, elbow.autonHB())
                .waitSeconds(2)
                .afterTime(0, intake.autoOuttake())
                .waitSeconds(1)
                .afterTime(0, intake.autoStoptake())
                .afterTime(0, elbow.autonZero())
                .afterTime(0, viper.autonDown())
                .waitSeconds(1)
                .afterTime(0, shoulder.autonMidDown())

                .strafeTo(new Vector2d(-54.5, -54.5))
                .afterTime(0, shoulder.autonDown())
                //one in hb

                //pick sample
                .splineToLinearHeading(new Pose2d(new Vector2d(-48, -46), Math.toRadians(90)), Math.toRadians(90))
                .afterTime(0, elbow.autonPick())
                .afterTime(0, intake.autoIntake())
                .waitSeconds(0.5)
                .strafeTo(new Vector2d(-48, -44))
                .waitSeconds(2)
                .afterTime(0, intake.autoStoptake())
                .afterTime(0, elbow.autonZero())
                .waitSeconds(1)


                .splineToLinearHeading(new Pose2d(new Vector2d(-54.5, -54.5), Math.toRadians(225)), Math.toRadians(225))
                .afterTime(0, shoulder.autonUpHB())
                .waitSeconds(1)
                .afterTime(0, viper.autonHB())
                .afterTime(1.1, elbow.autonHB())
                .waitSeconds(2)
                .afterTime(0, intake.autoOuttake())
                .waitSeconds(1)
                .afterTime(0, intake.autoStoptake())
                .afterTime(0, elbow.autonZero())
                .afterTime(0, viper.autonDown())
                .waitSeconds(1)
                .afterTime(0, shoulder.autonMidDown())
                .strafeTo(new Vector2d(-54.5, -54.5))
                .afterTime(0, shoulder.autonDown())
                //2 in hb

                //pick sample
                .splineToLinearHeading(new Pose2d(new Vector2d(-42, -29), Math.toRadians(180)), Math.toRadians(90))
                .afterTime(0, elbow.autonPick())
                .afterTime(0, intake.autoIntake())
                .waitSeconds(0.5)
                .strafeTo(new Vector2d(-44, -29))
                .waitSeconds(2)
                .afterTime(0, intake.autoStoptake())
                .afterTime(0, elbow.autonZero())
                .waitSeconds(1)

                .splineToLinearHeading(new Pose2d(new Vector2d(-54.5, -54.5), Math.toRadians(225)), Math.toRadians(225))
                .afterTime(0, shoulder.autonUpHB())
                .waitSeconds(1)
                .afterTime(0, viper.autonHB())
                .afterTime(1.1, elbow.autonHB())
                .waitSeconds(2)
                .afterTime(0, intake.autoOuttake())
                .waitSeconds(1)
                .afterTime(0, intake.autoStoptake())
                .afterTime(0, elbow.autonZero())
                .afterTime(0, viper.autonDown())
                .waitSeconds(1)
                .afterTime(0, shoulder.autonMidDown())
                .strafeTo(new Vector2d(-54.5, -54.5))
                .afterTime(0, shoulder.autonDown())
                //3 in hb
                ;

        shoulder.init();
        elbow.init();
        intake.init();
        viper.init();
        claw.init();

        while (!isStopRequested() && !opModeIsActive()) {
            viper.listen();

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

