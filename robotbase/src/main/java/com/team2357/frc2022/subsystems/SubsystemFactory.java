package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.team2357.frc2022.Constants;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

public class SubsystemFactory {
    public SubsystemFactory() {
    }

    public FalconTrajectoryDriveSubsystem CreateFalconTrajectoryDriveSubsystem() {
        FalconTrajectoryDriveSubsystem.Configuration config = new FalconTrajectoryDriveSubsystem.Configuration();
        config.m_isRightInverted = true;

        WPI_TalonFX leftFalconMaster = new WPI_TalonFX(Constants.DRIVE_MOTOR_LEFT_1);
        WPI_TalonFX[] leftFalconSlaves = new WPI_TalonFX[] { new WPI_TalonFX(Constants.DRIVE_MOTOR_LEFT_2) };
        WPI_TalonFX rightFalconMaster = new WPI_TalonFX(Constants.DRIVE_MOTOR_RIGHT_1);
        WPI_TalonFX[] rightFalconSlaves = new WPI_TalonFX[] { new WPI_TalonFX(Constants.DRIVE_MOTOR_RIGHT_2) };
        PigeonIMU gyro = new PigeonIMU(Constants.GYRO_ID);
        FalconTrajectoryDriveSubsystem subsystem = new FalconTrajectoryDriveSubsystem(leftFalconMaster,
                leftFalconSlaves, rightFalconMaster, rightFalconSlaves, gyro, Constants.ENCODER_DISTANCE_PER_PULSE);
        subsystem.configure(config);
        return subsystem;
    }
}
