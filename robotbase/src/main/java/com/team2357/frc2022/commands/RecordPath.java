// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022.commands;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.team2357.frc2022.util.Utility;
import com.team2357.lib.subsystems.drive.FalconTrajectoryDriveSubsystem;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RecordPath extends CommandBase {

  private static final double TIME_STEP = 0.5;
  private static final String FILE_PATH = "/home/lvuser/RecordPaths";

  private final FalconTrajectoryDriveSubsystem m_drive;
  private final Boolean m_shouldResetOdometry;
  private double m_timestamp;
  private ArrayList<Translation2d> m_path;
  private Rotation2d m_beginRotation;
  private Rotation2d m_endRotation;

  public RecordPath(final FalconTrajectoryDriveSubsystem drive) {
    this(drive, true);
  }

  /** Creates a new RecordPath. */
  public RecordPath(final FalconTrajectoryDriveSubsystem drive, final Boolean shouldResetOdometry) {
    m_drive = drive;
    m_shouldResetOdometry = shouldResetOdometry;
    m_timestamp = 0.0;
    m_path = new ArrayList<Translation2d>();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (m_shouldResetOdometry) {
      m_drive.zeroHeading();
      m_drive.resetOdometry(new Pose2d(0.0, 0.0, new Rotation2d(0)));
    }
    m_timestamp = Timer.getFPGATimestamp();
    m_path.clear();
    m_path.add(m_drive.getPose().getTranslation());
    m_beginRotation = m_drive.getPose().getRotation();
    System.out.println("Recording...");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final double currentTime = Timer.getFPGATimestamp();
    if (currentTime < m_timestamp + TIME_STEP) {
      return;
    }
    if (Utility.isWithinTolerance(m_drive.getVelocityLeftEncoder(), 0, 0.1)
        && Utility.isWithinTolerance(m_drive.getVelocityRightEncoder(), 0, 0.1)) {
      return;
    }
    m_path.add(m_drive.getPose().getTranslation());
    m_timestamp = currentTime;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_path.add(m_drive.getPose().getTranslation());
    m_endRotation = m_drive.getPose().getRotation();
    final Translation2d begin = m_path.remove(0);
    final Translation2d end = m_path.remove(m_path.size() - 1);
    String lines = "";
    lines += "new Pose2d(" + begin.getX() + ", " + begin.getY() + ", Rotation2d.fromDegrees("
        + m_beginRotation.getDegrees() + ")),\n";
    lines += "List.of(\n";
    for (Translation2d t : m_path) {
      lines += "  new Translation2d(" + t.getX() + ", " + t.getY() + "),\n";
    }
    lines += "),\n";
    lines += "new Pose2d(" + end.getX() + ", " + end.getY() + ", Rotation2d.fromDegrees("
        + m_endRotation.getDegrees() + ")),\n";
    PrintWriter fileWriter = createFile();
    fileWriter.println(lines);
    System.out.print(lines);
    fileWriter.close();
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

  public PrintWriter createFile() {

    try {
      File file = new File(FILE_PATH);
      if (!file.exists()) {
        if (file.mkdir()) {
          System.out.println("Log Directory is created!");
        } else {
          System.out.println("Failed to create Log directory!");
        }
      }

      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
      PrintWriter fileWriter = new PrintWriter(FILE_PATH + dtf.format(LocalDateTime.now()) + "-Log.txt", "UTF-8");
      fileWriter.flush();
      return fileWriter;
    } catch (Exception e) {
      System.err.println("Done messed up the file for the trajectory record");
    }
    return null;

  }
}