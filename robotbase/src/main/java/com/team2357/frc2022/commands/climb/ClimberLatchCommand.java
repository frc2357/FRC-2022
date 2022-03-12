package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberLatchCommand extends CommandLoggerBase{
        private ClimberSubsystem m_climbSub;
        private boolean m_openHook;
    
        public ClimberLatchCommand(ClimberSubsystem climbSub, boolean openHook) {
            m_climbSub = climbSub;
            m_openHook = openHook;
        }
    
        @Override
        public void initialize() {
            m_climbSub.setHookPivot(m_openHook);
        }
    
        @Override
        public boolean isFinished() {
            return true;
        }
}
