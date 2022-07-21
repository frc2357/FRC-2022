package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2357.frc2022.Constants;
import com.team2357.frc2022.sensors.SensorBooleanState;
import com.team2357.lib.subsystems.LimelightSubsystem;
import com.team2357.lib.subsystems.drive.FalconDriveSubsystem;
import com.team2357.lib.subsystems.PDHSubsystem;
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

    public PDHSubsystem CreatePDHSubsystem() {
        return new PDHSubsystem(Constants.CAN_ID.POWER_DISTRIBUTION_HUB_ID);
    }

    public SensorSubsystem CreateSensorSubsystem(SensorBooleanState intakeSensor, SensorBooleanState indexSensor, SensorBooleanState feederSensor) {
        return new SensorSubsystem(intakeSensor, indexSensor, feederSensor);
    }

    public FalconDriveSubsystem CreateFalconTrajectoryDriveSubsystem() {
        WPI_TalonFX leftFalconMaster = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_1);

        WPI_TalonFX[] leftFalconSlaves = new WPI_TalonFX[] {
            new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_2),
            new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_3)};

        WPI_TalonFX rightFalconMaster = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_1);

        WPI_TalonFX[] rightFalconSlaves = new WPI_TalonFX[] {
            new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_2),
            new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_3)};

        PigeonIMU gyro = new PigeonIMU(Constants.CAN_ID.GYRO_ID);

        FalconDriveSubsystem subsystem = new FalconDriveSubsystem(leftFalconMaster,
                leftFalconSlaves, rightFalconMaster, rightFalconSlaves, gyro);

        subsystem.configure(Constants.DRIVE.GET_FALCON_DRIVE_CONFIG());
        return subsystem;
    }

    public IntakeArmSubsystem createIntakeArmSubsystem() {
        DoubleSolenoid intakeDoubleSolenoid = new DoubleSolenoid(Constants.CAN_ID.PNEUMATICS_HUB_ID,
                PneumaticsModuleType.REVPH, Constants.PH_ID.INTAKE_SOLENOID_FORWARD_CHANNEL,
                Constants.PH_ID.INTAKE_SOLENOID_REVERSE_CHANNEL);
        IntakeArmSubsystem subsystem = new IntakeArmSubsystem(intakeDoubleSolenoid);
        subsystem.configure(Constants.INTAKE_ARM.GET_INTAKE_ARM_CONFIG());
        return subsystem;
    }

    public IntakeRollerSubsystem CreateIntakeRollerSubsystem() {
        WPI_TalonSRX masterIntakeTalon = new WPI_TalonSRX(Constants.CAN_ID.MASTER_INTAKE_MOTOR_ID);
        WPI_TalonSRX followerIntakeTalon = new WPI_TalonSRX(Constants.CAN_ID.FOLLOWER_INTAKE_MOTOR_ID);
        IntakeRollerSubsystem subsystem = new IntakeRollerSubsystem(masterIntakeTalon, followerIntakeTalon);
        subsystem.configure(Constants.INTAKE_ROLLER.GET_INTAKE_ROLLER_CONFIG());
        return subsystem;
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
        DoubleSolenoid climberDoubleSolenoid = new DoubleSolenoid(
                Constants.CAN_ID.PNEUMATICS_HUB_ID,
                PneumaticsModuleType.REVPH,
                Constants.PH_ID.CLIMBER_SOLENOID_FORWARD_CHANNEL,
                Constants.PH_ID.CLIMBER_SOLENOID_REVERSE_CHANNEL
        );
        Solenoid hookSolenoid = new Solenoid(
                Constants.CAN_ID.PNEUMATICS_HUB_ID,
                PneumaticsModuleType.REVPH,
                Constants.PH_ID.CLIMBER_HOOK_SOLENOID_CHANNEL
        );
        ClimberSubsystem subsystem = new ClimberSubsystem(
                leftClimberMotor,
                rightClimberMotor,
                climberDoubleSolenoid,
                hookSolenoid
        );

        subsystem.configure(Constants.CLIMBER.GET_CLIMBER_CONFIG());

        return subsystem;
    }

    public FeederSubsystem CreateFeederSubsystem() {
        WPI_VictorSPX feederVictor = new WPI_VictorSPX((Constants.CAN_ID.FEEDER_MOTOR_ID));
        FeederSubsystem subsystem = new FeederSubsystem(feederVictor);
        subsystem.configure(Constants.FEEDER.GET_FEEDER_SUBSYSTEM_CONFIG());
        return subsystem;
    }   

    public TurretSubsystem CreateTurretSubsystem() {
        CANSparkMax turretMotor = new CANSparkMax(Constants.CAN_ID.TURRET_MOTOR_ID, MotorType.kBrushless);
        TurretSubsystem subsystem = new TurretSubsystem(turretMotor);
        subsystem.configure(Constants.TURRET.GET_TURRET_CONFIG());
        return subsystem;
    }

    public LimelightSubsystem CreateVisionSubsystem() {
        LimelightSubsystem subsystem = new LimelightSubsystem();
        subsystem.setConfiguration(Constants.LIMELIGHT.GET_LIMELIGHT_SUBSYSTEM_CONFIG());
        return subsystem;
    }
}
