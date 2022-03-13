package com.team2357.frc2022.controls;

import com.team2357.frc2022.commands.human.ClimbProgressionCommand;
import com.team2357.frc2022.commands.human.FireCommand;
import com.team2357.frc2022.commands.human.IntakeDeployToggleCommand;
import com.team2357.frc2022.commands.human.TargetLockCommand;
import com.team2357.frc2022.commands.human.TurretAxisCommand;
import com.team2357.frc2022.commands.human.panic.ClimberArmsCommand;
import com.team2357.frc2022.commands.human.panic.ClimberLatchCommand;
import com.team2357.frc2022.commands.human.panic.ClimberWinchAxisCommand;
import com.team2357.frc2022.commands.human.panic.FeederRollerAxisCommand;
import com.team2357.frc2022.commands.human.panic.IntakeRollerAxisCommand;
import com.team2357.frc2022.commands.human.panic.ShooterRollerAxisCommand;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.XboxRaw;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * These are the controls for the gunner.
 * 
 * @category Drive
 */
public class GunnerControls {
    XboxController m_controller;

    // Triggers
    public AxisThresholdTrigger m_leftTrigger;
    public AxisThresholdTrigger m_rightTrigger;

    // Buttons
    public JoystickButton m_backButton;
    public JoystickButton m_startButton;
    public JoystickButton m_leftBumper;
    public JoystickButton m_rightBumper;
    public Trigger m_aButton;
    public Trigger m_bButton;
    public Trigger m_xButton;
    public Trigger m_yButton;

    // Dpad
    public POVButton m_upDPad;
    public POVButton m_rightDPad;
    public POVButton m_downDPad;
    public POVButton m_leftDPad;

    // Chords
    public Trigger m_upDPadAndXButton;
    public Trigger m_upDPadAndYButton;
    public Trigger m_downDPadAndAButton;

    /**
     * @param builder The GunnerControlsBuilder object
     */
    public GunnerControls(XboxController controller) {
        m_controller = controller;

        // Triggers
        m_rightTrigger = new AxisThresholdTrigger(controller, Axis.kRightTrigger, .1);
        m_leftTrigger = new AxisThresholdTrigger(controller, Axis.kLeftTrigger, .1);

        // Buttons
        m_backButton = new JoystickButton(controller, XboxRaw.Back.value);
        m_startButton = new JoystickButton(controller, XboxRaw.Start.value);
        m_leftBumper = new JoystickButton(controller, XboxRaw.BumperLeft.value);
        m_rightBumper = new JoystickButton(controller, XboxRaw.BumperRight.value);
        m_aButton = new JoystickButton(controller, XboxRaw.A.value);
        m_bButton = new JoystickButton(controller, XboxRaw.B.value);
        m_xButton = new JoystickButton(controller, XboxRaw.X.value);
        m_yButton = new JoystickButton(controller, XboxRaw.Y.value);

        // Dpad
        m_upDPad = new POVButton(controller, 0);
        m_rightDPad = new POVButton(controller, 90);
        m_downDPad = new POVButton(controller, 180);
        m_leftDPad = new POVButton(controller, 270);

        // Chords
        m_upDPadAndXButton = m_leftDPad.and(m_xButton);
        m_upDPadAndYButton = m_leftDPad.and(m_yButton);
        m_downDPadAndAButton = m_downDPad.and(m_aButton);

        mapControls();
    }

    private void mapControls() {
        AxisInterface axisLeftStickX = () -> {
            return m_controller.getLeftX();
        };

        AxisInterface axisRightStickY = () -> {
            return m_controller.getRightY();
        };

        new TurretAxisCommand(axisLeftStickX);

        m_aButton.toggleWhenActive(new IntakeDeployToggleCommand());
        m_bButton.toggleWhenActive(new TargetLockCommand());
        m_yButton.toggleWhenActive(new ClimbProgressionCommand());
        m_rightTrigger.whenActive(new FireCommand());

        m_downDPad.whileActiveOnce(new IntakeRollerAxisCommand(axisRightStickY));
        m_rightDPad.whileActiveOnce(new ShooterRollerAxisCommand(axisRightStickY));
        m_leftDPad.whileActiveOnce(new FeederRollerAxisCommand(axisRightStickY));
        m_upDPad.whileActiveOnce(new ClimberWinchAxisCommand(axisRightStickY));

        m_upDPadAndXButton.whenActive(new ClimberLatchCommand());
        m_upDPadAndYButton.whenActive(new ClimberArmsCommand());
    }
}
