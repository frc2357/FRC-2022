package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class AutoStartIntakeCommand extends CommandLoggerBase {

    public AutoStartIntakeCommand() {
        addRequirements(IntakeArmSubsystem.getInstance());
        addRequirements(IntakeRollerSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeArmSubsystem intakeArm = IntakeArmSubsystem.getInstance();
        IntakeRollerSubsystem intakeRoller = IntakeRollerSubsystem.getInstance();

        intakeArm.deploy();
        intakeRoller.collect();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
