package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberSetLatchCommand extends CommandLoggerBase{
        private boolean m_openHook;
    
        public ClimberSetLatchCommand(boolean openHook) {
            m_openHook = openHook;
            addRequirements(ClimberSubsystem.getInstance());
        }
    
        @Override
        public void initialize() {
            ClimberSubsystem.getInstance().setLatch(m_openHook);
        }
    
        @Override
        public boolean isFinished() {
            return true;
        }
}
