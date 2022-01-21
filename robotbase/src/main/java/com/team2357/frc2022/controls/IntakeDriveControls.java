package com.team2357.frc2022.controls;

import com.team2357.frc2022.commands.IntakeRollerCommand;
import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.controllers.InvertDriveControls;
import com.team2357.lib.subsystems.TogglableLimelightSubsystem;
import com.team2357.lib.subsystems.drive.SingleSpeedFalconDriveSubsystem;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.ControllerAxis;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.XboxController.Axis;

/**
 * These are the controls for the IntakeDriver.
 * 
 * @category Drive
 */
public class IntakeDriveControls extends InvertDriveControls{
    XboxController m_controller;

    public AxisThresholdTrigger m_leftTrigger;

    /**
     * @param builder The IntakeDriverControlsBuilder object
     */
    public IntakeDriveControls(IntakeDriveControlsBuilder builder) {
      super(builder.m_invertDriveBuilder);

        //Triggers
        m_leftTrigger = new AxisThresholdTrigger(builder.m_controller, Axis.kLeftTrigger, .1);

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
     * Class for building IntakeDriverControls
     */
    public static class IntakeDriveControlsBuilder {
        private XboxController m_controller = null;
        private IntakeSubsystem m_intakeSub = null;
        private InvertDriveControlsBuilder m_invertDriveBuilder = null;

        /**
         * @param controller the controller of the IntakeDriver controls
         */
        public IntakeDriveControlsBuilder(XboxController controller, double deadband) {
            m_controller = controller;
            m_invertDriveBuilder = new InvertDriveControlsBuilder(controller, deadband);
        }

        public IntakeDriveControlsBuilder withIntakeSub(IntakeSubsystem intakeSub) {
            m_intakeSub = intakeSub;
            return this;
        }

        public IntakeDriveControlsBuilder withDriveSub(SingleSpeedFalconDriveSubsystem driveSubsystem){
            m_invertDriveBuilder.withDriveSub(driveSubsystem);
            return this;
        }

        public IntakeDriveControlsBuilder withVisionSub(TogglableLimelightSubsystem visionSubsystem) {
            m_invertDriveBuilder.withVisionSub(visionSubsystem);
            return this;
        }

        public IntakeDriveControls build() {
            m_invertDriveBuilder.build();
            IntakeDriveControls m_IntakeDriverControls = new IntakeDriveControls(this);

            // Intake Mode Bindings
            if (m_intakeSub != null) {
                if (m_intakeSub.getPivot() == Value.kForward) {
                    m_IntakeDriverControls.m_leftTrigger.whileActiveOnce(
                            new IntakeRollerCommand(m_intakeSub,
                                    m_IntakeDriverControls.getControllerAxisValue(Axis.kLeftTrigger)));
                }
            }

            return m_IntakeDriverControls;
        }
    }
}
