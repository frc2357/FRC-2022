package com.team2357.frc2022.controls;

import com.team2357.frc2022.commands.human.FireLowHubCommandGroup;
import com.team2357.frc2022.commands.human.FireVisionCommand;
import com.team2357.frc2022.commands.intake.IntakeDeployCommand;
import com.team2357.frc2022.commands.shooter.ShootOverBotCommandGroup;
import com.team2357.lib.commands.InvertDriveCommand;
import com.team2357.lib.controllers.InvertDriveControls;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.XboxRaw;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * These are the controls for the IntakeDriver.
 * 
 * @category Drive
 */
public class IntakeDriveControls extends InvertDriveControls {
    public AxisThresholdTrigger m_leftTrigger;
    public AxisThresholdTrigger m_rightTrigger;

    public JoystickButton m_rightBumper;
    public JoystickButton m_leftBumper;
    public JoystickButton m_aButton;

    /**
     * @param builder The IntakeDriverControlsBuilder object
     */
    public IntakeDriveControls(XboxController controller, double deadband) {
        super(controller, deadband);

        m_leftTrigger = new AxisThresholdTrigger(controller, Axis.kLeftTrigger, .1);
        m_rightTrigger = new AxisThresholdTrigger(controller, Axis.kRightTrigger, .1);
        m_rightBumper = new JoystickButton(controller, XboxRaw.BumperRight.value);
        m_leftBumper = new JoystickButton(controller, XboxRaw.BumperLeft.value);
        m_aButton = new JoystickButton(controller, XboxRaw.A.value);

        mapControls();
    }

    private void mapControls() {
        m_aButton.whenPressed(new InvertDriveCommand(this));

        m_rightBumper.whileActiveOnce(new FireLowHubCommandGroup());
        m_leftBumper.whileActiveOnce(new ShootOverBotCommandGroup());

        m_rightTrigger.whileActiveOnce(new FireVisionCommand());
        m_leftTrigger.whileActiveOnce(new IntakeDeployCommand());
    }
}
