// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022;

import com.team2357.frc2022.subsystems.TurretSubsystem;

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

    public final class CAN_ID {
        public static final int DRIVE_MOTOR_LEFT_1 = 11;
        public static final int DRIVE_MOTOR_RIGHT_1 = 12;
        public static final int DRIVE_MOTOR_LEFT_2 = 13;
        public static final int DRIVE_MOTOR_RIGHT_2 = 14;
        public static final int GYRO_ID = 5;
        // Intake
        public static final int INTAKE_MOTOR_ID = 21;
        // Feeder
        public static final int FEEDER_MOTOR_ID = 0;

        // Turret
        public static final int TURRET_MOTOR_ID = 24;
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

    // Turret
    // TODO: Tune Turret constants, currently values from rev's example
    public static final class TURRET {
        public static final double MANUAL_TURRET_ROTATE_SPEED = 0.1;
        public static final double MOTOR_DEGREES_ZERO_INCREMENT = 0;

        public static final TurretSubsystem.Configuration config = new TurretSubsystem.Configuration();

        public static final TurretSubsystem.Configuration GET_TURRET_CONFIG() {
            TurretSubsystem.Configuration config = new TurretSubsystem.Configuration();

            config.m_turretMotorStallLimitAmps = 15;
            config.m_turretMotorFreeLimitAmps = 3;

            config.m_turretMotorP = 0.00005;
            config.m_turretMotorI = 0.000001;
            config.m_turretMotorD = 0;
            config.m_turretMotorIZone = 0;
            config.m_turretMotorFF = 0.000156;
            config.m_turretMotorMaxOutput = 0.2;
            config.m_turretMotorMinOutput = -0.2;
            config.m_turretMotorMaxRPM = 1000;

            config.m_turretMotorMaxVel = 500;
            config.m_turretMotorMinVel = 0;
            config.m_turretMotorMaxAcc = 5;
            config.m_turretMotorAllowedError = 5;

            config.m_turretRotationsClockwiseSoftLimit = 0;
            config.m_turretRotationsCounterClockwiseSoftLimit = 0;
            config.m_rotationsPerDegree = 1;
            config.m_degreeOffset = 0;
            return config;
        }

    }

    public final class LIMELIGHT {
        /** Angle of the Limelight axis from horizontal (degrees) */
        public static final double MOUNTING_ANGLE = 0;

        /** Height of the Limelight lens center from the floor (inches) */
        public static final double MOUNTING_HEIGHT_INCHES = 0;

        // TODO: Find out how big we want the vision target
        /** Target width in inches */
        public static final double VISION_TARGET_WIDTH_INCHES = 5;

        /** Target height in inches */
        public static final double VISION_TARGET_HEIGHT_INCHES = 2;

        public static final double VISION_TARGET_HEIGHT_FROM_FLOOR_INCHES = 101.625;
    }

    public final class ARDUINO {
        public static final String ARDUINO_SENSOR_DEVICE_NAME = "/dev/ttyACM0";
        public static final String TURRET_HALL_SENSOR_NAME = "hallSensor";
        public static final String IR_SENSOR_JSON_NAME = "IRSensor";
    }
}
