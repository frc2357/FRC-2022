package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Command to toggle dynamic hooks open and close
 * 
 * @category Climber
 */
public class ClimberToggleLatchCommand extends CommandLoggerBase {

    public ClimberToggleLatchCommand() {
    }

    @Override
    public void initialize() {
        ClimberSubsystem.getInstance()
                .setLatch(ClimberSubsystem.getInstance().isHookOpen() ? false
                        : true);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
