package com.team2357.frc2022.commands.feeder;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class FeederExtraAdvanceCommand extends CommandLoggerBase {
    private long m_endMillis = 0;

    public FeederExtraAdvanceCommand() {
        addRequirements(FeederSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        m_endMillis = System.currentTimeMillis() + Constants.FEEDER.EXTRA_ADVANCE_MILLIS;
    }

    @Override
    public void execute() {
        FeederSubsystem.getInstance().advance();
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() > m_endMillis;
    }

    @Override
    public void end(boolean interrupted) {
        FeederSubsystem.getInstance().stop();
        m_endMillis = 0;
    }
}
