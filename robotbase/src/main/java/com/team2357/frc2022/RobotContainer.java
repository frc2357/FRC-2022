// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022;

import com.team2357.frc2022.arduino.RobotArduino;
import com.team2357.frc2022.arduino.ArduinoSensorState;
import com.team2357.frc2022.controls.GunnerControls;
import com.team2357.frc2022.controls.IntakeDriveControls;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.frc2022.subsystems.SubsystemFactory;
import com.team2357.lib.commands.DriveProportionalCommand;
import com.team2357.lib.subsystems.TogglableLimelightSubsystem;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private FalconTrajectoryDriveSubsystem m_driveSub;
  private IntakeSubsystem m_intakeSub;
  private FeederSubsystem m_feederSub;
  private TogglableLimelightSubsystem m_visionSub;

  private final IntakeDriveControls m_driverControls;
  private final GunnerControls m_gunnerControls;

  private final RobotArduino m_arduinoSensor;


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_arduinoSensor = new RobotArduino(Constants.ARDUINO.ARDUINO_SENSOR_DEVICE_NAME);
    ArduinoSensorState intakeIRSensor = () -> {
      return m_arduinoSensor.getIntakeValue();
    };
    ArduinoSensorState feederIRSensor = () -> {
      return m_arduinoSensor.getFeederValue();
    };
    ArduinoSensorState turretIRSensor = () -> {
      return m_arduinoSensor.getTurretValue();
    };

    
    // Create subsystems
    SubsystemFactory subsystemFactory = new SubsystemFactory();
    m_driveSub = subsystemFactory.CreateFalconTrajectoryDriveSubsystem();
    m_intakeSub = subsystemFactory.CreateIntakeSubsystem(intakeIRSensor);
    m_feederSub = subsystemFactory.CreateFeederSubsystem(feederIRSensor);
    m_visionSub = subsystemFactory.CreateVisionSubsystem();

    // Configure the button bindings
    m_driverControls = new IntakeDriveControls.IntakeDriveControlsBuilder(
        new XboxController(Constants.CONTROLLER.DRIVE_CONTROLLER_PORT), Constants.CONTROLLER.DRIVE_CONTROLLER_DEADBAND)
            .withIntakeSub(m_intakeSub).withVisionSub(m_visionSub).build();

    m_gunnerControls = new GunnerControls.GunnerControlsBuilder(
        new XboxController(Constants.CONTROLLER.GUNNER_CONTROLLER_PORT)).withIntakeSub(m_intakeSub)
            .build();

    m_driveSub.setDefaultCommand(new DriveProportionalCommand(m_driveSub, m_driverControls));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
