package com.team2357.frc2022.commands.human.panic;

import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class IntakeArmsCommand extends CommandLoggerBase {
    public IntakeArmsCommand() {
        addRequirements(IntakeArmSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeArmSubsystem intakeArms = IntakeArmSubsystem.getInstance();
        if (intakeArms.isStowed() || intakeArms.isStowing()) {
            intakeArms.deploy();
        } else {
            intakeArms.stow();
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
