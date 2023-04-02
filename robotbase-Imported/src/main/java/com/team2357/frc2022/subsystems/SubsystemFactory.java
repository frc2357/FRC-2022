package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.team2357.frc2022.Constants;
import com.team2357.frc2022.sensors.SensorBooleanState;
import com.team2357.lib.subsystems.PDHSubsystem;
import com.team2357.lib.subsystems.drive.FalconDriveSubsystem;

/**
 * This class is a factory that creates subsystems.
 * 
 * @category Subsystems
 */
public class SubsystemFactory {
    public SubsystemFactory() {
    }

    public PDHSubsystem CreatePDHSubsystem() {
        return new PDHSubsystem(Constants.CAN_ID.POWER_DISTRIBUTION_HUB_ID);
    }

    public SensorSubsystem CreateSensorSubsystem(SensorBooleanState intakeSensor, SensorBooleanState indexSensor, SensorBooleanState feederSensor) {
        return new SensorSubsystem(intakeSensor, indexSensor, feederSensor);
    }

    public FalconDriveSubsystem CreateFalconTrajectoryDriveSubsystem() {
        WPI_TalonFX leftFalconMaster = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_1);

        WPI_TalonFX[] leftFalconSlaves = new WPI_TalonFX[] {
            new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_2)};

        WPI_TalonFX rightFalconMaster = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_1);

        WPI_TalonFX[] rightFalconSlaves = new WPI_TalonFX[] {
            new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_2)};

        PigeonIMU gyro = new PigeonIMU(Constants.CAN_ID.GYRO_ID);

        FalconDriveSubsystem subsystem = new FalconDriveSubsystem(leftFalconMaster,
                leftFalconSlaves, rightFalconMaster, rightFalconSlaves, gyro);

        subsystem.configure(Constants.DRIVE.GET_FALCON_DRIVE_CONFIG());
        return subsystem;
    }
}
