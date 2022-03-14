package com.team2357.frc2022.commands.human;

import com.team2357.lib.commands.CommandLoggerBase;

public class ClimbProgressionCommand extends CommandLoggerBase {
    @Override
    public void initialize() {
        System.out.println("ClimbProgressionCommand");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
