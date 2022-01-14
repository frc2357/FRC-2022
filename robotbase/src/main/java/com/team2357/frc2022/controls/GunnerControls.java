package com.team2357.frc2022.controls;

import com.team2357.frc2022.commands.IntakeRollerCommand;
import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.ControllerAxis;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;

public class GunnerControls {
    XboxController m_controller;

    public AxisThresholdTrigger m_leftTrigger;

    /**
     * @param builder The GunnerControlsBuilder object
     */
    public GunnerControls(GunnerControlsBuilder builder) {
        m_controller = builder.m_controller;
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
                m_gunnerControls.m_leftTrigger.whileActiveOnce(
                        new IntakeRollerCommand(m_intakeSub,
                                m_gunnerControls.getControllerAxisValue(Axis.kLeftTrigger)));
            }

            return m_gunnerControls;
        }
    }
}
