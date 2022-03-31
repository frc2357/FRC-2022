package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class AutoStopIntakeCommand extends CommandLoggerBase{
    public AutoStopIntakeCommand() {
        addRequirements(IntakeRollerSubsystem.getInstance(), IntakeArmSubsystem.getInstance());
    }

    @Override 
    public void initialize() {
        IntakeRollerSubsystem.getInstance().stop();
        IntakeArmSubsystem.getInstance().stow();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
