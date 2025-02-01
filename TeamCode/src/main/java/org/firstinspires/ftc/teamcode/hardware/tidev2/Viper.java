/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.hardware.tidev2;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Viper {





    // Define class members

    private DcMotorEx viper;

    private TouchSensor touch;

    private OpMode myOpMode;   // gain access to methods in the calling OpMode.

    private PIDFController controller;

    public static double p = 0.001, i = 0.01, d = 0.0;
    public static double f = 0;

    public static int target = 0;

    int vipPos;

    public Viper(OpMode opmode) {
        myOpMode = opmode;
    }

    public static int max;

    public static double pidf = 0;

    public static double shoulderDeg;

    private boolean resetTouch;



    public void init() {
        controller = new PIDFController(p, i, d, f);
        // Define and Initialize Motors (note: need to use reference to actual OpMode).
        viper = myOpMode.hardwareMap.get(DcMotorEx.class, "viper_slide");
        viper.setDirection(DcMotorSimple.Direction.REVERSE);

        touch = myOpMode.hardwareMap.get(TouchSensor.class, "touch");


        viper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        viper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


//        viper.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfNew);

        pidf = 0;
        target = 0;
    }

    public class AutonListen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            autoListen();
            return true;
        }
    }
    public Action autonListen() {
        return new Viper.AutonListen();
    }

    public class AutonDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setTarget(0);
            return false;
        }
    }
    public Action autonDown() {
        return new AutonDown();
    }

    public class AutonHB implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            setTarget(3800);
            return false;
        }
    }

    public Action autonHB() {
        return new AutonHB();
    }

    public class AutonTargetSetter implements Action {
        private int autonTarget = 0;
        public AutonTargetSetter(int t) {
            this.autonTarget = t;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setTarget(autonTarget);
            return false;
        }
    }

    public Action autonSetViperTarget(int t) {
        return new AutonTargetSetter(t);
    }

    public class AutonSlightOut implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            setTarget(50);
            return false;
        }
    }

    public Action autonSlightOut() {
        return new AutonSlightOut();
    }

    public class AutonHangSpecimen implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            setTarget(3000);
            return false;
        }
    }

    public Action autonHangSpecimen() {
        return new AutonHangSpecimen();
    }

    public double toInches(int pos) {
        return pos / 300.0;
    }

    public int fromInches(double in) {
        return (int) (in * 300.0);
    }

    public void passShoulderDeg(double passed) {
        shoulderDeg = passed;
    }

    public int getPosition() {
        return viper.getCurrentPosition();
    }


    public void setTarget(int tar) {
        target = tar;
    }

    public void autoListen() {
        int armPos = viper.getCurrentPosition();
        pidf = controller.calculate(armPos, target);

        viper.setPower(pidf);

        sendTelemetry();
        myOpMode.telemetry.update();

    }

    public void sendTelemetry() {
        myOpMode.telemetry.addLine("----VIPER----");
        myOpMode.telemetry.addData("Position", viper.getCurrentPosition());
        myOpMode.telemetry.addData("Power", pidf);
        myOpMode.telemetry.addData("Target Position", target);
        myOpMode.telemetry.addData("Max", max);
        myOpMode.telemetry.addData("Pressed", touch.isPressed());
        myOpMode.telemetry.addData("Touch Value", touch.getValue());
        myOpMode.telemetry.addData("Touch Reset", resetTouch);
        myOpMode.telemetry.addLine();
    }

    public void manualSetPower(double pow) {
        viper.setPower(pow);
    }

    public double getTouch() {
        return touch.getValue();
    }

    public boolean isRetracted() {
        return touch.isPressed();
    }

    public void listen_simple() {
        pidf = -myOpMode.gamepad2.left_stick_y;
        if (pidf != 0.0 && !touch.isPressed()) {
            viper.setPower(pidf);
            target = viper.getCurrentPosition();
        }
    }

    public void listen() {
        if (myOpMode.gamepad2.dpad_up) {
            listen_simple();
            return;
        }

        if (shoulderDeg % 180 != 90) {
            // move viper according to the left stick y
            if (Math.abs(5000 / (Math.cos(Math.toRadians(shoulderDeg)))) > 5000) {
                max = 5000;
            } else {
                //                horizontal max ↓
                max = fromInches(Math.abs((5000.0 / 300) * (Math.cos(Math.toRadians(shoulderDeg)))));
            }
        } else {
            max = 5000;
        }


        if (target >= 0 && target <= max) {
            target += (int) (-myOpMode.gamepad2.left_stick_y) * 100;
        } else if (target < 0) {
            target = 0;
        } else {
            target = max;
        }

        vipPos = viper.getCurrentPosition();
        pidf = controller.calculate(vipPos, target);

        viper.setPower(pidf);

        if (!touch.isPressed()) {
            resetTouch = true;
        }

        if (myOpMode.gamepad2.start || (touch.isPressed() && resetTouch)) {
            // Reset the target to zero

            viper.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            viper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            target = 0;

            resetTouch = false;

        }

    }

}