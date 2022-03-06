// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022;

import com.team2357.lib.subsystems.LimelightSubsystem;
import com.team2357.lib.subsystems.LimelightSubsystem.Configuration;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public final class CAN_ID {
        public static final int DRIVE_MOTOR_LEFT_1 = 11;
        public static final int DRIVE_MOTOR_RIGHT_1 = 12;
        public static final int DRIVE_MOTOR_LEFT_2 = 13;
        public static final int DRIVE_MOTOR_RIGHT_2 = 14;
        public static final int DRIVE_MOTOR_LEFT_3 = 15;
        public static final int DRIVE_MOTOR_RIGHT_3 = 16;

        public static final int GYRO_ID = 5;
        // Intake
        public static final int INTAKE_MOTOR_ID = 21;
        // Feeder
        public static final int FEEDER_MOTOR_ID = 28;
        // Kicker
        public static final int KICKER_MOTOR_ID = 29;
        // Pneumatic hub
        public static final int PNEUMATICS_HUB_ID = 30;
    }

    public final class PH_ID {
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
    public final static class DRIVE {
        public static final double DRIVE_MOTOR_RAMP_RATE_SECONDS = 0.75;

        public static final double WHEEL_DIAMETER_IN_METERS = 0.102;
        public static final int ENCODER_PPR = 256;

        public static final double ENCODER_DISTANCE_PER_PULSE_METERS = (WHEEL_DIAMETER_IN_METERS * Math.PI)
                / (double) ENCODER_PPR;

        public static final int LEFT_ENCODER_CHANNEL_A = 6;
        public static final int LEFT_ENCODER_CHANNEL_B = 7;
        public static final int RIGHT_ENCODER_CHANNEL_A = 8;
        public static final int RIGHT_ENCODER_CHANNEL_B = 9;

        public static final double MAX_VOLTAGE = 10;
        // TODO: Run characterization on all below constants
        /**
         * Characterization Constants Zeroes are currently placeholder values
         */
        public static final double kS_VOLTS = 0.0;
        public static final double KV_VOLTS_SECONDS_PER_METER = 0.0;
        public static final double KA_VOLTS_SECONDS_SQUARED_PER_METER = 0.0;

        /**
         * Differential Drive Kinematics Zeroes as place holder values
         */

        public static final double TRACK_WIDTH_METERS = 0.0;
        public static final DifferentialDriveKinematics DRIVE_KINEMATICS = new DifferentialDriveKinematics(
                TRACK_WIDTH_METERS);

        /**
         * Max Trajectory acceleration and velocity Zeroes as place holder values
         */

        public static final double MAX_SPEED_METERS_PER_SECOND = 0;
        public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = 0;

        /**
         * Values from the ramsete example. Ramsete Parameters Reasonable baseline
         * values for a RAMSETE follower in units of meters and seconds.
         */
        public static final double RAMSETE_B = 2;
        public static final double RAMSETE_ZETA = 0.7;
        public static final double KS_VOLTS = 0;

        /**
         * Proportional velocity value for Ramsete PID
         */
        public static final double P_DRIVE_VEL = 0;

        public static final FalconTrajectoryDriveSubsystem.Configuration GET_FALCON_DRIVE_CONFIG() {
            FalconTrajectoryDriveSubsystem.Configuration config = new FalconTrajectoryDriveSubsystem.Configuration();
            config.m_isRightInverted = true;
            config.m_isGyroReversed = true;
            return config;
        }
    }

    public static final class LIMELIGHT {

        public static final LimelightSubsystem.Configuration GET_LIMELIGHT_SUBSYSTEM_CONFIG() {
            LimelightSubsystem.Configuration config = new LimelightSubsystem.Configuration();
            /** Angle of the Limelight axis from horizontal (degrees) */
            config.m_LimelightMountingAngle = 0;
            /** Height of the Limelight lens center from the floor (inches) */

            config.m_LimelightMountingHeightInches = 0;
            /** Target width in inches */
            config.m_TargetWidth = 5;
            /** Target height in inches */
            config.m_TargetHeight = 2;
            return config;
        }

    }

    public final class ARDUINO {
        public static final String FEEDER_SENSOR_JSON_NAME = "feederIRSensor";
        public static final String INTAKE_SENSOR_JSON_NAME = "intakeIRSensor";
        public static final String TURRET_SENSOR_JSON_NAME = "turretHALSensor";

        public static final String FEEDER_SENSOR_STATE_FIELD = "state";
        public static final String INTAKE_SENSOR_STATE_FIELD = "state";
        public static final String TURRET_SENSOR_STATE_FIELD = "state";

        public static final String ARDUINO_SENSOR_DEVICE_NAME = "/dev/ttyACM0";
    }

    public final class COMPRESSOR {
        public static final int MIN_PRESSURE_PSI = 70;
        public static final int MAX_PRESSURE_PSI = 120;
    }

    public final class KICKER {
        public static final double SPEED = 0;
    }
}
