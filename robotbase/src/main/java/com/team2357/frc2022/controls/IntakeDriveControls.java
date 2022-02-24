package com.team2357.frc2022.controls;

import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.controllers.InvertDriveControls;
import com.team2357.lib.subsystems.TogglableLimelightSubsystem;
import com.team2357.lib.subsystems.drive.SingleSpeedFalconDriveSubsystem;
import com.team2357.lib.util.ControllerAxis;

import java.util.function.BooleanSupplier;

import com.team2357.frc2022.commands.IntakeSequenceCommandGroup;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * These are the controls for the IntakeDriver.
 * 
 * @category Drive
 */
public class IntakeDriveControls extends InvertDriveControls{
    XboxController m_controller;

    public Button m_leftTrigger;

    /**
     * @param builder The IntakeDriverControlsBuilder object
     */
    public IntakeDriveControls(IntakeDriveControlsBuilder builder) {
      super(builder.m_invertDriveBuilder);

        //Triggers
        BooleanSupplier condition = () -> {return builder.m_controller.getLeftTriggerAxis()>.1;};
        m_leftTrigger = new Button(condition);

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
        private IntakeSubsystem m_intakeSub = null;
        private InvertDriveControlsBuilder m_invertDriveBuilder = null;

        /**
         * @param controller the controller of the IntakeDriver controls
         */
        public IntakeDriveControlsBuilder(XboxController controller, double deadband) {
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
            IntakeDriveControls m_IntakeDriverControls = new IntakeDriveControls(this);

            // Intake Mode Bindings
            if (m_intakeSub != null) {
                    m_IntakeDriverControls.m_leftTrigger.whenPressed(
                        new IntakeSequenceCommandGroup(m_intakeSub), false);
            }

            return m_IntakeDriverControls;
        }
    }
}
