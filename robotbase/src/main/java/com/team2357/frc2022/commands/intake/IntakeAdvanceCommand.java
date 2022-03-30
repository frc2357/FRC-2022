package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class IntakeAdvanceCommand extends CommandLoggerBase {
    private boolean m_finishWhenCargoInIndex;

    public IntakeAdvanceCommand() {
        this(true);
    }

    public IntakeAdvanceCommand(boolean finishWhenCargoInIndex) {
        m_finishWhenCargoInIndex = finishWhenCargoInIndex;
        addRequirements(IntakeRollerSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeRollerSubsystem.getInstance().advance();
    }
    
    @Override
    public boolean isFinished() {
        if (m_finishWhenCargoInIndex) {
            return SensorSubsystem.getInstance().isCargoInIndex();
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        IntakeRollerSubsystem.getInstance().stop();
    }
}
