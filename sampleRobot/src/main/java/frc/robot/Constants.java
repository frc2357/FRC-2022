// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.DriveSubsystem;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int XBOX_CONTROLLER_PORT = 0;

    public static final int LATCH_ID = 4;

    public final class CAN_ID {
        public static final int DRIVE_MOTOR_LEFT_1 = 11;
        public static final int DRIVE_MOTOR_RIGHT_1 = 12;
        public static final int DRIVE_MOTOR_LEFT_2 = 13;
        public static final int DRIVE_MOTOR_RIGHT_2 = 14;
        public static final int DRIVE_MOTOR_LEFT_3 = 15;
        public static final int DRIVE_MOTOR_RIGHT_3 = 16;

        public static final int PNEUMATICS_HUB_ID = 2;
    }

    public static final DriveSubsystem.Configuration GET_DRIVE_CONFIG() {
        DriveSubsystem.Configuration config = new DriveSubsystem.Configuration();
        config.m_speedSensitivity = .6;
        config.m_turnSensitivity = .5;
        return config;
    } 
}
