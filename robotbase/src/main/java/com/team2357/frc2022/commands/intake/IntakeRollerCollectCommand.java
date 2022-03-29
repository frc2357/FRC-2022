package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class IntakeRollerCollectCommand extends CommandLoggerBase {
    public IntakeRollerCollectCommand() {
        addRequirements(IntakeRollerSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeRollerSubsystem.getInstance().collect();
    }

    @Override
    public void end(boolean interrupted) {
        IntakeRollerSubsystem.getInstance().stop();
    }
}
