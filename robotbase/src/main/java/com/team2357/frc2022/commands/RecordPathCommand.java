// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022.commands;

import java.util.ArrayList;

import com.team2357.frc2022.util.Utility;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RecordPathCommand extends CommandBase {

  private static final double TIME_STEP = 1.0;

  private final Boolean m_shouldResetOdometry;
  private double m_timestamp;
  private ArrayList<Pose2d> m_path;

  public RecordPathCommand() {
    this(true);
  }

  /** Creates a new RecordPath. */
  public RecordPathCommand(final Boolean shouldResetOdometry) {
    m_shouldResetOdometry = shouldResetOdometry;
    m_timestamp = 0.0;
    m_path = new ArrayList<Pose2d>();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    FalconTrajectoryDriveSubsystem drive = FalconTrajectoryDriveSubsystem.getInstance();

    if (m_shouldResetOdometry) {
      drive.zeroHeading();
      drive.resetOdometry(new Pose2d(0.0, 0.0, new Rotation2d(0)));
    }
    m_timestamp = Timer.getFPGATimestamp();
    m_path.clear();
    m_path.add(drive.getPose());
    System.out.println("Recording...");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    FalconTrajectoryDriveSubsystem drive = FalconTrajectoryDriveSubsystem.getInstance();

    final double currentTime = Timer.getFPGATimestamp();
    if (currentTime < m_timestamp + TIME_STEP) {
      return;
    }
    if (Utility.isWithinTolerance(drive.getVelocityLeftEncoder(), 0, 0.1)
        && Utility.isWithinTolerance(drive.getVelocityRightEncoder(), 0, 0.1)) {
      return;
    }
    m_path.add(drive.getPose());
    m_timestamp = currentTime;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_path.add(FalconTrajectoryDriveSubsystem.getInstance().getPose());
    String lines = "";
    lines += "List.of(";
    for (Pose2d t : m_path) {
      lines += "  new Pose2d(" + t.getX() + ", " + t.getY() + ", Rotation2d.fromDegrees("+ t.getRotation().getDegrees() + ")),\n";
    }
    lines = lines.substring(0, lines.lastIndexOf(',')) + "),";
    System.out.print(lines);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}