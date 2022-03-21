package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class IntakeAdvanceCommand extends CommandLoggerBase {
    public IntakeAdvanceCommand() {
        addRequirements(IntakeRollerSubsystem.getInstance());
    }
    
    @Override
    public void execute() {
        SensorSubsystem sensors = SensorSubsystem.getInstance();
        boolean inFeeder = sensors.isCargoInFeeder();
        boolean inIndex = sensors.isCargoInIndex();

        if (inIndex && !inFeeder) {
            IntakeRollerSubsystem.getInstance().advance();
        } else {
            IntakeRollerSubsystem.getInstance().stop();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        IntakeRollerSubsystem.getInstance().stop();
    }
}
