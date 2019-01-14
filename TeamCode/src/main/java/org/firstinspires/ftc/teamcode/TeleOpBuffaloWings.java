package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleOpBuffaloWings")
public class TeleOpBuffaloWings extends OpMode
{
    Definitions robot = new Definitions();
    ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
    DigitalChannel leadScrewLimitBot;

    public void init()
    {
        robot.robotHardwareMapInit(hardwareMap);
        leadScrewLimitBot = hardwareMap.get(DigitalChannel.class, "leadScrewLimitBot");
        leadScrewLimitBot.setMode(DigitalChannel.Mode.INPUT);
        robot.resetEncoders();
        robot.runWithOutEncoders();
    }

    public void loop()
    {
        /**
         * DRIVING SECTION
         */
        //slow is used as a multiplier to change speed
        double slowMovement;
        if(gamepad1.right_bumper)
        {
            slowMovement = 0.5;
        }
        else
        {
            slowMovement = 1;
        }

        //Using Range.clip to limit joystick values from -1 fto 1 (clipping the outputs)
        double driveFrontRightPower = Range.clip((-gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
        double driveFrontLeftPower = Range.clip((gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
        double driveBackRightPower = Range.clip((-gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);
        double driveBackLeftPower = Range.clip((gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1);

        //Apply the values to the motors.
        robot.rightFrontMotor.setPower(driveFrontRightPower);
        robot.leftFrontMotor.setPower(driveFrontLeftPower);
        robot.rightBackMotor.setPower(driveBackRightPower);
        robot.leftBackMotor.setPower(driveBackLeftPower);

        /**
         * LEADSCREW SECTION
         */
        if(gamepad2.dpad_up)
        {
            robot.leadScrewMotor.setPower(-1);
        }
        else if(gamepad2.dpad_down)
        {
            if(!leadScrewLimitBot.getState())
                robot.leadScrewMotor.setPower(-0.75);
            else
                robot.leadScrewMotor.setPower(1);
        }
        else
        {
            if(!leadScrewLimitBot.getState())
                robot.leadScrewMotor.setPower(-0.75);
            else
                robot.leadScrewMotor.setPower(0);
        }


        /**
         * SCORING ARM SECTION
         */

        //Scoring arm - controls input from gamepad2 left joystick
        double scoringArmMotorPower = Range.clip(gamepad2.left_stick_y, -1, 1);

        //Scoring arm - Sets speed for lift arm
       // robot.scoringArmMotor.setPower(scoringArmMotorPower * 0.8);

       // robot.armReelMotor.setPower(-gamepad2.right_stick_y);





        /**
         * SERVO SECTION
         */
//        if(gamepad2.a)
//        {
//            robot.armServo.setPower(1);
//        }
//        else if(gamepad2.b)
//        {
//            robot.armServo.setPower(-1);
//        }
//        else
//        {
//            robot.armServo.setPower(0);
//        }
//
//        if(gamepad2.x)
//        {
//            robot.armExtendorServo.setPower(1);
//        }
//        else if(gamepad2.y)
//        {
//            robot.armExtendorServo.setPower(-1);
//        }
//        else
//        {
//            robot.armExtendorServo.setPower(0);
//        }



        /**
         * Telemetry Section
         */
        //No debugging needed right now!! YAY

        telemetry.addData("Status:", "Running TeleOpMode");
        telemetry.addData("Lead Screw:", robot.leadScrewMotor.getCurrentPosition());
        //telemetry.addData("scoringArm", robot.scoringArmMotor.getCurrentPosition());
        //telemetry.addData("reel", robot.armReelMotor.getCurrentPosition())
               // .addData("Controller 1 x", gamepad1.left_stick_x);
        telemetry.update();
    }

    public void stop()
    {
        robot.setPower(0);
        robot.leadScrewMotor.setPower(0);
        //robot.armReelMotor.setPower(0);
        //robot.scoringArmMotor.setPower(0);
    }
}
