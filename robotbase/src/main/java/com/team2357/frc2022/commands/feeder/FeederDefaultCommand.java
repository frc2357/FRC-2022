package com.team2357.frc2022.commands.feeder;

import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj2.command.Command;

public class FeederDefaultCommand extends CommandLoggerBase {
    private Command m_extraAdvance;
    private boolean m_needToAdvance;

    public FeederDefaultCommand() {
        addRequirements(FeederSubsystem.getInstance());

        m_extraAdvance = new FeederExtraAdvanceCommand();
    }

    @Override
    public void initialize() {
        m_needToAdvance = false;
    }

    @Override
    public void execute() {
        if (SensorSubsystem.getInstance().isCargoInFeeder()) {
            FeederSubsystem.getInstance().stop();

            if (m_needToAdvance) {
                m_needToAdvance = false;
                m_extraAdvance.schedule();
            }
            return;
        }

        if (m_needToAdvance || SensorSubsystem.getInstance().isCargoInIndex()) {
            m_needToAdvance = true;
            FeederSubsystem.getInstance().advance();
        } else {
            FeederSubsystem.getInstance().stop();
        }
    }

    @Override
    public void end(boolean interrupted) {
        FeederSubsystem.getInstance().stop();
    }
}
