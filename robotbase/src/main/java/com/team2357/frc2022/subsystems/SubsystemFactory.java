package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMaxLowLevel;
import com.team2357.frc2022.Constants;
import com.team2357.frc2022.sensors.SensorBooleanState;
import com.team2357.lib.subsystems.TogglableLimelightSubsystem;
import com.team2357.lib.subsystems.TogglableLimelightSubsystem.PipelineIndex;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;
import com.team2357.lib.util.Utility;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * This class is a factory that creates subsystems.
 * 
 * @category Subsystems
 */
public class SubsystemFactory {
    public SubsystemFactory() {
    }

    public FalconTrajectoryDriveSubsystem CreateFalconTrajectoryDriveSubsystem() {

        WPI_TalonFX leftFalconMaster = Utility.createDriveTalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_1,
                Constants.DRIVE.DRIVE_MOTOR_RAMP_RATE_SECONDS);

        WPI_TalonFX[] leftFalconSlaves = new WPI_TalonFX[] {
                Utility.createDriveTalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_2,
                        Constants.DRIVE.DRIVE_MOTOR_RAMP_RATE_SECONDS),
                Utility.createDriveTalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_3,
                        Constants.DRIVE.DRIVE_MOTOR_RAMP_RATE_SECONDS) };

        WPI_TalonFX rightFalconMaster = Utility.createDriveTalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_1,
                Constants.DRIVE.DRIVE_MOTOR_RAMP_RATE_SECONDS);

        WPI_TalonFX[] rightFalconSlaves = new WPI_TalonFX[] {
                Utility.createDriveTalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_2,
                        Constants.DRIVE.DRIVE_MOTOR_RAMP_RATE_SECONDS),
                Utility.createDriveTalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_3,
                        Constants.DRIVE.DRIVE_MOTOR_RAMP_RATE_SECONDS) };

        PigeonIMU gyro = new PigeonIMU(Constants.CAN_ID.GYRO_ID);

        FalconTrajectoryDriveSubsystem subsystem = new FalconTrajectoryDriveSubsystem(leftFalconMaster,
                leftFalconSlaves, rightFalconMaster, rightFalconSlaves, gyro,
                Constants.DRIVE.ENCODER_DISTANCE_PER_PULSE_METERS,
                Constants.DIO_IDS.LEFT_ENCODER_CHANNEL_A, Constants.DIO_IDS.LEFT_ENCODER_CHANNEL_B,
                Constants.DIO_IDS.RIGHT_ENCODER_CHANNEL_A, Constants.DIO_IDS.RIGHT_ENCODER_CHANNEL_B);

        subsystem.configure(Constants.DRIVE.GET_FALCON_DRIVE_CONFIG());
        return subsystem;
    }

    public IntakeSubsystem CreateIntakeSubsystem(SensorBooleanState intakeSensorState) {
        DoubleSolenoid intakeDoubleSolenoid = new DoubleSolenoid(Constants.CAN_ID.PNEUMATICS_HUB_ID,
                PneumaticsModuleType.REVPH, Constants.PH_ID.INTAKE_SOLENOID_FORWARD_CHANNEL,
                Constants.PH_ID.INTAKE_SOLENOID_REVERSE_CHANNEL);
        VictorSPX intakeVictor = new VictorSPX(Constants.CAN_ID.INTAKE_MOTOR_ID);
        intakeVictor.setInverted(Constants.INTAKE.INVERT_MOTOR);
        return new IntakeSubsystem(intakeVictor, intakeDoubleSolenoid, intakeSensorState);
    }

    public ShooterSubsystem CreateShooterSubsystem() {
        WPI_TalonFX leftBottom = new WPI_TalonFX(Constants.CAN_ID.SHOOTER_BOTTOM_LEFT);
        WPI_TalonFX rightBottom = new WPI_TalonFX(Constants.CAN_ID.SHOOTER_BOTTOM_RIGHT);
        WPI_TalonFX top = new WPI_TalonFX(Constants.CAN_ID.SHOOTER_TOP);
        ShooterSubsystem subsystem = new ShooterSubsystem(leftBottom, rightBottom, top);
        subsystem.configure(Constants.SHOOTER.CONFIG_SHOOTER());
        return subsystem;
    }

    public ClimberSubsystem CreateClimberSubsystem() {
        CANSparkMax leftClimberMotor = new CANSparkMax(Constants.CAN_ID.CLIMBER_MOTOR_LEFT_ID, MotorType.kBrushless);
        CANSparkMax rightClimberMotor = new CANSparkMax(Constants.CAN_ID.CLIMBER_MOTOR_RIGHT_ID, MotorType.kBrushless);
        DoubleSolenoid climberDoubleSolenoid = new DoubleSolenoid(30, PneumaticsModuleType.REVPH,
                Constants.PH_ID.CLIMBER_SOLENOID_FORWARD_CHANNEL, Constants.PH_ID.CLIMBER_SOLENOID_REVERSE_CHANNEL);
        Solenoid hookSolenoid = new Solenoid(30, PneumaticsModuleType.REVPH,
                Constants.PH_ID.CLIMBER_HOOK_SOLENOID_CHANNEL);
        ClimberSubsystem subsystem = new ClimberSubsystem(leftClimberMotor, rightClimberMotor, climberDoubleSolenoid,
                hookSolenoid);

        subsystem.configure(Constants.CLIMBER.GET_CLIMBER_CONFIG());

        return subsystem;
    }

    public FeederSubsystem CreateFeederSubsystem(SensorBooleanState feederSensorState) {
        WPI_VictorSPX feederVictor = new WPI_VictorSPX((Constants.CAN_ID.FEEDER_MOTOR_ID));
        feederVictor.setInverted(Constants.FEEDER.IS_INVERTED);
        return new FeederSubsystem(feederVictor, feederSensorState);
    }   

    public KickerSubsystem CreateKickerSubsystem() {
        CANSparkMax kickerMotor = new CANSparkMax(Constants.CAN_ID.KICKER_MOTOR_ID,
                CANSparkMaxLowLevel.MotorType.kBrushless);
        return new KickerSubsystem(kickerMotor);
    }

    public TurretSubsystem CreateTurretSubsystem() {
        CANSparkMax turretMotor = new CANSparkMax(Constants.CAN_ID.TURRET_MOTOR_ID,
                CANSparkMaxLowLevel.MotorType.kBrushless);
        TurretSubsystem subsystem = new TurretSubsystem(turretMotor);
        subsystem.configure(Constants.TURRET.GET_TURRET_CONFIG());
        return subsystem;
    }

    public TogglableLimelightSubsystem CreateVisionSubsystem() {
        TogglableLimelightSubsystem subsystem = new TogglableLimelightSubsystem(false);
        subsystem.setPipeline(PipelineIndex.HUMAN_VIEW);
        subsystem.setStream(false);
        subsystem.setConfiguration(Constants.LIMELIGHT.GET_LIMELIGHT_SUBSYSTEM_CONFIG());
        return subsystem;
    }
}
