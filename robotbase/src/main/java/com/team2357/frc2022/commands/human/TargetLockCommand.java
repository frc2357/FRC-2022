package com.team2357.frc2022.commands.human;

import com.team2357.lib.commands.CommandLoggerBase;

public class TargetLockCommand extends CommandLoggerBase {
    @Override
    public void initialize() {
        System.out.println("TargetLockCommand");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
