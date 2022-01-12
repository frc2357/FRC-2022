// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private FalconTrajectoryDriveSubsystem m_driveSub;
  private final InvertDriveControls m_driverControls;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    SubsystemFactory subsystemFactory = new SubsystemFactory();
    m_driveSub = subsystemFactory.CreateFalconTrajectoryDriveSubsystem();
    // Configure the button bindings
    // Configure the button bindings
    m_driverControls = new InvertDriveControls.InvertDriveControlsBuilder(new XboxController(0), .1)
        .withDriveSub(m_driveSub).build();

    configureDriveSub();
  }

  private void configureDriveSub() {
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
