package com.team2357.frc2022.commands.auto;

import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.frc2022.subsystems.SensorSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class AutoIntakeCargoCommand extends CommandLoggerBase{


    public AutoIntakeCargoCommand() {
        addRequirements(IntakeRollerSubsystem.getInstance(), IntakeArmSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        IntakeArmSubsystem.getInstance().deploy();
        IntakeRollerSubsystem.getInstance().collect();
    }

    @Override
    public boolean isFinished() {
        return SensorSubsystem.getInstance().isCargoInIntake();
    }

    @Override
    public void end(boolean interrupted) {
        IntakeRollerSubsystem.getInstance().stop();
    }
}
