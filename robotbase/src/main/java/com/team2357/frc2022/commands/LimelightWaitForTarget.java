package com.team2357.frc2022.commands;

import com.team2357.lib.commands.CommandLoggerBase;
import com.team2357.lib.subsystems.LimelightSubsystem;

public class LimelightWaitForTarget extends CommandLoggerBase {

    @Override
    public boolean isFinished() {
        return LimelightSubsystem.getInstance().validTargetExists();
    }
}
