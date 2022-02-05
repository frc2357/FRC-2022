package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.team2357.frc2022.Constants;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * This class is a factory that creates subsystems.
 * 
 * @category Subsystems
 */
public class SubsystemFactory {
    public SubsystemFactory() {
    }

    public FalconTrajectoryDriveSubsystem CreateFalconTrajectoryDriveSubsystem() {
        FalconTrajectoryDriveSubsystem.Configuration config = new FalconTrajectoryDriveSubsystem.Configuration();
        config.m_isRightInverted = Constants.DRIVE.INVERT_GYRO;
        config.m_isGyroReversed = Constants.DRIVE.INVERT_RIGHT_SIDE;

        WPI_TalonFX leftFalconMaster = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_1);
        WPI_TalonFX[] leftFalconSlaves = new WPI_TalonFX[] { new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_2), new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_3) };
        WPI_TalonFX rightFalconMaster = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_1);
        WPI_TalonFX[] rightFalconSlaves = new WPI_TalonFX[] { new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_2), new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_3) };
        PigeonIMU gyro = new PigeonIMU(Constants.CAN_ID.GYRO_ID);
        FalconTrajectoryDriveSubsystem subsystem = new FalconTrajectoryDriveSubsystem(leftFalconMaster,
                leftFalconSlaves, rightFalconMaster, rightFalconSlaves, gyro,
                Constants.DRIVE.ENCODER_DISTANCE_PER_PULSE_METERS, Constants.DRIVE.LEFT_ENCODER_CHANNEL_A,
                Constants.DRIVE.LEFT_ENCODER_CHANNEL_B,
                Constants.DRIVE.RIGHT_ENCODER_CHANNEL_A,
                Constants.DRIVE.RIGHT_ENCODER_CHANNEL_B);
                
        subsystem.configure(config);
        return subsystem;
    }

    public IntakeSubsystem CreateIntakeSubsystem() {
        DoubleSolenoid intakeDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH,
                Constants.PCM_ID.INTAKE_SOLENOID_FORWARD_CHANNEL, Constants.PCM_ID.INTAKE_SOLENOID_REVERSE_CHANNEL);
        VictorSPX intakeVictor = new VictorSPX(Constants.CAN_ID.INTAKE_MOTOR_ID);
        return new IntakeSubsystem(intakeVictor, intakeDoubleSolenoid);
    }

    public FeederSubsystem CreateFeederSubsystem() {
        WPI_TalonSRX feederTalon = new WPI_TalonSRX((Constants.CAN_ID.FEEDER_MOTOR_ID));
        return new FeederSubsystem(feederTalon);
    }
               
}
