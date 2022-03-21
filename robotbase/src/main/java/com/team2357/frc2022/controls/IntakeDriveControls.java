package com.team2357.frc2022.controls;

import com.team2357.frc2022.commands.intake.IntakeDeployCommand;
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
    public JoystickButton m_aButton;

    /**
     * @param builder The IntakeDriverControlsBuilder object
     */
    public IntakeDriveControls(XboxController controller, double deadband) {
        super(controller, deadband);

        m_leftTrigger = new AxisThresholdTrigger(super.m_controller, Axis.kLeftTrigger, .1);
        m_aButton = new JoystickButton(controller, XboxRaw.A.value);

        mapControls();
    }

    private void mapControls() {
        m_aButton.whenPressed(new InvertDriveCommand(this));
        m_leftTrigger.whileActiveOnce(new IntakeDeployCommand(2));
    }
}
