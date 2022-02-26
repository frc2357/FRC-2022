package com.team2357.frc2022.commands;

import com.team2357.frc2022.subsystems.ClimberSubsystem;
import com.team2357.lib.commands.CommandLoggerBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Command to set the pivot of the pivoting arms
 * 
 * @category Climber
 */
public class ClimberTogglePivotCommand extends CommandLoggerBase {
    private ClimberSubsystem m_climbSub;

    public ClimberTogglePivotCommand(ClimberSubsystem climbSub){
        m_climbSub = climbSub;
        addRequirements(m_climbSub);
    }

    @Override
    public void initialize(){
        m_climbSub.setPivot(m_climbSub.getPivot() == DoubleSolenoid.Value.kReverse ? DoubleSolenoid.Value.kForward: DoubleSolenoid.Value.kReverse);
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
