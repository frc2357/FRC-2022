// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.subsystems.LimelightSubsystem;
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

    /**
     * When setting values on components, wait this long for a response before
     * failing. milliseconds
     */
    public static final int TIMEOUT_MS = 30;

    public final class CAN_ID {
        // Pneumatic hub
        public static final int PNEUMATICS_HUB_ID = 2;

        // Drive
        public static final int DRIVE_MOTOR_LEFT_1 = 11;
        public static final int DRIVE_MOTOR_RIGHT_1 = 12;
        public static final int DRIVE_MOTOR_LEFT_2 = 13;
        public static final int DRIVE_MOTOR_RIGHT_2 = 14;
        public static final int DRIVE_MOTOR_LEFT_3 = 15;
        public static final int DRIVE_MOTOR_RIGHT_3 = 16;

        public static final int GYRO_ID = 5;

        // Intake
        public static final int INTAKE_MOTOR_ID = 21;

        // Climber
        public static final int CLIMBER_MOTOR_LEFT_ID = 22;
        public static final int CLIMBER_MOTOR_RIGHT_ID = 23;

        // Turret
        public static final int TURRET_MOTOR_ID = 24;

        // Shooter
        public static final int SHOOTER_BOTTOM_LEFT = 25;
        public static final int SHOOTER_BOTTOM_RIGHT = 26;
        public static final int SHOOTER_TOP = 27;

        // Feeder
        public static final int FEEDER_MOTOR_ID = 28;

        // Kicker
        public static final int KICKER_MOTOR_ID = 29;
    }

    public final class PH_ID {
        public static final int INTAKE_SOLENOID_FORWARD_CHANNEL = 0;
        public static final int INTAKE_SOLENOID_REVERSE_CHANNEL = 1;
        public static final int CLIMBER_SOLENOID_FORWARD_CHANNEL = 2;
        public static final int CLIMBER_SOLENOID_REVERSE_CHANNEL = 3;
        public static final int CLIMBER_HOOK_SOLENOID_CHANNEL = 4;
    }

    public final class CONTROLLER {
        public static final int DRIVE_CONTROLLER_PORT = 0;
        // Controller Constants
        public static final int GUNNER_CONTROLLER_PORT = 1;

        public static final double DRIVE_CONTROLLER_DEADBAND = 0.1;
        public static final double GUNNER_CONTROLLER_DEADBAND = 0.1;
    }

    // Encoder Constants
    public final static class DRIVE {
        public static final double WHEEL_DIAMETER_IN_METERS = 0.1016;
        public static final double DRIVE_MOTOR_OPEN_RAMP_RATE_SECONDS = 0.75;
        public static final double DRIVE_MOTOR_CLOSED_RAMP_RATE_SECONDS = 0.1;

        public static final int ENCODER_PPR = 256;

        public static final double ENCODER_DISTANCE_PER_PULSE_METERS = (WHEEL_DIAMETER_IN_METERS * Math.PI)
                / (double) ENCODER_PPR;

        public static final int LEFT_ENCODER_CHANNEL_A = 6;
        public static final int LEFT_ENCODER_CHANNEL_B = 7;
        public static final int RIGHT_ENCODER_CHANNEL_A = 8;
        public static final int RIGHT_ENCODER_CHANNEL_B = 9;

        public static final boolean INVERT_GYRO = true;
        public static final boolean INVERT_RIGHT_SIDE = true;

        public static final double MAX_VOLTAGE = 10;

        // TODO: Run characterization on all below constants
        /**
         * Characterization Constants Zeroes are currently placeholder values
         */
        public static final double KS_VOLTS = 0.65717;
        public static final double KV_VOLTS_SECONDS_PER_METER = 3.5385;
        public static final double KA_VOLTS_SECONDS_SQUARED_PER_METER = 1.1685;

        /**
         * Differential Drive Kinematics Zeroes as place holder values
         */
        public static final double TRACK_WIDTH_METERS = 0.650875;
        public static final DifferentialDriveKinematics DRIVE_KINEMATICS = new DifferentialDriveKinematics(
                TRACK_WIDTH_METERS);

        /**
         * Max Trajectory acceleration and velocity Zeroes as place holder values
         */
        public static final double MAX_SPEED_METERS_PER_SECOND = 1;
        public static final double MAX_ACCELERATION_METERS_PER_SECOND_SQUARED = 0.5;

        /**
         * Values from the ramsete example.
         * Ramsete Parameters Reasonable baseline values for a RAMSETE follower in units
         * of meters and seconds.
         */
        public static final double RAMSETE_B = 1.5;
        public static final double RAMSETE_ZETA = 0.7;

        /**
         * Proportional velocity value for Ramsete PID
         */
        public static final double P_DRIVE_VEL = 3.0953;

        public static final SimpleMotorFeedforward TRAJECTORY_FEEDFORWARD = new SimpleMotorFeedforward(
                Constants.DRIVE.KS_VOLTS,
                Constants.DRIVE.KV_VOLTS_SECONDS_PER_METER,
                Constants.DRIVE.KA_VOLTS_SECONDS_SQUARED_PER_METER);

        public static final RamseteController TRAJECTORY_RAMSETE_CONTROLLER = new RamseteController(
                Constants.DRIVE.RAMSETE_B, Constants.DRIVE.RAMSETE_ZETA);

        public static final PIDController TRAJECTORY_DRIVE_PID = new PIDController(Constants.DRIVE.P_DRIVE_VEL, 0, 0);

        public static final DifferentialDriveVoltageConstraint TRAJECTORY_VOLTAGE_CONSTRAINT = new DifferentialDriveVoltageConstraint(
                Constants.DRIVE.TRAJECTORY_FEEDFORWARD,
                Constants.DRIVE.DRIVE_KINEMATICS,
                Constants.DRIVE.MAX_VOLTAGE);

        public static final FalconTrajectoryDriveSubsystem.Configuration GET_FALCON_DRIVE_CONFIG() {
            FalconTrajectoryDriveSubsystem.Configuration config = new FalconTrajectoryDriveSubsystem.Configuration();
            config.m_isRightInverted = true;
            config.m_isGyroReversed = true;

            // The deadband of output percentage on the motor controller
            config.m_falconOutputDeadband = 0.001;

            config.m_sensorUnitsMaxVelocity = 6000.0 * 2048.0 / 600.0;

            config.m_turnSensitivity = 0.5;

            // Velocity PID constants
            config.m_gainsSlot = 0;

            /*
             * Feedforward calculated by taking max theoratical gain and then manually
             * tuning
             */
            config.m_velF = (1023.0 / 20660.0) + 0.03;

            // PID calculated from SysID tool
            config.m_velP = 0.00069903;
            config.m_velI = 0.0;
            config.m_velD = 0.0;

            config.m_nominalOutput = 0;
            config.m_peakOutput = 1;

            config.m_timeoutMs = 0;
            return config;
        }

    }

    public static final class SHOOTER {
        public static final ShooterSubsystem.Configuration CONFIG_SHOOTER() {
            ShooterSubsystem.Configuration config = new ShooterSubsystem.Configuration();

            /**
             * Clicks per rotation for the
             * internal encoder in the
             * Falcon 500
             */
            config.m_encoder_cpr = 2048;

            config.m_bottomShooterGearingRatio = 24 / 18;
            config.m_topShooterGearingRatio = 2 / 1;
            config.m_timeoutMS = TIMEOUT_MS;
            config.m_shooterMotorPeakOutput = 1.0;

            config.m_shooterAllowedErrorRPM = 100;
            config.m_PIDSlot = 0;

            // Bottom
            config.m_bottomShooterP = 0.1;
            config.m_bottomShooterI = 0.0;
            config.m_bottomShooterD = 0.001;
            config.m_bottomShooterF = 0.047;

            // Top
            config.m_topShooterP = 0.1;
            config.m_topShooterI = 0.0;
            config.m_topShooterD = 0.005;
            config.m_topShooterF = 0.05;

            return config;
        }
    }

    // Turret
    // TODO: Tune Turret constants, currently values from rev's example
    public static final class TURRET {
        public static final TurretSubsystem.Configuration config = new TurretSubsystem.Configuration();

        public static final TurretSubsystem.Configuration GET_TURRET_CONFIG() {
            TurretSubsystem.Configuration config = new TurretSubsystem.Configuration();

            config.m_trackingP = 0.03;
            config.m_trackingI = 0.0;
            config.m_trackingD = 0.0;
            config.m_trackingSetpoint = 0; // The center of the camera view is zero.
            config.m_trackingToleranceDegrees = 1.0;
            config.m_trackingMaxSpeed = 0.4;
            config.m_trackingMinSpeed = 0.05;

            config.m_turretAxisMaxSpeed = 0.5;

            config.m_turretMotorStallLimitAmps = 30;
            config.m_turretMotorFreeLimitAmps = 3;

            config.m_turretMotorP = 0.00005;
            config.m_turretMotorI = 0.0;
            config.m_turretMotorD = 0.0;
            config.m_turretMotorIZone = 0.0;
            config.m_turretMotorFF = 0.000156;
            config.m_turretMotorMaxOutput = 0.2;
            config.m_turretMotorMinOutput = -0.2;
            config.m_turretMotorMaxRPM = 1000;

            config.m_turretMotorMaxVel = 500;
            config.m_turretMotorMinVel = 0;
            config.m_turretMotorMaxAcc = 5;
            config.m_turretMotorAllowedError = (10 / 360); // Max error is 10 degrees of motor rotation (0.20 degrees
                                                           // turret rotation)

            config.m_turretRotationsCounterClockwiseSoftLimit = -0.60;
            config.m_turretRotationsClockwiseSoftLimit = 0.60;
            config.m_turretGearRatio = 49.6;
            return config;
        }
    }

    public static final class INTAKE_ARM {
        public static final IntakeArmSubsystem.Configuration GET_INTAKE_ARM_CONFIG() {
            IntakeArmSubsystem.Configuration config = new IntakeArmSubsystem.Configuration();

            config.m_deployMilliseconds = 1000;
            config.m_stowMilliseconds = 1000;

            return config;
        }
    }

    public static final class INTAKE_ROLLER {

        public static final IntakeRollerSubsystem.Configuration GET_INTAKE_ROLLER_CONFIG() {
            IntakeRollerSubsystem.Configuration config = new IntakeRollerSubsystem.Configuration();

            config.m_rollerTopSpeed = 0.5;
            config.m_rollerAxisMaxSpeed = 1.0;

            config.m_rollerContinousAmpLimit = 35;
            config.m_rollerPeakAmpLimit = 50;
            config.m_rollerSpeedUpMillis = 2000;

            return config;
        }
    }

    public static final class CLIMBER {
        public static final ClimberSubsystem.Configuration GET_CLIMBER_CONFIG() {
            ClimberSubsystem.Configuration config = new ClimberSubsystem.Configuration();

            config.m_climberAxisMaxSpeed = 1.0;

            config.m_climberMotorIdleMode = IdleMode.kBrake;
            config.m_climberMotorStallLimitAmps = 35;
            config.m_climberMotorFreeLimitAmps = 35;
            config.m_isRightSideInverted = false;
            config.m_climberGrippedAmps = 20;

            // TODO: Tune climber smart motion constants, currently values from rev's
            // example
            config.m_climberMotorP = 0.00005;
            config.m_climberMotorI = 0.0;
            config.m_climberMotorD = 0.0;
            config.m_climberMotorIZone = 0.0;
            config.m_climberMotorFF = 0.000156;
            config.m_climberMotorMaxOutput = 0.4;
            config.m_climberMotorMinOutput = -0.2;
            config.m_climberMotorMaxRPM = 1000;

            config.m_climberMotorMaxVel = 500;
            config.m_climberMotorMinVel = 0;
            config.m_climberMotorMaxAcc = 5;
            config.m_climberMotorAllowedError = 5;

            return config;
        }
    }

    public static final class LIMELIGHT {
        public static long m_pipelineSwitchMillis = 1000;

        public static final LimelightSubsystem.Configuration GET_LIMELIGHT_SUBSYSTEM_CONFIG() {
            LimelightSubsystem.Configuration config = new LimelightSubsystem.Configuration();
            config.m_humanPipelineIndex = 0;
            config.m_targetingPipelineIndex = 1;

            config.m_isLimelightPrimaryStream = true;

            /** Angle of the Limelight axis from horizontal (degrees) */
            config.m_LimelightMountingAngle = 30;

            /** Height of the Limelight lens center from the floor (inches), from CAD */
            config.m_LimelightMountingHeightInches = 35.64;

            /**
             * Target width in inches: This varies, but if we catch 4 stripes, it's about 3
             * feet
             */
            config.m_TargetWidth = 36;

            /**
             * Target height in inches: This also varies but the arc of stripes is about 5
             * inches
             */
            config.m_TargetHeight = 2;

            config.m_targetHeightFromFloor = 103.5;

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

    public final class DIO_IDS {
        public static final int FEEDER_SENSOR_DIO_PORT = 1;
        public static final int INTAKE_SENSOR_DIO_PORT = 0;
        public static final int LEFT_ENCODER_CHANNEL_A = 6;
        public static final int LEFT_ENCODER_CHANNEL_B = 7;
        public static final int RIGHT_ENCODER_CHANNEL_A = 8;
        public static final int RIGHT_ENCODER_CHANNEL_B = 9;
    }

    public final class COMPRESSOR {
        public static final int MIN_PRESSURE_PSI = 90;
        public static final int MAX_PRESSURE_PSI = 120;
    }

    public static final class FEEDER {
        public static FeederSubsystem.Configuration GET_FEEDER_SUBSYSTEM_CONFIG() {
            FeederSubsystem.Configuration config = new FeederSubsystem.Configuration();
            config.m_feederMotorAxisMaxSpeed = 1.0;
            config.m_feederMotorShootSpeed = 1.0;
            return config;
        }
    }
}
