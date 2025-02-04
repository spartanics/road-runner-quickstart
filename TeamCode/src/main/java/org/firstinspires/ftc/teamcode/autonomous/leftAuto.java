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
import com.arcrobotics.ftclib.controller.PIDFController;
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
@Autonomous(name = "Left Auto", group = "Autonomous")
public class leftAuto extends LinearOpMode {
    Pose2d startPose;
    MecanumDrive drive;

    Shoulder shoulder = new Shoulder(this);
    Elbow elbow = new Elbow(this);
    Intake intake = new Intake(this);
    Viper viper = new Viper(this);
    Claw claw = new Claw(this);

    public static final double kp = 0.003, ki = 0.1, kd = 0.0002;
    public static final double kf = 0.00003;

    PIDFController controller = new PIDFController(kp, ki, kd, kf);

    boolean inited;
    ElapsedTime timer;

    @Override
    public void runOpMode() throws InterruptedException {
        startPose = new Pose2d(-38, -58, Math.toRadians(180));
        drive = new MecanumDrive(hardwareMap, startPose);
        TrajectoryActionBuilder build = drive.actionBuilder(startPose)
                .afterTime(0, intake.autoIntake())
                .splineToLinearHeading(new Pose2d(new Vector2d(-53, -53), Math.toRadians(225)), Math.toRadians(245))
                .afterTime(0, shoulder.autonUpHB())
                .waitSeconds(0.8)
                .afterTime(0, viper.autonHB())
                .waitSeconds(1)
                .afterTime(0, elbow.autonHB())
                .waitSeconds(1.3)
                .afterTime(0, intake.autoOuttake())
                .waitSeconds(0.6)
                .afterTime(0, intake.autoStoptake())
                .afterTime(0, elbow.autonZero())
                .afterTime(0, viper.autonDown())
                .waitSeconds(1)
                .afterTime(0, shoulder.autonDown())
                //one in hb

                //pick sample
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-42, -26), Math.toRadians(180)), Math.toRadians(90))
                .afterTime(0, elbow.autonPick())
                .afterTime(0, intake.autoIntake())
                .waitSeconds(1.3)
                .afterTime(0, intake.autoStoptake())
                .afterTime(0, elbow.autonZero())
                .waitSeconds(0.1)

                .splineToLinearHeading(new Pose2d(new Vector2d(-53, -53), Math.toRadians(225)), Math.toRadians(200))
                .afterTime(0, shoulder.autonUpHB())
                .waitSeconds(0.8)
                .afterTime(0, viper.autonHB())
                .waitSeconds(1)
                .afterTime(0, elbow.autonHB())
                .waitSeconds(1.3)
                .afterTime(0, intake.autoOuttake())
                .waitSeconds(0.6)
                .afterTime(0, intake.autoStoptake())
                .afterTime(0, elbow.autonZero())
                .afterTime(0, viper.autonDown())
                .waitSeconds(1)
                .afterTime(0, shoulder.autonDown())
                //two in hb

                //pick sample
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-50, -26), Math.toRadians(180)), Math.toRadians(90))
                .afterTime(0, elbow.autonPick())
                .afterTime(0, intake.autoIntake())
                .waitSeconds(1.3)
                .afterTime(0, intake.autoStoptake())
                .afterTime(0, elbow.autonZero())
                .waitSeconds(0.1)

                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-53, -53), Math.toRadians(225)), Math.toRadians(200))
                .afterTime(0, shoulder.autonUpHB())
                .waitSeconds(0.8)
                .afterTime(0, viper.autonHB())
                .waitSeconds(1)
                .afterTime(0, elbow.autonHB())
                .waitSeconds(1.3)
                .afterTime(0, intake.autoOuttake())
                .waitSeconds(0.6)
                .afterTime(0, intake.autoStoptake())
                .afterTime(0, elbow.autonZero())
                .afterTime(0, viper.autonDown())
                .waitSeconds(1.2)
                .afterTime(0, shoulder.autonDown())
                .strafeTo(new Vector2d(-54.25, -54.25))
                //three in hb
                ;

        shoulder.init(controller);
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

            if (timer.seconds() < 4 && !inited && !viper.isRetracted()) {
                viper.manualSetPower(-0.2);
            } else if (inited) {
                viper.listen();
                shoulder.listen();
            } else {
                viper.init();
                shoulder.init(controller);

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

