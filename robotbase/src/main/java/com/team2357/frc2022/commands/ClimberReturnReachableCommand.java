package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberReturnReachableCommand extends CommandLoggerBase {
    private ClimberSubsystem m_climberSub;


    public ClimberReturnReachableCommand(ClimberSubsystem climberSub) {
        m_climberSub = climberSub;
    }

    @Override
    public void initialize() {
        m_climberSub.returnReachable();
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
