package com.team2357.frc2022.commands.human;

import com.team2357.frc2022.commands.ShooterSetRPMsCommand;
import com.team2357.frc2022.commands.feeder.FeederShootCommand;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class FireLowHubCommand extends CommandLoggerBase {
    private ShooterSetRPMsCommand shootCommand;
    private FeederShootCommand feederCommand;

    @Override
    public void initialize() {
        shootCommand = new ShooterSetRPMsCommand(ShooterSubsystem.getInstance(), 1500, 3000);
        feederCommand = new FeederShootCommand();
        shootCommand.schedule();
        feederCommand.schedule();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        shootCommand.cancel();
        feederCommand.cancel();
    }
}
