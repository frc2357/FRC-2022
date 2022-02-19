// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022;

import com.team2357.frc2022.subsystems.ShooterSubsystem;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    /**
     * When setting values on components, wait this long for a response before
     * failing. milliseconds
     */
    public static final int TIMEOUT_MS = 30;

    public final class CAN_ID {
        public static final int DRIVE_MOTOR_LEFT_1 = 11;
        public static final int DRIVE_MOTOR_RIGHT_1 = 12;
        public static final int DRIVE_MOTOR_LEFT_2 = 13;
        public static final int DRIVE_MOTOR_RIGHT_2 = 14;
        public static final int GYRO_ID = 5;
        // Intake
        public static final int INTAKE_MOTOR_ID = 21;

        // Shooter
        public static final int SHOOTER_BOTTOM_LEFT = 22;
        public static final int SHOOTER_BOTTOM_RIGHT = 23;
        public static final int SHOOTER_TOP = 24;

        // Feeder
        public static final int FEEDER_MOTOR_ID = 0;
    }

    public final class PCM_ID {
        public static final int INTAKE_SOLENOID_FORWARD_CHANNEL = 0;
        public static final int INTAKE_SOLENOID_REVERSE_CHANNEL = 0;
    }

    public final class CONTROLLER {
        public static final int DRIVE_CONTROLLER_PORT = 0;
        // Controller Constants
        public static final int GUNNER_CONTROLLER_PORT = 1;

        public static final double DRIVE_CONTROLLER_DEADBAND = 0.1;
    }

    public final class INTAKE {
        public static final double FORWARD_SPEED = 0;
        public static final double REVERSE_SPEED = 0;
    }

    // Encoder Constants
    public final class DRIVE {
        public static final double WHEEL_DIAMETER_IN_METERS = 0.102;
        public static final int ENCODER_PPR = 256;

        public static final double ENCODER_DISTANCE_PER_PULSE_METERS = (WHEEL_DIAMETER_IN_METERS * Math.PI)
                / (double) ENCODER_PPR;

        public static final int LEFT_ENCODER_CHANNEL_A = 0;
        public static final int LEFT_ENCODER_CHANNEL_B = 1;
        public static final int RIGHT_ENCODER_CHANNEL_A = 2;
        public static final int RIGHT_ENCODER_CHANNEL_B = 3;

        public static final boolean INVERT_GYRO = true;
        public static final boolean INVERT_RIGHT_SIDE = true;
    }

    public static final class SHOOTER {
        public static final ShooterSubsystem.Configuration CONFIG_SHOOTER() {
            ShooterSubsystem.Configuration config = new ShooterSubsystem.Configuration();
            /** Clicks per rotation for the internal encoder in the Falcon 500 */
            config.m_encoder_cpr = 2048;

            config.m_shooterGearingRatio = 0;
            config.m_timeoutMS = TIMEOUT_MS;
            config.m_shooterMotorPeakOutput = 1.0;

            // Bottom
            config.m_bottomShooterP = 0.09;
            config.m_bottomShooterI = 0;
            config.m_bottomShooterD = 0;
            config.m_bottomShooterF = 0.01;

            // Top
            config.m_topShooterP = 0.09;
            config.m_topShooterI = 0;
            config.m_topShooterD = 0;
            config.m_topShooterF = 0.01;

            return config;
        }

    }

    public final class LIMELIGHT {
        /** Angle of the Limelight axis from horizontal (degrees) */
        public static final double MOUNTING_ANGLE = 0;

        /** Height of the Limelight lens center from the floor (inches) */
        public static final double MOUNTING_HEIGHT = 0;

        /** Target width in inches */
        public static final double VISION_TARGET_WIDTH = 5;

        /** Target height in inches */
        public static final double VISION_TARGET_HEIGHT = 2;
    }

    public final class ARDUINO {
        public static final String ARDUINO_SENSOR_DEVICE_NAME = "/dev/ttyACM0";
        public static final String IR_SENSOR_JSON_NAME = "IRSensor";
    }
}
