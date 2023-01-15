package com.team2357.frc2022.controls;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2357.lib.commands.InvertDriveCommand;
import com.team2357.lib.controllers.InvertDriveControls;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.XboxRaw;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * These are the controls for the IntakeDriver.
 * 
 * @category Drive
 */
public class IntakeDriveControls extends InvertDriveControls {
    public AxisThresholdTrigger m_leftTrigger;
    public JoystickButton m_aButton;
    public JoystickButton m_leftbumper;

    /**
     * @param builder The IntakeDriverControlsBuilder object
     */
    public IntakeDriveControls(XboxController controller, double deadband) {
        super(controller, deadband);

        m_leftTrigger = new AxisThresholdTrigger(super.m_controller, Axis.kLeftTrigger, .1);
        m_aButton = new JoystickButton(controller, XboxRaw.A.value);
        m_leftbumper = new JoystickButton(controller, XboxRaw.BumperLeft.value);

        mapControls();
    }

    private void mapControls() {
        CANSparkMax rightintake = new CANSparkMax(24, MotorType.kBrushless);
        CANSparkMax leftintake = new CANSparkMax(22, MotorType.kBrushless);
        double rightIntakeSpeed, leftIntakeSpeed;

        rightintake.setIdleMode(IdleMode.kCoast);
        leftintake.setIdleMode(IdleMode.kCoast);

        rightintake.setOpenLoopRampRate(2);
        leftintake.setOpenLoopRampRate(2);

        rightIntakeSpeed = 1.0;
        leftIntakeSpeed = .5;
        m_leftbumper.onTrue(new InstantCommand(() -> {
            rightintake.set(rightIntakeSpeed);
            leftintake.set(leftIntakeSpeed);
        }));
        m_leftbumper.onFalse(new InstantCommand(() -> {
            rightintake.set(0);
            leftintake.set(0);
        }));
    }
}
