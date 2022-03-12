package com.team2357.frc2022.commands.intake;

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
    private IntakeArmSubsystem m_intakeArmSub;
    private IntakeRollerSubsystem m_intakeRollerSub;
    private SensorSubsystem m_sensorSub;
    private int m_startingAcquireCount;
    private int m_acquireCount;

    /**
     * Deploys and runs the intake
     * 
     * @param intakeArmSub The {@link IntakeArmSubsystem}.
     * @param intakeRollerSub The {@link IntakeRollerSubsystem}.
     * @param sensorSub The {@link SensorSubsystem}.
     * @param acquireCount The number of cargo to acquire before stowing again, or 0 to stay deployed.
     */
    public IntakeDeployCommand(IntakeArmSubsystem intakeArmSub, IntakeRollerSubsystem intakeRollerSub, SensorSubsystem sensorSub, int acquireCount) {
        m_intakeArmSub = intakeArmSub;
        m_intakeRollerSub = intakeRollerSub;
        m_sensorSub = sensorSub;
        m_acquireCount = acquireCount;
        addRequirements(m_intakeArmSub);
        addRequirements(m_intakeRollerSub);
    }

    @Override 
    public void initialize() {
        m_intakeArmSub.deploy();
        m_intakeRollerSub.start();
        m_startingAcquireCount = m_sensorSub.getCargoAcquired();
    }

    @Override
    public boolean isFinished() {
        if (m_acquireCount > 0) {
            int acquired = m_sensorSub.getCargoAcquired() - m_startingAcquireCount;
            if (acquired >= m_acquireCount) {
                // We've acquired the right amount of cargo.
                return true;
            }
        }
        return false;
    } 

    @Override
    public void end(boolean interrupted) {
        m_intakeArmSub.stow();
        m_intakeRollerSub.stop();
    }
}