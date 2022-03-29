package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class IntakeArmStowCommand extends CommandLoggerBase {
    public IntakeArmStowCommand() {
        addRequirements(IntakeArmSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeArmSubsystem.getInstance().stow();
    }
}
