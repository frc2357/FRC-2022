package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.IntakeSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;

//TODO: Make Colsen write this one
/**
 * Moves the intake by calling setPivot on the {@link IntakeSubsystem}.
 * 
 * @category Intake
 */
public class IntakeSetPivotCommand extends CommandLoggerBase {

    /**
     * @param intakeSubsystem The {@link IntakeSubsystem}.
     */
    public IntakeSetPivotCommand() {
       
    }

    @Override 
    public void initialize() {
      
    }

    @Override
    public boolean isFinished() {
        return true;
    } 
}