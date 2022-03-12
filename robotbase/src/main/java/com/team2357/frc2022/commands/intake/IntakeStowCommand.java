package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Stows the intake arm.
 * 
 * @category Intake
 */
public class IntakeStowCommand extends CommandLoggerBase {
    private IntakeArmSubsystem m_intakeArmSub;
    private IntakeRollerSubsystem m_intakeRollerSub;

    /**
     * Stows and stops the intake
     * 
     * @param intakeArmSub The {@link IntakeArmSubsystem}.
     * @param intakeRollerSub The {@link IntakeRollerSubsystem}.
     */
    public IntakeStowCommand(IntakeArmSubsystem intakeArmSub, IntakeRollerSubsystem intakeRollerSub) {
        m_intakeArmSub = intakeArmSub;
        m_intakeRollerSub = intakeRollerSub;
        addRequirements(m_intakeArmSub);
        addRequirements(m_intakeRollerSub);
    }

    @Override 
    public void initialize() {
        m_intakeArmSub.stow();
        m_intakeRollerSub.stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    } 
}
