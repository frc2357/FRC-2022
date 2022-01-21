package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

//TODO: Make Noah write this one
/**
 * Moves the intake by calling changeArmPosition on the {@link IntakeSubsystem}.
 * 
 * @category Intake
 */
public class IntakeTogglePivotCommand extends CommandLoggerBase {

    /**
     * @param intakeSubsystem The {@link IntakeSubsystem}.
     */
    public IntakeTogglePivotCommand() {

    }
    
    @Override 
    public void initialize() {
        
    }

    @Override
    public boolean isFinished() {
        return true;
    } 
}