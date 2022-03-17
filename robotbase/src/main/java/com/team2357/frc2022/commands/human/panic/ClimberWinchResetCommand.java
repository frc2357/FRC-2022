package com.team2357.frc2022.commands.human.panic;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberWinchResetCommand extends CommandLoggerBase {
    public ClimberWinchResetCommand() {
        addRequirements(ClimberSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        ClimberSubsystem.getInstance().stopClimberMotors();
        ClimberSubsystem.getInstance().resetEncoders();
        System.out.println("---------- RESETTING CLIMBER ENCODERS ----------");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
