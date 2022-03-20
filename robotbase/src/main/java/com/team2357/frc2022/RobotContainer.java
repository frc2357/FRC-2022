// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022;

import com.team2357.frc2022.commands.RecordPathCommand;
import com.team2357.frc2022.commands.feeder.FeederAdvanceCommand;
import com.team2357.frc2022.controls.GunnerControls;
import com.team2357.frc2022.controls.IntakeDriveControls;
import com.team2357.frc2022.sensors.SensorBooleanState;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.SubsystemFactory;
import com.team2357.frc2022.util.AvailableTrajectories;
import com.team2357.lib.commands.DriveVelocityCommand;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final IntakeDriveControls m_driverControls;
  private final GunnerControls m_gunnerControls;
  private final Compressor m_compressor;

  private final DigitalInput m_intakeSensor;
  private final DigitalInput m_feederSensor;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_intakeSensor = new DigitalInput(Constants.DIO_IDS.INTAKE_SENSOR_DIO_PORT);
    m_feederSensor = new DigitalInput(Constants.DIO_IDS.FEEDER_SENSOR_DIO_PORT);

    SensorBooleanState intakeIRSensor = () -> {
      return !m_intakeSensor.get();
    };
    SensorBooleanState feederIRSensor = () -> {
      return !m_feederSensor.get();
    };

    // Create subsystems
    SubsystemFactory subsystemFactory = new SubsystemFactory();
    FalconTrajectoryDriveSubsystem driveSub = subsystemFactory.CreateFalconTrajectoryDriveSubsystem();
    subsystemFactory.CreateSensorSubsystem(intakeIRSensor, feederIRSensor);
    subsystemFactory.CreateShooterSubsystem();
    subsystemFactory.createIntakeArmSubsystem();
    subsystemFactory.CreateIntakeRollerSubsystem();
    subsystemFactory.CreateFeederSubsystem();
    subsystemFactory.CreateVisionSubsystem();
    subsystemFactory.CreateClimberSubsystem();
    subsystemFactory.CreateTurretSubsystem();

    // Configure the controllers
    XboxController driverXboxController = new XboxController(Constants.CONTROLLER.DRIVE_CONTROLLER_PORT);
    XboxController gunnerXboxController = new XboxController(Constants.CONTROLLER.GUNNER_CONTROLLER_PORT);

    m_driverControls = new IntakeDriveControls(driverXboxController, Constants.CONTROLLER.DRIVE_CONTROLLER_DEADBAND);
    m_gunnerControls = new GunnerControls(gunnerXboxController);

    driveSub.setDefaultCommand(new DriveVelocityCommand(driveSub, m_driverControls));
    FeederSubsystem.getInstance().setDefaultCommand(new FeederAdvanceCommand());

    // Setup compressor
    m_compressor = new Compressor(Constants.CAN_ID.PNEUMATICS_HUB_ID, PneumaticsModuleType.REVPH);
    m_compressor.enableAnalog(0, 1);

    // Build trajectories
    AvailableTrajectories.generateTrajectories();

    SmartDashboard.putData("Record Path", new RecordPathCommand());
    SmartDashboard.putData("Record Keep Odometry Path", new RecordPathCommand(true));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    int auto = 2;
    switch (auto) {
      case 1:
        return AvailableTrajectories.exampleTrajectory;
      case 2:
        return AvailableTrajectories.exampleRecordPathTrajectory;
      default:
        return null;
    }
  }
}
