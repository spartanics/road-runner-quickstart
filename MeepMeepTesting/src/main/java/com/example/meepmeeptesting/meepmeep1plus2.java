package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class meepmeep1plus2 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);



        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-14, -61, Math.toRadians(90)))
                .splineToLinearHeading(new Pose2d(new Vector2d(-54.5, -54.5), Math.toRadians(225)), Math.toRadians(225))
                //.afterTime(0, shoulder.autonUpHB())
                .waitSeconds(1)
                //.afterTime(0, viper.autonHB())
                //.afterTime(0.8, elbow.autonHB())
                .strafeTo(new Vector2d(-56.5, -56.5))
                .waitSeconds(2)
                //.afterTime(0, intake.autoOuttake())
                .waitSeconds(1)
                //.afterTime(0, intake.autoStoptake())
                //.afterTime(0, elbow.autonZero())
                //.afterTime(0, viper.autonDown())
                .waitSeconds(1)
                //.afterTime(0, shoulder.autonMidDown())
                .waitSeconds(0.6)
                .strafeTo(new Vector2d(-54.5, -54.5))
                //.afterTime(0, shoulder.autonDown())
                //one in hb

                //pick sample
                .splineToLinearHeading(new Pose2d(new Vector2d(-36, -28), Math.toRadians(180)), Math.toRadians(90))

                .strafeTo(new Vector2d(-34, -28))
                //.afterTime(0, elbow.autonPick())
                //.afterTime(0, intake.autoIntake())
                .waitSeconds(0.5)
                .strafeTo(new Vector2d(-36, -28))
                .waitSeconds(2)
                //.afterTime(0, intake.autoStoptake())
                //.afterTime(0, elbow.autonZero())
                .waitSeconds(1)


                .splineToLinearHeading(new Pose2d(new Vector2d(-54.5, -54.5), Math.toRadians(225)), Math.toRadians(225))
                //.afterTime(0, shoulder.autonUpHB())
                .waitSeconds(1)
                //.afterTime(0, viper.autonHB())
                //.afterTime(0.8, elbow.autonHB())
                .strafeTo(new Vector2d(-56.5, -56.5))
                .waitSeconds(2)
                //.afterTime(0, intake.autoOuttake())
                .waitSeconds(1)
                //.afterTime(0, intake.autoStoptake())
                //.afterTime(0, elbow.autonZero())
                //.afterTime(0, viper.autonDown())
                .waitSeconds(1)
                //.afterTime(0, shoulder.autonMidDown())
                .waitSeconds(0.6)
                .strafeTo(new Vector2d(-54.5, -54.5))
                //.afterTime(0, shoulder.autonDown())
                //2 in hb

                //pick sample
                .splineToLinearHeading(new Pose2d(new Vector2d(-48, -28), Math.toRadians(180)), Math.toRadians(90))
                //.afterTime(0, elbow.autonPick())
                //.afterTime(0, intake.autoIntake())
                .waitSeconds(0.5)
                .strafeTo(new Vector2d(-50, -28))
                .waitSeconds(2)
                //.afterTime(0, intake.autoStoptake())
                //.afterTime(0, elbow.autonZero())
                .waitSeconds(1)

                .splineToLinearHeading(new Pose2d(new Vector2d(-54.5, -54.5), Math.toRadians(225)), Math.toRadians(225))
                //.afterTime(0, shoulder.autonUpHB())
                .waitSeconds(1)
                //.afterTime(0, viper.autonHB())
                //.afterTime(0.8, elbow.autonHB())
                .strafeTo(new Vector2d(-56.5, -56.5))
                .waitSeconds(2)
                //.afterTime(0, intake.autoOuttake())
                .waitSeconds(1)
                //.afterTime(0, intake.autoStoptake())
                //.afterTime(0, elbow.autonZero())
                //.afterTime(0, viper.autonDown())
                .waitSeconds(1)
                //.afterTime(0, shoulder.autonMidDown())
                .waitSeconds(0.6)
                .strafeTo(new Vector2d(-54.5, -54.5))
                //.afterTime(0, shoulder.autonDown())
                //3 in hb
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}