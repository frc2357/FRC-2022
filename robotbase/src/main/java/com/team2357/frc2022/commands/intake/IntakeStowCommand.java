package com.team2357.frc2022.commands.intake;

import com.team2357.frc2022.subsystems.IntakeArmSubsystem;
import com.team2357.frc2022.subsystems.IntakeRollerSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

/**
 * Stows the intake arm.
 * 
 * @category Intake
 */
public class IntakeStowCommand extends CommandLoggerBase {
    /**
     * Stows and stops the intake
     * 
     */
    public IntakeStowCommand() {
        addRequirements(IntakeArmSubsystem.getInstance());
        addRequirements(IntakeRollerSubsystem.getInstance());
    }

    @Override 
    public void initialize() {
        IntakeArmSubsystem.getInstance().stow();
        IntakeRollerSubsystem.getInstance().stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    } 
}
