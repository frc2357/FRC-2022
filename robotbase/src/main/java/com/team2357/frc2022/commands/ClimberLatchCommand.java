package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberLatchCommand extends CommandLoggerBase{
        private boolean m_openHook;
    
        public ClimberLatchCommand(boolean openHook) {
            m_openHook = openHook;
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
