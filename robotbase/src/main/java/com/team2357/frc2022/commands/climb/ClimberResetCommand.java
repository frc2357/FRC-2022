package com.team2357.frc2022.commands.climb;

import com.team2357.frc2022.Constants;
import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

public class ClimberResetCommand extends CommandLoggerBase{
    private static int CLIMB_SETTLE_MILLIS = 1000;

    private long totalTime;

   @Override
    public void initialize() {
        ClimberSubsystem.getInstance().setClimberSpeed(Constants.CLIMBER.RETRACT_SLOW);
        totalTime = System.currentTimeMillis() + CLIMB_SETTLE_MILLIS;
    }

    @Override
    public boolean isFinished() {
        return totalTime < System.currentTimeMillis();
    }

    @Override
    public void end(boolean interrupted) {
        ClimberSubsystem.getInstance().stopClimberMotors();;
        ClimberSubsystem.getInstance().resetEncoders();
    }
}
