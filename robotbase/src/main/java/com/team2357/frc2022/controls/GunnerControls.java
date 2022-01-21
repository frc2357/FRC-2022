package com.team2357.frc2022.controls;

import com.team2357.frc2022.commands.IntakeRollerCommand;
import com.team2357.frc2022.commands.IntakeTogglePivotCommand;
import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.ControllerAxis;
import com.team2357.lib.util.XboxRaw;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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

    public AxisThresholdTrigger m_leftTrigger;

    public Trigger m_xButton;
    public Trigger m_xButtonAndLeftDPad;

    public POVButton m_leftDPad;

    /**
     * @param builder The GunnerControlsBuilder object
     */
    public GunnerControls(GunnerControlsBuilder builder) {
        m_controller = builder.m_controller;

        //Triggers
        m_leftTrigger = new AxisThresholdTrigger(builder.m_controller, Axis.kLeftTrigger, .1);

        //Buttons
        m_xButton = new JoystickButton(builder.m_controller, XboxRaw.X.value);

        //Dpad
        m_leftDPad = new POVButton(builder.m_controller, 270);

        //Chords
        m_xButtonAndLeftDPad = m_xButton.and(m_leftDPad);

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

        public GunnerControls build() {
            GunnerControls m_gunnerControls = new GunnerControls(this);

            // Intake Mode Bindings
            if (m_intakeSub != null) {
                if (m_intakeSub.getPivot() == Value.kForward) {
                    m_gunnerControls.m_leftTrigger.whileActiveOnce(
                            new IntakeRollerCommand(m_intakeSub,
                                    m_gunnerControls.getControllerAxisValue(Axis.kLeftTrigger)));
                }

                m_gunnerControls.m_xButtonAndLeftDPad.whileActiveOnce(new IntakeTogglePivotCommand());
            }

            return m_gunnerControls;
        }
    }
}
