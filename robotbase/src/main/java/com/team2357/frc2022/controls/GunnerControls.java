package com.team2357.frc2022.controls;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.commands.ClimberHookTogglePivotCommand;
import com.team2357.frc2022.commands.ClimberSimpleRunMotorsCommand;
import com.team2357.frc2022.commands.ClimberTogglePivotCommand;
import com.team2357.frc2022.commands.IntakeRollerCommand;
import com.team2357.frc2022.commands.IntakeTogglePivotCommand;
import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.ControllerAxis;
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
    public Trigger m_xButtonAndLeftDPad;
    public Trigger m_yButtonAndLeftDPad;

    public Trigger m_bButtonAndRightDPad;
    public Trigger m_yButtonAndRightDPad;
    public Trigger m_aButtonAndRightDPad;
    public Trigger m_xButtonAndRightDPad;

    /**
     * @param builder The GunnerControlsBuilder object
     */
    public GunnerControls(GunnerControlsBuilder builder) {
        m_controller = builder.m_controller;

        // Triggers
        m_rightTrigger = new AxisThresholdTrigger(builder.m_controller, Axis.kRightTrigger, .1);
        m_leftTrigger = new AxisThresholdTrigger(builder.m_controller, Axis.kLeftTrigger, .1);

        // Buttons
        m_backButton = new JoystickButton(builder.m_controller, XboxRaw.Back.value);
        m_startButton = new JoystickButton(builder.m_controller, XboxRaw.Start.value);
        m_leftBumper = new JoystickButton(builder.m_controller, XboxRaw.BumperLeft.value);
        m_rightBumper = new JoystickButton(builder.m_controller, XboxRaw.BumperRight.value);
        m_aButton = new JoystickButton(builder.m_controller, XboxRaw.A.value);
        m_bButton = new JoystickButton(builder.m_controller, XboxRaw.B.value);
        m_xButton = new JoystickButton(builder.m_controller, XboxRaw.X.value);
        m_yButton = new JoystickButton(builder.m_controller, XboxRaw.Y.value);

        // Dpad
        m_upDPad = new POVButton(builder.m_controller, 0);
        m_rightDPad = new POVButton(builder.m_controller, 90);
        m_downDPad = new POVButton(builder.m_controller, 180);
        m_leftDPad = new POVButton(builder.m_controller, 270);

        // Chords
        m_xButtonAndLeftDPad = m_xButton.and(m_leftDPad);
        m_yButtonAndLeftDPad = m_yButton.and(m_leftDPad);
        m_bButtonAndRightDPad = m_bButton.and(m_rightDPad);
        m_yButtonAndRightDPad = m_yButton.and(m_rightDPad);
        m_aButtonAndRightDPad = m_aButton.and(m_rightDPad);
        m_xButtonAndRightDPad = m_xButton.and(m_rightDPad);
    }

    /**
     * Gets the current trigger value from the axis on the left or right.
     * 
     * @param axis Which axis you want to get a value from.
     * 
     * @return The trigger value from the left or right axis.
     */
    public double getControllerAxisValue(Axis axis) {

        ControllerAxis controllerAxis = () -> {
            switch (axis) {
                case kLeftX:
                    return m_controller.getLeftX();
                case kLeftY:
                    return m_controller.getLeftY();
                case kLeftTrigger:
                    return m_controller.getLeftTriggerAxis();
                case kRightTrigger:
                    return m_controller.getRightTriggerAxis();
                case kRightX:
                    return m_controller.getRightX();
                case kRightY:
                    return m_controller.getRightY();
            }
            return 0;
        };
        return controllerAxis.getAxisValue();
    }

    /**
     * Class for building GunnerControls
     */
    public static class GunnerControlsBuilder {
        private XboxController m_controller = null;
        private IntakeSubsystem m_intakeSub = null;
        private ClimberSubsystem m_climbSub = null;

        /**
         * @param controller the controller of the gunner controls
         */
        public GunnerControlsBuilder(XboxController controller) {
            m_controller = controller;
        }

        public GunnerControlsBuilder withIntakeSub(IntakeSubsystem intakeSub) {
            m_intakeSub = intakeSub;
            return this;
        }

        public GunnerControlsBuilder withClimbSub(ClimberSubsystem climbSub) {
            m_climbSub = climbSub;
            return this;
        }

        public GunnerControls build() {
            GunnerControls m_gunnerControls = new GunnerControls(this);

            // Intake Mode Bindings
            if (m_intakeSub != null) {
                m_gunnerControls.m_leftTrigger.whileActiveOnce(
                        new IntakeRollerCommand(m_intakeSub,
                                Constants.INTAKE.FORWARD_SPEED));

                m_gunnerControls.m_yButtonAndLeftDPad.whileActiveOnce(
                        new IntakeRollerCommand(m_intakeSub, Constants.INTAKE.REVERSE_SPEED));

                m_gunnerControls.m_xButtonAndLeftDPad.whileActiveOnce(new IntakeTogglePivotCommand(m_intakeSub));
            }

            if (m_climbSub != null) {
                m_gunnerControls.m_yButtonAndRightDPad.whileActiveOnce(
                        new ClimberSimpleRunMotorsCommand(m_climbSub, -0.4));
                m_gunnerControls.m_aButtonAndRightDPad.whileActiveOnce(
                        new ClimberSimpleRunMotorsCommand(m_climbSub, 0.2));
                m_gunnerControls.m_bButtonAndRightDPad.whileActiveOnce(new ClimberTogglePivotCommand(m_climbSub));
                m_gunnerControls.m_yButtonAndRightDPad.whileActiveOnce(new ClimberHookTogglePivotCommand(m_climbSub));
            }

            return m_gunnerControls;
        }
    }
}
