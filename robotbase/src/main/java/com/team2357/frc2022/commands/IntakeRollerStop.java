package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class IntakeRollerStop extends CommandLoggerBase {
    private IntakeSubsystem m_intakeSub;

    /**
     * @param intakeSub The {@link IntakeSubsystem}.
     */
    public IntakeRollerStop(IntakeSubsystem intakeSub) {
        m_intakeSub = intakeSub;
        addRequirements(m_intakeSub);
    }

    @Override
    public void initialize() {
        super.initialize();
            m_intakeSub.triggerIntakeRoller(0.0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
