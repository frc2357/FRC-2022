package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class FeederSetSpeedCommand extends CommandLoggerBase{
    private FeederSubsystem m_feederSub;
    private double m_speed;

    public FeederSetSpeedCommand(FeederSubsystem feederSub, double speed) {
        m_feederSub = feederSub;
        m_speed = speed;
        addRequirements(m_feederSub);
    }

    @Override
    public void initialize(){
        super.initialize();
        if (m_feederSub.isCargoAtFeederWheel()) {
            m_feederSub.runFeedermotor(m_speed);
        }
    }

    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);
        m_feederSub.runFeedermotor(0.0);
    }
}
