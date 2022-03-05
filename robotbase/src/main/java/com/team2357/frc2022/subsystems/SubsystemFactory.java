package com.team2357.frc2022.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.team2357.frc2022.Constants;
import com.team2357.frc2022.sensors.SensorBooleanState;
import com.team2357.lib.subsystems.LimelightSubsystem;
import com.team2357.lib.subsystems.TogglableLimelightSubsystem;
import com.team2357.lib.subsystems.TogglableLimelightSubsystem.PipelineIndex;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;
import com.team2357.lib.util.Utility;

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
            Constants.DRIVE.LEFT_ENCODER_CHANNEL_A, Constants.DRIVE.LEFT_ENCODER_CHANNEL_B,
            Constants.DRIVE.RIGHT_ENCODER_CHANNEL_A, Constants.DRIVE.RIGHT_ENCODER_CHANNEL_B);

        subsystem.configure(Constants.DRIVE.GET_FALCON_DRIVE_CONFIG());
        return subsystem;
    }

    public IntakeSubsystem CreateIntakeSubsystem(SensorBooleanState intakeSensorState) {
        DoubleSolenoid intakeDoubleSolenoid = new DoubleSolenoid(Constants.CAN_ID.PNEUMATICS_HUB_ID,
            PneumaticsModuleType.REVPH, Constants.PH_ID.INTAKE_SOLENOID_FORWARD_CHANNEL,
            Constants.PH_ID.INTAKE_SOLENOID_REVERSE_CHANNEL);
        VictorSPX intakeVictor = new VictorSPX(Constants.CAN_ID.INTAKE_MOTOR_ID);
        return new IntakeSubsystem(intakeVictor, intakeDoubleSolenoid, intakeSensorState);
    }    

    public FeederSubsystem CreateFeederSubsystem(SensorBooleanState feederSensorState) {
        WPI_TalonSRX feederTalon = new WPI_TalonSRX((Constants.CAN_ID.FEEDER_MOTOR_ID));
        return new FeederSubsystem(feederTalon, feederSensorState);
    }   

    public TogglableLimelightSubsystem CreateVisionSubsystem() {
        TogglableLimelightSubsystem subsystem = new TogglableLimelightSubsystem(false);
        subsystem.setPipeline(PipelineIndex.HUMAN_VIEW);
        subsystem.setStream(false);
        subsystem.setConfiguration(Constants.LIMELIGHT.GET_LIMELIGHT_SUBSYSTEM_CONFIG());
        return subsystem;
    }
}
