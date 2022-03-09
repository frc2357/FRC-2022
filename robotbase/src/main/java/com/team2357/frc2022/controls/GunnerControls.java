package com.team2357.frc2022.controls;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.commands.IntakeRollerCommand;
import com.team2357.frc2022.commands.IntakeTogglePivotCommand;
import com.team2357.frc2022.commands.ShooterSetRPMsCommand;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.frc2022.commands.KickerSetSpeedCommand;
import com.team2357.frc2022.commands.ShootAgainstHubCommand;
import com.team2357.frc2022.commands.TurretRotateCommand;
import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.frc2022.subsystems.KickerSubsystem;
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
    public Trigger m_aButtonAndDownDPad;
    public Trigger m_xButtonAndDownDPad;

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
        m_aButtonAndDownDPad = m_aButton.and(m_downDPad);
        m_xButtonAndDownDPad = m_aButton.and(m_downDPad);
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
        private ShooterSubsystem m_shooterSub = null;
        private KickerSubsystem m_kickerSub = null;
        private FeederSubsystem m_feederSub = null;
        private TurretSubsystem m_turretSub = null;

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

        public GunnerControlsBuilder withShooterSub(ShooterSubsystem shooterSub) {
            m_shooterSub = shooterSub;
            return this;
        }

        public GunnerControlsBuilder withKickerSub(KickerSubsystem kickerSub) {
            m_kickerSub = kickerSub;
            return this;
        }

        public GunnerControlsBuilder withFeederSub(FeederSubsystem feederSub) {
            m_feederSub = feederSub;
            return this;
        }

        public GunnerControlsBuilder withTurretSub(TurretSubsystem turretSub) {
            m_turretSub = turretSub;
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

            // TODO: Finish shooter controls
            if (m_shooterSub != null) {
                m_gunnerControls.m_rightTrigger.whileActiveOnce(
                        new ShooterSetRPMsCommand(m_shooterSub, 2250, 2750));
            }

            // Turret bindings
            if (m_turretSub != null) {
                m_gunnerControls.m_leftBumper.whileActiveOnce(
                        new TurretRotateCommand(m_turretSub, -Constants.TURRET.MANUAL_TURRET_ROTATE_SPEED));
                m_gunnerControls.m_rightBumper.whileActiveOnce(
                        new TurretRotateCommand(m_turretSub, Constants.TURRET.MANUAL_TURRET_ROTATE_SPEED));
            }

            if (m_kickerSub != null) {
                m_gunnerControls.m_aButtonAndDownDPad.whileActiveOnce(
                        new KickerSetSpeedCommand(m_kickerSub, Constants.KICKER.SPEED));
            }

            if (m_kickerSub != null && m_feederSub != null && m_shooterSub != null) {
                m_gunnerControls.m_xButtonAndDownDPad.whileActiveOnce(
                    new ShootAgainstHubCommand(m_kickerSub, m_feederSub, m_shooterSub));
            }

            return m_gunnerControls;
        }
    }
}
