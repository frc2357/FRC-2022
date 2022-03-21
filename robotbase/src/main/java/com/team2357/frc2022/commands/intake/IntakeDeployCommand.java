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
    private int m_startingAcquireCount;
    private int m_acquireCount;


    /**
     * Deploys and runs the intake
     * 
     * @param acquireCount The number of cargo to acquire before stowing again, or 0 to stay deployed.
     */
    public IntakeDeployCommand(int acquireCount) {
        m_acquireCount = acquireCount;
        addRequirements(IntakeArmSubsystem.getInstance());
        addRequirements(IntakeRollerSubsystem.getInstance());
    }

    @Override 
    public void initialize() {
        IntakeArmSubsystem intakeArm = IntakeArmSubsystem.getInstance();
        IntakeRollerSubsystem intakeRoller = IntakeRollerSubsystem.getInstance();
        SensorSubsystem sensors = SensorSubsystem.getInstance();

        intakeArm.deploy();
        intakeRoller.collect();
        m_startingAcquireCount = sensors.getCargoAcquired();
    }

    @Override
    public boolean isFinished() {
        if (m_acquireCount > 0) {
            int acquired = SensorSubsystem.getInstance().getCargoAcquired() - m_startingAcquireCount;
            if (acquired >= m_acquireCount) {
                // We've acquired the right amount of cargo.
                return true;
            }
        }
        return false;
    } 

    @Override
    public void end(boolean interrupted) {
        if (SensorSubsystem.getInstance().isCargoInFeeder()) {
            new FeederPackCommand().schedule();
        }

        IntakeArmSubsystem.getInstance().stow();
        IntakeRollerSubsystem.getInstance().stop();
    }
}