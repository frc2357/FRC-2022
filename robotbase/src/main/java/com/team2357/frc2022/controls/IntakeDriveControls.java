package com.team2357.frc2022.controls;

import com.team2357.frc2022.commands.human.IntakeDeployToggleCommand;
import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.controllers.InvertDriveControls;
import com.team2357.lib.subsystems.TogglableLimelightSubsystem;
import com.team2357.lib.subsystems.drive.SingleSpeedFalconDriveSubsystem;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.ControllerAxis;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;

/**
 * These are the controls for the IntakeDriver.
 * 
 * @category Drive
 */
public class IntakeDriveControls extends InvertDriveControls {
    public AxisThresholdTrigger m_leftTrigger;

    /**
     * @param builder The IntakeDriverControlsBuilder object
     */
    public IntakeDriveControls(IntakeDriveControlsBuilder builder) {
        super(builder.m_invertDriveBuilder);

        // Triggers
        m_leftTrigger = new AxisThresholdTrigger(super.m_controller, Axis.kLeftTrigger, .1);

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
                    return super.m_controller.getLeftX();
                case kLeftY:
                    return super.m_controller.getLeftY();
                case kLeftTrigger:
                    return super.m_controller.getLeftTriggerAxis();
                case kRightTrigger:
                    return super.m_controller.getRightTriggerAxis();
                case kRightX:
                    return super.m_controller.getRightX();
                case kRightY:
                    return super.m_controller.getRightY();
            }
            return 0;
        };
        return controllerAxis.getAxisValue();
    }

    /**
     * Class for building IntakeDriverControls
     */
    public static class IntakeDriveControlsBuilder {
        private IntakeArmSubsystem m_intakeArmSub = null;
        private IntakeRollerSubsystem m_intakeRollerSub = null;
        private SensorSubsystem m_sensorSub = null;
        private InvertDriveControlsBuilder m_invertDriveBuilder = null;

        /**
         * @param controller the controller of the IntakeDriver controls
         */
        public IntakeDriveControlsBuilder(XboxController controller, double deadband) {
            m_invertDriveBuilder = new InvertDriveControlsBuilder(controller, deadband);
        }

        public IntakeDriveControlsBuilder withSensorSub(SensorSubsystem sensorSub) {
            m_sensorSub = sensorSub;
            return this;
        }

        public IntakeDriveControlsBuilder withIntakeSubs(IntakeArmSubsystem intakeArmSub, IntakeRollerSubsystem intakeRollerSub) {
            m_intakeArmSub = intakeArmSub;
            m_intakeRollerSub = intakeRollerSub;
            return this;
        }

        public IntakeDriveControlsBuilder withDriveSub(SingleSpeedFalconDriveSubsystem driveSubsystem) {
            m_invertDriveBuilder.withDriveSub(driveSubsystem);
            return this;
        }

        public IntakeDriveControlsBuilder withVisionSub(TogglableLimelightSubsystem visionSubsystem) {
            m_invertDriveBuilder.withVisionSub(visionSubsystem);
            return this;
        }

        public IntakeDriveControls build() {
            IntakeDriveControls m_IntakeDriverControls = new IntakeDriveControls(this);

            // Intake Mode Bindings
            if (m_intakeArmSub != null && m_intakeRollerSub != null && m_sensorSub != null) {
                m_IntakeDriverControls.m_leftTrigger.toggleWhenActive(
                    new IntakeDeployToggleCommand(m_intakeArmSub, m_intakeRollerSub, m_sensorSub)
                );
            } else {
                System.out.println("DRIVER: No Intake bindings, required subsystems not present");
            }

            return m_IntakeDriverControls;
        }
    }
}
