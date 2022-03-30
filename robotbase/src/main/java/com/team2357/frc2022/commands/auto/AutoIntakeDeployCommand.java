package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.commands.intake.IntakeDeployCommand;
import com.team2357.lib.commands.CommandLoggerBase;

public class AutoIntakeDeployCommand extends CommandLoggerBase{
    private IntakeDeployCommand deployCommand;

    public AutoIntakeDeployCommand() {
        deployCommand = new IntakeDeployCommand();
    }

    @Override
    public void initialize() {
        deployCommand.schedule();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        deployCommand.cancel();
    }
}
