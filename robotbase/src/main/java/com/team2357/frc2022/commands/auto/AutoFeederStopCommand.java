package com.team2357.frc2022.commands.auto;

public class AutoFeederStopCommand {
    public AutoFeederStartCommand() {
        addRequirements(FeederSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        FeederSubsystem.getInstance().shoot();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
