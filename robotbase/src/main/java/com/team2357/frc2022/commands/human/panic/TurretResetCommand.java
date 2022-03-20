package com.team2357.frc2022.commands.human.panic;

import com.team2357.frc2022.subsystems.TurretSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class TurretResetCommand extends CommandLoggerBase {
    public TurretResetCommand() {
        addRequirements(TurretSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        TurretSubsystem.getInstance().stop();
        TurretSubsystem.getInstance().resetEncoder();
        System.out.println("---------- RESETTING TURRET ENCODER ----------");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
