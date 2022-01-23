package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
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
        WPI_TalonFX[] leftFalconSlaves = new WPI_TalonFX[] { new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_2) };
        WPI_TalonFX rightFalconMaster = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_1);
        WPI_TalonFX[] rightFalconSlaves = new WPI_TalonFX[] { new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_2) };
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

    public ClimberSubsystem CreateClimberSubsystem() {
        ClimberSubsystem.Configuration config = new ClimberSubsystem.Configuration();
        config.m_climberMotorIdleMode = IdleMode.kBrake;
        config.m_climbExtendSpeed = Constants.CLIMBER.CLIMB_EXTEND_SPEED;
        config.m_climbReturnSpeed = Constants.CLIMBER.CLIMB_RETURN_SPEED;
        config.m_transExtendSpeed = Constants.CLIMBER.TRANS_EXTEND_SPEED;
        config.m_transReturnSpeed = Constants.CLIMBER.TRANS_RETURN_SPEED;
        config.m_climberMotorStallLimitAmps = Constants.CLIMBER.MOTOR_STALL_LIMIT_AMPS;
        config.m_climberMotorFreeLimitAmps = Constants.CLIMBER.MOTOR_FREE_LIMIT_AMPS;
        config.m_isRightSideInverted = Constants.CLIMBER.IS_RIGHT_SIDE_INVERTED;

        CANSparkMax leftClimberMotor = new CANSparkMax(Constants.CAN_ID.CLIMBER_MOTOR_LEFT_ID, MotorType.kBrushless);
        CANSparkMax rightClimberMotor = new CANSparkMax(Constants.CAN_ID.CLIMBER_MOTOR_RIGHT_ID, MotorType.kBrushless);
        DoubleSolenoid climberDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH,
                Constants.PCM_ID.CLIMBER_SOLENOID_FORWARD_CHANNEL, Constants.PCM_ID.CLIMBER_SOLENOID_REVERSE_CHANNEL);

        ClimberSubsystem subsystem = new ClimberSubsystem(leftClimberMotor, rightClimberMotor, climberDoubleSolenoid);
        subsystem.configure(config);

        return subsystem;
    }
}
