// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022;

import com.team2357.frc2022.controls.IntakeDriveControls;
import com.team2357.frc2022.sensors.SensorBooleanState;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.SubsystemFactory;
import com.team2357.lib.commands.DriveVelocityCommand;
import com.team2357.lib.subsystems.drive.FalconDriveSubsystem;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final IntakeDriveControls m_driverControls;
  private final Compressor m_compressor;

  private final DigitalInput m_intakeSensor;
  private final DigitalInput m_indexSensor;
  private final DigitalInput m_feederSensor;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_intakeSensor = new DigitalInput(Constants.DIO_IDS.INTAKE_SENSOR_DIO_PORT);
    m_indexSensor = new DigitalInput(Constants.DIO_IDS.INDEX_SENSOR_DIO_PORT);
    m_feederSensor = new DigitalInput(Constants.DIO_IDS.FEEDER_SENSOR_DIO_PORT);

    SensorBooleanState intakeIRSensor = () -> {
      return !m_intakeSensor.get();
    };
    SensorBooleanState indexIRSensor = () -> {
      return !m_indexSensor.get();
    };
    SensorBooleanState feederIRSensor = () -> {
      return !m_feederSensor.get();
    };

    // Create subsystems
    SubsystemFactory subsystemFactory = new SubsystemFactory();
    FalconDriveSubsystem driveSub = subsystemFactory.CreateFalconTrajectoryDriveSubsystem();
    subsystemFactory.CreatePDHSubsystem();
//    subsystemFactory.CreateIntakeRollerSubsystem();
    // Configure the controllers
    XboxController driverXboxController = new XboxController(Constants.CONTROLLER.DRIVE_CONTROLLER_PORT);

    m_driverControls = new IntakeDriveControls(driverXboxController, Constants.CONTROLLER.DRIVE_CONTROLLER_DEADBAND);

    driveSub.setDefaultCommand(new DriveVelocityCommand(driveSub, m_driverControls));
    
    // Setup compressor
    m_compressor = new Compressor(Constants.CAN_ID.PNEUMATICS_HUB_ID, PneumaticsModuleType.REVPH);
   // m_compressor.enableAnalog(Constants.COMPRESSOR.MIN_PRESSURE_PSI, Constants.COMPRESSOR.MAX_PRESSURE_PSI);

    // Start USB camera capture

    // Build trajectories

    // SmartDashboard.putData("Record Keep Odometry Path", new RecordPathCommand(true));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new WaitCommand(0);
  }
}
