package com.team2357.frc2022.commands.feeder;

import com.team2357.frc2022.subsystems.FeederSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.frc2022.subsystems.ShooterSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class FeederToShooterCommand extends CommandLoggerBase{

    public FeederToShooterCommand() {
        addRequirements(FeederSubsystem.getInstance());
    }

    @Override
    public void execute() {

        boolean hasCargo = (SensorSubsystem.getInstance().isCargoInIndex() || SensorSubsystem.getInstance().isCargoInFeeder());
        if (hasCargo && ShooterSubsystem.getInstance().atTargetSpeed()) {
            FeederSubsystem.getInstance().shoot();
        } else {
            FeederSubsystem.getInstance().stop();
        }
    }

    @Override
    public void end(boolean interrupted) {
        FeederSubsystem.getInstance().stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
