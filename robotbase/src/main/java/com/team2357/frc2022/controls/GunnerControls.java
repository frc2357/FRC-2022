package com.team2357.frc2022.controls;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.commands.human.ClimbProgressionCommand;
import com.team2357.frc2022.commands.human.FireVisionCommand;
import com.team2357.frc2022.commands.human.LockAndPrimeCommandGroup;
import com.team2357.frc2022.commands.CargoAdjustCommand;
import com.team2357.frc2022.commands.feeder.FeederShootCommand;
import com.team2357.frc2022.commands.human.FireLowHubCommandGroup;
import com.team2357.frc2022.commands.human.FireTaxiLineCommandGroup;
import com.team2357.frc2022.commands.human.TargetLockCommand;
import com.team2357.frc2022.commands.human.TurretAxisCommand;
import com.team2357.frc2022.commands.human.panic.ClimberArmsCommand;
import com.team2357.frc2022.commands.human.panic.ClimberLatchCommand;
import com.team2357.frc2022.commands.human.panic.ClimberWinchAxisCommand;
import com.team2357.frc2022.commands.human.panic.ClimberWinchResetCommand;
import com.team2357.frc2022.commands.human.panic.FeederRollerAxisCommand;
import com.team2357.frc2022.commands.human.panic.IntakeArmsCommand;
import com.team2357.frc2022.commands.human.panic.IntakeRollerAxisCommand;
import com.team2357.frc2022.commands.human.panic.ShooterRollerAxisCommand;
import com.team2357.frc2022.commands.human.panic.TurretResetCommand;
import com.team2357.frc2022.commands.intake.IntakeDeployCommand;
import com.team2357.frc2022.commands.shooter.ShooterSetRPMsCommand;
import com.team2357.frc2022.commands.shooter.ShooterWaitForRPMsCommand;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.triggers.AxisThresholdTrigger;
import com.team2357.lib.util.Utility;
import com.team2357.lib.util.XboxRaw;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
    public AxisThresholdTrigger m_primeRightTrigger;
    public AxisThresholdTrigger m_feedShooterRightTrigger;

    // Buttons
    public JoystickButton m_leftStickButton;
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
        m_primeRightTrigger = new AxisThresholdTrigger(controller, Axis.kRightTrigger, .1);
        m_feedShooterRightTrigger = new AxisThresholdTrigger(controller, Axis.kRightTrigger, .6);
        m_leftTrigger = new AxisThresholdTrigger(controller, Axis.kLeftTrigger, .1);

        // Buttons
        m_leftStickButton = new JoystickButton(controller, XboxRaw.StickPressLeft.value);
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

        mapControls();
    }

    public double getLeftXAxis() {
        double value = m_controller.getLeftX();
        return Utility.deadband(value, Constants.CONTROLLER.GUNNER_CONTROLLER_DEADBAND);
    }

    public double getRightYAxis() {
        double value = m_controller.getRightY();
        return Utility.deadband(value, Constants.CONTROLLER.GUNNER_CONTROLLER_DEADBAND);
    }

    private void mapControls() {
        AxisInterface axisLeftStickX = () -> {
            return getLeftXAxis();
        };

        AxisInterface axisRightStickY = () -> {
            return getRightYAxis();
        };

        Trigger noDPad = new Trigger(() -> {
            return !m_upDPad.get() && !m_downDPad.get() && !m_leftDPad.get() && !m_rightDPad.get();
        });

        Trigger noLetterButtons = new Trigger(() -> {
            return !m_aButton.get() && !m_bButton.get() && !m_xButton.get() && !m_yButton.get();
        });

        Trigger upDPadOnly = m_upDPad.and(noLetterButtons);
        Trigger downDPadOnly = m_downDPad.and(noLetterButtons);
        Trigger leftDPadOnly = m_leftDPad.and(noLetterButtons);
        Trigger rightDPadOnly = m_rightDPad.and(noLetterButtons);

        Trigger upDPadAndX = m_upDPad.and(m_xButton);
        Trigger upDPadAndY = m_upDPad.and(m_yButton);
        Trigger upDPadAndB = m_upDPad.and(m_bButton);

        Trigger downDPadAndA = m_downDPad.and(m_aButton);

        Trigger aButton = m_aButton.and(noDPad);
        Trigger bButton = m_bButton.and(noDPad);
        Trigger yButton = m_yButton.and(noDPad);
        Trigger xButton = m_xButton.and(noDPad);

        // Left stick is "always on" for turret movement.
        TurretSubsystem.getInstance().setDefaultCommand(new TurretAxisCommand(axisLeftStickX));
        m_leftStickButton.whenActive(new TurretResetCommand());

        aButton.whileActiveOnce(new IntakeDeployCommand());
        //bButton.toggleWhenActive(new LockAndPrimeCommandGroup());
        bButton.toggleWhenActive(new TargetLockCommand());
        yButton.toggleWhenActive(new ClimbProgressionCommand());
        m_primeRightTrigger.whileActiveOnce(new FireVisionCommand());

        m_feedShooterRightTrigger.whileActiveOnce(
            new SequentialCommandGroup(
                new ShooterWaitForRPMsCommand(),
                new FeederShootCommand()
            )
        );

        xButton.whenActive(new CargoAdjustCommand());
        m_leftTrigger.whileActiveOnce(new FireLowHubCommandGroup());
        m_leftBumper.whileActiveContinuous(new FireTaxiLineCommandGroup());

        downDPadOnly.whileActiveOnce(new IntakeRollerAxisCommand(axisRightStickY));
        downDPadAndA.whenActive(new IntakeArmsCommand());

        rightDPadOnly.whileActiveOnce(new ShooterRollerAxisCommand(axisRightStickY));

        leftDPadOnly.whileActiveOnce(new FeederRollerAxisCommand(axisRightStickY));

        upDPadOnly.whileActiveOnce(new ClimberWinchAxisCommand(axisRightStickY));
        upDPadAndX.whenActive(new ClimberLatchCommand());
        upDPadAndY.whenActive(new ClimberArmsCommand());
        upDPadAndB.whenActive(new ClimberWinchResetCommand());
    }
}
