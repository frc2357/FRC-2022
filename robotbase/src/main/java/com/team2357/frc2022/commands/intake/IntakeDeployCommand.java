package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.commands.feeder.FeederPackCommand;
import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Deploys the intake arm.
 * 
 * @category Intake
 */
public class IntakeDeployCommand extends CommandLoggerBase {
    private boolean m_fillRobot;
    private boolean m_hasACargo;

    public IntakeDeployCommand() {
        this(true);
    }

    public IntakeDeployCommand(boolean fillRobot) {
        m_fillRobot = fillRobot;

        addRequirements(IntakeArmSubsystem.getInstance());
        addRequirements(IntakeRollerSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeArmSubsystem intakeArm = IntakeArmSubsystem.getInstance();
        IntakeRollerSubsystem intakeRoller = IntakeRollerSubsystem.getInstance();

        intakeArm.deploy();
        intakeRoller.collect();

        m_hasACargo = SensorSubsystem.getInstance().isCargoInFeeder();
    }

    @Override
    public boolean isFinished() {
        SensorSubsystem sensors = SensorSubsystem.getInstance();

        if (m_fillRobot) {
            return sensors.isRobotFilled();
        } else {
            if (m_hasACargo) {
                return sensors.isRobotFilled();
            } else {
                return SensorSubsystem.getInstance().isCargoInFeeder();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (SensorSubsystem.getInstance().isRobotFilled()) {
            new FeederPackCommand().schedule();
        }

        IntakeArmSubsystem.getInstance().stow();
        IntakeRollerSubsystem.getInstance().stop();
    }
}