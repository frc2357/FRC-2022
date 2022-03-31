package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class IntakeDefaultCommand extends CommandLoggerBase {
    public IntakeDefaultCommand() {
        addRequirements(IntakeRollerSubsystem.getInstance());
    }

    @Override
    public void execute() {
        if (SensorSubsystem.getInstance().isCargoInIndex() && !SensorSubsystem.getInstance().isCargoInFeeder()) {
            IntakeRollerSubsystem.getInstance().advance();
        } else {
            IntakeRollerSubsystem.getInstance().stop();
        }
    }

    @Override
    public void end(boolean interrupted) {
        IntakeRollerSubsystem.getInstance().stop();
    }
}
