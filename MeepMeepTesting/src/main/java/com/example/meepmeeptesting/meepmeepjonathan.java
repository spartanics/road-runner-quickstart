package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class meepmeepjonathan {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(750);


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(14, -61, Math.toRadians(90)))

                //.afterTime(0, shoulder.autonHC())
                //.afterTime(0.5, viper.autonHangSpecimen())

                .strafeTo(new Vector2d(6, -37))//, drive.halfVelConstraint)

                //put arm up while strafing
                //stop and place the sample on the bar
                //.afterTime(0, claw.autonOpenClaw())
                //.afterTime(0, shoulder.autonDownHC())
                //.afterTime(0, viper.autonSlightOut())


                //.afterTime(1, shoulder.autonDown())



                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(31,-43), Math.toRadians(-90)), 0)
                //.afterTime(0, shoulder.autonDown())

                .splineToConstantHeading(new Vector2d(45, -13), 0)

                .splineToConstantHeading(new Vector2d(45,-33), -90)
                .splineToConstantHeading(new Vector2d(45,-53), -90)
                //one in observation zone
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(45,-13), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(60,-20), Math.toRadians(-90))

                //.strafeTo(new Vector2d(43,-59))
                //undo ^ if something goes wrong.
                .splineToSplineHeading(new Pose2d(new Vector2d(55,-59), Math.toRadians(-80)), Math.toRadians(-90)) //slightvel here
                //CRASH!!! INTO THE WALL!!!

                //.afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.3)

                //.afterTime(0.3, shoulder.autonHC())
                //grab sample, routing towards chamber.
                //raise arm to clip
                //.afterTime(1.5, viper.autonHangSpecimen())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(8, -39), Math.toRadians(90)), Math.toRadians(90))

                //.afterTime(0, claw.autonOpenClaw())
                .strafeTo(new Vector2d(6, -39))
                //.afterTime(0, shoulder.autonDownHC())
                //.afterTime(0, viper.autonSlightOut())


                //.afterTime(1, shoulder.autonDown())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(35, -60), Math.toRadians(-80)), Math.toRadians(0))

                //.afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.3)

                //.afterTime(0.3, shoulder.autonHC())
                //grab sample, routing towards chamber.
                //raise arm to clip
                //.afterTime(1.5, viper.autonHangSpecimen())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(8, -38), Math.toRadians(90)), Math.toRadians(90))

                //.afterTime(0, claw.autonOpenClaw())
                .strafeTo(new Vector2d(6, -38))
                //.afterTime(0, shoulder.autonDownHC())
                //.afterTime(0, viper.autonSlightOut())


                //.afterTime(1, shoulder.autonDown())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(35, -60), Math.toRadians(-80)), Math.toRadians(0))

                //.afterTime(0, claw.autonCloseClaw())
                .waitSeconds(0.3)

                //.afterTime(0.3, shoulder.autonHC())
                //grab sample, routing towards chamber.
                //raise arm to clip
                //.afterTime(1.5, viper.autonHangSpecimen())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(new Vector2d(8, -38), Math.toRadians(90)), Math.toRadians(90))

                //.afterTime(0, claw.autonOpenClaw())
                //.afterTime(0, shoulder.autonDownHC())
                //.afterTime(0, viper.autonSlightOut())


                //.afterTime(1, shoulder.autonDown())

                //.strafeToSplineHeading(new Vector2d(13, -40), Math.toRadians(180-42))
                .setReversed(true)
                .splineTo(new Vector2d(40, -60), -45)


                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(1f)
                .addEntity(myBot)
                .start();
    }
}