// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team2357.frc2022;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.util.HashMap;
import java.util.Map;

import com.team2357.log.LogSession;
import com.team2357.log.outputs.LogOutput;
import com.team2357.log.outputs.PrintStreamOutput;
import com.team2357.log.outputs.ZipFileOutput;
import com.team2357.log.topics.StringTopic;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static StringTopic log = new StringTopic("robot");
  private static StringTopic logError = new StringTopic("robot-error");

  private static LogOutput stdout = new PrintStreamOutput("", System.out);
  private static LogOutput stderr = new PrintStreamOutput("ERROR", System.out);

  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private LogSession m_logSession;

  private Map<String, LogOutput> createLogOutputs(String sessionType) {
    Map<String, Object> usbFileHeader = Map.of();

    return Map.of(
      "stdout", stdout,
      "stderr", stderr,
      "usb", new ZipFileOutput("/U", sessionType, usbFileHeader, 0.01)
    );
  }

  private void startLogSession(String sessionType) {
    if (m_logSession != null) {
      stopLogSession();
    }

    long before = System.currentTimeMillis();
    m_logSession = new LogSession(createLogOutputs(sessionType));
    long after = System.currentTimeMillis();
    System.out.println("log init time: " + ((double)(after - before) / 1000));

    m_logSession.subscribeTopic("robot", "stdout");
    m_logSession.subscribeTopic("robot-error", "stderr");
    m_logSession.subscribeTopic("robot", "usb");
    m_logSession.subscribeTopic("robot-error", "usb");
  }

  private void stopLogSession() {
    if (m_logSession != null) {
      m_logSession.stop();
      m_logSession = null;
    }
  }

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    //m_robotContainer = new RobotContainer();
    System.out.println("--robot init--");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    System.out.println("--disabled init--");
    stopLogSession();
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    System.out.println("--auto init--");
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    stopLogSession();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    startLogSession("teleop");

    /*
    try {
      log.log("Teleop Initialized");
    } catch (Throwable t) {
      logError.log("Uncaught throwable: " + t);
    }
    */
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    System.out.println("--test init--");
    stopLogSession();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
