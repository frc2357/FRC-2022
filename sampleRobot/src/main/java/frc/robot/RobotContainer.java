// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.LatchCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private XboxController m_xboxController = new XboxController(Constants.XBOX_CONTROLLER_PORT);
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings


    WPI_TalonFX left1 = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_1);
    WPI_TalonFX left2 = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_2);
    WPI_TalonFX left3 = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_LEFT_3);
    WPI_TalonFX right1 = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_1);
    WPI_TalonFX right2 = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_2);
    WPI_TalonFX right3 = new WPI_TalonFX(Constants.CAN_ID.DRIVE_MOTOR_RIGHT_3);

    MotorControllerGroup leftController = new MotorControllerGroup(left1,left2,left3);
    MotorControllerGroup rightController = new MotorControllerGroup(right1,right2,right3);


    new DriveSubsystem(leftController, rightController);
    DriveSubsystem.getInstance().config(Constants.GET_DRIVE_CONFIG());
    DriveSubsystem.getInstance().setDefaultCommand(new DriveCommand(m_xboxController));

    Compressor compressor = new Compressor(Constants.CAN_ID.PNEUMATICS_HUB_ID, PneumaticsModuleType.REVPH);
    compressor.enableAnalog(90,120);

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    JoystickButton xButton = new JoystickButton(m_xboxController,3);
    xButton.whenPressed(new LatchCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
