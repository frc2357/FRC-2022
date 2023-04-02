// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022;

import com.team2357.frc2022.controls.IntakeDriveControls;
import com.team2357.frc2022.subsystems.SubsystemFactory;
import com.team2357.lib.commands.DriveVelocityCommand;
import com.team2357.lib.subsystems.drive.FalconDriveSubsystem;

import edu.wpi.first.wpilibj.XboxController;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final IntakeDriveControls m_driverControls;



  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Create subsystems
    SubsystemFactory subsystemFactory = new SubsystemFactory();
    FalconDriveSubsystem driveSub = subsystemFactory.CreateFalconTrajectoryDriveSubsystem();
    subsystemFactory.CreatePDHSubsystem();

    // Configure the controllers
    XboxController driverXboxController = new XboxController(Constants.CONTROLLER.DRIVE_CONTROLLER_PORT);

    m_driverControls = new IntakeDriveControls(driverXboxController, Constants.CONTROLLER.DRIVE_CONTROLLER_DEADBAND);

    driveSub.setDefaultCommand(new DriveVelocityCommand(driveSub, m_driverControls));
    


  }

  public int getAutonomousCommand() {
    return 0;
}
}
