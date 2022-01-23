// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022;

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

        // Drive
        public static final int DRIVE_MOTOR_LEFT_1 = 11;
        public static final int DRIVE_MOTOR_RIGHT_1 = 12;
        public static final int DRIVE_MOTOR_LEFT_2 = 13;
        public static final int DRIVE_MOTOR_RIGHT_2 = 14;
        public static final int GYRO_ID = 5;

        // Intake
        public static final int INTAKE_MOTOR_ID = 21;

        // Climber
        public static final int CLIMBER_MOTOR_LEFT_ID = 22;
        public static final int CLIMBER_MOTOR_RIGHT_ID = 23;
    }

    public final class PCM_ID {
        public static final int INTAKE_SOLENOID_FORWARD_CHANNEL = 0;
        public static final int INTAKE_SOLENOID_REVERSE_CHANNEL = 0;
        public static final int CLIMBER_SOLENOID_FORWARD_CHANNEL = 0;
        public static final int CLIMBER_SOLENOID_REVERSE_CHANNEL = 0;
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
        public static final boolean IS_RIGHT_SIDE_INVERTED = true;
        public static final double WHEEL_DIAMETER_IN_METERS = 0.102;
        public static final int ENCODER_PPR = 256;

        public static final double ENCODER_DISTANCE_PER_PULSE_METERS = (WHEEL_DIAMETER_IN_METERS * Math.PI) / (double) ENCODER_PPR;

        public static final int LEFT_ENCODER_CHANNEL_A = 0;
        public static final int LEFT_ENCODER_CHANNEL_B = 1;
        public static final int RIGHT_ENCODER_CHANNEL_A = 2;
        public static final int RIGHT_ENCODER_CHANNEL_B = 3;

        public static final boolean INVERT_GYRO = true;
        public static final boolean INVERT_RIGHT_SIDE = true;
    }

    public final class CLIMBER {
        public static final double CLIMB_EXTEND_SPEED = 0;
        public static final double CLIMB_RETURN_SPEED = 0;
        public static final double TRANS_EXTEND_SPEED = 0;
        public static final double TRANS_RETURN_SPEED = 0;
        public static final int MOTOR_STALL_LIMIT_AMPS = 0;
        public static final int MOTOR_FREE_LIMIT_AMPS = 0;
        public static final boolean IS_RIGHT_SIDE_INVERTED = true;
    }
}
