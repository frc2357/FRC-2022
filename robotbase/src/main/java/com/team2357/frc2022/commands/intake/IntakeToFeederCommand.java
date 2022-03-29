package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class IntakeToFeederCommand extends CommandLoggerBase {
    public IntakeToFeederCommand() {
        addRequirements(IntakeRollerSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeRollerSubsystem.getInstance().advance();
    }
    
    @Override
    public boolean isFinished() {
        return !SensorSubsystem.getInstance().isCargoInIndex();
    }

    @Override
    public void end(boolean interrupted) {
        IntakeRollerSubsystem.getInstance().stop();
    }
}
