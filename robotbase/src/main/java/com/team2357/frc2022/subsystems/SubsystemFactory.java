package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2357.frc2022.Constants;
import com.team2357.lib.subsystems.LimelightSubsystem;
import com.team2357.lib.subsystems.TogglableLimelightSubsystem;
import com.team2357.lib.subsystems.TogglableLimelightSubsystem.PipelineIndex;
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

    public FeederSubsystem CreateFeederSubsystem() {
        WPI_TalonSRX feederTalon = new WPI_TalonSRX((Constants.CAN_ID.FEEDER_MOTOR_ID));
        return new FeederSubsystem(feederTalon);
    }

    public TogglableLimelightSubsystem CreateVisionSubsystem() {
        TogglableLimelightSubsystem subsystem = new TogglableLimelightSubsystem(false);
        subsystem.setPipeline(PipelineIndex.HUMAN_VIEW);
        subsystem.setStream(false);
        LimelightSubsystem.Configuration config = new LimelightSubsystem.Configuration();
        config.m_LimelightMountingAngle = Constants.LIMELIGHT.MOUNTING_ANGLE;
        config.m_LimelightMountingHeightInches = Constants.LIMELIGHT.MOUNTING_HEIGHT_INCHES;
        config.m_TargetWidth = Constants.LIMELIGHT.VISION_TARGET_WIDTH_INCHES;
        config.m_TargetHeight = Constants.LIMELIGHT.VISION_TARGET_HEIGHT_INCHES;
        subsystem.setConfiguration(config);
        return subsystem;
    }

    public TurretSubsystem createTurretSubsystem() {
        TurretSubsystem.Configuration config = new TurretSubsystem.Configuration();
        config.m_turretMotorStallLimitAmps = Constants.TURRET.TURRET_MOTOR_STALL_LIMIT_AMPS;
        config.m_turretMotorFreeLimitAmps = Constants.TURRET.TURRET_MOTOR_FREE_LIMIT_AMPS;

        config.m_turretMotorP = Constants.TURRET.TURRET_MOTOR_P;
        config.m_turretMotorI = Constants.TURRET.TURRET_MOTOR_I;
        config.m_turretMotorD = Constants.TURRET.TURRET_MOTOR_D;
        config.m_turretMotorIZone = Constants.TURRET.TURRET_MOTOR_I_ZONE;
        config.m_turretMotorFF = Constants.TURRET.TURRET_MOTOR_FF;
        config.m_turretMotorMaxOutput = Constants.TURRET.TURRET_MOTOR_MAX_OUTPUT;
        config.m_turretMotorMinOutput = Constants.TURRET.TURRET_MOTOR_MIN_OUTPUT;
        config.m_turretMotorMaxRPM = Constants.TURRET.TURRET_MOTOR_MAX_RPM;

        config.m_turretMotorMaxVel = Constants.TURRET.TURRET_MOTOR_MAX_VEL;
        config.m_turretMotorMinVel = Constants.TURRET.TURRET_MOTOR_MIN_VEL;
        config.m_turretMotorMaxAcc = Constants.TURRET.TURRET_MOTOR_MAX_ACC;
        config.m_turretMotorAllowedError = Constants.TURRET.TURRET_MOTOR_ALLOWEDERROR;

        config.m_turretRotationsClockwiseSoftLimit = Constants.TURRET.TURRET_ROTATIONS_CLOCKWISE_SOFT_LIMIT;
        config.m_turretRotationsCounterClockwiseSoftLimit = Constants.TURRET.TURRET_ROTATIONS_COUNTER_CLOCKWISE_SOFT_LIMIT;
        config.m_rotationsPerDegree = Constants.TURRET.ROTATIONS_PER_DEGREE;

        CANSparkMax turretMotor = new CANSparkMax(Constants.CAN_ID.TURRET_MOTOR_ID, MotorType.kBrushless);
        TurretSubsystem subsystem = new TurretSubsystem(turretMotor);
        subsystem.configure(config);
        return subsystem;
    }

}
